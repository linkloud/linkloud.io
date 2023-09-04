package io.linkloud.api.domain.article.model;

import lombok.AllArgsConstructor;
import lombok.Getter;


@AllArgsConstructor
@Getter
public enum ReadStatus {
    UNREAD("unread"),
    READING("reading"),
    READ("read");

    private String status;


}
