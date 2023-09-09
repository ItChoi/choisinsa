# 최신사 토이프로젝트
- [프로젝트 전반적인 설정 및 이슈 관리 - 노션](https://www.notion.so/ChoiSinSa-c77c2469974845e2b9c7dcca11717772)
- [프로젝트 세부 사항 기획 및 정책 정리 - 피그마](https://www.figma.com/file/UbMVszmu3byhrEBEYfqhxm/ChoiSinsa?type=design&node-id=0-1&mode=design&t=wQcIFUfzDyhtBeqY-0)
- [RDBMS ERD - dbdiagram](https://dbdiagram.io/d/6309f8a7f1a9b01b0ff374bd)

## 프로젝트 설정
- java - openjdk16
- spring boot - 2.7.2
- gradle - 7.5
- jpa & querydsl
- mysql - 8.0.30

## 프로젝트 설명
- 무신사 사이트 클론 토이프로젝트
- 노션을 통해 프로젝트의 큰 흐름 정리
- 보여지지 않는 세부 규칙 & 정책은 고민하여 피그마를 통해 정리
- 전체 데이터베이스 흐름은 dbdiagram을 통해 정리

## 진행 완료 항목
### 서비스 Controller
1. [module-api-client] MemberController (회원 - 조회, 가입, 추천인, 로그인, sns 연동, 이메일 이용 가능 여부)
2. [module-api-client] Oauth2Controller (oauth - sns 로그인, 현재 카카오만 연동)
3. [module-api-client] ItemController (상품 - 상세 조회, 메인 페이지 상품 데이터 조회)
4. [module-api-client] CategoryController (카테고리 - 모드 카테고리 타입 조회, 계층 카테고리 조회)

### 관리자 Controller
1. [module-api-admin] AdminMemberController (관리자 회원 - 로그인)
2. [module-api-admin] AdminItemController (관리자 상품 - 등록(기본 정보, 상세 정보, 에디터 정보), 수정, 상세 조회, 삭제, 상품 전체 조회 ())

### 시큐리티
1. [module-core] ApplicationReadyListener (권한별 메뉴 인가 데이터 세팅)
2. [module-core] AdminAuthMenuListener (메뉴 관련 엔티티 리스너)

### 테스트 코드
- 우선 rest docs 문서 테스트 코드만 작성 - 각 Controller 네이밍 뒤에 Test, ex) MemberControllerTest

## 리팩토링 필요 항목
1. 멀티 모듈 의존성 관계 설정 - 스프링 시큐리티 및 리스너 등을 사용하면서 억지로 @profile로 구분하여 사용하게 되면서 모듈간 분리가 제대로 되어 있지 않음 -> 전반적으로 모듈 구조 변경 필요
2. 단위 테스트 세부적 작성
