package io.linkloud.api.domain.article.model;

import lombok.Getter;


@Getter
public enum ReadStatus {
    UNREAD,
    READING,
    READ;
}