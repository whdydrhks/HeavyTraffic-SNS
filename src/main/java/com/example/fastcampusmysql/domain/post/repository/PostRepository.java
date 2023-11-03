package com.example.fastcampusmysql.domain.post.repository;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Repository;

import java.util.List;

@RequiredArgsConstructor
@Repository
public class PostRepository {
    static final String TABLE = "Post";
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public List<DailyPostCount> groupByCreateDate(DailyPostCountRequest request) {
        var sql = """
                    SELECT createdDate, memberId, count(id)
                    FROM %s
                    WHERE memberId = :memberId and createdDate between :firstDate and lastDate
                    GROUP BY memberId, createdDate
                  """;
        var params = new BeanPropertySqlParameterSource(request);
        return namedParameterJdbcTemplate.query(sql, params,  )
    }
    public Post save(Post post) {
        if(post.getId() == null)
            return insert(post);
        throw new UnsupportedOperationException("Post는 갱신을 지원하지 않습니다.");
    }

    private Post insert(Post post) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(namedParameterJdbcTemplate.getJdbcTemplate())
                .withTableName(TABLE)
                .usingGeneratedKeyColumns("id");

        SqlParameterSource params = new BeanPropertySqlParameterSource(post);
        var id = jdbcInsert.executeAndReturnKey(params).longValue();

        return Post.builder()
                .id(id)
                .memberId(post.getMemberId())
                .contents(post.getContents())
                .createdDate(post.getCreatedDate())
                .createdAt(post.getCreatedAt())
                .build();
    }
}
