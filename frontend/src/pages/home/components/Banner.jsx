const Banner = () => {
  return (
    <section className="w-full flex flex-col justify-center items-center h-80 bg-primary-600">
      <h1 className="px-5 text-center text-xl md:text-2xl text-neutral-50 font-semibold">
        생산성을 높일 수 있는 유용한 도구와 자료를 제공하는 링크를 찾아보세요.
      </h1>
      <p className="my-3 text-md md:text-lg text-neutral-50">
        지금까지 0개의 링크가 모였습니다
      </p>
    </section>
  );
};

export default Banner;
