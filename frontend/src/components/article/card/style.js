import styled, { css, keyframes } from "styled-components";

const Gradient = keyframes`
	0% {
		background-position: 0% 50%;
	}
	50% {
		background-position: 100% 50%;
	}
	100% {
		background-position: 0% 50%;
	}
`;

export const Container = styled.article`
  display: flex;
  height: 80px;
  width: 100%;
  margin-top: 1.25rem;
  margin-bottom: 1.25rem;
  cursor: pointer;

  &:hover {
    background: linear-gradient(
      -45deg,
      rgba(78, 165, 250, 0.07),
      rgba(250, 78, 243, 0.05),
      rgba(75, 99, 193, 0.05),
      rgba(203, 237, 253, 0.08)
    );
    background-size: 400% 400%;
    animation: ${Gradient} 10s infinite;
    transition: all 1s;
  }
`;

export const Thumbnail = styled.div`
  min-width: 80px;
  height: 80px;
  background-color: grey;
`;

export const ArticleInfo = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  padding-left: 1rem;
`;

export const Title = styled.h1`
  font-weight: normal;

  ${({ theme }) => css`
    font-size: ${theme.FONT_SIZE.BODY};
  `}
`;

export const Description = styled.span`
  ${({ theme }) => css`
    color: ${theme.COLOR.TEXT.MEDIUM};
  `}
`;

export const Tags = styled.div`
  margin-top: auto;
  ${({ theme }) => css`
    font-size: ${theme.FONT_SIZE.DESCRIPTION};
    color: ${theme.COLOR.TEXT.LOW};
  `}
`;
