import Banner from "./components/Banner";
import Aside from "./components/Aside";
import Search from "@/common/components/search";
import ArticleCard from "@/common/components/article/ArticleCard";
import AnchorSelectable from "@/common/components/anchor/AnchorSelectable";

import { fakeArticleList } from "@/common/utils/fakedata";

const HomePage = () => {
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
    {
      id: 3,
      name: "이주의 링크",
      isSelected: false,
    },
    {
      id: 4,
      name: "이달의 링크",
      isSelected: false,
    },
  ];

  return (
    <>
      <Banner />
      <section className="px-5 md:px-0 w-full max-w-[600px] translate-y-[-50%]">
        <h1 className="hidden">search section</h1>
        <Search />
      </section>
      <div className="flex flex-row-reverse w-full max-w-6xl">
        <Aside />
        <section className="w-full p-5">
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
      </div>
    </>
  );
};

export default HomePage;
