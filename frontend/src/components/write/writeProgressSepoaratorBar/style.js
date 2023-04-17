import styled, { css } from "styled-components";

export const Container = styled.div`
  position: relative;
  height: 40px;
  width: 120px;
`;

export const Line = styled.div`
  position: absolute;
  top: 50%;
  transform: translateY(-50%);
  width: 100%;
  height: 1px;
  transition: all 0.3s;

  ${({ theme, isComplete }) => css`
    background-color: ${isComplete
      ? theme.COLOR.BORDER.HIGH
      : theme.COLOR.BORDER.MEDIUM};
  `}
`;
