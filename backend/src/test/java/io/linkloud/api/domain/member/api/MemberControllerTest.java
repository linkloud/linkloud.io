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
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.queryParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.google.gson.Gson;
import io.linkloud.api.domain.article.dto.ArticleStatusRequest;
import io.linkloud.api.domain.article.dto.ArticleStatusResponse;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.model.ReadStatus;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.dto.MemberLoginResponse;
import io.linkloud.api.domain.member.dto.MemberNicknameRequestDto;
import io.linkloud.api.domain.article.dto.MyArticlesResponseDto;
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

import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
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
    @MockBean
    private ArticleRepository articleRepository;
    @Mock
    private Member member;

    @Autowired
    private JwtProvider jwtProvider;
    private String accessToken;
    private final String BASE_URL = "/api/v1/member";

    @Autowired
    private Gson gson;

    MyArticlesResponseDto article1dto;
    MyArticlesResponseDto article2dto;
    Page<MyArticlesResponseDto> articleResponseDto;

    Member member1 = Member.builder()
        .id(1L)
        .email("temp@gmail.com")
        .socialType(SocialType.google)
        .socialId("123123")
        .nickname("asdasd")
        .picture(null)
        .role(Role.MEMBER)
        .build();

    @BeforeEach
    void setUp() {
        when(member.getId()).thenReturn(1L);
        when(member.getNickname()).thenReturn("KimMinJae");
        when(member.getPicture()).thenReturn("picture");
        when(member.getRole()).thenReturn(Role.NEW_MEMBER);
        when(member.getSocialType()).thenReturn(SocialType.google);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.of(member));

        accessToken = jwtProvider.generateAccessToken(member.getId(), member.getSocialType());


        Article article1 = Article.builder()
                .id(1L)
                .title("게시글1의 제목")
                .url("게시글1의 url")
                .description("게시글1의 설명")
                .member(member1)
                .build();

        Article article2 = Article.builder()
                .id(1L)
                .title("게시글2의 제목")
                .url("게시글2의 url")
                .description("게시글2의 설명")
                .member(member1)
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

        article1dto = new MyArticlesResponseDto(article1);
        article2dto = new MyArticlesResponseDto(article2);
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
                    fieldWithPath("data.id").description("회원의 PK ID"),
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
                    fieldWithPath("data.id").description("회원의 PK ID"),
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
        Sort.Direction orderBy = Direction.DESC;
        PageRequest pageable = PageRequest.of(0, 15, Sort.by(orderBy, "latest"));

        articleResponseDto = new PageImpl<>(List.of(article1dto, article2dto), pageable, 100);

        given(memberService.fetchMyArticles(anyLong(), anyLong(), anyString(), anyString(), anyInt()))
            .willReturn(articleResponseDto);

        ResultActions actions = mockMvc.perform(get(BASE_URL + "/{memberId}/articles", memberId)
                .header("Authorization", "Bearer " + accessToken)
                .param("sortBy", "latest")
                .param("tag", "hello")
                .param("page", "1")
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
                        queryParameters(
                            parameterWithName("sortBy").description("정렬 옵션(latest, title, reading, read)"),
                            parameterWithName("tag").description("검색 태그"),
                            parameterWithName("page").description("현재 페이지")
                        ),
                        responseFields(
                                fieldWithPath("data").description("데이터 필드"),
                                fieldWithPath("data[].id").description("해당 회원의 게시글 식별자"),
                                fieldWithPath("data[].memberId").description("해당 게시글의 회원 식별자"),
                                fieldWithPath("data[].memberNickname").description("해당 게시글의 회원 별명"),
                                fieldWithPath("data[].views").description("해당 게시글의 조회수"),
                                fieldWithPath("data[].bookmarks").description("해당 게시글의 북마크수"),
                                fieldWithPath("data[].title").description("해당 회원의 게시글 제목"),
                                fieldWithPath("data[].url").description("해당 회원의 게시글 url"),
                                fieldWithPath("data[].description").description("해당 회원의 게시글 설명"),
                                fieldWithPath("data[].tags[]").description("해당 회원의 게시글 태그"),
                                fieldWithPath("data[].readStatus").description("게시글 상태[UNREAD, READING, READ]"),
                                fieldWithPath("pageInfo").description("해당 조회의 페이지 정보"),
                                fieldWithPath("pageInfo.page").description("해당 조회의 페이지"),
                                fieldWithPath("pageInfo.size").description("해당 조회의 페이지 크기"),
                                fieldWithPath("pageInfo.totalElements").description("해당 조회의 전체 요소"),
                                fieldWithPath("pageInfo.totalPages").description("해당 조회의 전체 페이지")
                        )));
    }

    @DisplayName("회원 내 게시글 목록 조회 실패")
    @Test
    public void getMyArticlesByMemberFail() throws Exception {

        // given
        Long memberId = 1L;

        doThrow(new CustomException(LogicExceptionCode.MEMBER_NOT_MATCH))
                .when(memberService).fetchMyArticles(any(Long.class),any(Long.class), anyString(), anyString(), anyInt());

        ResultActions actions = mockMvc.perform(get(BASE_URL + "/{memberId}/articles", memberId)
                .header("Authorization", "Bearer " + accessToken)
                .param("sortBy", "latest")
                .param("tag", "hello")
                .param("page", "1")
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

    @DisplayName("회원 게시글 상태 변경 성공")
    @ParameterizedTest
    @EnumSource(ReadStatus.class)
    public void updateMyArticleStatusSuccess() throws Exception {

        // given
        Long memberId = 1L;
        Long articleId = 1L;

        // UNREAD
        ReadStatus UNREAD = ReadStatus.UNREAD;

        ArticleStatusRequest articleStatusRequest = new ArticleStatusRequest();
        articleStatusRequest.setReadStatus(UNREAD);

        // when
        when(memberService.updateMyArticleStatus(anyLong(), anyLong(), anyLong(), any(ArticleStatusRequest.class)))
                .thenReturn(new ArticleStatusResponse(articleId,UNREAD.name()));


        String json_UNREAD = gson.toJson(articleStatusRequest);
        ResultActions actions_UNREAD = mockMvc.perform(patch(BASE_URL + "/{memberId}/article-status/{articleId}", memberId,articleId)
                .header("Authorization", "Bearer " + accessToken)
                .contentType(MediaType.APPLICATION_JSON)
                .content(json_UNREAD)
                .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/article_status/success/UNREAD",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("회원PK ID"),
                                parameterWithName("articleId").description("변경할 게시글PK ID")

                        ),
                        requestFields(
                                fieldWithPath("readStatus").description("변경할 게시글의 상태")
                        ),
                        responseFields(
                                fieldWithPath("data.articleId").description("변경된 게시글PK ID"),
                                fieldWithPath("data.readStatus").description("변경된 게시글 상태")
                        )));

        // READING
        ReadStatus READING = ReadStatus.READING;
        articleStatusRequest.setReadStatus(READING);
        when(memberService.updateMyArticleStatus(anyLong(), anyLong(), anyLong(), any(ArticleStatusRequest.class)))
                .thenReturn(new ArticleStatusResponse(articleId, READING.name()));

        String json_READING = gson.toJson(articleStatusRequest);
        ResultActions actions_READING = mockMvc.perform(patch(BASE_URL + "/{memberId}/article-status/{articleId}", memberId,articleId)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_READING)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/article_status/success/READING",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("회원PK ID"),
                                parameterWithName("articleId").description("변경할 게시글PK ID")

                        ),
                        requestFields(
                                fieldWithPath("readStatus").description("변경할 게시글의 상태")
                        ),
                        responseFields(
                                fieldWithPath("data.articleId").description("변경된 게시글PK ID"),
                                fieldWithPath("data.readStatus").description("변경된 게시글 상태")
                        )));

        // READ
        ReadStatus READ = ReadStatus.READ;
        articleStatusRequest.setReadStatus(READ);
        when(memberService.updateMyArticleStatus(anyLong(), anyLong(), anyLong(), any(ArticleStatusRequest.class)))
                .thenReturn(new ArticleStatusResponse(articleId, READ.name()));

        String json_READ = gson.toJson(articleStatusRequest);
        ResultActions actions_READ = mockMvc.perform(patch(BASE_URL + "/{memberId}/article-status/{articleId}", memberId,articleId)
                        .header("Authorization", "Bearer " + accessToken)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json_READ)
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andDo(document("member/article_status/success/READ",
                        preprocessRequest(prettyPrint()),
                        pathParameters(
                                parameterWithName("memberId").description("회원PK ID"),
                                parameterWithName("articleId").description("변경할 게시글PK ID")

                        ),
                        requestFields(
                                fieldWithPath("readStatus").description("변경할 게시글의 상태")
                        ),
                        responseFields(
                                fieldWithPath("data.articleId").description("변경된 게시글PK ID"),
                                fieldWithPath("data.readStatus").description("변경된 게시글 상태")
                        )));

    }
}
