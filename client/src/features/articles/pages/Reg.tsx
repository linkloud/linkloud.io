import { useEffect } from "react";
import { toast } from "react-toastify";

import { useArticleReg } from "../hooks";

import { Button } from "@/components/Button";
import { InputText } from "@/components/Input";
import { TagItem } from "@/features/tags";

import { PlusIcon } from "@/assets/svg";

const Reg = () => {
  const {
    form,
    enteredTagValue,
    formErrorMessage,
    submitArticleError,
    handleChangeForm,
    handleChangeTags,
    handleAddTag,
    handleRemoveTag,
    handleSubmitArticle,
  } = useArticleReg();

  const handleTagInputEnter = (e: React.KeyboardEvent<HTMLInputElement>) => {
    if (
      e.key === "Enter" &&
      e.currentTarget.id === "tag" &&
      e.nativeEvent.isComposing === false
    ) {
      e.preventDefault();
      handleAddTag();
    }
  };

  useEffect(() => {
    if (submitArticleError) {
      toast.error("서버 오류가 발생했습니다. 잠시후에 다시 시도해주세요.", {
        toastId: "server",
      });
    }
  }, [submitArticleError]);

  return (
    <section className="mt-12 md:mt-20 w-full max-w-3xl">
      <h1 className="sr-only">링크 등록</h1>
      <form
        onSubmit={(e) => e.preventDefault()}
        className="flex flex-col gap-6 overflow-hidden px-6"
      >
        <InputText
          id="link"
          label="링크"
          placeholder="링크를 입력하세요"
          required
          errorMessage={formErrorMessage.url}
          className="mt-8 w-full"
          onChange={handleChangeForm("url")}
        />
        <InputText
          id="title"
          label="제목"
          placeholder="제목을 입력하세요"
          required
          errorMessage={formErrorMessage.title}
          className="mt-8 w-full"
          onChange={handleChangeForm("title")}
        />
        <InputText
          id="description"
          label="설명"
          placeholder="간단한 설명을 입력하세요"
          errorMessage={formErrorMessage.description}
          className="mt-8 w-full"
          onChange={handleChangeForm("description")}
        />
        <div className="w-full ">
          <div className="h-20 w-full flex items-center">
            <div className="w-full">
              <InputText
                id="tag"
                label="태그"
                placeholder="태그를 입력하세요"
                value={enteredTagValue}
                errorMessage={formErrorMessage.tags}
                className="mt-8 w-full"
                onChange={handleChangeTags}
                onKeyDown={handleTagInputEnter}
              />
            </div>
            <div>
              <Button
                name="태그 등록"
                type="button"
                size="md"
                styleName="outline-neutral"
                className="ml-10"
                onClick={handleAddTag}
              >
                <PlusIcon className="h-5 w-5 stroke-neutral-600" />
              </Button>
            </div>
          </div>

          {form.tags.length > 0 && (
            <ul className="mt-4 flex flex-wrap gap-2">
              {form.tags.map((tag) => (
                <li key={tag}>
                  <TagItem
                    name={tag}
                    size="md"
                    onRemove={() => handleRemoveTag(tag)}
                  />
                </li>
              ))}
            </ul>
          )}
        </div>

        <div>
          <Button
            name="링크 등록"
            type="submit"
            size="lg"
            styleName="solid"
            disabled={false}
            className="fixed md:static bottom-20 w-[calc(100%-3rem)] md:w-full md:mt-8"
            onClick={handleSubmitArticle}
          >
            등록
          </Button>
        </div>
      </form>
    </section>
  );
};

export default Reg;
