# [Step2 - 클린 아키텍처]
## 특강 신청 API
| 메서드  | 요청 URL                    |
|------|---------------------------|
| POST | /api/v1/lectures/{userId} |

### 비즈니스 로직 흐름
1. 특강 신청 목록 확인
   - 해당 특강 목록 조회
      1. 4월 20일 토요일 1시 이전인 경우에는 실패하는 케이스
      2. 특강의 개수가 0 미만이면 Error Throwing
      3. 특강 예약 목록 조회(Lazy vs Eager, fetch join)
         - 같은 유저가 중복하여 신청한 경우 Error Throwing

### 동시성
#### 1. Task Queue
Queue를 통하여 순차적으로 실행할 수 있도록 제어합니다.
#### 2. ConcurrentHashMap
특강 ID에 Lock을 걸어 ConcurrentHashMap에 저장하는 방식으로 동시성을 제어합니다.
#### 3. 낙관적 락
Hibernate의 ```@Version```을 통해 동시성을 제어합니다.
#### 4. 비관적 락
JPA에서 제공해주는 ```@Lock```을 이용하여 ```select for update```방식으로 동시성을 제어합니다.
#### 결론
저는 4번을 선택했습니다.   
그 이유는 프레임워크의 힘을 빌리고 엔티티 레벨에서 풀어 내는 것이 좀 더 쉽다고 느껴져서 입니다.

## 특강 신청 완료 여부 조회 API
| 메서드  | 요청 URL                        |
|------|-------------------------------|
| GET | GET /api/v1/lectures/{userId} |
- String 값으로 특강 신청이 완료했으면 "완료했음", 신청하지 못했으면 "실패했음" 반환 


## ERD
![LectureReservation ERD](https://github.com/shihaim/hhplus/tree/main/step2/lecture-reservation/step2_ERD.png)

---
### 추가 요구사항
- 날짜별 특강이 존재
- 날짜별 특강 목록 조회