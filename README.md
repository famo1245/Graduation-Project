# 메운디 7.4

- 문화 생활 공공 데이터를 이용한 커뮤니티 서비스
## 개요
- 서울특별시 문화 생활 공공 데이터를 이용한 커뮤니티 서비스

---

### 주환
```
- 04.01 문화 엔티티 추가,문화 서비스 추가, 문화 리포지토리 추가, 리뷰 컨트롤러 문화제목으로 검색으로 수정 +검색 부분을 만들어야함
- 04.03 user entity 추가에 따른, 오류 수정, 문화 객체 DB에 삽입하는 메소드 생성
- TODO
  - 로그인을 한 상태일 경우, 리뷰를 쓸 때 사용자 정보를 받아와야함
  - 로그인을 하지 않을 상태일 경우, 리뷰 작성을 못하게 접근 거부 해야함
  - Culture 엔티티의 모든 field를 String으로 받고있기때문에, 수정필요
```

### 지영
```
- 04.03 문화 서비스에서 공공데이터 api와 연결 후 화면에 출력 생성, DB에 삽입할 부분을 만들어야함
- 04.05 문화 생활 목록, 상세정보 만듦 화면 내용은 좀 더 상의 필요
-TODO
  - 문화 생활 db pk 역순 저장
  - 매일 업데이트 어떻게 할 건지
  - jpa를 사용하는 repository 함수명 refactor 필요
```

### 재준
```
- 04.03 구글 로그인
```
