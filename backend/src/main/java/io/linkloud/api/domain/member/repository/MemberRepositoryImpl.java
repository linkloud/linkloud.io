package io.linkloud.api.domain.member.repository;


import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.linkloud.api.domain.tag.dto.MemberTagsDto;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import io.linkloud.api.global.utils.QueryDslUtils;
import io.linkloud.api.domain.tag.model.QArticleTag;
import io.linkloud.api.domain.tag.model.QTag;
import io.linkloud.api.domain.article.model.QArticle;
import static io.linkloud.api.domain.tag.model.QTag.tag;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepositoryCustom {


    private final JPAQueryFactory queryFactory;
    private final QueryDslUtils queryDslUtils;

    @Override
    public Page<MemberTagsDto> findMyTagsByMemberId(Long memberId, Pageable pageable) {
        OrderSpecifier[] orders = queryDslUtils.getAllOrderSpecifiers(pageable, tag);

        QArticle article = QArticle.article;
        QTag tag = QTag.tag;
        QArticleTag articleTag = QArticleTag.articleTag;

        // SELECT DISTINCT t1.id, t1.name
        // FROM article a1
        // JOIN article_tag at1 ON a1.id = at1.article_id
        // JOIN tag t1 ON at1.tag_id = t1.id
        // WHERE member_id = ?;
        List<MemberTagsDto> tags = queryFactory
            .selectDistinct(Projections.constructor(MemberTagsDto.class, tag.id, tag.name))
            .from(article)
            .join(articleTag).on(article.id.eq(articleTag.article.id))
            .join(tag).on(articleTag.tag.id.eq(tag.id))
            .where(article.member.id.eq(memberId))
            .orderBy(orders)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        // 내 게시글 태그 개수1 (쿼리로 개수 받아옴)
//        Long count = queryFactory
//            .selectDistinct(tag.count())
//            .from(article)
//            .join(articleTag).on(article.id.eq(articleTag.article.id))
//            .join(tag).on(articleTag.tag.id.eq(tag.id))
//            .where(article.member.id.eq(memberId))
//            .fetchFirst();

        // 내 게시글 태그 개수2 (쿼리로 받아온다음 객체의 개수를 셈)
        JPAQuery<Long> countQuery = queryFactory
            .selectDistinct(tag.count())
            .from(article)
            .join(articleTag).on(article.id.eq(articleTag.article.id))
            .join(tag).on(articleTag.tag.id.eq(tag.id))
            .where(article.member.id.eq(memberId));

        return PageableExecutionUtils.getPage(tags, pageable, countQuery::fetchOne);
    }
}
