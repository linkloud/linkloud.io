import { useSearchParams } from "react-router-dom";

import Search from "@/common/components/search";
import AnchorSelectable from "@/common/components/anchor/AnchorSelectable";
import ArticleCard from "@/common/components/article/ArticleCard";
import TagItemContainer from "@/common/components/tag/TagItemContainer";

import { fakeArticleList } from "@/common/utils/fakedata";

const SearchPage = () => {
  const [searchParams] = useSearchParams();
  // TODO: q -> api DOC
  const searchKeyword = searchParams.get("q");

  const orderList = [
    {
      id: 1,
      name: "최신순",
      isSelected: true,
    },
    {
      id: 2,
      name: "인기순",
      isSelected: false,
    },
  ];

  return (
    <div className="flex flex-col py-10 max-w-7xl w-full">
      <section className="mx-auto max-w-xl w-full">
        <h1 className="sr-only">search section</h1>
        <Search />
      </section>
      {/* <section className="pt-5 max-w-7xl w-full">
        <h1 className="text-xl">
          <strong>'{searchKeyword}'</strong> 검색 결과
        </h1>
      </section> */}
      <div className="px-6">
        <h2 className="text-xl">
          <strong>'{searchKeyword}'</strong> 검색 결과
        </h2>
      </div>
      <div className="flex w-full max-w-7xl">
        <section className="w-full p-6">
          <h1 className="hidden">link article list section</h1>
          <div className="hidden md:block w-full mb-4">
            <nav>
              <h1 className="hidden">link article order option</h1>
              <ul className="flex py-3">
                {orderList.map((o) => (
                  <li key={o.id}>
                    <AnchorSelectable isSelected={o.isSelected}>
                      {o.name}
                    </AnchorSelectable>
                  </li>
                ))}
              </ul>
            </nav>
          </div>
          {fakeArticleList.map((a) => (
            <ArticleCard article={a} key={a.id}></ArticleCard>
          ))}
        </section>
        <TagItemContainer />
      </div>
    </div>
  );
};

export default SearchPage;
