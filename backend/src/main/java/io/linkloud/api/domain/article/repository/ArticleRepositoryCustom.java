package io.linkloud.api.domain.article.repository;

import io.linkloud.api.domain.article.dto.ArticleResponseDto;
import io.linkloud.api.domain.article.model.Article;
import io.linkloud.api.domain.member.model.Member;
import java.util.List;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleRepositoryCustom {
    // 목록 조회 : 검색
    Page<ArticleResponseDto> findArticleListBySearch(String keyword, List<String> tags, Pageable pageable);
    Page<ArticleResponseDto> findMyArticleByTag(Member m, String t, String articleStatus, Pageable pageable);

}
