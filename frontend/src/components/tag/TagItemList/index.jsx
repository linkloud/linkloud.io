import { Container } from "./style";
import Tag from "../TagItem";
import Anchor from "@/components/common/anchor";
import { ArrowRightIcon } from "@/static/svg";

const TagItemList = () => {
  const fakeTags = [
    "#무료",
    "#스프링",
    "#프론트엔드",
    "#디자인",
    "#아티클",
    "#리액트",
  ];

  return (
    <Container>
      <nav>
        <h1>태그</h1>
        <ul>
          {fakeTags &&
            fakeTags.map((tag) => (
              <li key={tag}>
                <Tag>{tag}</Tag>
              </li>
            ))}
        </ul>
      </nav>
      <div>
        <Anchor href="/tags">
          모든 태그 <ArrowRightIcon />
        </Anchor>
      </div>
    </Container>
  );
};

export default TagItemList;
