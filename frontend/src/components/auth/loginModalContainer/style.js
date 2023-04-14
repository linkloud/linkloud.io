import styled, { css } from "styled-components";
import { media } from "@/styles";

export const Title = styled.h1`
  ${({ theme }) => css`
    font-size: ${theme.FONT_SIZE.TITLE_LG};
    margin: auto;
  `}
`;

export const SocialLoginContainer = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  width: 360px;
  height: 100%;

  h2 {
    display: none;
  }

  & > p {
    margin: 1rem auto;
  }

  ${media.small} {
    justify-content: center;
    width: auto;
  }
`;
