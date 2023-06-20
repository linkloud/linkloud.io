import { renderHook, act } from "@testing-library/react";
import { useNavigate } from "react-router-dom";
import useArticleSearch from "../useArticleSearch";

jest.mock("react-router-dom", () => ({
  useNavigate: jest.fn(),
}));

describe("useArticleSearch TEST", () => {
  let navigateMock;

  beforeEach(() => {
    navigateMock = jest.fn();
    useNavigate.mockReturnValue(navigateMock);
  });

  it("[success] 요청한 검색 경로로 이동한다", () => {
    const { result } = renderHook(() => useArticleSearch());

    act(() => {
      result.current.handleSearch("test [tag1] [tag2]");
    });

    expect(useNavigate()).toHaveBeenCalledWith(
      "/search?keyword=test&tag=tag1&tag=tag2"
    );
  });

  it("[success] 태그 없이 검색 경로로 이동한다", () => {
    const { result } = renderHook(() => useArticleSearch());

    act(() => {
      result.current.handleSearch("test");
    });

    expect(useNavigate()).toHaveBeenCalledWith("/search?keyword=test");
  });

  it("[success] 중복된 태그는 제거 후 검색 경로로 이동한다", () => {
    const { result } = renderHook(() => useArticleSearch());

    act(() => {
      result.current.handleSearch("test [tag1] [tag1]");
    });

    expect(useNavigate()).toHaveBeenCalledWith("/search?keyword=test&tag=tag1");
  });
});
