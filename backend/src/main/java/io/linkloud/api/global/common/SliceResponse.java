package io.linkloud.api.global.common;

import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class SliceResponse<T> {
    private final List<T> items;
    private final int currentPage;
    private final int size;
    private final boolean isFirst;
    private final boolean isLast;
    public SliceResponse(Slice<T> sliceContent) {
        this.items = sliceContent.getContent();
        this.currentPage = sliceContent.getNumber() + 1;
        this.size = sliceContent.getSize();
        this.isFirst = sliceContent.isFirst();
        this.isLast = sliceContent.isLast();
    }
}