# HappyHouse_Vue_구미_05반_이동하_최지원

<br>

 
### 팀원
- 이동하
- 최지원

<br>

### 구현내용
1) 메인 페이지 
2) 매물검색 페이지 - 지역별, 거래기간별 검색 가능 및 관심지역 등록 가능
3) QnA 페이지 - 전체 글 보기, 글 작성, 글 불러오기, 댓글작성, 조회수 반영
4) 로그인 / 로그아웃 / 회원정보수정 / 회원탈퇴
5) 관심지역 등록 
 
### 개발스택
1) SpringBoot
2) VueJS
3) MyBatis(MySQL)
 
### 1. 메인 페이지 
<img src="https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/%EB%A9%94%EC%9D%B8.png" width="700"> <br><br>
매물찾기, Qna, 회원관리 페이지로 이동할 수 있는 네비게이션이 있습니다..  <br>
<br><br>
### 2-1. 동별 실거래가 검색 페이지 
<img src = "https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/%EB%A7%A4%EB%AC%BC%EC%B0%BE%EA%B8%B0.png"  width="700">  <br><br> 
시도, 구군, 동 순서로 선택하여 거래 기간별 매물을 조회할 수 있습니다.  <br>

### 2-2. 아파트별 실거래가 검색 페이지
 <img src = "https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/%EB%A7%A4%EB%AC%BC%EC%B0%BE%EA%B8%B0-%EA%B4%80%EC%8B%AC%EC%A7%80%EC%97%AD.png"  width="700">  <br><br> 
로그인 한 사용자는 조회한 지역을 관심지역으로 설정하여 불러 올 수 있습니다.<br>


### 3-1. QnA 리스트 불러오기 페이지
<img src="https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/qna%EB%A6%AC%EC%8A%A4%ED%8A%B8.png" width="700"> <br> 
데이터베이스에 저장된 QnA리스트를 불러와 목록으로 구성하는 페이지 입니다..<br>

### 3-2. QnA 글 쓰기 페이지
<img src="https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/qna%20%EA%B8%80%EC%93%B0%EA%B8%B0.png" width="700"> <br> <br>
QnA를 작성하여 데이터베이스에 저장 할 수 있는 페이지 입니다. <br>

### 3-3. 글 불러오기 페이지
<img src="https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/qna%20%EA%B8%80%EB%B3%B4%EA%B8%B0.png" width="700"> <br><br>
QnA에 작성된 글을 불러와 제목과 내용 및 세부 사항을 조회할 수 있습니다.<br>
댓글 작성 및 불러오기를 구현하였습니다. 해당 글을 방문하면 조회수가 증가 합니다.<br>

### 4-1. 로그인 페이지
<img src="https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/%EB%A1%9C%EA%B7%B8%EC%9D%B8.png" width="700"> <br><br>
로그인 페이지 입니다.<br>
### 4-2. 회원가입 페이지
<img src="https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/%ED%9A%8C%EC%9B%90%EA%B0%80%EC%9E%85.png" width="700"> <br><br>
회원가입 페이지 입니다.<br>
### 4-3. 회원정보수정 페이지
<img src="https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/%ED%9A%8C%EC%9B%90%EC%A0%95%EB%B3%B4%EC%88%98%EC%A0%95.png" width="700"> <br><br>
로그인 페이지 입니다.<br>
### 4-4. 회원탈퇴 페이지
<img src="https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/%ED%83%88%ED%87%B4%ED%95%98%EA%B8%B0.png" width="700"> <br><br>
로그인 페이지 입니다.<br>
### 5-1. 관심지역 등록 페이지(1)
<img src="https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/%EB%A7%88%EC%9D%B4%ED%8E%98%EC%9D%B4%EC%A7%80-%EA%B4%80%EC%8B%AC%EC%A7%80%EC%97%AD%EC%97%86%EC%9D%8C.png" width="700"> <br><br>
관심지역 등록 전 페이지 입니다..<br>
### 5-2. 관심지역 등록 페이지(2)
<img src="https://lab.ssafy.com/gwonwon1201/happyhouse_vue/-/raw/master/image/%EB%A7%88%EC%9D%B4%ED%8E%98%EC%9D%B4%EC%A7%80-%EA%B4%80%EC%8B%AC%EC%A7%80%EC%97%AD%EC%9E%88%EC%9D%8C.png" width="700"> <br><br>
관심지역 등록 후 페이지 입니다..<br>

<br><br>

이상으로 필수 기능을 모두 구현하였습니다.

