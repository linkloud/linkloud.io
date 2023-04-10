import styled, { css } from "styled-components";
import { media } from "@/styles/";

export const ModalWrap = styled.div`
  position: fixed;
  inset: 0px;
`;

/**
  dialog의 backdrop이 아직 파이어폭스만 지원함
  &::backdrop {
    position: fixed;
    inset: 0;
    background-color: rgba(0, 0, 0, 0.2);
  }
 */
export const ModalContent = styled.dialog`
  display: flex;
  flex-direction: column;
  border-radius: 8px;
  background-color: white;
  box-shadow: 0px 4px 7px -1px rgba(0, 0, 0, 0.25),
    0px 2px 4px -1px rgba(0, 0, 0, 0.06);
  max-width: 1200px;
  padding: 40px;
  z-index: 1000;
  top: 20%;
  ${({ theme }) => css`
    border: 1px solid ${theme.COLOR.BORDER.MEDIUM};
  `}

  ${media.small} {
    width: auto;
    height: 100%;
    top: auto;
  }
`;
