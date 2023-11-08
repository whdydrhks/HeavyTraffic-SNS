package com.example.fastcampusmysql.domain.util;

public record CusorRequest(Long key, Long size) {
    public static final Long NONE_KEY = -1L;

    // 클라이언트가 사용할 다음 키에 대한 정보
    public CusorRequest next(Long key) {
        return new CusorRequest(key, size);
    }
}
