package io.linkloud.api.global.common;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class SingleDataResponse<T> {
    private T data;
}