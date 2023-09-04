package io.linkloud.api.global.converter;

import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.tag.model.Tag.SortBy;
import org.springframework.core.convert.converter.Converter;

public class StringToArticleStatusByConverter implements Converter<String, ReadStatus> {

    @Override
    public ReadStatus convert(String source) {
        return ReadStatus.valueOf(source.toUpperCase());
    }
}
