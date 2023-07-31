# ☁️ [LINKLOUD](https://linkloud.io)

<p align="center">
  <img width="124" height="124" src="https://avatars.githubusercontent.com/u/129953078?s=200&v=4">
</p>

## 🔍 프로젝트 소개

**링클라우드**는 웹 상의 개발, 디자인, 공부 등 다양하고 유용한 링크를 공유하고, 효과적으로 관리할 수 있는 서비스입니다.

## 기획 배경 및 동기

유용한 링크를 브라우저 북마크나 블로그, 노션 등으로 관리하는 것은 한계가 있습니다.

링크 분류와 찾는 것에 불편함이 있었고, 유용한 링크를 검색 엔진을 통해 찾거나 공유하려면 별도의 과정이 필요했습니다.

이런 문제를 해결하고자 특정 링크를 쉽게 보관, 관리하며 공유하는 링클라우드를 기획했습니다.

링클라우드를 사용하면, 이제 링크를 쉽게 저장, 분류하고 커뮤니티와 공유하여 더 좋은 정보를 얻을 수 있게 됩니다.
## ✔️ 기간

### 2023.04.05 ~ 2023.00.00

## 인원(소개)

<table>
  <tr>
    <td align="center"><b>Project Owner</b></td>
    <td align="center"><b>Developer</b></td>
    <td align="center"><b>Developer</b></td>
    <td align="center"><b>Developer</b></td>
</tr>
  <tr>
    <td>
        <a href="https://github.com/a-ryang">
            <img src="https://avatars.githubusercontent.com/u/105474635?v=4" width="100px" />
        </a>
    </td>
    <td>
        <a href="https://github.com/FaberJoo">
            <img src="https://avatars.githubusercontent.com/u/79781818?v=4" width="100px" />
        </a>
    </td>
    <td>
        <a href="https://github.com/Rope-player">
            <img src="https://avatars.githubusercontent.com/u/38906192?v=4" width="100px" />
        </a>
    </td>
    <td>
        <a href="https://github.com/als904204">
            <img src="https://avatars.githubusercontent.com/u/55350901?s=400&u=521657f21121efcef03c848800b3713f7ec094ec&v=4" width="100px" />
        </a>
    </td>
  </tr>

  <tr> 
    <td align="center"><a href="https://github.com/a-ryang">a-ryang</a></td>
    <td align="center"><a href="https://github.com/FaberJoo">FaberJoo</a></td>
    <td align="center"><a href="https://github.com/Rope-player">Rope-playe</a></td>
    <td align="center"><a href="https://github.com/als904204">als904204</a></td>
  </tr>
</table>

## 🌐 사용 기술

### Backend

<img src="https://img.shields.io/badge/Java-17-blue.svg?logo=java">
<img src="https://img.shields.io/badge/Gradle-7.6.1-green.svg?logo=gradle">
<img src="https://img.shields.io/badge/Spring_Boot-3.0.4-green.svg?logo=spring">
<img src="https://img.shields.io/badge/Spring_Web-green.svg?logo=spring">
<img src="https://img.shields.io/badge/Spring_Data_JPA-green.svg?logo=spring">
<img src="https://img.shields.io/badge/Spring_Security-green.svg?logo=spring">
<img src="https://img.shields.io/badge/Spring_Rest_Docs-green.svg?logo=spring">
<img src="https://img.shields.io/badge/Spring_Docs-green.svg?logo=spring">
<img src="https://img.shields.io/badge/JWT-orange.svg?logo=jsonwebtokens">

## 인프라

[![MariaDB](https://img.shields.io/badge/MariaDB-TODO-blue?logo=mariadb&logoColor=white)](https://mariadb.org/)
[![Docker](https://img.shields.io/badge/Docker-TODO-blue?logo=docker&logoColor=white)](https://www.docker.com/)

## 협업

![git](https://img.shields.io/badge/git-F05032?style=flat&logo=Git&logoColor=white)
![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=Plastic&logo=github&logoColor=white)
[![Discord](https://img.shields.io/badge/Discord-7289DA?logo=discord&logoColor=white)](https://discord.com/)
![Notion](https://img.shields.io/badge/Notion-000000?style=Plastic&logo=Notion&logoColor=white)
## 커밋 메세지 컨벤션

### 커밋 형식

커밋 메세지 형식은 다음을 따릅니다:

```bash
type: subject #issue-number

body

footer
```

- `type` : 커밋의 종류를 나타냅니다. 종류는 다음 항목을 참고해주세요.
- `subject` : 커밋의 요약을 나타냅니다.
- `iuuse-number` : 관련된 이슈 번호를 나타냅니다.
- `body` : 커밋의 자세한 내용을 나타냅니다. (선택)
- `footer` : 커밋과 관련된 이슈를 닫거나, 어떤 변경을 가져오는지 등 추가적인 정보를 나타냅니다. (선택)

예시는 다음과 같습니다:

> Header 컴포넌트를 개발, 이에 따른 스타일을 변경함

```bash
feat: Header 컴포넌트 구현 및 스타일 수정 #123

* 헤더 컴포넌트를 개발합니다. 새 컴포넌트에 맞게 style.css를 수정했습니다.

* Close #123
```

### 커밋 종류 (type)

- `feat` : 새로운 기능
- `fix` : 버그 수정
- `docs` : 문서만 변경
- `style` : 코드에 영향을 주지 않는 변경 (공백, 세미콜론, css 등)
- `refactor` : 코드 리팩토링. 코드의 기능 변경이 아닌 가독성을 높이거나 재사용성 향상, 주석 추가 등
- `test` : 테스트 추가, 기존 테스트 수정 등
- `cleanup` : 불필요 파일 삭제, 코드 삭제
- `chore` : 프로젝트 운영(유지보수, 개선) 업데이트. 빌드 설정, 의존성 변경, 스크립트 추가 등
# 테스트

![JUnit5](https://img.shields.io/badge/JUnit5-white?style=Plastic&logo=JUnit5)
![Postman](https://img.shields.io/badge/postman-white?style=Plastic&logo=postman)

## 🏗️ architecture

### Backend

TODO

### CI/CD

TODO

### ERD

ERD 사진추가
