import { createGlobalStyle, css } from "styled-components";

const GlobalStyle = createGlobalStyle`
  *,
  *::before,
  *::after {
    box-sizing: border-box;
    ${({ theme }) => css`
      color: ${theme.COLOR.TEXT.HIGH};
      font-family: ${theme.FONT.PRIMARY}, -apple-system, BlinkMacSystemFont,
        system-ui, Roboto, "Helvetica Neue", "Segoe UI", "Apple SD Gothic Neo",
        "Noto Sans KR", "Malgun Gothic", "Apple Color Emoji", "Segoe UI Emoji",
        "Segoe UI Symbol", sans-serif;
    `}
  }
  
  body,
  h1,
  h2,
  h3,
  h4,
  p,
  figure,
  blockquote,
  dl,
  dd,
  ul,
  ol {
    margin: 0;
    padding: 0;
  }

  ul{
    list-style: none;
  }

  a {
    text-decoration: none;
    color: inherit;
  }

  input,
  button,
  textarea,
  select {
    font: inherit;
  }

  html, body {
  height: 100%;
  overflow: auto;
}
`;

export default GlobalStyle;
