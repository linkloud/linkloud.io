import { useState, useEffect } from "react";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";

import useModal from "@/hooks/useModal";
import useTagList from "@/hooks/tag/useTagList";
import useAuthStore from "@/stores/useAuthStore";

import { registerArticle } from "@/service/api";

import ArticleRegConfirmModal from "@/common/components/article/ArticleRegConfirmModal";
import InputText from "@/common/components/input/InputText";
import Button from "@/common/components/button";
import { ERROR_CODE, ROLE } from "@/common/constants";

const LinksRegPage = () => {
  const [title, setTitle] = useState("");
  const [url, setUrl] = useState("");
  const [description, setDescription] = useState("");

  const navigate = useNavigate();

  const { isOpened: isArticleRegConfirmModalOpened, toggleModal } = useModal();
  const { tagList, fetchTagListError } = useTagList({
    page: 1,
    size: 10,
    sortBy: "popularity",
  });
  const { userInfo } = useAuthStore();

  useEffect(() => {
    if (userInfo.role === ROLE.ANONYMOUS || userInfo.role === ROLE.GUEST) {
      toast.error("가입후 3일이 지나야 링크를 등록할 수 있습니다.", {
        position: toast.POSITION.TOP_RIGHT,
        autoClose: 3000,
      });
      navigate("/");
    }
  }, [userInfo]);

  const handleTitleChange = (e) => {
    setTitle(e.target.value);
  };

  const handleUrlChange = (e) => {
    setUrl(e.target.value);
  };

  const handleDescriptionChange = (e) => {
    setDescription(e.target.value);
  };

  const handleRegisterArticle = async () => {
    //TODO: 예외처리
    //TODO: 유효성검사
    const { data } = await registerArticle({ title, url, description }).catch(
      (e) => {
        console.log(e);
      }
    );
    navigate("/");
    toast.success("링크 등록이 완료되었습니다.", {});
  };

  return (
    <>
      <section className="mt-20 px-6 w-full max-w-3xl">
        <h1 className="sr-only">link article register section</h1>
        <form>
          <p className="text-xl md:text-2xl font-semibold">
            게시하려는 링크의 이름과 주소를 작성해주세요.
          </p>
          <InputText
            labelText="이름"
            className="mt-8"
            onChange={handleTitleChange}
          />
          <InputText
            labelText="주소 URL"
            className="mt-8"
            onChange={handleUrlChange}
          />
          <p className="my-8 text-xl md:text-2xl font-semibold">
            간단한 한 줄 설명을 작성해주세요.
          </p>
          <InputText
            labelText="설명"
            className="mt-8"
            onChange={handleDescriptionChange}
          />
          <div className="mt-8 flex justify-end w-full">
            <Button onClick={toggleModal} size="lg" styleType="fill">
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
