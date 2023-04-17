import styled, { css } from "styled-components";

const sizeTable = {
  xs: css`
    width: 16px;
    height: 16px;
    ${({ theme }) => css`
      font-size: ${theme.FONT_SIZE.CAPTION};
    `}
  `,
  sm: css`
    width: 24px;
    height: 24px;
    ${({ theme }) => css`
      font-size: ${theme.FONT_SIZE.DESCRIPTION};
    `}
  `,
  md: css`
    width: 32px;
    height: 32px;
  `,
  lg: css`
    width: 40px;
    height: 40px;
    ${({ theme }) => css`
      font-size: ${theme.FONT_SIZE.TITLE_SM};
    `}
  `,
};

const styleTable = {
  primary: css`
    ${({ theme }) => css`
      background-color: ${theme.COLOR.PRIMARY.HIGH};
      color: ${theme.COLOR.TEXT.INVERTED};
    `}
  `,
  suc: css`
    ${({ theme }) => css`
      background-color: ${theme.COLOR.SUC.LOW};
      color: ${theme.COLOR.SUC.MEDIUM};
    `}
  `,
  warn: css`
    ${({ theme }) => css`
      background-color: ${theme.COLOR.WARN.MEDIUM};
      color: ${theme.COLOR.TEXT.INVERTED};
    `}
  `,
  disabled: css`
    ${({ theme }) => css`
      background-color: ${theme.COLOR.TEXT.DISABLED};
      color: ${theme.COLOR.TEXT.INVERTED};
    `}
  `,
};

export const Container = styled.span`
  display: inline-flex;
  justify-content: center;
  align-items: center;
  border-radius: 50%;
  font-weight: 600;

  ${({ size, styleType }) => css`
    ${sizeTable[size]}
    ${styleTable[styleType]}
  `};
`;
