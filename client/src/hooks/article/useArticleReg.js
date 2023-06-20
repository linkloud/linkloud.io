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
  const [formErrorMessage, setFormErrorMessage] = useState({
    title: null,
    url: null,
    description: null,
  });
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);
  const navigate = useNavigate();

  useEffect(() => {
    if (!form.title.trim() || form.title.length < 2 || form.title.length > 50) {
      setFormErrorMessage((prev) => ({
        ...prev,
        title: "이름을 2 ~ 50자 사이로 입력해주세요.",
      }));
      return;
    }

    setFormErrorMessage((prev) => ({ ...prev, title: null }));

    return () => {
      setFormErrorMessage((prev) => ({ ...prev, title: null }));
    };
  }, [form.title]);

  useEffect(() => {
    if (!form.url.trim()) {
      setFormErrorMessage((prev) => ({
        ...prev,
        url: "url을 입력해주세요.",
      }));
      return;
    }

    if (form.url.length > 255) {
      setFormErrorMessage((prev) => ({
        ...prev,
        url: "255자를 넘을 수 없습니다.",
      }));
      return;
    }

    const urlReg = /^(https?:\/\/)([\w.-]+)\.([a-zA-Z]{2,})(\/\S*)?$/;

    if (!urlReg.test(form.url)) {
      setFormErrorMessage((prev) => ({
        ...prev,
        url: "올바른 url 주소를 입력해주세요.",
      }));
      return;
    }

    return () => {
      setFormErrorMessage((prev) => ({ ...prev, url: null }));
    };
  }, [form.url]);

  useEffect(() => {
    if (!form.description.trim()) {
      setFormErrorMessage((prev) => ({
        ...prev,
        description: "설명을 입력해주세요.",
      }));
      return;
    }

    if (form.description.length > 255) {
      setFormErrorMessage((prev) => ({
        ...prev,
        description: "255자를 넘을 수 없습니다.",
      }));
      return;
    }

    setFormErrorMessage((prev) => ({ ...prev, description: null }));

    return () => {
      setFormErrorMessage((prev) => ({ ...prev, description: null }));
    };
  }, [form.description]);

  const handleRegisterArticle = async () => {
    try {
      setLoading(true);
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

  const isValid = !Object.values(formErrorMessage).some(
    (errorMessage) => errorMessage !== null
  );

  return {
    form,
    setForm,
    isValid,
    formErrorMessage,
    loading,
    registerArticleError: error,
    handleRegisterArticle,
  };
};

export default useArticleReg;
