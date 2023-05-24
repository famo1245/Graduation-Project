# 메운디 7.4

- 문화 생활 공공 데이터를 이용한 커뮤니티 서비스
---
## Rule
- Push 전 항상 Pull 한번 씩 하기
- Push 후, 카톡 방에 공지 & Readme에 진행 상황 최신화 할 것
- 자신의 branch에 push 후 master branch에 pull request 할 것
---

## 개요
- 서울특별시 문화 생활 공공 데이터를 이용한 커뮤니티 서비스

---

### 주환
- 04.01 문화 엔티티 추가,문화 서비스 추가, 문화 리포지토리 추가, 리뷰 컨트롤러 문화제목으로 검색으로 수정 +검색 부분을 만들어야함
- 04.03 user entity 추가에 따른, 오류 수정, 문화 객체 DB에 삽입하는 메소드 생성
- 04.05 문화 전체를 DB로 옮기는 메소드 생성
- 04.08 리뷰 수정 페이지 추가, 리뷰 Controller에 validation, 오류 페이지 추가
- 04.19 리뷰: 리뷰 제목, 문화 제목으로 검색 기능 추가
- 05.11 리뷰: 리뷰 댓글 추가
- 05.21 리뷰: 세션을 통해 리뷰, 리뷰 댓글 사용자 추가 , 리뷰,리뷰 댓글 삭제, 
- 05.21 리뷰:세션과 작성자가 일치해야 리뷰, 리뷰 댓글 수정 삭제 가능 구현
- 05.21 리뷰: URL 직접 접근 거부 => 인터셉터를 이용한 리뷰 추가 거부, 세션과 리뷰 작성자가 다르면 리뷰 수정 거부

- **TODO**
  - Culture 엔티티의 모든 field를 String으로 받고있기때문에, 수정필요
  - 댓글을 쓴 사용자이면 댓글 수정 미구현
  - 리뷰 쓸때, 사진도 추가 -> 파일업로드 미구현 => MultiPart가 파일경로를 지정하는거라서 다른방법이 요구
---

### 지영
- 04.03 문화 서비스에서 공공데이터 api와 연결 후 화면에 출력 생성, DB에 삽입할 부분을 만들어야함
- 04.05 문화 생활 목록, 상세정보 만듦 화면 내용은 좀 더 상의 필요
- 04.07 매일 21시에 문화 생활 업데이트 하는 메서드 추가
- 04.08 보기 편하게 문화 상세 페이지에 페이지네이션 적용, DB에서 역순으로 읽어와서 화면에 출력
- 04.09 구글 로그인 구글 api 내에서 테스트 완료, 코드 적용 
- 04.10 구글 로그인, 네이버 로그인 사용자 정보 가져와서 DB에 저장 완료, 로그아웃 구현 필요
- 04.21 git ignore 적용, 소셜 로그인 통합을 위한 refactoring 준비단계, Member DTO 생성
- 04.28 소셜 로그인 통합 완료, 사용하지 않는 import문 삭제, 리뷰 게시판 로그인 할때만 글쓰기 버튼 보이게 함
- 05.02 사용자 정보 수정 추가, 문화생활 요청 기능 추가, refactoring
- 05.12 문화 상세페이지에서 목록으로 링크 수정
- 05.15 문화 상세페이지 링크 수정, 목록으로 뒤로가기로 변경, 홈 화면에서 최신 문화 생활 5개 보여줌
- 05.16 회원가입 페이지 변경, 문화 생활 서버 시작 시 불러옴
- 05.22 회원가입: 닉네임 중복 등 회원가입 시 validation 추가, 서버 시작시 Culture table 비어있는지 확인
- 05.23 내 정보 수정 페이지 validation 추가, memberDTO를 memberForm으로 통합, review 수정 시 DTO 사용
- 05.24 review 수정 시 validation 추가 -- 미완, interceptor에 사용자 정보 업데이트 url 추가
        
- **TODO**
  - jpa를 사용하는 repository 함수명 refactor 필요
  - CultureService class의 메서드에서 중복되는 내용 refactor 필요
  - 나이대를 나이로 바꿀지 결정 필요
  - ~~네이버 로그인의 경우 id가 string으로 넘어오기 때문에 Long type으로 변환 로직 -> 조금 더 안전하게 구현 할 필요 있음~~
  - ~~보안성을 위해 member dto~~ 
  - ~~Login controller에서 repository 의존~~
  - ~~로그인 api 하나로 refactor 필요~~
  - ~~문화 생활 db pk 역순 저장~~
  - ~~매일 업데이트 어떻게 할 건지~~
  - ~~구글 로그인으로 사용자 정보 받아오기~~
  - ~~MemberForm class와 MemberDTO class가 동일하므로 통합 필요~~
  - ~~Server 실행시 문화생활 DB에 내용 있으면 문화생활 가져오지 않게 하기 필요~~
---

### 재준
- 04.03 구글 로그인, 사용자 정보 받아오는 건 추후
- 04.07 네이버 로그인, 구글 로그인으로 access token 받아오기
- **TODO**
  - **로그인 api 하나로 refactor 필요**
