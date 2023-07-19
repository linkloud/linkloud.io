package io.linkloud.api.global.converter;

import io.linkloud.api.domain.article.model.Article.SortBy;
import org.springframework.core.convert.converter.Converter;

public class StringToArticleSortByConverter implements Converter<String, SortBy> {

    @Override
    public SortBy convert(String source) {
        // request param으로 받은 field(소문자)를 enum(대문자)로 변환
        return SortBy.valueOf(source.toUpperCase());
    }
}
