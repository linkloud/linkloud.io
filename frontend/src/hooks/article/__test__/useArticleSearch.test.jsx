import { renderHook, act } from "@testing-library/react";
import { useNavigate } from "react-router-dom";
import useArticleSearch from "../useArticleSearch";

jest.mock("react-router-dom", () => ({
  useNavigate: jest.fn(),
}));

describe("useArticleSearch TEST", () => {
  let openSearchValidationModalMock;

  beforeEach(() => {
    openSearchValidationModalMock = jest.fn();
    useNavigate.mockReturnValue(jest.fn());
  });

  it("[success] 요청한 검색 경로로 이동한다", () => {
    const { result } = renderHook(() =>
      useArticleSearch(openSearchValidationModalMock)
    );

    act(() => {
      result.current.handleSearch("test [tag1] [tag2]");
    });

    expect(useNavigate()).toHaveBeenCalledWith(
      "/search?keyword=test&tag=tag1&tag=tag2"
    );
  });

  it("[fail] 검색 키워드가 없다면 예외가 발생한다", () => {
    const { result } = renderHook(() =>
      useArticleSearch(openSearchValidationModalMock)
    );

    act(() => {
      result.current.handleSearch("");
    });

    expect(result.current.searchValidationErrMsg).toEqual(
      "검색어를 입력해 주세요."
    );
    expect(openSearchValidationModalMock).toHaveBeenCalled();
  });

  it("[fail] 태그 검색 최대 5개를 초과할 경우 예외가 발생한다", () => {
    const { result } = renderHook(() =>
      useArticleSearch(openSearchValidationModalMock)
    );

    act(() => {
      result.current.handleSearch(
        "test [tag1] [tag2] [tag3] [tag4] [tag5] [tag6]"
      );
    });

    expect(result.current.searchValidationErrMsg).toEqual(
      "태그의 개수는 최대 5개까지 가능합니다."
    );
    expect(openSearchValidationModalMock).toHaveBeenCalled();
  });
});
