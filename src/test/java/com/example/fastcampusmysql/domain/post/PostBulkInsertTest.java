package com.example.fastcampusmysql.domain.post;

import com.example.fastcampusmysql.domain.post.entity.Post;
import com.example.fastcampusmysql.domain.post.repository.PostRepository;
import com.example.fastcampusmysql.util.PostFixtureFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.StopWatch;

import java.time.LocalDate;
import java.util.stream.IntStream;

@SpringBootTest
public class PostBulkInsertTest { // 스프링을 띄워서, 레포지토리에 직접 주입받아서 데이터를 삽입
    @Autowired
    private PostRepository postRepository;

    @Test
    public void bulkInsert() {
        var easyRandom = PostFixtureFactory.get(
                3L,
                LocalDate.of(1995, 1, 1),
                LocalDate.of(2023, 11, 6)
        );

        var stopWatch = new StopWatch();
        stopWatch.start();

        int _1만 = 10000;
        var posts = IntStream.range(0, _1만 * 100)// 백만건
                .parallel()
                .mapToObj(i -> easyRandom.nextObject(Post.class))
                .toList();

        stopWatch.stop();
        System.out.println("객체 생성 시간: "+stopWatch.getTotalTimeSeconds());

        var queryStopWatch = new StopWatch();
        queryStopWatch.start();

        postRepository.bulkInsert(posts);

        queryStopWatch.stop();
        System.out.println("DB INSEDRT 시간: "+queryStopWatch.getTotalTimeSeconds());

        /*
            IneliiJ에서 Ctrl+Shift+a에서 memory indicator를 ON을 하면 오른쪽 하단에 메모리가 나온다.
            그리고 다시 Ctrl+Shift+a에서 "Edit Custom VM Options로 메모리에 대한 설정도 가능하다.
            '
           WINDOW: netstat -ano | findstr :3306
           UNIX: lsof -i:3306

           MySQL의 CPU 사용량 확인방법
           PowerShell 에서 위의 명령어로 찾은 프로세스의 ID(PID)를 가지고,
           get-process -id 위의_결과_PID번호 로 친다.

         */
    }
}
