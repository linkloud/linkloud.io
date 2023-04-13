import styled, { css } from "styled-components";

const colorByStyleType = (styleType, theme) => {
  switch (styleType) {
    case "default":
      return theme.COLOR.ACCENT.HIGH;
    case "success":
      return theme.COLOR.SUCCESS.MEDIUM;
    case "error":
      return theme.COLOR.ERROR.MEDIUM;
  }
};

export const Container = styled.div`
  position: relative;
  margin: 20px;

  /* input */
  input {
    display: block;
    width: 100%;
    padding: 10px;
    font-size: 16px;
    border: none;
    border-bottom: 2px solid #ccc;
    outline: none;
    background-color: transparent;
  }

  /* label */
  label {
    position: absolute;
    top: 0;
    left: 0;
    color: rgba(204, 204, 204, 0);
    pointer-events: none;
    transition: all 0.3s ease;
  }

  /* input bar highlight */
  ${({ theme, styleType }) => css`
    span {
      position: absolute;
      bottom: 0;
      left: 0;
      height: 2px;
      width: 0;
      background-color: ${colorByStyleType(styleType, theme)};
      transition: all 0.3s ease;
    }
  `}
  /* input:focus styles */
  ${({ theme, styleType }) => css`
    input:focus + label {
      top: -20px;
      font-size: ${theme.FONT_SIZE.DESCRIPTION};
      color: ${colorByStyleType(styleType, theme)};
    }
  `}

  input:focus::placeholder {
    color: transparent;
  }

  input:focus + label + span {
    width: 100%;
  }
`;
