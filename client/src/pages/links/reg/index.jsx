import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import useArticleReg from "@/hooks/article/useArticleReg";
import useTags from "@/hooks/tag/useTags";
import useAuthStore from "@/stores/useAuthStore";
import { isServerError } from "@/service/request/helper";

import InputText from "@/common/components/input/InputText";
import TagItem from "@/common/components/tag/TagItem";
import Button from "@/common/components/button";
import { PlusIcon } from "@/static/svg";

import { ROLE, ERROR_CODE } from "@/common/constants";

const LinksRegPage = () => {
  const { userInfo, isAuthLoading } = useAuthStore((state) => ({
    userInfo: state.userInfo,
    isAuthLoading: state.isAuthLoading,
  }));

  const navigate = useNavigate();
  const {
    form,
    setForm,
    inputTagsValue,
    formErrorMessage,
    submitArticleError,
    handleChangeForm,
    handleChangeTags,
    handleAddTag,
    handleRemoveTag,
    handleSubmitArticle,
  } = useArticleReg();

  const { tags, fetchTagsError } = useTags({
    page: 1,
    size: 10,
    sortBy: "popularity",
  });

  useEffect(() => {
    if (!isAuthLoading && userInfo.role === ROLE.NEW_MEMBER) {
      toast.error("가입후 3일이 지나야 링크를 등록할 수 있습니다.", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: 3000,
      });
      navigate("/");
    }
  }, [userInfo, isAuthLoading]);

  const handleInputTags = (e) => {
    if (e.key === "Enter") {
      e.preventDefault();
      handleAddTag();
    }
  };

  useEffect(() => {
    if (submitArticleError && isServerError(submitArticleError)) {
      toast.error("서버 오류가 발생했습니다. 잠시후에 다시 시도해주세요.", {
        toastId: ERROR_CODE.SERVER_ERROR,
      });
    }
  }, [submitArticleError]);

  return (
    <>
      <section className="mt-12 md:mt-20 w-full max-w-3xl">
        <h1 className="sr-only">link article register section</h1>
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
                  value={inputTagsValue}
                  errorMessage={formErrorMessage.tags}
                  className="mt-8 w-full"
                  onChange={handleChangeTags}
                  onKeyPress={handleInputTags}
                />
              </div>
              <div>
                <Button
                  onClick={handleAddTag}
                  type="button"
                  size="md"
                  styleType="lined"
                  className="ml-10"
                >
                  <PlusIcon className="h-5 w-5 stroke-gray-800" />
                </Button>
              </div>
            </div>

            {form.tags.length > 0 && (
              <ul className="mt-4 flex flex-wrap gap-2">
                {form.tags.map((tag) => (
                  <li key={tag}>
                    <TagItem tag={tag} onRemove={() => handleRemoveTag(tag)} />
                  </li>
                ))}
              </ul>
            )}
          </div>

          <div className="">
            <Button
              type="submit"
              size="lg"
              styleType="fill"
              disabled={false}
              className="fixed md:static bottom-4 w-[calc(100%-3rem)] md:w-full md:mt-8"
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

export default LinksRegPage;
