import { useState, useEffect } from "react";
import { useNavigate } from "react-router-dom";

import articleApi from "@/service/api/article";

const useArticleReg = () => {
  const [form, setForm] = useState({
    title: "",
    url: "",
    description: "",
    tags: [],
  });
  const [inputTagsValue, setInputTagsValue] = useState("");
  const [formErrorMessage, setFormErrorMessage] = useState({
    title: null,
    url: null,
    description: null,
    tags: null,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  const validateForm = ({ title, url, description, tags }) => {
    let errorMessages = {
      title: null,
      url: null,
      description: null,
    };

    if (!title.trim() || title.length < 2 || title.length > 50) {
      errorMessages.title = "이름을 2 ~ 50자 사이로 입력해주세요.";
    }

    if (!url.trim()) {
      errorMessages.url = "url을 입력해주세요.";
    } else if (form.url.length > 255) {
      errorMessages.url = "255자를 넘을 수 없습니다.";
    } else {
      const urlReg =
        /^https?:\/\/(?:www\.)?[-a-zA-Z0-9@:%._\+~#=]{1,256}\.[a-zA-Z0-9()]{1,6}\b(?:[-a-zA-Z0-9()@:%_\+.~#?&\/=]*)$/;
      if (!urlReg.test(url)) {
        errorMessages.url = "올바른 url 주소를 입력해주세요.";
      }
    }

    if (description.length > 255) {
      errorMessages.description = "255자를 넘을 수 없습니다.";
    }

    setFormErrorMessage(errorMessages);

    return !Object.values(errorMessages).some(
      (errorMessage) => errorMessage !== null
    );
  };

  const handleChangeForm = (key) => (e) => {
    setForm({ ...form, [key]: e.target.value });
  };

  const handleChangeTags = (e) => {
    setInputTagsValue(e.target.value);
  };

  const handleAddTag = () => {
    const tag = inputTagsValue.trim().replace(/\s+/g, "-");
    const existingTags = form.tags.map((t) => t.toLowerCase());

    if (
      tag.length < 2 ||
      tag.length > 20 ||
      !/^[a-zA-Z0-9ㄱ-힣-]+$/.test(tag)
    ) {
      setFormErrorMessage((prev) => ({
        ...prev,
        tags: "태그는 2자 이상 20자 이하의 한글, 영문, 숫자, 하이픈(-)만 입력 가능합니다.",
      }));
      return;
    }

    if (form.tags.length >= 5) {
      setFormErrorMessage((prev) => ({
        ...prev,
        tags: "최대 5개까지 등록 가능합니다.",
      }));
      return;
    }

    if (existingTags.includes(tag.toLocaleLowerCase())) {
      setFormErrorMessage((prev) => ({
        ...prev,
        tags: "중복된 태그입니다.",
      }));
      return;
    }

    setForm((prev) => ({
      ...prev,
      tags: [...prev.tags, tag],
    }));
    setInputTagsValue("");
    setFormErrorMessage((prev) => ({
      ...prev,
      tags: null,
    }));
  };

  const handleRemoveTag = (tag) => {
    setForm((prev) => ({
      ...prev,
      tags: form.tags.filter((t) => t !== tag),
    }));
  };

  const handleSubmitArticle = async (e) => {
    e.preventDefault();
    setLoading(true);

    const isValid = validateForm(form);
    if (!isValid) {
      setLoading(false);
      return false;
    }

    try {
      await articleApi.create(form);
      navigate("/");
      return true;
    } catch (error) {
      setError(error);
      return false;
    } finally {
      setLoading(false);
    }
  };

  return {
    form,
    setForm,
    inputTagsValue,
    formErrorMessage,
    loading,
    submitArticleError: error,
    handleChangeForm,
    handleChangeTags,
    handleAddTag,
    handleRemoveTag,
    handleSubmitArticle,
  };
};

export default useArticleReg;
