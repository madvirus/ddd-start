> DDD START! 재출간판인 **도메인 주도 개발 시작하기** 책의 소스 코드는
> https://github.com/madvirus/ddd-start2 리포지토리에 확인 가능합니다.

# 예제 안내

## 준비
아래 프로그램을 준비한다.
* JDK 1.8
* 메이븐
* MySQL

## 소스 다운로드
git을 안다면 아래 명령어로 리포지토리를 클론한다.

```
git clone https://github.com/madvirus/ddd-start.git
```

git을 모른다면 우측 상단의 Download ZIP을 클릭해서 코드를 다운로드 받고 압축을 푼다.

## MySQL DB 생성 및 데이터 초기화

* src/sql/ddl.sql 파일을 이용해서 데이터베이스와 테이블 생성
  * shop 데이터베이스 생성
  * shopuser 사용자 생성
  * 관련 테이블 생성
* src/sql/init.sql 파일로 예제 실행에 필요한 데이터 초기화

### docker-compose 사용시
```sh
docker-compose up
```

## JPA 메타모델 생성
OrderSummary_ 와 같이 이름에 '_'가 포함된 JPA 메타 모델 클래스를 생성하려면 다음 중 한 방법을 사용한다.

* mvn compile 명령어로 생성
* mvn generate-sources 명령어로 생성

### mvn compile 명령어로 생성하기
현재 pom.xml 파일은 mvn compile 명령어를 실행하는 과정에서 JPA 모델 클래스를 생성한다.
mvn compile 명령어를 실행하면 다음 폴더에 메타모델 클래스가 생성된다.

 * target/generate-soruces/annotations

### mvn generate-sources 명령어로 생성하기

generate-sources 명령어로 실행하려면 pom.xml 파일에 다음과 같이 maven-process-plugin 설정을 추가한다.

```
            <!-- 기존 maven-compiler-plugin 주석 처리
            <plugin>
                <artifactId>maven-compiler-plugin</artifactId>
                <configuration>
                    <compilerArguments>
                        <processor>org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor</processor>
                    </compilerArguments>
                </configuration>
            </plugin>
            -->

            <plugin>
                <groupId>org.bsc.maven</groupId>
                <artifactId>maven-processor-plugin</artifactId>
                <version>2.2.4</version>
                <executions>
                    <execution>
                        <id>process</id>
                        <goals>
                            <goal>process</goal>
                        </goals>
                        <phase>generate-sources</phase>
                        <configuration>
                            <processors>
                                <processor>org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor</processor>
                            </processors>
                        </configuration>
                    </execution>
                </executions>
                <dependencies>
                    <dependency>
                        <groupId>org.hibernate</groupId>
                        <artifactId>hibernate-jpamodelgen</artifactId>
                        <version>${hibernate.version}</version>
                    </dependency>
                </dependencies>
            </plugin>
```

pom.xml 설정을 변경했으면 다음 명령어를 실행한다.

```
$ mvn generate-sources
```

이 명령어를 실행하면 target/generate-sources/apt 폴더에 메타모델 클래스가 생성된다.

## 예제 실행

프로젝트 폴더에서 다음 명령어로 예제를 실행한다.

```
$ mvn spring-boot:run
```

http://localhost:8080에 접속하면 결과 화면을 볼 수 있다.
