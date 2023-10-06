package io.linkloud.api.domain.article.model;


import lombok.Getter;


@Getter
public enum ReadStatus {
    UNREAD("unread"),
    READING("reading"),
    READ("read");

    private String status;
    ReadStatus(String status) {
        this.status = status;
    }
    public static ReadStatus fromString(String value) {
        for (ReadStatus status : ReadStatus.values()) {
            if (status.status.equalsIgnoreCase(value)) {
                return status;
            }
        }
        return null;
    }
}
