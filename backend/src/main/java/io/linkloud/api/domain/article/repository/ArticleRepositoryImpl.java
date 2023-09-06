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
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesSortedResponse;
import io.linkloud.api.domain.article.dto.ArticleResponseDtoV2.MemberArticlesSortedResponse.MemberArticlesSortedByStatus;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.Article.SortBy;
import io.linkloud.api.domain.article.model.ArticleStatus;
import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.article.dto.MyArticlesResponseDto;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.MemberArticleStatus;
import io.linkloud.api.global.utils.QueryDslUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

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
    public Slice<ArticleResponseDto> findArticlesWithNoOffset(Long lastArticleId,
        Pageable pageable,SortBy sortBy) {

        OrderSpecifier<?>[] orderSpecifier = createOrderSpecifier(sortBy);

        // fetchJoin 을 사용하여 한번에 쿼리문 날림
        List<Article> fetch = query.selectFrom(article)
            .leftJoin(article.member, member).fetchJoin() // Member 엔터티 fetchJoin 적용하지 않음
            .leftJoin(article.articleTags, articleTag).fetchJoin() // ArticleTag 엔터티 fetchJoin 적용
            .leftJoin(articleTag.tag, tag).fetchJoin() // Tag 엔터티 fetchJoin 적용
            .where(getWhereLastArticleIdLowerThan(lastArticleId))
            .orderBy(orderSpecifier)
            .limit(pageable.getPageSize() + 1)
            .fetch();


        List<ArticleResponseDto> content = fetch.stream()
            .map(ArticleResponseDto::new)
            .collect(Collectors.toList());

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }
        return new SliceImpl<>(content, pageable, hasNext);
    }

    @Override
    public Slice<MemberArticlesSortedResponse> findArticlesByMemberSorted(Long memberId,Long lastArticleId, Pageable pageable,
        SortBy sortBy) {

        BooleanExpression whereClause = article.member.id.eq(memberId);
        if (lastArticleId != null) {
            whereClause = whereClause.and(article.id.lt(lastArticleId));
        }

        OrderSpecifier<?>[] orderSpecifiers = createOrderSpecifier(Objects.requireNonNullElse(sortBy, SortBy.LATEST));

        List<Article> fetch = query
            .selectFrom(article)
            .leftJoin(article.articleTags, articleTag).fetchJoin()  // ArticleTag를 즉시 로딩
            .where(whereClause)
            .orderBy(orderSpecifiers)
            .limit(pageable.getPageSize() + 1)
            .fetch();

        // TODO : 게시글 상태 Null 값 이므로 생성자 변경
//        List<ArticleResponseDto> content = fetch.stream()
//            .map(ArticleResponseDto::new)
//            .collect(Collectors.toList());

        List<MemberArticlesSortedResponse> content = fetch.stream()
            .map(MemberArticlesSortedResponse::new)
            .collect(Collectors.toList());

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);

    }


    @Override
    public Slice<MemberArticlesSortedByStatus> findArticlesByReadStatus(Long memberId, Long lastArticleId, Pageable pageable,
        ReadStatus readStatus) {
        BooleanExpression whereClause = memberArticleStatus.member.id.eq(memberId);
        if (lastArticleId != null) {
            whereClause = whereClause.and(memberArticleStatus.article.id.lt(lastArticleId));
        }
        if (readStatus != null) {
            whereClause = whereClause.and(memberArticleStatus.readStatus.eq(readStatus));
        }

        // TODO : N+1문제 DTO 로 해야함
        List<MemberArticleStatus> fetch = query
            .selectFrom(memberArticleStatus)
            .leftJoin(memberArticleStatus.article, article).fetchJoin()
            .leftJoin(memberArticleStatus.member, member).fetchJoin()  // 추가
            .leftJoin(article.articleTags, articleTag).fetchJoin() // ArticleTag 엔터티 fetchJoin 적용
            .where(whereClause)
            .orderBy(article.createdAt.desc())
            .limit(pageable.getPageSize() + 1)
            .fetch();


        List<MemberArticlesSortedByStatus> content = fetch.stream()
            .map(status -> new MemberArticlesSortedByStatus(status.getArticle(), status.getReadStatus()))
            .collect(Collectors.toList());

        boolean hasNext = false;
        if (content.size() > pageable.getPageSize()) {
            content.remove(pageable.getPageSize());
            hasNext = true;
        }

        return new SliceImpl<>(content, pageable, hasNext);
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






