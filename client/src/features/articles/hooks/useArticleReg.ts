import { ChangeEvent, FormEvent, MouseEventHandler, useState } from "react";
import { useNavigate } from "react-router-dom";

import { ROUTE_PATH } from "@/routes/constants";

import articleApi from "../apis";

interface Form {
  title: string;
  url: string;
  description: string;
  tags: string[];
}

export const useArticleReg = () => {
  const [form, setForm] = useState<Form>({
    title: "",
    url: "",
    description: "",
    tags: [],
  });
  const [formErrorMessage, setFormErrorMessage] = useState({
    title: "",
    url: "",
    description: "",
    tags: "",
  });
  const [enteredTagValue, setEnteredTagValue] = useState("");
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const validateForm = ({ title, url, description, tags }: Form) => {
    const errorMessages = {
      title: "",
      url: "",
      description: "",
      tags: "",
    };

    if (!title.trim() || title.length < 2 || title.length > 50) {
      errorMessages.title = "이름을 2 ~ 50자 사이로 입력해주세요.";
    }

    if (!url.trim()) {
      errorMessages.url = "url을 입력해주세요.";
    } else if (form.url.length > 255) {
      errorMessages.url = "255자를 넘을 수 없습니다.";
    } else {
      const regex =
        /^((http(s?))\:\/\/)([0-9a-zA-Z\-]+\.)+[a-zA-Z]{2,6}(\:[0-9]+)?(\/\S*)?$/;
      if (!regex.test(url)) {
        errorMessages.url = "올바른 url 주소를 입력해주세요.";
      }
    }

    if (description.length > 255) {
      errorMessages.description = "255자를 넘을 수 없습니다.";
    }

    setFormErrorMessage(errorMessages);
    return !Object.values(errorMessages).some(
      (errorMessage) => errorMessage !== "",
    );
  };

  const handleChangeForm =
    (key: string) => (e: ChangeEvent<HTMLInputElement>) => {
      setForm({ ...form, [key]: e.target.value });
    };

  const handleChangeTags = (e: ChangeEvent<HTMLInputElement>) => {
    const value = e.target.value;

    const findTagsRegex = /#[^\s#]+(?=\s|#|$)/g;
    const tags = value.match(findTagsRegex) || [];

    if (tags.length > 5) {
      setFormErrorMessage((prev) => ({
        ...prev,
        tags: "태그는 최대 5개까지 입력할 수 있습니다.",
      }));
      return;
    }

    const tagRegex = /^#[\wㄱ-ㅎㅏ-ㅣ가-힣-]{1,20}$/;
    const isValidLengthAndChar = tags.every((tag) => {
      return tagRegex.test(tag);
    });

    if (!isValidLengthAndChar) {
      setFormErrorMessage((prev) => ({
        ...prev,
        tags: "20자 미만의 한글, 영문, 숫자, 하이픈만 사용할 수 있습니다.",
      }));
      return;
    }

    const isNoDuplicate = tags.every((tag, index, self) => {
      return self.indexOf(tag) === index;
    });

    if (!isNoDuplicate) {
      setFormErrorMessage((prev) => ({
        ...prev,
        tags: "중복된 태그를 입력할 수 없습니다.",
      }));
      return;
    }

    const tagsWithoutHash = tags.map((tag) => tag.slice(1));
    setForm((prev) => ({ ...prev, tags: tagsWithoutHash }));
    setFormErrorMessage((prev) => ({ ...prev, tags: "" }));
    setEnteredTagValue(value);
  };

  const handleSubmitArticle = async () => {
    setLoading(true);

    const isValid = validateForm(form);

    if (!isValid) {
      setLoading(false);
      return;
    }

    try {
      await articleApi.create(form);
      navigate(ROUTE_PATH.LANDING);
    } catch (e: any) {
      setError(e);
    } finally {
      setLoading(false);
    }
  };

  return {
    form,
    enteredTagValue,
    formErrorMessage,
    loading,
    submitArticleError: error,
    handleChangeForm,
    handleChangeTags,
    handleSubmitArticle,
  };
};
