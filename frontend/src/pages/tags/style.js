import styled, { css } from "styled-components";

export const Container = styled.div`
  max-width: 792px;
`;

export const TagDescriptionTitle = styled.h2`
  margin: 32px auto;

  ${({ theme }) => css`
    font-size: ${theme.FONT_SIZE.TITLE_LG};
  `}
`;

export const TagDescription = styled.p`
  max-width: 640px;
  ${({ theme }) => css`
    font-size: ${theme.FONT_SIZE.BODY};
  `}
`;

export const TagRequestAction = styled.a`
  text-decoration: underline;
  cursor: pointer;

  ${({ theme }) => css`
    &:hover {
      color: ${theme.COLOR.ACCENT.HIGH};
    }
  `}
`;

export const TagCardList = styled.section`
  padding: 24px 0;
  display: flex;
  flex-wrap: wrap;
  gap: 16px 24px;

  h1 {
    display: none;
  }
`;
