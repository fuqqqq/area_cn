package com.fuqqqq.common.area.cn.define;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.io.Serial;
import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class R<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = -7077902387212470482L;

    @Builder.Default
    private int code = HttpStatus.OK.value();
    @Builder.Default
    private String message = HttpStatus.OK.getReasonPhrase();
    @Builder.Default
    private T data = null;


    public static <T> R<T> success(T data) {
        return R.<T>builder()
                .data(data)
                .build();
    }

    public static R<Void> success() {
        return success(null);
    }

    public static R<Void> fial(int code, String reasonPhrase) {
        return R.<Void>builder()
                .code(code)
                .message(reasonPhrase)
                .build();
    }
}
