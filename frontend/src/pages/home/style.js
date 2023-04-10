import styled from "styled-components";

export const Main = styled.main`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  width: 100%;
`;

export const SearchContainer = styled.div`
  width: 100%;
  max-width: 588px;
  transform: translateY(-50%);
`;

export const ArticleSection = styled.section`
  width: 100%;
  max-width: 792px;

  & > h1 {
    display: none;
  }
`;
