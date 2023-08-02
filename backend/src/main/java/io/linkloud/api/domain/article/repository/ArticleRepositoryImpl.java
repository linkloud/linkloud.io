package io.linkloud.api.domain.article.repository;

import static io.linkloud.api.domain.article.model.QArticle.article;
import static io.linkloud.api.domain.member.model.QMember.member;
import static io.linkloud.api.domain.tag.model.QArticleTag.articleTag;
import static io.linkloud.api.domain.tag.model.QTag.tag;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.JPQLQuery;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.model.ArticleStatus;
import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.article.dto.MyArticlesResponseDto;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.global.utils.QueryDslUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
}
