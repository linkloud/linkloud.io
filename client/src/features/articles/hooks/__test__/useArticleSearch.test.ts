import { renderHook, act } from "@testing-library/react";
import { useNavigate } from "react-router-dom";
import { useArticleSearchNavigation } from "../useArticleSearchNavigation";

jest.mock("react-router-dom", () => ({
  useNavigate: jest.fn(),
}));

describe("useArticleSearchNavigation TEST", () => {
  let navigateMock = jest.fn();

  jest
    .spyOn(require("react-router-dom"), "useNavigate")
    .mockImplementation(() => navigateMock);

  beforeEach(() => {
    navigateMock = jest.fn();
  });

  it("[success] 요청한 검색 경로로 이동한다", () => {
    const { result } = renderHook(() => useArticleSearchNavigation());

    act(() => {
      result.current.handleSearchNavigation("test [tag1] [tag2]");
    });

    expect(useNavigate()).toHaveBeenCalledWith(
      "/search?keyword=test&tags=tag1&tags=tag2"
    );
  });

  it("[success] 태그 없이 검색 경로로 이동한다", () => {
    const { result } = renderHook(() => useArticleSearchNavigation());

    act(() => {
      result.current.handleSearchNavigation("test");
    });

    expect(useNavigate()).toHaveBeenCalledWith("/search?keyword=test");
  });

  it("[success] 중복된 태그는 제거 후 검색 경로로 이동한다", () => {
    const { result } = renderHook(() => useArticleSearchNavigation());

    act(() => {
      result.current.handleSearchNavigation("test [tag1] [tag1]");
    });

    expect(useNavigate()).toHaveBeenCalledWith(
      "/search?keyword=test&tags=tag1"
    );
  });
});
