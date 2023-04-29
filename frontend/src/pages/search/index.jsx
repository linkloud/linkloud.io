import { useSearchParams } from "react-router-dom";

import Search from "@/components/search";

const SearchPage = () => {
  const [searchParams] = useSearchParams();

  const searchKeyword = searchParams.get("q");

  return (
    <div className="flex flex-col py-10 max-w-3xl w-full">
      <Search></Search>
      <section className="pt-5">
        <h1 className="py-5 text-xl">
          <strong>'{searchKeyword}'</strong> 검색 결과
        </h1>
      </section>
    </div>
  );
};

export default SearchPage;
