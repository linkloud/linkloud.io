import styled, { css } from "styled-components";

const styleTable = {
  disabled: css`
    ${({ theme }) => css`
      color: ${theme.COLOR.TEXT.DISABLED};
    `}
  `,
  inProgress: css`
    ${({ theme }) => css`
      color: ${theme.COLOR.PRIMARY.HIGH};
    `}
  `,
  complete: css`
    ${({ theme }) => css`
      color: ${theme.COLOR.PRIMARY.HIGH};
    `}
  `,
};

export const Container = styled.div`
  display: flex;
  font-weight: 600;
  align-items: center;

  & > span {
    transition: all 0.3s;
    margin-left: 8px;
  }

  & > span:nth-of-type(2) {
    ${({ styleType }) => css`
      ${styleTable[styleType]}
    `}
  }

  ${({ theme }) => css`
    font-size: ${theme.FONT_SIZE.TITLE_SM};
    color: ${theme.COLOR.TEXT.INVERTED};
    svg {
      stroke: ${theme.COLOR.TEXT.INVERTED};
    }
  `}
`;
