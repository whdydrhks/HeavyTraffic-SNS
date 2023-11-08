package com.example.fastcampusmysql.domain.post.service;

import com.example.fastcampusmysql.domain.post.dto.DailyPostCount;
import com.example.fastcampusmysql.domain.post.dto.DailyPostCountRequest;
import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.domain.util.CusorRequest;
import com.example.fastcampusmysql.domain.util.PageCusor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
public class PostReadService { // 일자 별 게시물 횟수 반환
    final private PostRepository postRepository;
    public List<DailyPostCount> getDailyPostCount(DailyPostCountRequest request) {
        /*
            반환 값 -> 리스트 [작성일자, 작성회원, 작성 게시물 갯수]
            select createdDate, memberId, count(id)
            from Post
            where memberId = :memberId and createdDate between firstDate and lastDate
            group by createdDate memberId
         */
        return postRepository.groupByCreateDate(request);
    }

    // 회원의 게시물 반환 - 페이징 사용
    public Page<Post> getPosts(Long memberId, Pageable pageRequest) {
        /*
            Input
                - memberId: 3
                - pageable: {
                    "page": 0,
                    "size": 5,
                    "sort": [
                    "createdDate,ASC" 또는 "id,DESC"
                    ]
                }
         */
        return postRepository.findAllByMemberId(memberId, pageRequest);
    }

    public PageCusor<Post> getPosts(Long memberId, CusorRequest cusorRequest) {

    }
}
