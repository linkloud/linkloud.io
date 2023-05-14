import MemberProfilePage from "@/pages/members/name";

const members = {
  path: "/members",
  children: [
    {
      path: ":name",
      element: <MemberProfilePage />,
    },
  ],
};

export default members;
