import { useState, useRef } from "react";
import { v4 as uuidv4 } from "uuid";

import Modal from "@/common/components/modal";
import Button from "@/common/components/button";
import TagItem from "@/common/components/tag/TagItem";

const ArticleRegConfirmModal = ({
  popularTags,
  isOpened,
  onClose,
  onAddTag,
  onRegister,
}) => {
  const [isFocused, setIsFocused] = useState(false);
  const [input, setInput] = useState("");
  const [inputError, setInputError] = useState("");
  const [tags, setTags] = useState([]);
  const textInput = useRef(null);

  const handleInput = (e) => {
    if (e.nativeEvent.isComposing) return;

    if (e.key === "Backspace" && input === "") removeLastTag();

    if (e.key === "Enter") {
      const newTag = input.replace(/\s/g, "-");
      handleAddTag(newTag);
    }
  };

  const handleAddTag = (newTag) => {
    // 태그는 최대 5개
    if (tags.length >= 5) {
      setInputError("태그는 최대 5개까지 입력 가능합니다");
      return false;
    }

    // 중복된 태그 name 입력 불가
    if (tags.find((tag) => tag.name === newTag)) {
      setInputError("중복된 태그를 입력했습니다");
      return false;
    }

    // 최소 2자, 최대 20자
    if (newTag.length < 2 || newTag.length > 20) {
      setInputError("태그는 최소 2자, 최대 20자여야 합니다");
      return false;
    }

    // 한글, 영어, 하이픈만 가능하며, 숫자가 들어갈 경우 반드시 한글 또는 영어가 포함되어야 함
    if (!/^(?=.*[a-zA-Z가-힣-])[a-zA-Z0-9가-힣-]+$/.test(newTag)) {
      setInputError(
        "태그는 한글, 영어, 하이픈(-)만 가능하며, 숫자가 들어갈 경우 반드시 한글 또는 영어가 포함되어야 합니다"
      );
      return false;
    }

    setTags([...tags, { id: uuidv4(), name: newTag }]);
    setInput("");
    setInputError("");
    onAddTag(newTag);
    return true;
  };

  const handleInputChange = (e) => {
    setInput(e.target.value);
    setInputError("");
  };

  const handleRemoveTag = (id) => {
    setTags(tags.filter((tag) => tag.id !== id));
  };

  const handleInputContainerClick = () => {
    textInput.current.focus();
  };

  const removeLastTag = () => {
    setTags(tags.slice(0, tags.length - 1));
  };

  const handleClose = () => {
    setIsFocused(false);
    setInput("");
    setInputError("");
    setTags([]);
    onClose();
  };

  return (
    <Modal isOpened={isOpened} onCloseModal={onClose}>
      {/* 제목 */}
      <h1 className="sr-only">글 등록 확인</h1>
      <label htmlFor="tags" className="self-start text-xl font-semibold">
        태그
        <span className="text-gray-600 text-sm"> (최대 5개)</span>
      </label>

      {/* 태그 입력 */}
      <div
        className={`mt-5 flex flex-wrap items-center gap-1 text-sm rounded-lg w-full border p-2.5 transition-colors cursor-text
        ${
          isFocused ? "border-gray-500 bg-white" : "bg-gray-50 border-gray-300"
        }`}
        onClick={handleInputContainerClick}
      >
        {tags?.map((tag) => (
          <div key={tag.id}>
            <TagItem tag={tag.name} onRemove={() => handleRemoveTag(tag.id)} />
          </div>
        ))}
        <div>
          <input
            type="text"
            id="tags"
            className="w-full bg-transparent focus:outline-none"
            placeholder="Enter로 태그를 추가"
            autoComplete="off"
            required
            ref={textInput}
            value={input}
            onChange={handleInputChange}
            onKeyDown={handleInput}
            onFocus={() => setIsFocused(true)}
            onBlur={() => setIsFocused(false)}
          />
        </div>
      </div>
      {inputError && <p className="mt-1 text-red-500">{inputError}</p>}

      {/* 인기 태그 목록 */}
      {popularTags && popularTags?.length > 0 ? (
        <div className="mt-5 flex flex-wrap w-full">
          <div>
            <span>인기태그:</span>
          </div>
          {popularTags?.map((tag, index) => (
            <div key={tag.id} className="ml-2 cursor-pointer">
              <span className="text-primary-600">
                {tag.name}
                {index !== popularTags.length - 1 && <>,</>}
              </span>
            </div>
          ))}
        </div>
      ) : null}

      {/* 버튼 */}
      <div className="mt-8 flex justify-end">
        <Button
          onClick={handleClose}
          size="md"
          styleType="lined"
          className="mr-3"
        >
          닫기
        </Button>
        <Button size="md" styleType="fill" onClick={onRegister}>
          등록
        </Button>
      </div>
    </Modal>
  );
};

export default ArticleRegConfirmModal;
