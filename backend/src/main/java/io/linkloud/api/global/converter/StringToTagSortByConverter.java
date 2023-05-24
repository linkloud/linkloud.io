package io.linkloud.api.global.converter;

import io.linkloud.api.domain.tag.model.Tag.SortBy;
import org.springframework.core.convert.converter.Converter;

// request param으로 받은 String을 enum 타입으로 변환
public class StringToTagSortByConverter implements Converter<String, SortBy> {

    @Override
    public SortBy convert(String source) {
        // request param으로 받은 field(소문자)를 enum(대문자)로 변환
        return SortBy.valueOf(source.toUpperCase());
    }
}
