## [TDD - Service]
### 1. 포인트 조회
- PointService 생성(UserPointTable 생성자 주입)
- 포인트 조회 성공 테스트 작성
  - given시 UserPointTable insertOrUpdate로 포인트 생성
- UserPointTable이 selectById시 null이면 새로운 객체를 생성하므로 
id 조회 실패에 대한 실패 테스트는 작성 X

### 2. 포인트 충전
- 충전할 포인트가 음수일 경우 Error를 발생시키는 테스트 작성
- 포인트 충전 성공 테스트 작성
- 리팩토링 진행(setUp 메소드)
  - UserPointTable 생성자 주입
  - given시 UserPointTable insertOrUpdate로 포인트 생성

### 3. 포인트 사용
- 사용할 포인트가 음수일 경우 Error를 발생시키는 테스트 작성
- 사용할 포인트보다 가지고 있는 포인트가 적을 경우 Error를 발생시키는 테스트 작성
- 포인트 사용 성공 테스트 작성

### 4. 포인트 상세 내역 조회
- 포인트 상세내역 조회시 충전/사용이 없는 ID에 대한 테스트 작성
- 포인트 상세내역 조회시 충전/사용한 경우에 대한 테스트 작성
  1. 테스트 코드 내 PointHistoryTable.insert()후 통한 테스트 작성
  2. PointService 내에서 충전(charge)/사용(use)시 History 내역을 insert하도록 리팩토링


---
## [TDD - Controller]
### 1. 포인트 조회 API
- MockMvc를 통한 API 성공 테스트 코드 작성
### 2. 포인트 충전 API
- MockMvc를 통한 API 성공 테스트 코드 작성
### 3. 포인트 사용 API
- MockMvc를 통한 API 성공 테스트 코드 작성
### 4. 포인트 상세 내역 조회 API
- MockMvc를 통한 API 성공 테스트 코드 작성