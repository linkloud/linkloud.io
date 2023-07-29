package io.linkloud.api.domain.tag.repository;

import static io.linkloud.api.domain.article.model.QArticle.article;
import static io.linkloud.api.domain.tag.model.QArticleTag.articleTag;
import static io.linkloud.api.domain.tag.model.QTag.tag;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.linkloud.api.domain.article.model.ArticleStatus;
import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.global.utils.QueryDslUtils;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepositoryCustom {
    private final JPAQueryFactory query;
    private final QueryDslUtils queryDslUtils;

    // 정렬 옵션을 기준으로 태그 및 연관 갯수 조회
    @Override
    public Page<TagDto.Response> findAllOrderBy(Pageable pageable) {
        // DSL Order 구분자 획득
        OrderSpecifier[] orders = queryDslUtils.getAllOrderSpecifiers(pageable, tag);

        // 데이터 조회를 위한 메인 쿼리
        // SELECT t.id, t.name, COUNT(at.tag_id) AS popularity
        // FROM tag AS t
        // LEFT JOIN article_tag AS at ON t.id = at.tag_id
        // INNER JOIN article AS a ON a.id = at.article_id
        // WHERE a.status = 'ACTIVE'
        // GROUP BY t.id, t.name
        // ORDER BY popularity DESC
        List<TagDto.Response> tags = query
                .select(Projections.constructor(TagDto.Response.class, tag.id, tag.name,
                        articleTag.tag.count().as("popularity")))
                .from(tag)
                .leftJoin(articleTag).on(tag.eq(articleTag.tag)).fetchJoin()
                .innerJoin(article).on(articleTag.article.eq(article))
                .where(article.articleStatus.eq(ArticleStatus.ACTIVE))  // 게시글이 ACTIVE 상태인 것만 조회
                .groupBy(tag.id, tag.name)
                .orderBy(orders)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        // pageable을 위한 countQuery
        JPAQuery<Long> countQuery = query
                .select(tag.count())
                .distinct()
                .from(tag);

        return PageableExecutionUtils.getPage(tags, pageable, countQuery::fetchOne);
    }

    @Override
    public List<TagDto.Response> findTagByNameIsStartingWith(String keyword) {

        // SELECT t.id, t.name, COUNT(at.tag_id) AS popularity
        // FROM tag AS t
        // LEFT JOIN article_tag AS at ON t.id = at.tag_id
        // INNER JOIN article AS a ON a.id = at.article_id
        // WHERE a.status = 'ACTIVE'
        // WHERE t.name LIKE '{keyword}%'
        // GROUP BY t.id, t.name
        // ORDER BY popularity DESC, t.name
        // LIMIT 5;
        List<TagDto.Response> tags = query
                .select(Projections.constructor(TagDto.Response.class, tag.id, tag.name,
                        articleTag.tag.count().as("popularity")))
                .from(tag)
                .leftJoin(articleTag).on(tag.eq(articleTag.tag)).fetchJoin()
                .innerJoin(article).on(articleTag.article.eq(article))
                .where(article.articleStatus.eq(ArticleStatus.ACTIVE)) // 게시글이 ACTIVE 상태인 것만 조회
                .where(tag.name.startsWith(keyword))
                .groupBy(tag.id, tag.name)
                .orderBy(articleTag.tag.count().desc(), tag.name.asc())
                .limit(5)
                .fetch();

        return tags;
    }
}
