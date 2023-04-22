import styled, { css } from "styled-components";

export const Container = styled.div`
  box-shadow: 0px 4px 7px -1px rgba(0, 0, 0, 0.15),
    0px 2px 4px -1px rgba(0, 0, 0, 0.06);
  display: none;
  position: absolute;
  top: 50px;
  left: 0;
  width: 100%;
  max-width: 100%;
  padding: 16px;
  border-radius: 8px;
  transform: translateY(30%);

  ${({ theme }) => css`
    background-color: ${theme.COLOR.WHITE};
    border: 1px solid ${theme.COLOR.BORDER.MEDIUM};
  `}
`;

export const Description = styled.span`
  margin-left: 8px;
  ${({ theme }) => css`
    color: ${theme.COLOR.TEXT.MEDIUM};
  `}
`;
