import styled, { css } from "styled-components";

export const Container = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  height: 200px;
  width: 100%;

  ${({ theme }) => css`
    background-color: ${theme.COLOR.PRIMARY.HIGH};
  `}
`;

export const Title = styled.h1`
  font-weight: 600;

  ${({ theme }) => css`
    font-size: ${theme.FONT_SIZE.TITLE_LG};
    color: ${theme.COLOR.TEXT.INVERTED};
  `}
`;

export const Body = styled.p`
  margin-top: 12px;
  margin-bottom: 12px;

  ${({ theme }) => css`
    font-size: ${theme.FONT_SIZE.TITLE_SM};
    color: ${theme.COLOR.TEXT.INVERTED};
  `}
`;
