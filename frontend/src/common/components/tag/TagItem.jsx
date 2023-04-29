const TagItem = ({ children }) => {
  return (
    <a
      href={`/tagged/TODO`}
      className="inline-flex justify-center px-3.5 py-1 rounded text-sm cursor-pointer text-indigo-400 bg-indigo-50 hover:bg-indigo-100 transition-colors"
    >
      {children}
    </a>
  );
};

export default TagItem;
