# [Step2 - 클린 아키텍처]
## 특강 신청 API
| 메서드  | 요청 URL                    |
|------|---------------------------|
| POST | /api/v1/lectures/{userId} |

### 비즈니스 로직 흐름
1. 특강 신청 목록 확인
   - 해당 특강 목록 조회
     - 존재하지 않으면 Error Throwing
   - 4월 20일 토요일 1시 이전인 경우에는 실패하는 케이스
   - 특강의 개수가 0 미만이면 Error Throwing
   - 특강 예약 목록 조회(Lazy vs Eager)
     - 같은 유저가 중복하여 신청한 경우 Error Throwing

### 동시성
#### 1. Task Queue
Queue를 통하여 순차적으로 실행할 수 있도록 제어합니다.
#### 2. ConcurrentHashMap
특강 ID에 Lock을 걸어 ConcurrentHashMap에 저장하는 방식으로 동시성을 제어합니다.
#### 3. 낙관적 락
Hibernate의 `@Version`을 통해 동시성을 제어합니다.
#### 4. 비관적 락
JPA에서 제공해주는 `@Lock`을 이용하여 `select for update`방식으로 동시성을 제어합니다.
#### 결론
저는 4번을 선택했습니다.   
- 1번과 2번의 경우 다수의 인스턴스 내 선착순을 위한 동시성 제어가 힘들어 보였습니다.
- 3번의 경우 동시성 제어는 가능하지만, 선착순과 같이 순서가 보장되어야 하는 경우 맞지 않다고 생각했습니다.
- DB Lock을 이용하여 `select for update`를 통해서 조회시 특강 ID으 레코드를 잠궈서
  처리하는 방식을 통해 순서를 보장시키는 것이 맞다고 생각했습니다.

## 특강 신청 완료 여부 조회 API
| 메서드  | 요청 URL                        |
|------|-------------------------------|
| GET | /api/v1/lectures/{userId} |
- String 값으로 특강 신청이 완료했으면 "완료했음", 신청하지 못했으면 "실패했음" 반환 

## 특강 목록 조회 API
| 메서드  | 요청 URL           |
|------|------------------|
| GET | /api/v1/lectures |
- 날짜별 특강이 존재
- 날짜별 특강 목록 조회

## ERD
![LectureReservation ERD](https://github.com/shihaim/hhplus/blob/main/step2/lecture-reservation/step2_ERD.png)

---
## [추가 고민사항]
#### 1. 값 객체를 이용
- `LocalDateTime`이 아닌 `연도/월/일/시`만 나타내주는 객체 생성
- 날짜 비교를 위해 YYYYMMDDHH 형태로 컨버팅(String)하여 compareTo로 비교하면 
  가독성이 저하된다고 생각