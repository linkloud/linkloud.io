package io.linkloud.api.domain.article.service;

import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.MEMBER_NOT_MATCH;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.global.exception.CustomException;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ArticleServiceTest {

    @Mock
    MemberRepository memberRepository;
    @Mock
    ArticleRepository articleRepository;
    @InjectMocks
    ArticleService articleService;
    Member fisrtMockMember = mock(Member.class);
    Member secondMockMember = mock(Member.class);
    private final Article article = Article.builder()
        .id(fisrtMockMember.getId())
        .member(fisrtMockMember)
        .title("title")
        .url("url")
        .description("desc")
        .views(1)
        .bookmarks(1)
        .build();

    ArticleRequestDto articleRequestDto = new ArticleRequestDto("title","url","desc");

    @Test
    @DisplayName("게시글 생성 성공")
    public void addArticleSuccess() {

        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3); // 3일 전 생성된 가입일로 설정

        // firstMockMember 가짜 객체의 값 설정
        when(fisrtMockMember.getId()).thenReturn(1L);
        when(fisrtMockMember.getEmail()).thenReturn("KIM@google.com");
        when(fisrtMockMember.getNickname()).thenReturn("KIM");
        when(fisrtMockMember.getCreatedAt()).thenReturn(threeDaysAgo);

        // secondMockMember 가짜 객체의 값 설정
        when(secondMockMember.getId()).thenReturn(2L);
        when(secondMockMember.getEmail()).thenReturn("PARK@naver.com");
        when(secondMockMember.getNickname()).thenReturn("PARK");
        when(secondMockMember.getCreatedAt()).thenReturn(threeDaysAgo);


        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(fisrtMockMember));
        given(articleRepository.save(any())).willReturn(article);

        // when
        ArticleResponseDto result = articleService.addArticle(fisrtMockMember.getId(), articleRequestDto);

        Assertions.assertNotNull(result);
        assertThat(article.getId()).isEqualTo(result.getId());
        assertThat(article.getMember()).isEqualTo(fisrtMockMember); // 게시글 작성자의 맴버 객체와 작성자 맴버 객체가 같은지
        assertThat(article.getMember().getNickname()).isEqualTo(fisrtMockMember.getNickname());
        assertThat(article.getMember().getId()).isNotEqualTo(secondMockMember.getId()); // 게시글 작성자의 맴버 객체와 다른 맴버 ID 가 다른지

    }
    @Test
    @DisplayName("게시글 생성 실패 - 가입한지 3일 안지남")
    public void addArticleFail_date() {
        LocalDateTime aDayAgo = LocalDateTime.now().minusDays(1); // 1일 전 생성된 가입일로 설정

        // firstMockMember 가짜 객체의 값 설정
        when(fisrtMockMember.getId()).thenReturn(1L);
        when(fisrtMockMember.getCreatedAt()).thenReturn(aDayAgo);

        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(fisrtMockMember));


        // when
        CustomException exception = Assertions.assertThrows(CustomException.class,
            () -> articleService.addArticle(fisrtMockMember.getId(), articleRequestDto));

        // then
        assertThat(exception.getMessage()).isEqualTo(MEMBER_NOT_MATCH.getMessage());
        assertThat(exception.getExceptionCode().getStatus()).isEqualTo(MEMBER_NOT_MATCH.getStatus());

    }
}