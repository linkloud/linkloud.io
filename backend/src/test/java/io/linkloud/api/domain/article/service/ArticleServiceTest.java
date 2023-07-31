package io.linkloud.api.domain.article.service;

import static io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode.MEMBER_NOT_MATCH;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import io.linkloud.api.domain.article.dto.ArticleRequestDto;
import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.dto.ArticleUpdateDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.domain.tag.service.TagService;
import io.linkloud.api.global.exception.CustomException;
import java.time.LocalDateTime;
import java.util.List;
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
    @Mock
    TagService tagService;
    Member firstMockMember = mock(Member.class);
    Member secondMockMember = mock(Member.class);

    Article firstMockArticle = mock(Article.class);
    private final Article article = Article.builder()
        .id(1L)
        .member(firstMockMember)
        .title("title")
        .url("url")
        .description("desc")
        .views(1)
        .bookmarks(1)
        .build();

    ArticleRequestDto articleRequestDto = new ArticleRequestDto(
        "title",
        "url",
        "desc",
        List.of("spring", "spring-boot")
    );

    ArticleUpdateDto articleUpdateRequestDto = new ArticleUpdateDto("updateTitle", "updateURL",
        "updateDesc", List.of(""));
    @Test
    @DisplayName("게시글 생성 성공")
    public void addArticleSuccess() {

        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3); // 3일 전 생성된 가입일로 설정

        // firstMockMember 가짜 객체의 값 설정
        when(firstMockMember.getId()).thenReturn(1L);
        when(firstMockMember.getEmail()).thenReturn("KIM@google.com");
        when(firstMockMember.getNickname()).thenReturn("KIM");
        when(firstMockMember.getCreatedAt()).thenReturn(threeDaysAgo);

        // secondMockMember 가짜 객체의 값 설정
        when(secondMockMember.getId()).thenReturn(2L);
        when(secondMockMember.getEmail()).thenReturn("PARK@naver.com");
        when(secondMockMember.getNickname()).thenReturn("PARK");
        when(secondMockMember.getCreatedAt()).thenReturn(threeDaysAgo);


        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(firstMockMember));
        given(articleRepository.save(any())).willReturn(article);
        given(tagService.addTags(anyList())).willReturn(
            List.of(
                Tag.builder().name("spring").build(),
                Tag.builder().name("spring-boot").build()
        ));

        // when
        ArticleResponseDto result = articleService.addArticle(firstMockMember.getId(), articleRequestDto);

        Assertions.assertNotNull(result);
        assertThat(article.getId()).isEqualTo(result.getId());
        assertThat(article.getMember()).isEqualTo(firstMockMember); // 게시글 작성자의 맴버 객체와 작성자 맴버 객체가 같은지
        assertThat(article.getMember().getNickname()).isEqualTo(firstMockMember.getNickname());
        assertThat(article.getMember().getId()).isNotEqualTo(secondMockMember.getId()); // 게시글 작성자의 맴버 객체와 다른 맴버 ID 가 다른지

    }


    @Test
    @DisplayName("게시글 수정 성공")
    public void updateArticleSuccess() {

        LocalDateTime threeDaysAgo = LocalDateTime.now().minusDays(3); // 3일 전 생성된 가입일로 설정

        // firstMockMember 가짜 객체의 값 설정
        when(firstMockMember.getId()).thenReturn(1L);
        when(firstMockMember.getNickname()).thenReturn("KIM");
        when(firstMockMember.getCreatedAt()).thenReturn(threeDaysAgo);


        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(firstMockMember));

        when(articleRepository.findById(anyLong())).thenReturn(Optional.of(article));

        // when
        ArticleResponseDto articleResponseDto = articleService.updateArticle(article.getId(),
            firstMockMember.getId(),
            articleUpdateRequestDto);

        // then

        // 게시글 수정 내용 dto 와 게시글 업데이트 후 응답하는 게시글 수정내용 dto 값이 같은지 테스트
        assertThat(articleUpdateRequestDto.getTitle()).isEqualTo(articleResponseDto.getTitle());
        assertThat(articleUpdateRequestDto.getUrl()).isEqualTo(articleResponseDto.getUrl());
        assertThat(articleUpdateRequestDto.getDescription()).isEqualTo(articleResponseDto.getDescription());

        // 게시글 수정된 응답 dto 의 memberId 와 작성자의 memberId 같은지 테스트
        assertThat(articleResponseDto.getMemberId()).isEqualTo(firstMockMember.getId());
        assertThat(articleResponseDto.getMemberNickname()).isEqualTo(firstMockMember.getNickname());
    }


    @Test
    @DisplayName("게시글 수정 실패 - 요청한 회원과 게시글 작성 회원이 일치하지 않음")
    public void updateArticleFail_memberMismatch() {

        // ID 가 다른 회원2명
        when(firstMockMember.getId()).thenReturn(1L);
        when(secondMockMember.getId()).thenReturn(9999L);

        // 첫 번째 게시글의 작성자 객체는 1L 회원의 게시글
        when(firstMockArticle.getMember()).thenReturn(firstMockMember);

        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(secondMockMember));
        given(articleRepository.findById(anyLong())).willReturn(Optional.of(firstMockArticle));

        // when
        // 9999L 회원이 첫 번째 게시글 수정 요청
        CustomException exception = assertThrows(CustomException.class,
            () -> articleService.updateArticle(
                secondMockMember.getId(),
                firstMockArticle.getId(),
                articleUpdateRequestDto)
        );

        // then
        assertThat(exception.getMessage()).isEqualTo(MEMBER_NOT_MATCH.getMessage());
        assertThat(exception.getExceptionCode().getStatus()).isEqualTo(MEMBER_NOT_MATCH.getStatus());
    }

    @Test
    @DisplayName("게시글 삭제")
    public void removeArticleSuccess() {

        when(firstMockMember.getId()).thenReturn(1L);
        // 게시글의 작성자 객체는 1L 회원의 게시글
        when(firstMockArticle.getMember()).thenReturn(firstMockMember);

        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(firstMockMember));
        given(articleRepository.findById(anyLong())).willReturn(Optional.of(firstMockArticle));

        // when
        // removeArticle 실행 (예외가 발생하지 않아야 함)
        assertDoesNotThrow(() -> articleService.removeArticle(firstMockMember.getId(), firstMockArticle.getId()));
    }

    @Test
    @DisplayName("게시글 삭제 실패- 요청한 회원과 게시글 작성 회원이 일치하지 않음")
    public void removeArticleFail_memberMismatch() {

        // ID 가 다른 회원2명
        when(firstMockMember.getId()).thenReturn(1L);
        when(secondMockMember.getId()).thenReturn(9999L);

        // 첫 번째 게시글의 작성자 객체는 1L 회원의 게시글
        when(firstMockArticle.getMember()).thenReturn(firstMockMember);


        // given
        given(memberRepository.findById(anyLong())).willReturn(Optional.of(secondMockMember));
        given(articleRepository.findById(anyLong())).willReturn(Optional.of(firstMockArticle));

        // when
        // 첫 번째 게시글의 요청에 memberID 가 9999L인 memberId 요청이 오면
        CustomException exception = assertThrows(CustomException.class,
            () -> articleService.removeArticle(secondMockMember.getId(), firstMockArticle.getId()));

        // then
        assertThat(exception.getMessage()).isEqualTo(MEMBER_NOT_MATCH.getMessage());
        assertThat(exception.getExceptionCode().getStatus()).isEqualTo(MEMBER_NOT_MATCH.getStatus());
    }

}