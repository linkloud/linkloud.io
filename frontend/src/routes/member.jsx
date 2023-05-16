import MemberProfilePage from "@/pages/members/name";

const members = {
  path: "/members",
  children: [
    {
      path: ":nickname",
      element: <MemberProfilePage />,
    },
  ],
};

export default members;
