import styled, { css } from "styled-components";

export const Title = styled.h1`
  margin-bottom: 40px;

  ${({ theme }) => css`
    font-size: ${theme.FONT_SIZE.TITLE_MD};
    margin: 10px auto;
  `}
`;

export const Description = styled.p`
  margin-top: 16px;
  margin-bottom: 16px;
`;
