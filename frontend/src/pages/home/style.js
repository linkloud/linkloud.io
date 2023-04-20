import styled from "styled-components";

export const SearchContainer = styled.div`
  width: 100%;
  max-width: 588px;
  transform: translateY(-50%);
`;

export const ArticleOrder = styled.div`
  width: 100%;
  max-width: 792px;
  margin-top: 20px;
  margin-bottom: 32px;

  ul {
    display: flex;
  }

  li {
    display: inline-block;
    margin-right: 4px;
  }
`;

export const ArticleCardList = styled.section`
  width: 100%;
  max-width: 792px;

  & > h1 {
    display: none;
  }
`;
