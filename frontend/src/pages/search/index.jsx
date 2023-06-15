import { useState, useEffect } from "react";
import { useSearchParams } from "react-router-dom";
import { v4 as uuidv4 } from "uuid";

import useArticleSearch from "@/hooks/article/useArticleSearch";
import articleApi from "@/service/api/article";

import TagItem from "@/common/components/tag/TagItem";
import ArticleItem from "@/common/components/article/ArticleItem";

const SearchPage = () => {
  const [articles, setArticles] = useState([]);
  const [articlePageInfo, setArticlePageInfo] = useState({
    page: 1,
    size: 10,
    totalElements: 0,
    totalPages: 0,
  });

  const [searchParams] = useSearchParams();

  const searchKeyword = searchParams.get("keyword");
  const tags = searchParams
    .getAll("tag")
    .map((tag) => ({ name: tag, id: uuidv4() }));

  const { handleSearch } = useArticleSearch();

  useEffect(() => {
    searchArticles(searchKeyword);
  }, []);

  const searchArticles = async (keyword) => {
    const { data, pageInfo } = await articleApi.search({
      keyword,
      type: "title",
    });
    setArticles(data);
    setArticlePageInfo(pageInfo);
  };

  return (
    <div className="flex flex-col py-10 max-w-7xl w-full">
      <div className="px-6 py-5">
        <h2 className="text-xl">
          {searchKeyword ? (
            <>
              <strong>'{searchKeyword}'</strong> 검색 결과
            </>
          ) : (
            <>태그로 검색 결과</>
          )}
        </h2>
        {tags?.length > 0 && (
          <>
            {searchKeyword && (
              <p className="mt-1">다음 태그와 함께 검색되었습니다.</p>
            )}
            <ul className="flex mt-1">
              {tags.map((tag) => (
                <li key={tag.id} className="mr-1 text-gray-400">
                  <TagItem tag={tag.name} />
                </li>
              ))}
            </ul>
          </>
        )}
      </div>

      <div className="flex w-full max-w-7xl">
        <section className="w-full p-6">
          <h1 className="hidden">검색 목록</h1>
          {articles?.length > 0 ? (
            articles.map((article) => (
              <ArticleItem article={article} key={article.id}></ArticleItem>
            ))
          ) : (
            <p>검색 결과가 없습니다.</p>
          )}
        </section>
      </div>
    </div>
  );
};

export default SearchPage;
