package io.linkloud.api.domain.tag.repository;

import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.article.repository.ArticleRepository;
import io.linkloud.api.domain.member.model.Member;
import io.linkloud.api.domain.member.model.Role;
import io.linkloud.api.domain.member.repository.MemberRepository;
import io.linkloud.api.domain.tag.dto.TagDto;
import io.linkloud.api.domain.tag.model.ArticleTag;
import io.linkloud.api.domain.tag.model.Tag;
import io.linkloud.api.global.TestConfig;
import java.util.ArrayList;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@TestInstance(Lifecycle.PER_CLASS)
@SpringBootTest
public class TagRepositoryTest {

    @Autowired
    TagRepository tagRepository;

    @Autowired
    ArticleTagRepository articleTagRepository;

    @Autowired
    MemberRepository memberRepository;

    @Autowired
    ArticleRepository articleRepository;

    Member member = Member.builder()
        .email("temp@naver.com")
        .role(Role.MEMBER)
        .socialId("123123123123")
        .nickname("asdasd")
        .build();

    char c = 'a';

    Tag tag_spring = Tag.builder().name("spring").build();
    Tag tag_spring_boot = Tag.builder().name("spring-boot").build();
    Tag tag_spring_data = Tag.builder().name("spring-data").build();
    Tag tag_spring_security = Tag.builder().name("spring-security").build();
    Tag tag_spring_restdocs = Tag.builder().name("spring-restDocs").build();
    Tag tag_react = Tag.builder().name("react").build();

    @BeforeAll
    public void init() {
        Member member1 = memberRepository.save(member);

        List<Article> articles = new ArrayList<>(3);
        for (int i = 0; i < 3; i++) {
            String s = String.valueOf(c);
            articles.add(Article.builder().url(s).title(s).description(s).member(member1).build());
            c += 1;
        }
        articleRepository.saveAll(articles);

        List<Tag> tags = List.of(
            tag_spring,
            tag_spring_boot,
            tag_spring_data,
            tag_spring_security,
            tag_spring_restdocs,
            tag_react
        );
        tagRepository.saveAll(tags);

        List<ArticleTag> articleTags = new ArrayList<>(7);
        for(int i = 0; i < 7; i++) {
            Article article = articles.get(i / 3);

            if (i / 3 == 2) {
                articleTags.add(
                    ArticleTag.builder().article(articles.get(2)).tag(tags.get(2)).build()
                );
                break;
            }

            articleTags.add(
                ArticleTag.builder().article(article).tag(tags.get(i % 3)).build()
            );
        }

        articleTagRepository.saveAll(articleTags);
    }

    @DisplayName("인기순 태그들 조회")
    @Test
    void findAllOrderByPopularity() {
        PageRequest pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "popularity"));

        List<TagDto.Response> tags  = tagRepository.findAllOrderBy(pageable).getContent();

        assertNotNull(tags);
        assertEquals(tag_spring_data.getName(), tags.get(0).getName());
    }

    @DisplayName("최신순 태그들 조회")
    @Test
    void findAllOrderByCreatedAt() {
        PageRequest pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<TagDto.Response> tags  = tagRepository.findAllOrderBy(pageable).getContent();

        assertNotNull(tags);
        assertEquals(tag_react.getName(), tags.get(0).getName());
    }

    @DisplayName("이름순 태그들 조회")
    @Test
    void findAllOrderByName() {
        PageRequest pageable = PageRequest.of(0, 5, Sort.by(Sort.Direction.ASC, "name"));

        List<TagDto.Response> tags  = tagRepository.findAllOrderBy(pageable).getContent();

        assertNotNull(tags);
        assertEquals(tag_react.getName(), tags.get(0).getName());
    }

    @DisplayName("태그 검색")
    @Test
    void findTagByNameIsStartingWith() {
        List<TagDto.Response> tags = tagRepository.findTagByNameIsStartingWith("sp");

        assertNotNull(tags);
        assertEquals(tags.get(0).getName(), tag_spring_data.getName());
    }
}
