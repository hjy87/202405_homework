# Kotlin_2405 Project
## 프로젝트 개요
이 프로젝트는 코프링을 기반으로 다양한 브랜드와 카테고리의 상품을 관리하는 서비스입니다. 

## 구현 범위
### 주요 기능
1. **카테고리 관리**
   - 카테고리 생성
   - 카테고리 수정
   - 카테고리 삭제
   - 카테고리 조회

2. **브랜드 관리**
   - 브랜드 생성
   - 브랜드 수정
   - 브랜드 삭제
   - 브랜드 조회

3. **가격 관리**
   - 가격 생성
   - 가격 수정
   - 가격 삭제
   - 최저가 조회
   - 브랜드별 최저가 조회
   - 카테고리별 가격 범위 조회

4. **단위 테스트**
   - BrandServiceTest
   - CategoryServiceTest
   - PriceServiceTest
  
### 사용된 기술 스택
- Kotlin
- Spring Boot
- JPA (Hibernate)
- Kotlin JDSL(Query With JPQL)
- H2 Database
- KoTest(테스트 라이브리러)
- MockK (테스트용 모킹 라이브러리)


## 코드 빌드, 테스트, 실행 방법

### 사전 준비
1. Java 17 및 코틀린이 준비되어 있어야 합니다.
2. Gradle이 설치되어 있어야 합니다.

### 빌드 및 실행
1. 프로젝트 클론
   ```bash
   git clone https://github.com/hjy87/202405_homework.git
2. IDE 상에서 빌드
3. Kotlin2405Application.kt Run
