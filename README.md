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
    - 게시물 작성/수정 페이지
    - 게시물 읽기 페이지
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
![상세페이지](https://user-images.githubusercontent.com/37447282/183000786-84b8487b-fa33-4a89-90ed-2953aa37217c.png)
- 스와이퍼 슬라이드 라이브러리를 사용하여 식물 사진 나열 및 자동 재생하여 보여준다.
- 식물의 특성을 가진 키워드 및 설명, 관리법을 보여준다.
- 식물 추가 버튼(내 식물 추가하기)을 누르면 사용자의 SNS 계정에 해당 식물의 카테고리가 생성이 되도록 한다.
- 사용자 계정 추천
  - 해당 식물을 나만의 식물로 추가한 계정이 있을 경우 그 식물을 키우고 있는 계정을 보여주고, 추가한 사용자가 없다면 랜덤으로 계정을 보여준다.
- 식물의 특성을 감안한 자주 묻는 질문을 통해 식물을 키우는데 도움을 주도록 한다.


### 6. SNS 메인 페이지
![sns메인](https://user-images.githubusercontent.com/37447282/183002911-9c832578-212c-4d85-b804-75e0ad19e1b7.png)
- 헤더에 검색창을 위치시켜 어느 페이지에서도 검색할 수 있도록 구현
- hover 이벤트를 통해 검색조건을 선택하여 검색할 수 있도록 구현
- 사용자들이 작성한 게시물 리스트를 보여준다.
- 로그인한 사용자의 계정 정보를 보여준다.

![검색조회](https://user-images.githubusercontent.com/37447282/183003967-6b44268d-cbf2-43f8-bb81-d15b5a5a5479.png)
- 조회 조건으로 식물을 선택하고 검색하면 해당 식물에 대한 글을 보여준다.
- 조회 조건으로 계정을 선택하고 검색하면 해당 사용자 계정을 보여준다.
- 조회 조건으로 내용을 선택하고 검색하면 게시글의 내용 중 해당 단어가 있는 게시물을 보여준다.


### 7. 팔로워/팔로잉 리스트 페이지
![팔로우](https://user-images.githubusercontent.com/37447282/183004124-f79c5876-5f64-4ee6-9500-9d786e3f998b.png)
- 로그인한 사용자를 팔로우/팔로잉하는 계정들을 보여준다.
- 팔로우 버튼을 클릭하면 해당 계정을 팔로우하며 팔로잉으로 버튼이 바뀐다.
- 팔로잉 버튼은 해당 계정을 팔로우하고 있을 경우 보여지는 버튼으로 해당 버튼을 클릭하면 해당 계정을 언팔로우하게 된다.

### 8. SNS 사용자 페이지
![사용자계정](https://user-images.githubusercontent.com/37447282/183005078-6003d2c9-cd7e-4667-9cca-c75d50068d1e.png)
- 상단에는 사용자의 프로필과 팔로워, 팔로잉 수, 사용자 게시물의 총 개수, 사용자가 좋아요 누른 게시물의 개수를 보여준다.
- 그 아래에는 사용자가 작성한 게시물이 식물 이름별로 나누어져 있으며 최신 게시물 기준으로 4개씩 보여준다.
- 식물 이름을 클릭하면 그에 해당되는 전체 게시물 데이터를 Ajax처리하여 화면에 보여준다.

![사용자계정2](https://user-images.githubusercontent.com/37447282/183005437-377a2636-095a-4cb3-b1b2-80297f255a6d.png)
- 사용자가 팔로우 버튼을 누르면 팔로잉 버튼으로 바뀌고 상대의 팔로워 수가 증가한다.


### 9. 사용자 프로필 수정 페이지
![그림15](https://user-images.githubusercontent.com/37447282/181892017-d7562624-ef18-40d2-b446-770ce6a73bca.png)


### 10. 게시물 작성 페이지
![쓰기](https://user-images.githubusercontent.com/37447282/183005775-e93575af-6353-495f-9457-c2e1939e6f41.png)
![쓰기2](https://user-images.githubusercontent.com/37447282/183007700-6fdfbffb-91f9-433e-aa38-e527123bc90f.png)
- (+) 버튼을 누르면 업로드할 파일을 선택할 수 있다.
- 업로드할 파일을 선택하면 파일명과 이미지 미리보기를 보여준다.
- 페이지가 로드 될 때 현재 GPS 기반으로 위치와 날씨를 가져온다. 지도 아이콘을 누르면 팝업창이 뜨면서 지도를 보여준다. 해당 지도에서 위치를 선택하고 팝업창을 닫으면 선택한 위치로 정보가 바뀐다. 
![쓰기3](https://user-images.githubusercontent.com/37447282/183008298-5d2a137d-5983-42a0-80ac-5637ea3a7590.png)
- 카테고리를 선택하면 상세페이지에서 사용자가 추가했던 식물의 리스트를 보여준다.
- 게시물 글, 업로드할 이미지, 카테고리 중 하나라도 빈 곳이 있다면 알림창을 띄워준다.

### 11. 게시물 읽기 및 수정 페이지
![글](https://user-images.githubusercontent.com/37447282/183005817-0625bb99-deee-4b69-8d7d-2582be4e344f.png)
![그림24](https://user-images.githubusercontent.com/37447282/181900892-947d12ff-09b7-4c97-9cd0-89be3a0b17c5.png)
![그림25](https://user-images.githubusercontent.com/37447282/181900911-cc6d9533-2581-491f-8e41-c84547148a86.png)



## 프로젝트 결과물
프로젝트 소개 영상: https://youtu.be/aKi-6DJJ9II
 
