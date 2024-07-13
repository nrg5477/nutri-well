# 🛠️ Infra and Tools

<div align="left">
<img src="https://img.shields.io/badge/-AWS EC2, S3, RDS, DNS, Code Deploy-232F3E?style=flat&logo=AmazonAWS">
<img src="https://img.shields.io/badge/-Xshell-FF0000?style=flat&logo=Xshell">
<img src="https://img.shields.io/badge/-Putty-808080?style=flat&logo=Putty">
<img src="https://img.shields.io/badge/-Jira Software-0052CC?style=flat&logo=JiraSoftware">
<img src="https://img.shields.io/badge/-GitHub-181717?style=flat&logo=github&logoColor=white">
<img src="https://img.shields.io/badge/-Notion-000000?style=flat&logo=notion&logoColor=white">
<img src="https://img.shields.io/badge/-Slack-4A154B?style=flat&logo=slack&logoColor=white">
<img src="https://img.shields.io/badge/-Intellij IDEA-000000?style=flat&logo=intellijidea&logoColor=white">
<img src="https://img.shields.io/badge/-Figma-F24E1E?style=flat&logo=figma&logoColor=white">
<img src="https://img.shields.io/badge/-DBeaver-382923?style=flat&logo=dbeaver&logoColor=white">

</div>

# 🦾 Lang and Frameworks

<div align="left">
  <img src="https://img.shields.io/badge/-Spring Boot-6DB33F?style=flat&logo=SpringBoot&logoColor=white">
  <img src="https://img.shields.io/badge/-Spring Security-6DB33F?style=flat&logo=springsecurity&logoColor=white">
  <img src="https://img.shields.io/badge/-Gradle-02303A?style=flat&logo=Gradle">
  <img src="https://img.shields.io/badge/-Java-007396?style=flat&logo=Java">
  <img src="https://img.shields.io/badge/-JPA-FF3621?style=flat&logo=Databricks&logoColor=white">
  <img src="https://img.shields.io/badge/-Oracle-F80000?style=flat&logo=oracle&logoColor=white">
  <img src="https://img.shields.io/badge/-Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white">
  <img src="https://img.shields.io/badge/-Bootstrap-7952B3?style=flat&logo=bootstrap&logoColor=white">
  <img src="https://img.shields.io/badge/-Javascript-F7DF1E?style=flat&logo=javascript&logoColor=white">
  <img src="https://img.shields.io/badge/-Jquery-0769AD?style=flat&logo=jquery&logoColor=white">
</div>
<br><br>

# [🥦 **Nutri - Well**](https://www.youtube.com/watch?v=nSRzYjURWPU)
### 🥦 Nutri - Well은 식품영양정보 제공 및 개인 영양정보 기록, 관리를 위한 웹 애플리케이션입니다.

![logo](https://github.com/user-attachments/assets/4272706a-0670-45fa-864d-e90e1e306585)

<br>

## ✨Overview
### Nutri Well, 왜 이용할까요?
식품의 영양정보와 매일매일 섭취한 영양소을 기록해두세요.
개인의 영양상태를 파악하고, 다양한 식품들의 영양소 정보를 제공하여 건강한 식생활을 돕는것을 목표로 합니다.

**📆 프로젝트 기간 : 2023.05.27 ~ 2023.07.15**
<br><br>

## 🙂 업무분배
|**이름**|**업무**|
|:---:|:---:|
|**[진영록](https://github.com/6worry)**|playDB 배우 정보 크롤링, Youtube API, 관리자 페이지 기능 담당|
|**[남정길](https://github.com/letsgilit)**|playDB 뮤지컬 정보 크롤링, 뮤지컬 관련 기능, 배포 담당|
|**[이희찬](https://github.com/nrg5477)**|회원로그인 기능(소셜), 마이페이지(캘린더, 정보변경, 관리자승인), 식품추가 기능 담당|
|**[조상우](https://github.com/sangwoo-00)**|커뮤니티 페이지(게시글, 상세게시글, 좋아요) 기능 담당, UI 디테일 담당|

<br><br>

## 🟢 주요 기술
**크롤링** : 구글이미로 부터 음식이미지 크롤링

**Naver Login API**

**Google Login API**

**EC2, RDS, S3** : S3 = 이미지 업로드에 사용

<br><br>

## 💻 개발환경
**Java : 17**

**Spring Boot : 3.1.6**

**Spring dependency-management : 1.1.4**

**Spring Security : 6.1.5**

**Local Oracle DB : xe 11g**

**RDS : Oracle 19.0.0.0.ru-2023-04.rur-2023-04.r1**
<br><br>

## 📋 서비스 아키텍처
![image](https://github.com/Weiver-project/Weiver/assets/76997735/6e881153-f943-4c2f-a562-c80dda027428)

<br><br>

## 📝 ERD
![erd](https://github.com/6worry/nutri-well/assets/82476657/d0b91f5d-66a4-4379-a3a1-3e6c83498e46)


<br><br>

## 🎯 구현 결과
### Nutri - Well
- [X] **메인 페이지**
+ 인기 식품 TOP4 : '즐겨찾기' 클릭이 가장 많은 식품 상위 4개 출력
+ 분류별 식품 : 음식 분류별 식품 목록 조회
+ 검색 : 음식 이름으로 식품 목록 조회
   <p>
![메인페이지](https://github.com/user-attachments/assets/fd346d02-a4c2-49b3-8dae-cbc7ebec3bcb)
</p>

- [X] **회원가입 페이지**
+ 사용자 회원 가입 기능
+ 사용자의 정보를 받아서 기초대사량 계산
     <p>    
![회원가입페이지](https://github.com/user-attachments/assets/8f70d4c6-2e84-49a9-ac59-29347b3e898b)
</p>

- [X] **로그인**
+ 사용자 로그인 기능
+ 구글 및 네이버로 소셜 로그인가능
     <p>    
![로그인페이지](https://github.com/user-attachments/assets/f6fc903d-a046-48ec-972b-f1a5a8ca01c1)
</p>

### 마이 페이지
- [X] **캘린더**
+ 일일 영양 섭취량 기록 기능
+ 캘린더 이미지 캡쳐 및 다운로드 기능
     <p>    
![캘린더](https://github.com/user-attachments/assets/2d3c7c0d-39c9-4276-9f75-d2201e00b53b)
![캘린더모달](https://github.com/user-attachments/assets/1e5f7d39-926e-447b-add9-5d9d47961b7b)
</p>

- [X] **회원 정보 수정**
+ 회원정보 수정기능
+ 사용자로 부터 정보를 받아 기초대사량 계산
     <p>    
![회원정보수정](https://github.com/user-attachments/assets/87bef016-703d-4234-b40f-1d7ac5fc9b1a)
</p>

- [X] **식품 승인 페이지**
+ 사용자가 추가 요청한 식품에 대한 승인
    <p>    
![image](https://github.com/user-attachments/assets/14b28ea7-a90d-47c9-b198-b50aec97f69f)
</p>

### **식품 검색 페이지**
- [X] **식품 검색**
+ 조회된 음식 목록 페이지네이션 구현
+ 카테고리별 음식검색 기능
+ 영양소 범위 검색 기능
    <p>    
![image](https://github.com/user-attachments/assets/6b4ca024-9343-4bf9-9fd5-498a1a8558dc)
</p>

- [X] **식품 담기**
+ 사용자가 담은 음식 목록 조회
+ 영양소별 일일 권장량 계산 및 표시
+ 영양 성분 그래프로 제공
    <p>    
![image](https://github.com/user-attachments/assets/f6d9206e-831f-4cc1-8885-ebf72da82eea)
![image](https://github.com/user-attachments/assets/4cc23692-8fb6-437e-89b8-8b6ee71e15ab)
</p>

- [X] **식품 추가**
+ 검색되지 않는 식품 추가 요청
    <p>    
![image](https://github.com/user-attachments/assets/8e9f17bf-c2be-4b79-a836-9eb3dbd3c709)
</p>

### **식품 상세 페이지**
- [X] **식품 상세**
+ 식품 상세영양소 테이블 제공
+ 식품 영양소 그래프 제공
+ 식품에서 가장 많은 영양소 알림
+ 식품별 즐겨찾기 기능
+ 식품 구매할 수 있는 사이트로 유도 및 페이지 이동
    <p>    
![image](https://github.com/user-attachments/assets/f93c70db-d4cb-4fd2-b7f5-dad6061224e3)
</p>

<br><br>

## ❗️ 힘들었던 점
1. 사용자별 기초대사량을 계산함에 있어서 근육량, 지방등 키와 몸무게로 알수 없는 계산을 전부 포함하지 못해 개인화 하는데 차이가 발생할 수 있어서 기준점을 잡기 힘들었음. -> 가장 표준적인 계산식 활용
2. 이미지의 양이 방대해서 가져와서 처리하기 힘들었음 -> 크롤링 방법을 개선해나감. 

<br><br>

## ⚠️ 트러블 슈팅
##### 캘린더 그래프 표시 오류
1. modal에서 그래프를 렌더링하는 과정에서 modal 이 먼저 생성되고 그래프가 생성됨
2. modal의 크기보다 그래프가 커서 렌더링 과정에서 그래프가 제대로 출력되지 않음
3. modal의 크기를 사용자 화면의 200배로 설정해준후 그래프가 렌더링 되는 과정에서 동적으로 modal의 크기를 세팅해서 해결


<br><br>

## 💬 프로젝트 진행 소감
🙂 **진영록** : 부족한 저와 함께 작업해준 팀원들께 감사하고 기간 내에 프로젝트 잘 수행한 것 같다고 생각합니다.
<br><br>

😄 **남정길** : 첫 협업 프로젝트에서 좋은 팀원들과 의사소통하며 배운점이 많아서 좋았습니다.
<br><br>

😆 **이희찬** : 개인으로 작업하는 것보다 프로젝트로 진행, 협업하면서 배울 수 있는 기회가 된것 같습니다
<br><br>

😌 **조상우** : 많이 느리고 부족했지만 유능한 팀원들을 만나 많은 것을 배우고 완성도 있게 마무리 할 수 있었습니다.
<br><br>



## 🎉 수상내역
