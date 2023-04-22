import styled, { css } from "styled-components";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  gap: 40px;

  & > p {
    font-weight: 600;

    ${({ theme }) => css`
      font-size: ${theme.FONT_SIZE.TITLE_MD};
    `}
  }
`;
