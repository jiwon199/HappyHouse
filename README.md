# HappyHouse_관통_구미_05반_이동하_최지원

<br>

 
### 팀원
- 이동하
- 최지원

<br>

# 🏠 *Happiest House* 🏠
더 좋은 집을 찾기 위한 지침서.  
주택 거래 정보 플랫폼

SSAFY 8기 관통프로젝트 <br>
🥇 최우수상 수상

<img src="https://raw.githubusercontent.com/okanekudasai/happyimage/74c2d74e24e7f0b3a12bf8b8efcffc43efabb3b0/%EB%A9%94%EC%9D%B8%ED%8E%98%EC%9D%B4%EC%A7%80.png" width="700"> <br><br>

<br><br>
 
<h2> 1️⃣ 기획배경 </h2>

*한 곳에서 원하는 매매물건을 찾고 유저간의 소통의 공간을 마련해주는 서비스는 없을까?*
<br>

유저들이 각자 관심을 갖는 지역들의 매물들을 쉽게 한번에 찾아 낼 수 있게 편의성을 높이고자 하였고, 관련 뉴스와 편의시설 정보 제공으로 서비스 제공의 질을 높였다. 또한 사용자간의 소통의 장을 마련하고 관리자와 직접 대화를 가능하게 구현하였다.

<br>

## 2️⃣ 목표

1. 다양한 매물 정보 검색
    - 매물의 가격과 사진 등 상세 정보 제공한다.
    - 각 시도별 구군 평균 매매가를 차트를 통해 제공한다.
2. 사용자 맞춤 서비스 제공 
    - 관심 매물, 관심 동네를 등록하고 한 눈에 모아볼 수 있도록 한다.
3. 매물에 특화된 정보 제공
    - 편의점이나 마트 등 주변 편의시설의 정보를 제공한다.
    - 매물 관련 뉴스를 크롤링하여 제공한다.
4. 유저간의 커뮤니티를 조성
    - 유저간의 자유로운 질문 답이 가능하도록 구현한다.
<br>

## 3️⃣ 역할

<h3>Backend (Spring) & Front-end(Vue)</h3>

  - 로그인, 회원가입, 로그아웃, 정보수정, 회원탈퇴 등 유저 서비스 개발  
  - 매물 검색, 뉴스 크롤링, 사진 자동 검색, 편의 시설 정보 제공, 관심지역 등록 개발
  - 커뮤니티 관련 CRUD 및 댓글 기능 개발
  - 그외 소셜 로그인, 실시간 상담 등 개발
<br>

## 4️⃣ 개발 환경 & 기술 스택
  - 운영체제 : Window 10
  - Front : Vue.js, Vue-CLI, Vue-router, Vuex, axios, HTML5, CSS5, BootStrap5, JavaScript, Chart.js
  - Back : SpringBoot, MyBatis, GSON
  - Database : MySQL
  - Etc : Git, Gitlab
<br>

## 5️⃣ 프로젝트 설계

### Client (Vue)
```
happyhouse_vue
dist/
image/
node_modules/
public/
src/
    article/
    assets/
        css/
    components/
        community/
            board/
                CommunityDetail.vue
                ...
            comment/
                addComment.vue
                ...
        house/
            Bar.vue
            ...
        user/
            NewNavBar
            ...
        NewNavBar.vue
        TheHeaderNavbar.vue
    profile/
    router/
        index.js
    store/
        modules/
        index.js
    views/
        CharView.vue
        CommunityView.vue
        GetPwModal.vue
        HouseView.vue
        LoginView.vue
        ...
    App.vue
    main.js
    readme.rd
    ...
    
```

### Server (Spring Boot)

```
happy_spring_boot
    src/main/java/
        com.ssaffy.happyapi/
            HappyBootApplication.java
        com.ssaffy.happyapi.Community/
            CommentController.java
            ...
        com.ssaffy.happyapi.Community.Board/
            BoardController.java
            ...
        com.ssaffy.happyapi.config/
            MainConfig.java
            ...
        com.ssaffy.happyapi.File/
            FileController.java
            ...
        com.ssaffy.happyapi.House/
            AreaVO.java
            ...
        com.ssaffy.happyapi.news/
            NewController.java
        com.ssaffy.happyapi.User/
            UserDto.java
            ...
    src/main/resource/
        mappers/
        static/
        application.properties
    src/test/java
    JRE System Library
    Maven Dependencies
    bin
    ...

```

1. ERD  
    <img src="https://raw.githubusercontent.com/okanekudasai/happyimage/793d562c2813bd241eb91d4971894a8490a9ecf7/%ED%85%8C%EC%9D%B4%EB%B8%94%EA%B5%AC%EC%A1%B0%EB%8F%84.PNG" width="700"> <br><br>

2. Class Diagram  
    - 유저 및 관리자 관련  
        <img src="https://raw.githubusercontent.com/okanekudasai/happyimage/793d562c2813bd241eb91d4971894a8490a9ecf7/%ED%81%B4%EB%9E%98%EC%8A%A4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8/%EC%9C%A0%EC%A0%80%20%EB%B0%8F%20%EA%B4%80%EB%A6%AC%EC%9E%90%20%EA%B4%80%EB%A0%A8.jpg" width="700"> <br><br>

    -  매물찾기 관련
        <img src="https://raw.githubusercontent.com/okanekudasai/happyimage/793d562c2813bd241eb91d4971894a8490a9ecf7/%ED%81%B4%EB%9E%98%EC%8A%A4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8/%EB%A7%A4%EB%AC%BC%20%EC%B0%BE%EA%B8%B0%20%EA%B4%80%EB%A0%A8.jpg" width="700"> <br><br>
    
    - 게시판 관련  
        <img src="https://raw.githubusercontent.com/okanekudasai/happyimage/793d562c2813bd241eb91d4971894a8490a9ecf7/%ED%81%B4%EB%9E%98%EC%8A%A4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8/%EA%B2%8C%EC%8B%9C%ED%8C%90%20%EA%B4%80%EB%A0%A8.jpg" width="700"> <br><br>

    - 파일 업로드 관련
        <img src="https://raw.githubusercontent.com/okanekudasai/happyimage/793d562c2813bd241eb91d4971894a8490a9ecf7/%ED%81%B4%EB%9E%98%EC%8A%A4%20%EB%8B%A4%EC%9D%B4%EC%96%B4%EA%B7%B8%EB%9E%A8/%ED%8C%8C%EC%9D%BC%20%EC%97%85%EB%A1%9C%EB%93%9C%20%EA%B4%80%EB%A0%A8.jpg" width="700"> <br><br>


<br>

## 6️⃣ 개발 내용 / 성과

  - **공공 데이터**를 활용한 전국 아파트 매물, 통계 정보 **DB 생성**
  - Kakao Map API를 활용한 **매물 정보, 편의 시설 정보** 시각화
  - Kakao API를 활용한 **SNS 로그인** 구현
  - 회원정보 수정, 탈퇴, 프로필 사진 업로드(파일 업로드)가 가능한 **MY PAGE** 구현
  - **JWT를 사용한 회원가입 및 로그인** 구현
  - 사용자 중심 **MY 관심 서비스** 구현 (관심 지역 설정)
  - **Chart.js**를 활용한 자치구별 통계 정보 시각화
  - File Upload를 적용한 **커뮤니티(QnA게시판)** 구현 및 댓글 기능 구현
  - **채널톡api**를 활용한 실시간 상담 기능 구현
  - **Git**을 활용한 협업관리

<br>

## 8️⃣ 발표 자료 / 시연 동영상
![슬라이드1](https://raw.githubusercontent.com/okanekudasai/happyimage/10f4652c97691a68c8aff8beb9b0abe45cd68f41/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C1.JPG)
![슬라이드2](https://raw.githubusercontent.com/okanekudasai/happyimage/10f4652c97691a68c8aff8beb9b0abe45cd68f41/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C2.JPG)
![슬라이드3](https://raw.githubusercontent.com/okanekudasai/happyimage/10f4652c97691a68c8aff8beb9b0abe45cd68f41/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C3.JPG)
![슬라이드4](https://raw.githubusercontent.com/okanekudasai/happyimage/10f4652c97691a68c8aff8beb9b0abe45cd68f41/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C4.JPG)
![슬라이드5](https://raw.githubusercontent.com/okanekudasai/happyimage/10f4652c97691a68c8aff8beb9b0abe45cd68f41/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C5.JPG)
![슬라이드6](https://raw.githubusercontent.com/okanekudasai/happyimage/10f4652c97691a68c8aff8beb9b0abe45cd68f41/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C6.JPG)
![슬라이드7](https://raw.githubusercontent.com/okanekudasai/happyimage/10f4652c97691a68c8aff8beb9b0abe45cd68f41/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C7.JPG)
![슬라이드8](https://raw.githubusercontent.com/okanekudasai/happyimage/3ba8d87033f4cb9ee5115b849a1e9c438a5e6af4/Slide8.jpg)
![슬라이드9](https://raw.githubusercontent.com/okanekudasai/happyimage/3ba8d87033f4cb9ee5115b849a1e9c438a5e6af4/Slide9.jpg)
![슬라이드10](https://raw.githubusercontent.com/okanekudasai/happyimage/3ba8d87033f4cb9ee5115b849a1e9c438a5e6af4/Slide10.jpg)
![슬라이드11](https://raw.githubusercontent.com/okanekudasai/happyimage/3ba8d87033f4cb9ee5115b849a1e9c438a5e6af4/Slide11.jpg)
![슬라이드12](https://raw.githubusercontent.com/okanekudasai/happyimage/3ba8d87033f4cb9ee5115b849a1e9c438a5e6af4/Slide12.jpg)
![슬라이드13](https://raw.githubusercontent.com/okanekudasai/happyimage/3ba8d87033f4cb9ee5115b849a1e9c438a5e6af4/Slide13.jpg)
![슬라이드14](https://raw.githubusercontent.com/okanekudasai/happyimage/3ba8d87033f4cb9ee5115b849a1e9c438a5e6af4/Slide14.jpg)
![슬라이드15](https://raw.githubusercontent.com/okanekudasai/happyimage/10f4652c97691a68c8aff8beb9b0abe45cd68f41/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C15.JPG)
![슬라이드16](https://raw.githubusercontent.com/okanekudasai/happyimage/10f4652c97691a68c8aff8beb9b0abe45cd68f41/%EC%8A%AC%EB%9D%BC%EC%9D%B4%EB%93%9C16.JPG)

### 시연영상
https://youtu.be/6YGsVOrG3tg
