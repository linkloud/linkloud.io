import {
  Container,
  Thumbnail,
  ArticleInfo,
  Title,
  Description,
  Tags,
} from "./style";
import Bookmark from "./bookmark";

const ArticleCard = ({ article }) => {
  return (
    <Container>
      <Thumbnail></Thumbnail>
      <ArticleInfo>
        <Title>{article.title}</Title>
        <Description>{article.description}</Description>
        <Tags>태그 리스트</Tags>
      </ArticleInfo>
      <Bookmark></Bookmark>
    </Container>
  );
};

export default ArticleCard;
