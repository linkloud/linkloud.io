import Search from "@/components/search";

const HomeSearchContainer = () => {
  return (
    <section className="w-full max-w-[600px] translate-y-[-50%] z-10">
      <h1 className="hidden">search section</h1>
      <Search />
    </section>
  );
};

export default HomeSearchContainer;
