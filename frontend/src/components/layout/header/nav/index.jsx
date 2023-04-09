import { GnbUl, Nav } from "./style";
import Button from "@/components/common/button";

const HeaderNav = ({ role = "GUEST" }) => {
  return (
    <Nav>
      <h1>네비게이션</h1>
      <GnbUl>
        <li>
          <Button
            size="md"
            styleType="default"
            aria-haspopup="dialog"
            onClick={console.log("공지사항")}
          >
            공지사항
          </Button>
        </li>
        {role === "ADMIN" && (
          <li>
            <Button
              size="md"
              styleType="default"
              aria-haspopup="dialog"
              onClick={console.log("태그등록")}
            >
              태그 등록
            </Button>
          </li>
        )}
        <li>
          <Button
            size="md"
            styleType="default"
            aria-haspopup="dialog"
            onClick={console.log("링크 등록")}
          >
            링크 등록
          </Button>
        </li>
        {role === "GUEST" && (
          <li>
            <Button
              size="md"
              styleType="fill"
              aria-haspopup="dialog"
              onClick={console.log("로그인")}
            >
              로그인
            </Button>
          </li>
        )}
      </GnbUl>
    </Nav>
  );
};

export default HeaderNav;
