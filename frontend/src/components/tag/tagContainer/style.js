import styled, { css } from "styled-components";

export const Container = styled.div`
  max-width: 588px;
  margin-top: 16px;
  margin-bottom: 40px;

  & nav {
    display: flex;
    align-items: center;
    justify-content: center;

    h1 {
      display: none;
    }
  }

  & ul {
    display: flex;
    gap: 10px;
  }

  /* 모든 태그 */
  & div:nth-child(2) {
    display: flex;
    justify-content: flex-end;
    align-items: center;
    width: auto;
    margin-top: 14px;

    ${({ theme }) => css`
      color: ${theme.COLOR.TEXT.MEDIUM};
      font-size: ${theme.FONT_SIZE.DESCRIPTION};

      & a {
        display: flex;
        align-items: center;
        margin-left: auto;
      }

      & svg {
        stroke: ${theme.COLOR.TEXT.MEDIUM};
        width: 0.875rem;
        height: 0.875rem;
      }

      &:hover svg path {
        stroke: ${theme.COLOR.ACCENT.HIGH};
      }
    `}
  }
`;
