import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import useArticleReg from "@/hooks/article/useArticleReg";
import useModal from "@/hooks/useModal";
import useTags from "@/hooks/tag/useTags";
import useAuthStore from "@/stores/useAuthStore";
import { isServerError } from "@/service/request/helper";

import ArticleRegConfirmModal from "@/common/components/article/ArticleRegConfirmModal";
import InputText from "@/common/components/input/InputText";
import Button from "@/common/components/button";

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
    isValid,
    formErrorMessage,
    registerArticleError,
    handleRegisterArticle,
  } = useArticleReg();
  const { tags, fetchTagsError } = useTags({
    page: 1,
    size: 10,
    sortBy: "popularity",
  });
  const {
    isOpened: isArticleRegConfirmModalOpened,
    openModal,
    closeModal,
  } = useModal();

  useEffect(() => {
    if (!isAuthLoading && userInfo.role === ROLE.NEW_MEMBER) {
      toast.error("가입후 3일이 지나야 링크를 등록할 수 있습니다.", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: 3000,
      });
      navigate("/");
    }
  }, [userInfo, isAuthLoading]);

  useEffect(() => {
    if (registerArticleError && isServerError(registerArticleError)) {
      toast.error("서버 오류가 발생했습니다. 잠시후에 다시 시도해주세요.", {
        toastId: ERROR_CODE.SERVER_ERROR,
      });
    }
  }, [registerArticleError]);

  const handleChange = (key) => (e) => {
    setForm({ ...form, [key]: e.target.value });
  };
  const handleChangeTagList = (tag) => {
    setForm({ ...form, tags: [...form.tags, tag] });
  };

  const handlerArticleSubmit = async () => {
    const result = await handleRegisterArticle();
    if (result) toast.success("링크가 등록되었습니다");
  };

  return (
    <>
      <section className="mt-20 px-6 w-full max-w-3xl">
        <h1 className="sr-only">link article register section</h1>
        <form>
          <p className="text-xl md:text-2xl font-semibold">
            게시하려는 링크의 이름과 주소를 작성해주세요.{" "}
          </p>
          <InputText
            onChange={handleChange("title")}
            labelText="이름"
            validMessage={formErrorMessage.title}
            className="mt-8"
          />
          <InputText
            onChange={handleChange("url")}
            labelText="주소 URL"
            validMessage={formErrorMessage.url}
            className="mt-8"
          />
          <p className="my-8 text-xl md:text-2xl font-semibold">
            간단한 한 줄 설명을 작성해주세요.
          </p>
          <InputText
            onChange={handleChange("description")}
            labelText="설명"
            validMessage={formErrorMessage.description}
            className="mt-8"
          />
          <div className="mt-8 flex justify-end w-full">
            <Button
              onClick={openModal}
              size="lg"
              styleType="fill"
              disabled={!isValid}
            >
              계속
            </Button>
          </div>
        </form>
      </section>

      {isArticleRegConfirmModalOpened && (
        <ArticleRegConfirmModal
          isOpened={isArticleRegConfirmModalOpened}
          popularTags={[]}
          onClose={closeModal}
          onAddTag={handleChangeTagList}
          onRegister={handlerArticleSubmit}
        />
      )}
    </>
  );
};

export default LinksRegPage;
