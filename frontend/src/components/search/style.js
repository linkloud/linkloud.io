import styled, { css } from "styled-components";

const sizeTable = {
  md: css`
    height: 40px;
  `,
  lg: css`
    height: 48px;
  `,
  xl: css`
    height: 56px;
  `,
};

const styleTable = {
  default: css`
    box-shadow: 0px 5px 15px rgba(100, 100, 111, 0.15);
  `,
  accent: css`
    ${({ theme }) => css`
      &:hover,
      &:focus {
        outline: none;
        border-color: ${theme.COLOR.ACCENT.HIGH};
        background-color: ${theme.COLOR.WHITE};
        box-shadow: 0px 0px 4px ${theme.COLOR.ACCENT.HIGH};
      }
    `}
  `,
};

export const Container = styled.div`
  display: flex;
  line-height: 28px;
  align-items: center;
  position: relative;
  width: 100%;

  & form {
    width: 100%;
  }

  & label {
    display: none;
  }

  ${({ theme }) => css`
    & svg {
      position: absolute;
      left: 1rem;
      width: 1rem;
      height: 1rem;
      stroke: ${theme.COLOR.TEXT.MEDIUM};
    }
  `}
`;

export const Input = styled.input`
  width: 100%;
  line-height: 28px;
  padding: 0 1rem;
  padding-left: 2.5rem;
  border-radius: 8px;
  outline: none;
  transition: 0.3s ease;

  /* clears the ‘X’ from Internet Explorer */
  &::-ms-clear {
    display: none;
    width: 0;
    height: 0;
  }
  &::-ms-reveal {
    display: none;
    width: 0;
    height: 0;
  }
  /* clears the ‘X’ from Chrome */
  &::-webkit-search-decoration,
  &::-webkit-search-cancel-button,
  &::-webkit-search-results-button,
  &::-webkit-search-results-decoration {
    display: none;
  }

  ${({ size, styleType }) => css`
    ${styleTable[styleType]}
    ${sizeTable[size]}
  `}

  ${({ theme }) => css`
    color: ${theme.COLOR.TEXT.HIGH};
    background-color: ${theme.COLOR.WHITE};
    border: 1px solid ${theme.COLOR.BORDER.MEDIUM};

    &::placeholder {
      color: ${theme.COLOR.TEXT.MEDIUM};
    }
  `}
`;
