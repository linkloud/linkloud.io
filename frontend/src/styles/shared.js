import styled from "styled-components";

export const FlexRow = styled.div`
  display: flex;
  ${({ css }) => css}
`;

export const FlexColumn = styled.div`
  display: flex;
  flex-direction: column;
  ${({ css }) => css}
`;
