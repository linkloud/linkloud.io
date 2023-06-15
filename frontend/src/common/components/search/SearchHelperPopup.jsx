const SearchHelperPopup = ({ isVisible }) => {
  const display = isVisible ? "block" : "hidden";

  return (
    <div
      className={`${display} p-4 absolute top-14 left-0 w-full max-w-full bg-white border border-gray-50 rounded-md shadow-lg translate-y-[30%]`}
    >
      <p>
        [tag]
        <span className="ml-2 text-gray-600">
          검색 옵션에 태그를 추가할 수 있습니다.
        </span>
      </p>
    </div>
  );
};

export default SearchHelperPopup;
