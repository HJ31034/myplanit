# PLANiT

## 프로젝트 설명
식물 초보도 쉽게 키우는 반려식물 소셜 플랫폼

- 개발 환경: STS4
- 프레임워크: Spring Boot, MyBatis
- 개발 언어: Java, Javascript, jQuery, HTML, CSS
- 데이터베이스: Oracle
- 템플릿 엔진: Thymeleaf
- 라이브러리: Lombok, Spring Security, Spring Batch, Mahout
- 빌드 도구: Gradle
- 개발 기간: 약 2개월

## 프로젝트 목표(개요)
1. 사용자가 식물 이름으로 검색하거나 관심 키워드를 선택하면 해당 식물에 대한 정보를 제공해줌.
2. 사용자가 SNS에 현재 키우고 있는 반려식물에 대한 게시물을 올리며 다른 사용자들과 소통.

## 프로젝트 구조
- 메인
  - 로그인/회원가입 페이지
    - 회원수정 페이지
  - 검색 페이지
    - 식물 정보 상세 페이지
- SNS
  - SNS 메인 페이지
    - 팔로워/팔로잉 리스트 페이지
  - 게시물 페이지
  - 사용자 계정 페이지
  

## ERD
![ERD_Planit](https://user-images.githubusercontent.com/37447282/181880171-16e679a0-1dc2-467b-beaa-651dc82a86b5.png)


## 프로젝트 기능
### 1. 회원가입
![그림1](https://user-images.githubusercontent.com/37447282/182808635-007c4e8d-b103-4b26-9483-0b9ef507d360.png)
- 스프링 시큐리티를 적용하여 회원가입 시 DB에 비밀번호가 암호화되어 저장된다.
- 아이디 중복 체크를 하며 비밀번호와 비밀번호 확인에 입력된 문자열이 일치하는지 유효성 검사를 한다.

### 2. 로그인
![그림7](https://user-images.githubusercontent.com/37447282/181885590-f68305c5-0a37-4109-9926-efb4bcd6d4ca.png)
- 로그인 실패 시 FailHandler가 작동하고 에러 메세지를 송출한다.
- 로그인 성공 시 페이지의 헤더가 바뀐다.
- 로그인에 성공하면 세션 처리하여 사용자 정보를 담는다.

![그림8](https://user-images.githubusercontent.com/37447282/181886592-91458e9f-3f27-44d3-84fe-a1659522a7ee.png)
- 로그인이 되지 않은 상태에서 SNS 메인 페이지에서 게시물을 클릭하게 되면 게시물을 보기 위해 로그인을 하라는 메시지가 뜬다. 로그인 페이지로 이동하여 로그인을 하면 이전 작업 페이지로 이동한다.



### 3. 메인
![메인화면](https://user-images.githubusercontent.com/37447282/182805245-5f7cb4b1-cda8-4b0f-92b9-c1bd23a39e8b.png)
- 메인에서도 식물 검색이름이나 관심 키워드를 선택해 검색하면 해당 결과가 나온 검색 페이지로 이동한다.
- 추천 식물 리스트
  - 사용자가 로그인을 하지 않았을 경우 모든 식물에 대해 랜덤으로 6개의 리스트를 보여주고, 사용자가 로그인을 하면 회원가입 시 사용자가 입력한 관심 키워드 기준으로 6개의 식물 리스트를 보여줍니다.
- 추천 계정 리스트
  - 머하웃 추천 시스템을 사용하여 사용자가 회원가입 시 입력했던 관심 키워드를 기준으로 사용자 간의 유사도를 측정한 후 유사도가 제일 높은 순으로 6명의 사용자를 추천합니다.
- 최신 포스트
  - 사용자들이 작성한 최신 게시물 4개를 보여줍니다.





### 4. 식물필터 페이지
![검색페이지](https://user-images.githubusercontent.com/37447282/182808389-c39fe9ee-f7e1-4ede-909c-8a9af27d58b1.png)
- 사용자가 키워드 버튼을 누를 때마다 해당 식물을 바로 보여줄 수 있도록 Ajax 처리하여 화면에 보여줍니다.
- 식물의 이름으로 검색할 경우에도 조회된 데이터를 Ajax 처리하여 화면에 보여줍니다.


### 5. 식물 상세 페이지
![상세페이지](https://user-images.githubusercontent.com/37447282/182808887-9f24435f-c73b-4905-a7ca-940a7e63168a.png)
![그림5](https://user-images.githubusercontent.com/37447282/181887004-2ce91ed6-9a6e-4614-9174-498aa33ae204.png)
![그림6](https://user-images.githubusercontent.com/37447282/181887044-4dfad94e-1945-4d27-9646-9aca99071730.png)


### 6. SNS 메인 페이지
![그림9](https://user-images.githubusercontent.com/37447282/181888951-b43b9bdf-afce-4c8f-8350-62da3799e9b7.png)
![그림10](https://user-images.githubusercontent.com/37447282/181889044-0beac56b-6b14-462c-91fe-5f2ea0b650f0.png)

### 7. 팔로워/팔로잉 리스트 페이지
![그림11](https://user-images.githubusercontent.com/37447282/181889491-cf8a2d97-5557-45a7-b459-1bfb98bcd44d.png)
![그림12](https://user-images.githubusercontent.com/37447282/181890229-4df1788a-5513-46cd-a01c-be8d672366cb.png)

### 8. SNS 사용자 페이지
![그림14](https://user-images.githubusercontent.com/37447282/181895900-eccb073c-eadb-4e96-80a9-8065b56e638f.jpg)
![그림15](https://user-images.githubusercontent.com/37447282/181895923-f61046a1-72dc-432f-89fe-f2a3596b9cb7.jpg)


### 9. 사용자 프로필 수정 페이지
![그림15](https://user-images.githubusercontent.com/37447282/181892017-d7562624-ef18-40d2-b446-770ce6a73bca.png)

### 10. 게시물 작성 페이지
![그림20](https://user-images.githubusercontent.com/37447282/181900510-5ed207e4-647e-407b-935e-d59c3071423e.png)
![그림21](https://user-images.githubusercontent.com/37447282/181900538-86541bd3-eaf0-4bbf-b3cb-f79f842dee7b.png)
![그림22](https://user-images.githubusercontent.com/37447282/181900565-12b31a8c-26ea-4cf2-9ab9-ee8bfc03404c.png)

### 11. 게시물 읽기 및 수정 페이지
![그림23](https://user-images.githubusercontent.com/37447282/181900863-7d4c0ac3-b169-46b5-ba41-465edf3eb278.png)
![그림24](https://user-images.githubusercontent.com/37447282/181900892-947d12ff-09b7-4c97-9cd0-89be3a0b17c5.png)
![그림25](https://user-images.githubusercontent.com/37447282/181900911-cc6d9533-2581-491f-8e41-c84547148a86.png)



## 프로젝트 결과물
프로젝트 소개 영상: 
 
