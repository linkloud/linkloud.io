import { Title, Description } from "./style";
import Modal from "@/components/common/modal";
import InputText from "@/components/common/input/InputText";
import Button from "@/components/common/button";
import { FlexRow } from "@/styles";

const TagRequestModal = ({ isOpened, onCloseReqTagModal }) => {
  return (
    <Modal isOpened={isOpened} onCloseModal={onCloseReqTagModal}>
      <Title>새 태그를 요청</Title>
      <InputText labelText={"태그명"} />
      <Description>
        태그 업데이트 여부는 공지사항을 통해 확인하실 수 있습니다.
      </Description>
      <FlexRow
        css={{
          maxWidth: "100%",
          justifyContent: "flex-end",
          gap: "10px",
          marginTop: "16px",
        }}
      >
        <Button styleType="line" size="sm" onClick={onCloseReqTagModal}>
          닫기
        </Button>
        <Button styleType="fill" size="sm">
          요청
        </Button>
      </FlexRow>
    </Modal>
  );
};

export default TagRequestModal;
