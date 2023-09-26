package io.linkloud.api.global.common;

import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import java.util.List;
import lombok.Getter;
import org.springframework.data.domain.Slice;

@Getter
public class SliceResponse<T extends HasId> {
    private final List<T> items;
    private final int currentPage;
    private final int size;
    private final boolean isLastPage; // 마지막 페이지가 아닐경우 false 반환, 즉 마지막 페이지가 아니다
    private final long itemsSize;
    private final Long nextItemId;
    public SliceResponse(Slice<T> sliceContent) {
        this.items = sliceContent.getContent();
        this.currentPage = sliceContent.getNumber() + 1;
        this.size = sliceContent.getSize();
        this.isLastPage = sliceContent.isLast();
        this.itemsSize = sliceContent.getNumberOfElements();
        if (!isLastPage && !items.isEmpty()) {
            T item = items.get(items.size() - 1);
            this.nextItemId = item.getId();
        } else {
            nextItemId = null;
        }
    }
}





