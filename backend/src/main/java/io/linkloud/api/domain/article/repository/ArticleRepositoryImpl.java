package io.linkloud.api.domain.article.repository;

import static io.linkloud.api.domain.article.model.QArticle.article;
import static io.linkloud.api.domain.member.model.QMember.member;
import static io.linkloud.api.domain.member.model.QMemberArticleStatus.memberArticleStatus;
import static io.linkloud.api.domain.tag.model.QArticleTag.articleTag;
import static io.linkloud.api.domain.tag.model.QTag.tag;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.ArticleListResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesByCondition;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.model.ArticleStatus;
import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.article.dto.MyArticlesResponseDto;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.global.utils.QueryDslUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Slf4j
@Repository
@RequiredArgsConstructor
public class ArticleRepositoryImpl implements ArticleRepositoryCustom {
    private final JPAQueryFactory query;
    private final QueryDslUtils queryDslUtils;

    @Override
    public Page<ArticleResponseDto> findArticleListBySearch(String keyword, List<String> tags,
        Pageable pageable) {
        // 정렬 기준 변환
//        OrderSpecifier[] orders = getAllOrderSpecifiers(pageable, posts);

        BooleanBuilder builder = new BooleanBuilder();

        // posts.title 조건 생성
        builder.and(article.title.containsIgnoreCase(keyword));

        createTagSubQuery(tags, builder);

        List<ArticleResponseDto> content = query.selectDistinct(Projections.constructor(ArticleResponseDto.class, article))
            .from(article)
            .leftJoin(article.member, member).fetchJoin()
            // 나중에 상태도 추가하기
            .where(builder, article.articleStatus.eq(ArticleStatus.ACTIVE))
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = query.select(article.count())
            .from(article)
            .where(builder, article.articleStatus.eq(ArticleStatus.ACTIVE));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Page<MyArticlesResponseDto> findMyArticleByTag(Member m, String t, String readStatus, Pageable pageable) {
        // 정렬 기준 변환
        OrderSpecifier[] orders = queryDslUtils.getAllOrderSpecifiers(pageable, article);

        BooleanBuilder builder = new BooleanBuilder();

        // posts.title 조건 생성

        // tag 조건 생성
        if (t != null) {
            JPQLQuery<Long> sub = JPAExpressions.select(articleTag.article.id)
                .distinct()
                .from(articleTag)
                .join(articleTag.article, article)
                .join(articleTag.tag, tag)
                .where(tag.name.eq(t));

            // tag 조건 생성
            builder.and(article.id.in(sub));
        }

        if (!readStatus.equals("")) {
            builder.and(article.readStatus.eq(ReadStatus.valueOf(readStatus.toUpperCase())));
        }

        List<MyArticlesResponseDto> content = query.selectDistinct(Projections.constructor(MyArticlesResponseDto.class, article))
            .from(article)
            .leftJoin(article.member, member).fetchJoin()
            .where(builder, article.member.eq(m), article.articleStatus.eq(ArticleStatus.ACTIVE))
            .orderBy(orders)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = query.select(article.count())
            .from(article)
            .where(builder, article.articleStatus.eq(ArticleStatus.ACTIVE));

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchOne);
    }

    @Override
    public Slice<ArticleListResponse> findArticlesWithNoOffset(Long lastArticleId, Pageable pageable,SortBy sortBy) {
        OrderSpecifier<?>[] orderSpecifier = createOrderSpecifier(sortBy);

        // fetchJoin 을 사용하여 한번에 쿼리문 날림
        List<Article> fetch = query.selectFrom(article)
            .leftJoin(article.member, member).fetchJoin()
            .leftJoin(article.articleTags, articleTag).fetchJoin()
            .leftJoin(articleTag.tag, tag).fetchJoin()
            .where(getWhereLastArticleIdLowerThan(lastArticleId))
            .where(article.articleStatus.eq(ArticleStatus.ACTIVE))
            .orderBy(orderSpecifier)
            .limit(pageable.getPageSize() + 1)
            .fetch();


        List<ArticleListResponse> content = fetch.stream()
            .map(ArticleListResponse::new)
            .collect(Collectors.toList());

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }



    // 게시글 keyword || tags 검색
    @Override
    public Slice<ArticleListResponse> findArticlesByKeywordOrTags(String keyword, List<String> tags, Pageable pageable) {

        BooleanBuilder builder = new BooleanBuilder();

        // posts.title 조건 생성
        if (keyword != null && !keyword.isEmpty()) {
            builder.and(article.title.containsIgnoreCase(keyword));
        }

        createTagSubQuery(tags, builder);

        List<ArticleListResponse> content = query.selectDistinct(Projections.constructor(ArticleListResponse.class, article))
            .from(article)
            .leftJoin(article.member, member).fetchJoin()
            // 나중에 상태도 추가하기
            .where(builder, article.articleStatus.eq(ArticleStatus.ACTIVE))
            .orderBy(article.id.desc())
            .limit(pageable.getPageSize()+1)
            .fetch();


        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<MemberArticlesByCondition> MemberArticlesByCondition(Long memberId, SortBy sortBy,
        ReadStatus readStatus, Long lastArticleId, Pageable pageable) {
        log.info("repository={}",readStatus);

        // SELECT a.*, mas.read_status
        // FROM article a
        // LEFT JOIN member_article_status mas ON a.id = mas.article_id AND mas.member_id = 1
        // WHERE a.member_id = 1 and a.status = 'ACTIVE'
        // TODO : N+1
//        JPAQuery<MemberArticlesByCondition> jpaQuery  = query
//            .select(Projections.constructor(MemberArticlesByCondition.class, article, memberArticleStatus.readStatus))
//            .from(article)
//            .leftJoin(memberArticleStatus)
//            .on(article.id.eq(memberArticleStatus.article.id)
//                .and(memberArticleStatus.member.id.eq(memberId))).fetchJoin()
//            .where(article.member.id.eq(memberId).and(article.articleStatus.eq(ArticleStatus.ACTIVE)));

        JPAQuery<MemberArticlesByCondition> jpaQuery = query
            .select(Projections.constructor(MemberArticlesByCondition.class, article,
                memberArticleStatus.readStatus))
            .from(article)
            .leftJoin(memberArticleStatus).on(article.id.eq(memberArticleStatus.article.id))
            .where(memberArticleStatus.member.id.eq(memberId)
                .and(article.articleStatus.eq(ArticleStatus.ACTIVE)));


        // readStatus 로 조회 시
        // WHERE mas.read_status = ? (UNREAD,READING,READ)
        if (readStatus != null) {
            log.info("readStatus 로 조회합니다");
            jpaQuery.where(memberArticleStatus.readStatus.eq(readStatus))
                .orderBy(article.createdAt.desc());
        }

        // sortBy 로 정렬 시
        if (sortBy != null) {
            log.info("sortBy 로 조회합니다");
            switch (sortBy) {
                case LATEST -> jpaQuery.orderBy(article.createdAt.desc());
                case TITLE -> jpaQuery.orderBy(article.title.asc());
            }
        }

        else {
            log.info("검색 조건이 없습니다 -> 날짜순으로 정렬합니다 ");
            jpaQuery.orderBy(article.createdAt.desc());
        }
        List<MemberArticlesByCondition> content = jpaQuery
            .limit(pageable.getPageSize() + 1)
            .fetch();

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }


    // tags 에 해당하는 tag 목록 검색
    private void createTagSubQuery(List<String> tags, BooleanBuilder builder) {
        if (tags != null && !tags.isEmpty()) {
            // tag 조건 생성
            JPQLQuery<Long> sub = JPAExpressions.select(articleTag.article.id)
                .distinct()
                .from(articleTag)
                .join(articleTag.article, article)
                .join(articleTag.tag, tag)
                .where(tag.name.in(tags))
                .groupBy(articleTag.article.id)
                .having(articleTag.article.id.count().goe(tags.size()));
            // tag 조건 생성
            builder.and(article.id.in(sub));
        }
    }

    // 더보기 누를 경우
    // 이전 페이지나 데이터를 제공해야되는데
    // 이전 데이터를 가져오기 위한 기준
    // 즉, 가장 최근에 작성된 게시글을 보여주려 가장 큰 ID 값을가진 게시글들을 가져와야 함
    // 스크롤을 내리면서 게시글을 계속 볼 때,
    // 스크롤 중간에 위치한 게시글의 ID 값을 기준으로 그보다 작은 ID 값을 가진 이전 게시글들을 보여줘야 함
    private BooleanExpression getWhereLastArticleIdLowerThan(Long lastArticleId) {

        if (lastArticleId != null) {
            // article.id 값이 lastArticleId 보다 작은지 비교
            // 즉 마지막으로 본 게시글 보다 ID 가 작은 게시글 불러오기
            return article.id.lt(lastArticleId);
        }
        return null;
    }

    // 게시글 동적 정렬
    private OrderSpecifier<?>[] createOrderSpecifier(SortBy sortBy) {
        List<OrderSpecifier<?>> orderSpecifiers = new ArrayList<>();

        switch (sortBy) {
            case HEARTS -> orderSpecifiers.add(article.hearts.desc());
            case TITLE -> orderSpecifiers.add(article.title.asc());
            default -> orderSpecifiers.add(article.createdAt.desc());
        }

        return orderSpecifiers.toArray(new OrderSpecifier[0]);
    }
}






