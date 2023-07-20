import { useEffect } from "react";
import { toast } from "react-toastify";

import { useArticleReg } from "../hooks";

import { Button } from "@/components/Button";
import { InputText } from "@/components/Input";
import { TagItem } from "@/features/tags";
import { Head } from "@/components/Head";

const Reg = () => {
  const {
    form,
    enteredTagValue,
    formErrorMessage,
    submitArticleError,
    handleChangeForm,
    handleChangeTags,
    handleSubmitArticle,
  } = useArticleReg();

  useEffect(() => {
    if (submitArticleError) {
      toast.error("서버 오류가 발생했습니다. 잠시후에 다시 시도해주세요.", {
        toastId: "server",
      });
    }
  }, [submitArticleError]);

  return (
    <>
      <Head title="링클라우드 | 링크 등록" />
      <section className="mt-12 md:mt-20 h-full w-full max-w-3xl">
        <h1 className="sr-only">링크 등록</h1>
        <form
          onSubmit={(e) => e.preventDefault()}
          className="flex flex-col gap-2 overflow-hidden px-6"
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
          <InputText
            id="tag"
            label="태그"
            placeholder="#태그1 #태그2 ..."
            value={enteredTagValue}
            errorMessage={formErrorMessage.tags}
            className="mt-8 w-full"
            onChange={handleChangeTags}
          />

          {form.tags.length > 0 && (
            <ul className="mt-4 flex flex-wrap gap-2">
              {form.tags.map((tag) => (
                <li key={tag}>
                  <TagItem name={tag} size="md" />
                </li>
              ))}
            </ul>
          )}

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
    </>
  );
};

export default Reg;
