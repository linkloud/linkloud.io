import styled, { css } from "styled-components";

export const AnchorDefault = styled.a`
  cursor: pointer;
  ${({ theme }) => css`
    &:hover {
      color: ${theme.COLOR.ACCENT.HIGH};
      text-decoration: underline;
    }
  `}
`;