package com.example.fastcampusmysql.domain.util;

import java.util.List;

public record PageCusor<T> (
        CusorRequest nextCursorRequest,
        List<T> body
) {
}
