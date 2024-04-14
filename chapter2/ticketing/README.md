# [Chapter2 - 콘서트 예매 서비스]

## 1-1. 유저 토큰 발급 API
![token api](https://github.com/shihaim/hhplus/blob/main/chapter2/sequence_diagram/token_req_api.png)
### EndPoint
| Method | Request URL                         |
|--------|-------------------------------------|
| POST   | api/v1/concerts/{concertCode}/token |

### Request Body
| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| userUUID  | String | O        | 유저 ID       |

### Response Body `IssuedTokenResponse`
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| tokenId   | Long | O        | 대기열 순번      | 
| token     | int  | O        | 대기열 토큰      | 

### Error
- repository
  1. 존재하지 않는 콘서트 Code
  2. 존재하지 않는 유저 UUID

### 요구사항 분석
- 콘서트(아이유 콘서트 또는 BTS 콘서트)에 따라 대기열 토큰을 발급하고 티켓팅을 시작
- 순번 결정 방식
  - Auto Increment를 통해 순번을 결정

## 1-2. 대기열 순번 폴링 API
![token polling api]()
### EndPoint
| Method | Request URL                         |
|--------|-------------------------------------|
| GET    | api/v1/concerts/{concertCode}/token |

### Request Headers
| Parameter     | Type | Required | Description |
|---------------|------|----------|-------------|
| Authorization | int  | O        | 대기열 토큰      |

### Request Body
| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| userUUID  | String | O        | 유저 ID       |

### Response Body `QueuePollingResponse`
| Parameter   | Type   | Required | Description   |
|-------------|--------|----------|---------------|
| tokenId     | Long   | X        | 현재 유저의 대기열 순번 | 
| token       | int    | O        | 대기열 토큰        | 
| queueStatus | String | O        | 대기열 상태        | 

### Error
- repository
  1. 존재하지 않는 콘서트 Code
  2. 존재하지 않는 유저 UUID
- interceptor
  1. 현재 유저의 대기열 토큰과 일치하지 않음

### 요구사항 분석
- 클라이언트에서 대기열 순번 폴링 API를 요청
1. 자신의 대기열 상태를 조회
   1. 만약 `IN_PROGRESS`라면 대기열 폴링 API 레벨에서 예매 페이지로 넘어간다는 뜻이므로 순번 없이 바로 response
   2. `WAIT`이라면 자신의 현재 순번을 알 수 있는 마지막 `IN_PROGRESS`순번에서 자신의 순번을 뺀 값을 response

## 1-3. 대기열 순번 Scheduler

### 요구사항 분석
- 서버측에서 대기열 순번을 일괄적으로 변경
- `@Scheduled`을 이용하여 2초마다 실행
1. 예매가 안된 좌석(콘서트 예약 테이블과는 무관) Count를 조회
2. 토큰 상태가 `IN_PROGRESS`인 데이터 조회 `List`
3. 예매가 안된 좌석 Count - `List.size()`의 값 구하기
4. 마지막 `IN_PROGRESS`인 데이터 구하기
5. 마지막 `IN_PROGRESS` 토큰의 순번보다 다음 순번부터 3번의 값 만큼 토큰 변경
   - 만약 3번의 값이 음수이면 `continue`
   - 대기열 상태: `WAIT -> IN_PROGRESS`
   - 대기열 토큰 유효 시간: `LocalDateTime.now().plusMinutes(10)`

## 1-4. 대기열 만료 Scheduler

### 요구사항 분석
- 서버측에서 유효 시간이 만료된 토큰을 삭제 시킴
- `@Scheduled`을 이용하여 10초마다 실행

---

## 2-1. 예매 가능 날짜 조회 API
![date list api](https://github.com/shihaim/hhplus/blob/main/chapter2/sequence_diagram/ticketing_date_list_api.png)
### EndPoint
| Method | Request URL                   |
|--------|-------------------------------|
| GET    | api/v1/concerts/{concertCode} |

### Request Headers
| Parameter     | Type | Required | Description |
|---------------|------|----------|-------------|
| Authorization | int  | O        | 대기열 토큰      |

### Request Body
| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| userUUID  | String | O        | 유저 ID       |

### Response Body `List<AvailableConcertDate>`
| Parameter   | Type      | Required | Description |
|-------------|-----------|----------|-------------|
| concertCode | String    | O        | 콘서트 Code    | 
| concertName | String    | O        | 콘서트 이름      | 
| concertDate | LocalDate | O        | 예매 가능한 날짜   |

### Error
- repository
  1. 존재하지 않는 콘서트 Code 
  2. 존재하지 않는 유저 UUID
- interceptor
  1. 현재 유저의 대기열 토큰과 일치하지 않음

## 2-2. 해당 날짜의 좌석 조회 API
![seat list api](https://github.com/shihaim/hhplus/blob/main/chapter2/sequence_diagram/ticketing_seat_list_api.png)
### EndPoint
| Method | Request URL                         |
|--------|-------------------------------------|
| GET    | api/v1/concerts/{concertCode}/seats |

### Request Headers
| Parameter     | Type | Required | Description |
|---------------|------|----------|-------------|
| Authorization | int  | O        | 대기열 토큰      |

### Request Body `FindConcertSeatsRequest`
| Parameter   | Type   | Required | Description |
|-------------|--------|----------|-------------|
| userUUID    | String | O        | 유저 ID       |
| concertDate | String | O        | 콘서트 날짜      |


### Response Body `List<AvailableConcertSeatsResponse>`
| Parameter   | Type   | Required | Description   |
|-------------|--------|----------|---------------|
| concertCode | String | O        | 콘서트 코드        | 
| concertDate | String | O        | 예매 가능한 날짜     | 
| seatNumber  | int    | O        | 좌석 번호         | 
| status      | Enum   | O        | 임시 배정 상태      | 


### Error
- repository
  1. 존재하지 않는 콘서트 Code
  2. 존재하지 않는 유저 UUID
- interceptor
  1. 현재 유저의 대기열 토큰과 일치하지 않음

---

## 3-1. 좌석 예매 요청 API
![temp seat api](https://github.com/shihaim/hhplus/blob/main/chapter2/sequence_diagram/ticketing_temp_seat_api.png)
### EndPoint
| Method | Request URL                   |
|--------|-------------------------------|
| PATCH  | api/v1/concerts/{concertCode} |

### Request Headers
| Parameter     | Type | Required | Description |
|---------------|------|----------|-------------|
| Authorization | int  | O        | 토큰          |

### Request Body `ReservationRequest`
| Parameter   | Type   | Required | Description |
|-------------|--------|----------|-------------|
| userUUID    | String | O        | 유저 ID       | 
| concertDate | String | O        | 콘서트 날짜      | 
| seatNumber  | int    | O        | 좌석 번호       | 

### Response Body `ReservationResponse`
| Parameter   | Type   | Required | Description  |
|-------------|--------|----------|--------------|
| concertCode | String | O        | 콘서트 코드       | 
| concertDate | String | O        | 콘서트 날짜       | 
| seatNumber  | int    | O        | 임시 배정된 좌석 번호 | 
| assignedAt  | String | O        | 임시 배정된 시간    | 

### Error
- repository
  1. 존재하지 않는 콘서트 Code
  2. 존재하지 않는 좌석 (-1, 51, etc)
  3. 이미 예매가 완료된 좌석
  4. 존재하지 않는 유저 UUID
- interceptor
  1. 현재 유저의 대기열 토큰과 일치하지 않음
- business
  1. 이미 임시 배정된 좌석(동시성 문제인 경우 낙관적 락으로 해결)
  2. 현재 배정할 수 없는 좌석(결제가 되지 않은 시점으로 부터) :: `보류`

---

## 4-1. 잔액 조회 API
![balance api](https://github.com/shihaim/hhplus/blob/main/chapter2/sequence_diagram/balance_api.png)
### EndPoint
| Method | Request URL                 |
|--------|-----------------------------|
| GET    | api/v1/users/{UUID}/balance |

### Response Body
| Parameter | Type | Required | Description  |
|-----------|------|----------|--------------|
| balance   | int  | O        | 현재 가지고 있는 잔액 |  

### Error
- repository
  1. 존재하지 않는 유저 UUID

## 4-2. 잔액 충전 API
![balance charge api](https://github.com/shihaim/hhplus/blob/main/chapter2/sequence_diagram/balance_charge_api.png)
### EndPoint
| Method | Request URL                     |
|--------|---------------------------------|
| PATCH  | api/v1/users/{userUUID}/balance |

### Request Body
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| amount    | int  | O        | 충전 금액       |

### Response Body
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| balance   | int  | O        | 총 잔액        | 

### Error
- repository
  1. 존재하지 않는 유저 UUID
  2. amount가 음수

---

## 5. 결제 API
![payment api](https://github.com/shihaim/hhplus/blob/main/chapter2/sequence_diagram/payment_api.png)
### EndPoint
| Method | Request URL     |
|--------|-----------------|
| POST   | api/v1/payments |

### Request Headers
| Parameter     | Type | Required | Description |
|---------------|------|----------|-------------|
| Authorization | int  | O        | 대기열 토큰      |

### Request Body
| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| userUUID  | String | O        | 유저 ID       |

### Response Body `PaymentDetailResponse`
| Parameter   | Type   | Required | Description |
|-------------|--------|----------|-------------|
| userUUID    | String | O        | 유저 ID       |
| concertName | String | O        | 콘서트 코드      |
| concertDate | String | O        | 콘서트 날짜      |
| seatNumber  | int    | O        | 좌석 번호       |
| price       | int    | O        | 가격          |

### Error
- repository
  1. 존재하지 않는 유저 UUID
  2. 임시 배정된 좌석이 존재하지 않음 
  3. 배정 시간이 만료됨
  4. 충전한 잔액이 콘서트 가격보다 적음
- interceptor
    1. 현재 유저의 대기열 토큰과 일치하지 않음

---
# 동시성이 발생할 수 있는 상황
### (1) 찰나의 순간
1. 결제 진행하려고하는 순간에는 임시 배정이 되어있었지만, 찰나의 순간(임시 배정 시간 내 결제를 진행하지 않음)에 
   임시 배정이 다른 유저에게 넘어가는 경우
   - `Reservation`에서 낙관적 락을 이용하기 때문에, 결제가 실패할 것.
### (2) 동시에 임시 배정 진행
1. 동시에 임시 배정을 진행하려는 경우 `Reservation`에서 낙관적 락을 이용하기 때문에, 성공한 유저를 제외한 다른 유저는 실패할 것.

---
# ERD 설계서
![Ticketing ERD](https://github.com/shihaim/hhplus/blob/main/chapter2/erd/concert_erd.png)
### 보완사항
- 콘서트는 어떠한 장소에서 이루어짐(ex. 잠실구장).
- 좌석이 고정되어 있고, 콘서트마다 좌석 관련 데이터가 만들어지는 것은 비효율적.
- `ConcertOption`을 통해서 `Concert` 1:N `ConcertOption` N:1 `Seat`로 나눠 관리 필요
- 만약 콘서트를 개최하는 장소가 동적으로 늘어나게 되는 경우 `ConcertOption`의 중요성이 증가

---
# Swagger
![Ticketing Swagger](https://github.com/shihaim/hhplus/blob/main/chapter2/swagger/ticketing_swagger.png)