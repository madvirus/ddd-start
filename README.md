# 예제 안내

## 준비
아래 프로그램을 준비한다.
* JDK 1.8
* 메이븐
* MySQL

## 소스 다운로드
git을 안다면 아래 명령어로 리포지토리를 클론한다.

git clone https://github.com/madvirus/ddd-start.git

git을 모른다면 우측 상단의 Download ZIP을 클릭해서 코드를 다운로드 받고 압축을 푼다.

## MySQL DB 생성 및 데이터 초기화

* src/sql/ddl.sql 파일을 이용해서 데이터베이스와 테이블 생성
  * shop 데이터베이스 생성
  * shopuser 사용자 생성
  * 관련 테이블 생성
* src/sql/init.sql 파일로 예제 실행에 필요한 데이터 초기화

## 예제 실행

프로젝트 폴더에서 다음 명령어로 예제를 실행한다.

$ mvn spring-boot:run

http://localhost:8080에 접속하면 결과 화면을 볼 수 있다.
