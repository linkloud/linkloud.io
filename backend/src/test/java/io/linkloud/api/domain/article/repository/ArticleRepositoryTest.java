package io.linkloud.api.domain.article.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.Article.SortBy;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
class ArticleRepositoryTest {

    @Autowired
    ArticleRepository articleRepository;


    @DisplayName("다음 페이지 유/무")
    @Test
    void articlesSliceRequest() throws Exception {

        Article.SortBy sortBy = SortBy.LATEST;
        //given
        for (long i = 0; i <= 30; i++) {
            articleRepository.save(
                Article.builder()
                    .id(i)
                    .description("desc" + i)
                    .title("title" + i)
                    .url("url" + i)
                    .build(
                    ));
        }

        // When: 첫번째 페이지 요청할 때 (총 30개의 데이터 중 29-20)
        Slice<ArticleResponseDto> firstPage = articleRepository.findArticlesWithNoOffset(null, PageRequest.of(0, 10),sortBy);

        // Then
        assertThat(firstPage.getSize()).isEqualTo(10); // 응답온 페이지의 개수
        assertTrue(firstPage.hasNext()); // 다음 페이지가 있어야 한다.
        assertFalse(firstPage.isLast()); // 마지막 페이지면 안된다.

        // When: 두번째 페이지 요청할 때 (총 30개의 데이터 중 19-10)
        Long getLastPageFromFirstPage = firstPage.getContent().get(9).getId();
        Slice<ArticleResponseDto> secondPage = articleRepository.findArticlesWithNoOffset(getLastPageFromFirstPage, PageRequest.of(1, 10),sortBy);
        assertTrue(secondPage.hasNext()); // 다음 페이지가 있어야 한다
        assertFalse(secondPage.isLast()); // 마지막 페이지면 안된다

        // When: 세번째 페이지 요청할 때 (총 30개의 데이터 중 9-0)
        Long getLastPageFromSecondPage = secondPage.getContent().get(9).getId();
        Slice<ArticleResponseDto> lastPage = articleRepository.findArticlesWithNoOffset(getLastPageFromSecondPage, PageRequest.of(2, 10),sortBy);

        // Then
        assertFalse(lastPage.isLast()); // 마지막 페이지어야 한다.

    }

    @DisplayName("요청한 페이지 개수와 응답 페이지 개수가 같아야 한다")
    @Test
    void save10000Articles() {
        Article.SortBy sortBy = SortBy.LATEST;

        List<Article> articles = new ArrayList<>();
        for (long i = 1; i <= 1000; i++) {
            Article article = Article.builder()
                .id( i)
                .title("title" + i)
                .url("url" + i)
                .description("description" + i)
                .views(0)
                .hearts(0)
                .build();
            articles.add(article);
        }
        articleRepository.saveAll(articles);


        long count = articleRepository.count();

        System.out.println("count = " + count);
        Slice<ArticleResponseDto> firstPage = articleRepository.findArticlesWithNoOffset(null, PageRequest.of(0, 1000),sortBy);

        // Then
        assertThat(firstPage.getSize()).isEqualTo(1000); // 응답온 페이지의 개수

    }

    @DisplayName("마지막 페이지 일시 lastPage ture 반환")
    @Test
    void lastPageRequest() throws Exception {

        Article.SortBy sortBy = SortBy.LATEST;

        List<Article> articles = new ArrayList<>();
        for (long i = 1; i <= 1000; i++) {
            Article article = Article.builder()
                .id( i)
                .title("title" + i)
                .url("url" + i)
                .description("description" + i)
                .views(0)
                .hearts(0)
                .build();
            articles.add(article);
        }
        articleRepository.saveAll(articles);

        Slice<ArticleResponseDto> lastPageRequest = articleRepository.findArticlesWithNoOffset(null,
            PageRequest.of(0, 1001), sortBy);

        assertTrue(lastPageRequest.isLast()); // 마지막 페이지어야 한다.
        assertFalse(lastPageRequest.hasNext()); // 다음 페이지가 없어야 한다

    }
}