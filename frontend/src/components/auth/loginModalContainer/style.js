import styled, { css } from "styled-components";
import { media } from "@/styles";

export const Title = styled.h1`
  display: none;
`;

export const SocialLoginSection = styled.section`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  gap: 20px;
  width: 360px;
  height: 100%;

  ${({ theme }) => css`
    & h1 {
      font-size: ${theme.FONT_SIZE.TITLE_SM};
      font-weight: 600;
      margin: 1rem auto;
    }
  `}

  ${media.small} {
    justify-content: center;
    width: auto;
  }
`;
