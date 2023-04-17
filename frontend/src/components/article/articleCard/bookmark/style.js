import styled, { css } from "styled-components";
import { media } from "@/styles";

export const Container = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  gap: 1rem;
  width: 48px;
  margin-top: 10px;
  margin-bottom: 10px;
  cursor: pointer;

  ${({ theme }) => css`
    border-left: 1px solid ${theme.COLOR.BORDER.LOW};

    & svg {
      width: 1.5rem;
      height: 1.5rem;
      stroke: ${theme.COLOR.TEXT.MEDIUM};
    }
  `}
`;
export const Counts = styled.span`
  ${({ theme }) => css`
    font-size: ${theme.FONT_SIZE.DESCRIPTION};

    ${media.small} {
      font-size: ${theme.FONT_SIZE.CAPTION};
    }
  `}
`;
