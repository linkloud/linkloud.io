package io.linkloud.api.global.common;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;


@Getter
public class MultiDataResponse<T> {
    private final List<T> items;
    private final PageInfo pageInfo;

    public MultiDataResponse(Page<T> page) {
        this.items = page.getContent();
        this.pageInfo = new PageInfo(page.getNumber() + 1,
            page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
