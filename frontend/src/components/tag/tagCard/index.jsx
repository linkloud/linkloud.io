import { Container } from "./style";
import Tag from "../TagItem";

const TagCard = ({ tag }) => {
  return (
    <Container>
      <Tag>{tag.name}</Tag>
      <span>{tag.description}</span>
      <span>링크 {tag.articleCounts}개</span>
    </Container>
  );
};

export default TagCard;
