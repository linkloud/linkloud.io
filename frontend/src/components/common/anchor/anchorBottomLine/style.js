import styled, { css } from "styled-components";

export const Container = styled.a`
  padding: 8px 12px;
  cursor: pointer;

  ${(props) =>
    props.isActive &&
    css`
      font-weight: 600;
      color: ${props.theme.COLOR.TEXT.HIGH};
      border-bottom: 2px solid ${props.theme.COLOR.PRIMARY.HIGH};
    `}

  ${(props) =>
    !props.isActive &&
    css`
      color: ${props.theme.COLOR.TEXT.MEDIUM};
    `}
`;
