# 🛠️ Infra and Tools

<div align="left">
<img src="https://img.shields.io/badge/-AWS EC2, S3, RDS, DNS, Code Deploy-232F3E?style=flat&logo=AmazonAWS">
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
  <img src="https://img.shields.io/badge/-Xsehll-007396?style=flat&logo=Java">
  <img src="https://img.shields.io/badge/-JPA-FF3621?style=flat&logo=Databricks&logoColor=white">
  <img src="https://img.shields.io/badge/-Oracle-F80000?style=flat&logo=oracle&logoColor=white">
  <img src="https://img.shields.io/badge/-Thymeleaf-005F0F?style=flat&logo=Thymeleaf&logoColor=white">
  <img src="https://img.shields.io/badge/-Bootstrap-7952B3?style=flat&logo=bootstrap&logoColor=white">
  <img src="https://img.shields.io/badge/-Javascript-F7DF1E?style=flat&logo=javascript&logoColor=white">
  <img src="https://img.shields.io/badge/-Jquery-0769AD?style=flat&logo=jquery&logoColor=white">
</div>
<br><br>

# [🥦 **Nutri - Well**](https://www.youtube.com/watch?v=Gb5VXY6u1yA&t=3s)
### 🥦 Nutri - Well은 식품영양정보 제공 및 개인 영양정보 기록, 관리를 위한 웹 애플리케이션입니다.

![logo](https://github.com/Weiver-project/Weiver/assets/116157924/af5207db-9c59-4600-a519-c42211897935)

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
### 뮤지컬
- [X] **메인 페이지**
+ 인기 뮤지컬: '봤어요' 클릭이 가장 많은 뮤지컬 상위 3개 출력
+ 커뮤니티 인기글: '조회수' 기준 상위 게시글 9개 출력
+ 추천 배우: 랜덤으로 배우 프로필 사진과 출연작 출력, 클릭 시 배우 상세 페이지 이동, 새로고침 시 배우 정보 변동
+ 공연 중인 뮤지컬: 공연 기간 기준 현재 공연 중인 뮤지컬 리스트 출력
   <p>
![뮤지컬 메인 페이지](https://github.com/Weiver-project/Weiver/assets/81962257/fbf2cc82-c97d-4032-8195-ba350ce451c4)
</p>

- [X] **상세 페이지**
+ 뮤지컬 기본 정보 조회
+ '찜하기', '봤어요' 클릭
+ 출연 배우 정보 조회
+ Youtube API 통한 유튜브 클립 출력
     <p>    
![뮤지컬 상세 페이지](https://github.com/Weiver-project/Weiver/assets/81962257/2a88980a-3075-4e37-9507-e85e8976f389)
</p>

### 커뮤니티
- [X] **메인 페이지**
+ 커뮤니티 인기글: '조회수' 기준 상위 게시글 9개 출력
+ 게시글: 카테고리(리뷰, 잡담)에 따라 게시글 구분하여 출력
+ 로그인 시, 유저 간략 정보 측면 nav바에 출력
     <p>    
![커뮤니티 메인 페이지](https://github.com/Weiver-project/Weiver/assets/81962257/f6d4e069-5717-4d00-957b-99b3daa58e7d)
</p>

- [X] **상세 페이지**
+ 게시글 정보 상세 조회
+ 댓글, 대댓글 작성
+ 본인 작성에 한하여 게시글, 댓글, 대댓글 수정 및 삭제 가능
     <p>    
![게시글 상세 페이지](https://github.com/Weiver-project/Weiver/assets/81962257/cb916d13-dbd6-4d7e-a8f8-6ef17936db47)
</p>

- [X] **글 작성 페이지**
+ 글 카테고리(잡담, 리뷰) 선택 가능
+ 리뷰 선택 시, 뮤지컬 정보 연동
+ 이미지 첨부 가능   
  <img src="https://github.com/Weiver-project/Weiver/assets/81962257/53a64563-cf8a-48b8-bb2d-fc76fd7cc044.gif" width="550" height="350"/>

### **마이 페이지**
- [X] **메인 페이지**
+ 찜 / 봤던 뮤지컬 조회
+ 내가 쓴 글 / 댓글 / 좋아요한 글 조회
+ 프로필 수정 / 설정(로그아웃, 비밀번호 변경, 문의하기, 회원탈퇴)
<div>
  <img src="https://github.com/Weiver-project/Weiver/assets/78299214/98bb8c89-2e75-4d16-8e58-edfc996c3073" width="800">
  <br>
  <br>
  <img src="https://github.com/Weiver-project/Weiver/assets/78299214/f447a0bf-50b2-499b-95c1-c165207d1d85" width="300">
  <img src="https://github.com/Weiver-project/Weiver/assets/78299214/562a57c0-0467-4792-94e8-6456ea83f21d" width="300">
</div>

### **관리자 페이지**
- [X] **메인 페이지**
+ 관리자 문의 조회
+ 전체 배우, 뮤지컬 정보 조회
+ 유저, 게시글, 문의 조회 및 삭제
+ 문의글 답변
<div>
  <img src="https://github.com/Weiver-project/Weiver/assets/78299214/439607a3-d431-43c3-bd03-fb52dad71411" width="800">
  <br>
  <br>
  <img src="https://github.com/Weiver-project/Weiver/assets/78299214/311a35e0-07a8-4fe4-bb0d-52cc60df8420" width="800">
</div>   

<br><br>

## ❗️ 힘들었던 점
세 번의 DB 변경
![image](https://github.com/Weiver-project/Weiver/assets/129250941/670269c8-1450-4acd-a550-69c1d3c4f010)
1. 불규칙적인 KOPIS API 뮤지컬 정보와 PLAY DB 배우 정보와의 매핑 과정을 거쳐서 각각의 테이블에 저장하는 방법보다 한 번에 하나의 컬렉션에 전부 저장하는 방식이 조회 속도와 삽입 속도 측면에서 보다 더 효과적일 것이라고 생각되어 MongoDB를 사용
2. 뮤지컬과 배우를 제외한 나머지 데이터들은 정형화되어있고 관계성이 높기 때문에 Oracle DB를 도입하여 두 가지 데이터베이스를 사용
3. KOPIS API를 사용했을 때 PLAY DB 배우 정보와 서로 매핑되는 비율이 444건 중 25건인 5%정도로 매우 낮아 KOPIS API를 포기하고 PLAY DB에서 뮤지컬 정보도 같이 크롤링하는 방식을 사용 -> 뮤지컬 데이터도 정형화되었고 배우 정보와 동일한 페이지에서 데이터를 가져옴 -> 매핑 방식이 이전보다 수월해져 전체 DB를 Oracle로 사용

<br><br>

## ⚠️ 트러블 슈팅
크롤링 시 TimeOutException
1. 크롤링 시 Timeout Option을 주는 방법과 Thread.sleep()을 주는 방식을 사용했지만 해결이 되지 않음
2. 크롤링으로 데이터를 가져오는 과정과 DB에 데이터를 저장하는 과정이 같은 텀에서 실행되는 것이 리소스를 많이 잡아먹는다는 의문
3. 데이터를 메모리에 전부 저장한 다음 DB에 일괄 저장하는 방식을 사용 (Exception 발생 시기가 뒤로 늦춰졌을 뿐 여전히 발생)
4. 버퍼와 같이 일정량의 데이터를 저장한 후 비워주는 방식을 사용하여 해결
```java
/*뮤지컬 상세 페이지에서 뮤지컬 정보 저장*/
@SneakyThrows
public void saveAllMusical(List<String> musicalIds){
  List<Musical> musicals = new ArrayList<>();
    
    for(int i =0; i < musicalIds.size(); i++){
        //뮤지컬 1개에 대한 데이터 크롤링
        Musical musical = getMusical(musicalIds.get(i));
        musicals.add(musical);
        log.info(i + "번 MUSICAL(" + musicalIds.get(i) + ") 저장: " + musical);

        // 데이터를 1000개씩 저장
        if(i != 0 && i % 1000 == 0){
          // musical 저장
          musicalRepository.saveAll(musicals);

          // 새로운 MusicalId를 채우기 위해 기존 리스트 초기화
          musicals.clear();
        }
    }
```
<br><br>

## 💬 회고
🙂 **정찬영** : 소통이 활발한 팀원들이 모여서 적극적인 참여와 피드백을 통해 수월하게 프로젝트를  진행할 수 있었던 것 같습니다.
<br><br>

😄 **곽유섭** : 초반에 여러 시행착오가 있었지만 팀워크가 좋아서 프로젝트를 무사히 마치고 좋은 결과가 있을 수 있었습니다. 모두 감사해요^^7<br><br>

😆 **김현수** : 제 개발인생의 시작점이 된 프로젝트, 좋은 팀원들을 만나 많은 것을 배우고 완성도 있게 마무리 할 수 있었습니다.<br><br>

😌 **이종형** : 3번의 기획안 수정, 3번의 DB 변경 등 우여곡절이 많았지만 팀원들의 많은 도움 덕분에 프로젝트를 잘 마무리 할 수 있었습니다.
배포까지 진행되었지만 유저 반응을 보지 못한점이 아쉽습니다.<br><br>

😎 **이석현** : 팀으로 프로젝트를 진행하는 것이 개인 프로젝트와 다른 부분이 많다는 것을 좋은 팀원들과 함께하며 많이 배우게 된 것 같습니다.

<br><br>

## 🎉 수상내역
![image](https://github.com/Weiver-project/Weiver/assets/116157924/182f7cd6-29ad-4924-8bc9-d7ca4c4243ee)
