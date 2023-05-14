import { useState } from "react";
import { useParams } from "react-router-dom";

import MoreButton from "./components/MoreButton";
import MemberActionMenu from "./components/MemberActionMenu";
import UserProfile from "@/common/components/user/UserProfile";
import AnchorSelectable from "@/common/components/anchor/AnchorSelectable";

const MemberProfilePage = () => {
  const params = useParams();
  const [isActionMenuVisible, setIsActionMenuVisible] = useState(false);

  const { name = "닉네임" } = params;

  const handleClickMoreButton = () => {
    setIsActionMenuVisible((prev) => !prev);
  };

  const handleLeaveActionMenu = () => {
    setIsActionMenuVisible(false);
  };

  return (
    <>
      <section className="w-full max-w-7xl h-52">
        <h1 className="hidden">member {name} profile</h1>
        <div className="flex justify-start items-center w-full h-full px-6 mx-auto">
          <UserProfile size="lg"></UserProfile>
          <h2 className="ml-4 font-semibold text-2xl">{name}</h2>
          <div className="relative">
            <MoreButton onClick={handleClickMoreButton} />
            {isActionMenuVisible && (
              <MemberActionMenu handleMouseLeave={handleLeaveActionMenu} />
            )}
          </div>
        </div>
      </section>
      <section className="w-full max-w-7xl px-6">
        <h1 className="hidden">{name} activity history section</h1>
        <div className="w-full mb-4">
          <nav>
            <h1 className="hidden">activity history list</h1>
            <ul className="flex">
              <li>
                <AnchorSelectable isSelected={true}>
                  {"등록한 링크"}
                </AnchorSelectable>
              </li>
            </ul>
          </nav>
        </div>
        {/* {fakeArticleList.map((a) => (
          <ArticleCard article={a} key={a.id}></ArticleCard>
        ))} */}
      </section>
    </>
  );
};

export default MemberProfilePage;
