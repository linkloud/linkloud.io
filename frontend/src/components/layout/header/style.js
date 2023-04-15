import styled, { css } from "styled-components";

export const Wrap = styled.header`
  position: sticky;
  z-index: 1;
  top: 0;
  background-color: white;
  ${({ theme }) => css`
    border-bottom: 1px solid ${theme.COLOR.BORDER.MEDIUM};
  `}
`;

export const HeaderContent = styled.div`
  max-width: 1200px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin: 0 auto;
  padding-left: 1rem;
  padding-right: 1rem;
  height: 72px;
`;

export const HeaderTitle = styled.h1`
  font-size: 0;
  & > a {
    display: block;
  }
`;

export const Nav = styled.nav`
  & > h1 {
    display: none;
  }
`;

export const GnbUl = styled.ul`
  display: flex;
  align-items: center;
  & button {
    margin-left: 12px;
  }
`;
