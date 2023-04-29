import Banner from "@/components/banner";
import ArticleCard from "@/components/article/ArticleCard";
import AnchorBottomLine from "@/components/common/anchor/AnchorBottomLine";
import HomeSearchContainer from "@/container/HomeSearchContainer";
import HomeAsideContainer from "@/container/HomeAsideContainer";

const HomePage = () => {
  const fakeArticleList = [
    {
      id: 1,
      title: "이름1",
      description: "설명",
      tags: [{ name: "태그1" }, { name: "태그2" }],
    },
    {
      id: 2,
      title: "이름2",
      description: "설명",
    },
    {
      id: 3,
      title: "이름3",
      description: "설명",
    },
    {
      id: 4,
      title: "이름4",
      description: "설명",
    },
    {
      id: 5,
      title: "이름5",
      description: "설명",
    },
    {
      id: 6,
      title: "이름6",
      description: "설명",
    },
    {
      id: 7,
      title: "이름7",
      description: "설명",
    },
    {
      id: 8,
      title: "이름8",
      description: "설명",
    },
    {
      id: 9,
      title: "이름9",
      description: "설명",
    },
    {
      id: 10,
      title: "이름10",
      description: "설명",
    },
  ];

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
      <HomeSearchContainer />
      <div className="flex flex-row-reverse w-full max-w-6xl">
        <HomeAsideContainer />
        <section className="w-full p-5">
          <h1 className="hidden">link article section</h1>
          <div className="hidden md:block w-full mb-4">
            <nav>
              <h1 className="hidden">link article order option</h1>
              <ul className="flex py-3">
                {orderList.map((o) => (
                  <li key={o.id}>
                    <AnchorBottomLine isActive={o.isSelected}>
                      {o.name}
                    </AnchorBottomLine>
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
