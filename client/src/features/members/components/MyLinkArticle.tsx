import { useState, useEffect, MouseEvent } from "react";
import { toast } from "react-toastify";

import { Article, ReadStatus as Status } from "@/features/articles/types";
import { LinkArticle, useArticle } from "@/features/articles";

import { ReadStatus } from "./ReadStatus";
import { ActionMenu, ActionMenuItem } from "@/components/ActionMenu";
import {
  BookIcon,
  BookOpenCheckIcon,
  BookOpenIcon,
  EditIcon,
  MoreHorizonIcon,
  TrashIcon,
} from "@/assets/svg";
import memberApi from "../apis";
import useAuthStore from "@/stores/useAuthStore";

interface MyLinkArticleProps {
  article: Article;
  onUpdateReadStatus: (id: number, newStatus: Status) => void;
}

export const MyLinkArticle = ({
  article,
  onUpdateReadStatus,
}: MyLinkArticleProps) => {
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);

  const { articleError, handleClickArticle } = useArticle();
  const { id: memberId } = useAuthStore((state) => state.userInfo);

  const handleActionMenu = (value: boolean) => {
    setIsActionMenuVisible(value);
  };

  const handleReadStatus =
    (e: MouseEvent) => async (articleId: number, status: Status) => {
      e.preventDefault();
      try {
        await memberApi.updateReadStatus({ id: memberId, articleId, status });
        onUpdateReadStatus(articleId, status);
        handleActionMenu(false);
        toast.success("읽기 상태가 업데이트되었습니다.");
      } catch (e) {
        console.log(e);
        toast.error(
          "읽기 상태 업데이트에 실패했습니다. 잠시 후 다시 시도해 주세요."
        );
      }
    };

  useEffect(() => {
    if (articleError)
      toast.error("서버 오류가 발생했습니다. 잠시후에 다시 시도해주세요.");
  }, [articleError]);

  let ReadActionItems = null;
  switch (article.articleStatus) {
    case "UNREAD":
      ReadActionItems = (
        <>
          <ActionMenuItem
            to="#"
            onClick={(e) => handleReadStatus(e)(article.id, "READING")}
          >
            <BookOpenIcon className="mr-2 stroke-neutral-800 h-5 w-5" />
            읽는 중
          </ActionMenuItem>
          <ActionMenuItem
            to="#"
            onClick={(e) => handleReadStatus(e)(article.id, "READ")}
          >
            <BookOpenCheckIcon className="mr-2 stroke-neutral-800 h-5 w-5" />
            읽음
          </ActionMenuItem>
        </>
      );
      break;
    case "READ":
      ReadActionItems = (
        <ActionMenuItem
          to="#"
          onClick={(e) => handleReadStatus(e)(article.id, "UNREAD")}
        >
          <BookIcon className="mr-2 stroke-neutral-800 h-5 w-5" />
          읽기 해제
        </ActionMenuItem>
      );
      break;
    case "READING":
      ReadActionItems = (
        <>
          <ActionMenuItem
            to="#"
            onClick={(e) => handleReadStatus(e)(article.id, "READ")}
          >
            <BookOpenCheckIcon className="mr-2 stroke-neutral-800 h-5 w-5" />
            읽음
          </ActionMenuItem>
          <ActionMenuItem
            to="#"
            onClick={(e) => handleReadStatus(e)(article.id, "UNREAD")}
          >
            <BookIcon className="mr-2 stroke-neutral-800 h-5 w-5" />
            읽기 해제
          </ActionMenuItem>
        </>
      );
      break;
    default:
      break;
  }

  return (
    <LinkArticle
      article={article}
      ControlComponent={
        <>
          <button
            aria-label="더보기"
            type="button"
            className="flex justify-center items-center h-6 w-6"
            onClick={() => handleActionMenu(!isActionMenuVisible)}
          >
            <MoreHorizonIcon className="stroke-neutral-800 h-5 w-5" />
            <span className="sr-only">더보기</span>
          </button>
          {isActionMenuVisible && (
            <>
              <div
                className="fixed inset-0"
                onClick={() => handleActionMenu(false)}
              ></div>
              <div
                className="absolute z-10"
                onMouseLeave={() => handleActionMenu(false)}
              >
                <ActionMenu>
                  {/* <ActionMenuItem to="/links/edit">
                    <EditIcon className="mr-2 stroke-neutral-800 h-5 w-5" />
                    수정
                  </ActionMenuItem> */}
                  {ReadActionItems}
                  {/* <ActionMenuItem to="#">
                    <TrashIcon className="mr-2 stroke-neutral-800 h-5 w-5" />
                    삭제
                  </ActionMenuItem> */}
                </ActionMenu>
              </div>
            </>
          )}
          <ReadStatus status={article.articleStatus} />
        </>
      }
      onClick={() => handleClickArticle(article.id)}
    />
  );
};
