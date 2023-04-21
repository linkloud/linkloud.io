import styled, { css } from "styled-components";

export const Container = styled.div`
  width: 180px;
  height: 180px;
  padding: 20px;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  border-radius: 4px;

  ${({ theme }) => css`
    border: 1px solid ${theme.COLOR.BORDER.MEDIUM};

    span:last-child {
      font-size: ${theme.FONT_SIZE.CAPTION};
    }
  `}
`;
