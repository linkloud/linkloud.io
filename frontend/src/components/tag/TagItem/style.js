import styled, { css } from "styled-components";

export const Container = styled.a`
  display: inline-flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  padding: 0 15px;
  border-radius: 4px;
  line-height: 20px;
  height: 32px;
  cursor: pointer;

  ${({ theme }) => css`
    border: 1px solid ${theme.COLOR.BORDER.MEDIUM};
    background-color: ${theme.COLOR.WHITE};
    font-size: ${theme.FONT_SIZE.DESCRIPTION};

    &:hover {
      border-color: ${theme.COLOR.PRIMARY.HIHG};
    }
  `}
`;
