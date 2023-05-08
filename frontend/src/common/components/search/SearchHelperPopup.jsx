const SearchHelperPopup = ({ isVisible }) => {
  const display = isVisible ? "block" : "hidden";

  return (
    <div
      className={`${display} p-4 absolute top-14 left-0 w-full max-w-full bg-white border border-s-gray-200 rounded-lg shadow-lg translate-y-[30%]`}
    >
      <p>
        [tag] <span className="ml-2 text-gray-600">태그로 검색합니다.</span>
      </p>
    </div>
  );
};

export default SearchHelperPopup;
