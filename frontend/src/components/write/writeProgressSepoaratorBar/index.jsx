import { Container, Line } from "./style";

const WriteProgressSepoaratorBar = ({ isComplete = false }) => {
  return (
    <Container>
      <Line isComplete={isComplete} />
    </Container>
  );
};

export default WriteProgressSepoaratorBar;
