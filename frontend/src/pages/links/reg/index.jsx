import { useEffect } from "react";
import { useNavigate } from "react-router-dom";
import { toast } from "react-toastify";

import useArticleReg from "@/hooks/article/useArticleReg";
import useModal from "@/hooks/useModal";
import useTagList from "@/hooks/tag/useTagList";
import useAuthStore from "@/stores/useAuthStore";

import ArticleRegConfirmModal from "@/common/components/article/ArticleRegConfirmModal";
import InputText from "@/common/components/input/InputText";
import Button from "@/common/components/button";
import { ROLE } from "@/common/constants";

const LinksRegPage = () => {
  const { form, setForm, isValid, formErrorMessage, handleRegisterArticle } =
    useArticleReg();
  const navigate = useNavigate();
  const { tagList, fetchTagListError } = useTagList({
    page: 1,
    size: 10,
    sortBy: "popularity",
  });
  const { userInfo } = useAuthStore();
  const { isOpened: isArticleRegConfirmModalOpened, toggleModal } = useModal();

  useEffect(() => {
    if (userInfo.role === ROLE.NEW_MEMBER) {
      toast.error("가입후 3일이 지나야 링크를 등록할 수 있습니다.", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: 3000,
      });
      navigate("/");
    }
  }, [userInfo]);

  return (
    <>
      <section className="mt-20 px-6 w-full max-w-3xl">
        <h1 className="sr-only">link article register section</h1>
        <form>
          <p className="text-xl md:text-2xl font-semibold">
            게시하려는 링크의 이름과 주소를 작성해주세요.{" "}
          </p>
          <InputText
            onChange={(e) => setForm({ ...form, title: e.target.value })}
            labelText="이름"
            validMessage={formErrorMessage.title}
            className="mt-8"
          />
          <InputText
            onChange={(e) => setForm({ ...form, url: e.target.value })}
            labelText="주소 URL"
            validMessage={formErrorMessage.url}
            className="mt-8"
          />
          <p className="my-8 text-xl md:text-2xl font-semibold">
            간단한 한 줄 설명을 작성해주세요.
          </p>
          <InputText
            onChange={(e) => setForm({ ...form, description: e.target.value })}
            labelText="설명"
            validMessage={formErrorMessage.description}
            className="mt-8"
          />
          <div className="mt-8 flex justify-end w-full">
            <Button
              onClick={toggleModal}
              size="lg"
              styleType="fill"
              disabled={!isValid}
            >
              계속
            </Button>
          </div>
        </form>
      </section>
      <ArticleRegConfirmModal
        isOpened={isArticleRegConfirmModalOpened}
        tagList={tagList}
        onClose={toggleModal}
        onRegister={handleRegisterArticle}
      />
    </>
  );
};

export default LinksRegPage;
