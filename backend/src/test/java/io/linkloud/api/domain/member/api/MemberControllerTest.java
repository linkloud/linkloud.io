package io.linkloud.api.domain.member.api;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.when;
import static org.springframework.restdocs.headers.HeaderDocumentation.headerWithName;
import static org.springframework.restdocs.headers.HeaderDocumentation.requestHeaders;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.patch;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessRequest;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.preprocessResponse;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.member.dto.MemberLoginResponse;
import io.linkloud.api.domain.member.dto.MemberNicknameRequestDto;
import io.linkloud.api.domain.member.dto.MyArticlesResponseDto;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.model.SocialType;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.domain.member.service.MemberService;
import io.linkloud.api.domain.tag.model.ArticleTag;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.global.exception.CustomException;
import io.linkloud.api.global.exception.ExceptionCode.AuthExceptionCode;
import io.linkloud.api.global.exception.ExceptionCode.LogicExceptionCode;
import io.linkloud.api.global.security.auth.jwt.JwtProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentationExtension;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

@AutoConfigureRestDocs
@AutoConfigureMockMvc
@ExtendWith({RestDocumentationExtension.class, SpringExtension.class})
@SpringBootTest
class MemberControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MemberService memberService;

    @MockBean
    private MemberRepository memberRepository;
    @Mock
    private Member member;

    @Autowired
    private JwtProvider jwtProvider;
    private String accessToken;
    private final String BASE_URL = "/api/v1/member";

    @Autowired
    private Gson gson;
    @BeforeEach
    void setUp() {
        when(member.getId()).thenReturn(1L);
        when(member.getNickname()).thenReturn("KimMinJae");
        when(member.getPicture()).thenReturn("picture");
        when(member.getRole()).thenReturn(Role.NEW_MEMBER);
        when(member.getSocialType()).thenReturn(SocialType.google);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        accessToken = jwtProvider.generateAccessToken(member.getId(), member.getSocialType());
    }

    @AfterEach
    public void cleanUp() {
        memberRepository.findById(member.getId());
    }


    @DisplayName("회원정보 - 가입한지3일이 안지난 회원 NEW_MEMBER")
    @Test
    public void fineMe_success_new_member() throws Exception {

        MemberLoginResponse mockResponse = new MemberLoginResponse(member);
        when(memberService.fetchPrincipal(anyLong())).thenReturn(mockResponse);

        ResultActions actions = mockMvc.perform(
            get(BASE_URL + "/me")
                .header("Authorization", "Bearer " + accessToken));

        actions
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.data.nickname").value(member.getNickname()))
            .andExpect(jsonPath("$.data.picture").value(member.getPicture()))
            .andExpect(jsonPath("$.data.role").value(member.getRole().name()))
            .andDo(document("member/me/success/newMember",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("data.nickname").description("회원의 닉네임."),
                    fieldWithPath("data.picture").description("회원의 프로필 사진 URI."),
                    fieldWithPath("data.role").description("가입한지 3일 이내 회원의 권한")
                )));
    }

    @DisplayName("회원정보 - 가입한지 3일지난 회원 MEMBER")
    @Test
    public void fineMe_success_member() throws Exception {

        MemberLoginResponse mockResponse = new MemberLoginResponse(member);
        when(memberService.fetchPrincipal(anyLong())).thenReturn(mockResponse);

        ResultActions actions = mockMvc.perform(
            get(BASE_URL + "/me")
                .header("Authorization", "Bearer " + accessToken));

        actions.andDo(print())
            .andExpect(status().isOk())
            .andDo(document("member/me/success/member",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("data.nickname").description("회원의 닉네임."),
                    fieldWithPath("data.picture").description("회원의 프로필 사진 URI."),
                    fieldWithPath("data.role").description("가입한지 3일 지난 회원의 권한")
                )));
    }

    @DisplayName("회원 조회 실패_만료된 액세스토큰")
    @Test
    public void member_me_expired_accessToken() throws Exception {
        // given
        doThrow(new CustomException(AuthExceptionCode.EXPIRED_ACCESS_TOKEN))
            .when(memberService).fetchPrincipal(any(Long.class));

        ResultActions actions = mockMvc.perform(
            get(BASE_URL + "/me")
                .header("Authorization", "Bearer " + accessToken));

        actions
            .andDo(print())
            .andExpect(status().isUnauthorized())
            .andDo(document("member/me/fail/accessToken",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("status").description("에러 코드"),
                    fieldWithPath("message").description("에러 메시지"),
                    fieldWithPath("fieldErrors").description("필드 에러"),
                    fieldWithPath("violationErrors").description("검증 에러")
                )
            ));
    }

    @DisplayName("회원 조회 실패_없는 회원")
    @Test
    public void member_me_not_found_throws() throws Exception {
        // given
        doThrow(new CustomException(LogicExceptionCode.MEMBER_NOT_FOUND))
            .when(memberService).fetchPrincipal(any(Long.class));

        ResultActions actions = mockMvc.perform(
            get(BASE_URL + "/me")
                .header("Authorization", "Bearer " + accessToken));

        actions
            .andDo(print())
            .andExpect(status().isNotFound())
            .andExpect(jsonPath("$.status").value(LogicExceptionCode.MEMBER_NOT_FOUND.getStatus()))
            .andExpect(jsonPath("$.message").value(LogicExceptionCode.MEMBER_NOT_FOUND.getMessage()))
            .andDo(document("member/me/fail/notfound",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                responseFields(
                    fieldWithPath("status").description("에러 코드"),
                    fieldWithPath("message").description("에러 메시지"),
                    fieldWithPath("fieldErrors").description("필드 에러"),
                    fieldWithPath("violationErrors").description("검증 에러")
                )
            ));
    }

    @DisplayName("회원 닉네임 변경 성공")
    @Test
    public void updateNickname_success() throws Exception {
        MemberNicknameRequestDto memberNicknameRequest = new MemberNicknameRequestDto("new_nickname");

        String content = gson.toJson(memberNicknameRequest);

        ResultActions actions = mockMvc.perform(patch(BASE_URL + "/nickname")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(content));

        actions
            .andExpect(status().isNoContent())
            .andDo(document("member/nickname/success",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("nickname").description("수정할 닉네임")
                ),
                requestHeaders(
                    headerWithName("Authorization").description("액세스 토큰")
                ),
                requestHeaders(
                    headerWithName("Authorization").description("JWT 토큰")
                )
            ));
    }


    @DisplayName("회원 닉네임 변경 실패 중복 닉네임 예외처리")
    @Test
    public void updateNickname_duplicated_nickname_throws() throws Exception {
        // given

        MemberNicknameRequestDto memberNicknameRequest = new MemberNicknameRequestDto("new_nickname");

        String content = gson.toJson(memberNicknameRequest);

        doThrow(new CustomException(LogicExceptionCode.MEMBER_ALREADY_EXISTS))
            .when(memberService).updateNickname(anyLong(), any(MemberNicknameRequestDto.class));


        ResultActions actions = mockMvc.perform(patch(BASE_URL + "/nickname")
            .header("Authorization", "Bearer " + accessToken)
            .contentType(MediaType.APPLICATION_JSON)
            .accept(MediaType.APPLICATION_JSON)
            .content(content));

        actions
            .andDo(print())
            .andExpect(status().isConflict())
            .andDo(document("member/nickname/fail/duplicated",
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()),
                requestFields(
                    fieldWithPath("nickname").description("수정할 닉네임")
                ),
                responseFields(
                    fieldWithPath("status").description(
                        "HTTP 상태 코드. 중복된 닉네임일 경우 409 (Conflict)을 반환."),
                    fieldWithPath("message").description("에러 메시지"),
                    fieldWithPath("fieldErrors").description("필드 에러 리스트"),
                    fieldWithPath("violationErrors").description("벨리데이션 에러 리스트")
                )));
    }

    @DisplayName("회원 내 게시글 목록 조회 성공")
    @Test
    public void getMyArticlesByMemberSuccess() throws Exception {

        Long memberId = 1L;
        Long extractedMemberId = 1L;

        Article article1 = Article.builder()
                .id(memberId)
                .title("게시글1의 제목")
                .url("게시글1의 url")
                .description("게시글1의 설명")
                .build();

        Article article2 = Article.builder()
                .id(memberId)
                .title("게시글2의 제목")
                .url("게시글2의 url")
                .description("게시글2의 설명")
                .build();

        Tag tag1 = Tag.builder()
                .name("첫번째태그")
                .build();


        Tag tag2 = Tag.builder()
                .name("두번째태그")
                .build();

        ArticleTag articleTag1_1 = ArticleTag.builder()
                .article(article1)
                .tag(tag1)
                .build();

        ArticleTag articleTag1_2 = ArticleTag.builder()
                .article(article1)
                .tag(tag2)
                .build();

        ArticleTag articleTag2_1 = ArticleTag.builder()
                .article(article1)
                .tag(tag1)
                .build();

        ArticleTag articleTag2_2 = ArticleTag.builder()
                .article(article1)
                .tag(tag2)
                .build();



        article1.getArticleTags().add(articleTag1_1);
        article1.getArticleTags().add(articleTag1_2);

        article2.getArticleTags().add(articleTag2_1);
        article2.getArticleTags().add(articleTag2_2);


        MyArticlesResponseDto article1dto = new MyArticlesResponseDto(article1);
        MyArticlesResponseDto article2dto = new MyArticlesResponseDto(article2);


        List<MyArticlesResponseDto> articleResponseDto = new ArrayList<>();
        articleResponseDto.add(article1dto);
        articleResponseDto.add(article2dto);


        given(memberService.fetchMyArticlesByMemberId(memberId, extractedMemberId)).willReturn(articleResponseDto);

        ResultActions actions = mockMvc.perform(get(BASE_URL + "/{memberId}/articles", memberId)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/articles/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("회원PK ID")
                        ),
                        responseFields(
                                fieldWithPath("data[].title").description("해당 회원의 게시글 제목"),
                                fieldWithPath("data[].url").description("해당 회원의 게시글 url"),
                                fieldWithPath("data[].description").description("해당 회원의 게시글 설명"),
                                fieldWithPath("data[].tags[]").description("해당 회원의 게시글 태그"),
                                fieldWithPath("data[].articleStatus").description("게시글 상태[UNREAD,READING,READ]")
                        )));
    }

    @DisplayName("회원 내 게시글 목록 조회 실패")
    @Test
    public void getMyArticlesByMemberFail() throws Exception {

        // given
        Long memberId = 1L;

        doThrow(new CustomException(LogicExceptionCode.MEMBER_NOT_MATCH))
                .when(memberService).fetchMyArticlesByMemberId(any(Long.class),any(Long.class));

        ResultActions actions = mockMvc.perform(get(BASE_URL + "/{memberId}/articles", memberId)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON));

        actions
                .andDo(print())
                .andExpect(status().isForbidden())
                .andDo(document("member/articles/fail",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("회원PK ID")
                        ),
                        responseFields(
                                fieldWithPath("status").description("Http 상태 코드"),
                                fieldWithPath("message").description("{memberId}와 jwt토큰 추출 회원ID가 다름"),
                                fieldWithPath("fieldErrors").description("필드 에러 리스트"),
                                fieldWithPath("violationErrors").description("벨리데이션 에러 리스트")
                        )));
    }
}
