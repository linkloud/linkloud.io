package io.linkloud.api.global.exception.ExceptionCode;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public enum LogicExceptionCode implements ExceptionCode {
    BAD_REQUEST(400, "Bad Request"),
    TEMPORARY_ERROR(400, "Temporary ERROR"),
    MEMBER_NOT_FOUND(404, "Member Not Found"),
    MEMBER_NOT_MATCH(403, "Member Not Matched"),
    MEMBER_ALREADY_EXISTS(409, "Member Already Exist."),
    ARTICLE_NOT_FOUND(404, "Article not found"),
    BOOKMARK_NOT_FOUND(404, "Bookmark not found"),
    JSON_REQUEST_FAILED(401, "Json Request Failed.");


    @Getter
    private int status;

    @Getter
    private String message;
}
