import styled from "styled-components";

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
