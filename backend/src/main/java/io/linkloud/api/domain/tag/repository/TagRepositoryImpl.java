package io.linkloud.api.domain.tag.repository;

import static io.linkloud.api.domain.tag.model.QArticleTag.articleTag;
import static io.linkloud.api.domain.tag.model.QTag.tag;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.global.Utils.QueryDslUtils;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class TagRepositoryImpl implements TagRepositoryCustom {
    private final JPAQueryFactory query;

    @Override
    public Boolean existsByName(String name) {
        return true;
    }

    @Override
    public Optional<Tag> findTagByName(String name) {
        return Optional.empty();
    }

    @Override
    public Page<TagDto.Response> findAllOrderBy(Pageable pageable) {
        OrderSpecifier[] orders = getAllOrderSpecifiers(pageable, tag);

        List<TagDto.Response> tags = query
            .select(Projections.constructor(TagDto.Response.class, tag.id, tag.name,
                articleTag.tag.count().as("popularity")))
            .from(tag)
            .innerJoin(articleTag).on(tag.eq(articleTag.tag)).fetchJoin()
            .groupBy(tag.id, tag.name)
            .orderBy(orders)
            .fetch();

        JPAQuery<Long> countQuery = query
            .select(tag.count())
            .distinct()
            .from(tag);

        return PageableExecutionUtils.getPage(tags, pageable, countQuery::fetchOne);
    }

    @Override
    public List<Tag> findTagByNameIsStartingWith(String name) {
        return null;
    }

    private OrderSpecifier[] getAllOrderSpecifiers(Pageable pageable, Path path) {
        List<OrderSpecifier> orders = QueryDslUtils.getAllOrderSpecifiers(pageable, path);
        return orders.toArray(OrderSpecifier[]::new);
    }
}
