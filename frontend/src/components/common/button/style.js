import styled, { css } from "styled-components";

const sizeTable = {
  xs: css`
    padding: 0.25rem 0.625rem;
    ${({ theme }) => css`
      font-size: ${theme.FONT_SIZE.CAPTION};
    `}
  `,
  sm: css`
    padding: 0.5rem 1rem;
    ${({ theme }) => css`
      font-size: ${theme.FONT_SIZE.DESCRIPTION};
    `}
  `,
  md: css`
    padding: 0.75rem 1.25rem;
    ${({ theme }) => css`
      font-size: ${theme.FONT_SIZE.BODY};
    `}
  `,
  lg: css`
    padding: 1rem 1.75rem;
    ${({ theme }) => css`
      font-size: ${theme.FONT_SIZE.BODY};
    `}
  `,
};

const styleTable = {
  default: css`
    ${({ theme }) => css`
      color: ${theme.COLOR.TEXT.MEDIUM};
      &: hover {
        color: ${theme.COLOR.PRIMARY.HIHG};
      } ;
    `};
  `,

  line: css`
    ${({ theme }) => css`
      border: 1px solid ${theme.COLOR.BORDER.MEDIUM};
      background-color: ${theme.COLOR.WHITE};
      &: hover {
        border: 1px solid ${theme.COLOR.PRIMARY.HIHG};
        color: ${theme.COLOR.PRIMARY.HIHG};
      } ;
    `};
  `,

  fill: css`
    ${({ theme }) => css`
      color: ${theme.COLOR.WHITE};
      background-color: ${theme.COLOR.PRIMARY.HIHG};
      &: hover {
        background-color: ${theme.COLOR.PRIMARY.MEDIUM};
      } ;
    `};
  `,
};

export const ButtonDefault = styled.button`
  border: none;
  border-radius: 4px;
  cursor: pointer;
  transition: 0.2s;
  background-color: transparent;

  ${({ size, styleType }) => css`
    ${sizeTable[size]}
    ${styleTable[styleType]}
  `}
`;

export default ButtonDefault;
