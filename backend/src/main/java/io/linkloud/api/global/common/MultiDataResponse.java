package io.linkloud.api.global.common;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Page;


@Getter
public class MultiDataResponse<T> {
    private List<T> data;
    private PageInfo pageInfo;

    public MultiDataResponse(Page page) {
        this.data = page.getContent();
        this.pageInfo = new PageInfo(page.getNumber() + 1,
            page.getSize(), page.getTotalElements(), page.getTotalPages());
    }
}
