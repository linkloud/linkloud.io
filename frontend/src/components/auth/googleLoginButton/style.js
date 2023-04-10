import styled, { css } from "styled-components";

export const GoogleLoginBtn = styled.a`
  width: 100%;
  padding: 0.75rem 1.25rem;
  display: flex;
  align-items: center;
  justify-content: center;
  border: none;
  border-radius: 4px;
  background-color: transparent;
  cursor: pointer;
  transition: 0.2s;

  &:hover {
    background-color: #f5f5f5;
  }

  & svg {
    margin-right: 0.5rem;
  }

  ${({ theme }) => css`
    border: 1px solid ${theme.COLOR.BORDER.MEDIUM};
    background-color: ${theme.COLOR.WHITE};
    &:hover {
      background-color: ${theme.COLOR.ACTIVE};
    }
  `}
`;
