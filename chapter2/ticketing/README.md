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

### Response Body
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
| tokenId   | Long | O        | 대기열 순번      | 
| token     | int  | O        | 대기열 토큰      | 

### Error
1. 존재하지 않는 콘서트 Code
2. 존재하지 않는 유저 UUID

### 설계
- 콘서트(아이유 콘서트 또는 BTS 콘서트)에 따라 대기열 토큰을 발급하고 티켓팅을 시작
- 순번 결정 방식
  - Auto Increment를 통해 순번을 결정
  - 콘서트가 다를 때 ROW NUMBER를 통하여 순번을 결정   
    (일회성 데이터라면 해당 방식은 의미 X)

## 1-2. 대기열 순번 폴링 API
구상만 하였고, 아직 생각은 못해봤습니다..

### 설계
- 클라이언트에서 대기열 순번에 관해 API를 요청

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

### Response Body
| Parameter   | Type      | Required | Description |
|-------------|-----------|----------|-------------|
| concertCode | String    | O        | 콘서트 Code    | 
| concertName | String    | O        | 콘서트 이름      | 
| concertDate | LocalDate | O        | 예매 가능한 날짜   | 
- `List<AvailableConcertDate>`로 반환

### Error
1. 존재하지 않는 콘서트 Code 
2. 존재하지 않는 유저 UUID 
3. 현재 유저의 대기열 토큰과 일치하지 않음

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

### Request Body
| Parameter   | Type          | Required | Description |
|-------------|---------------|----------|-------------|
| userUUID    | String        | O        | 유저 ID       |
| concertDate | LocalDateTime | O        | 콘서트 날짜      |

### Response Body
| Parameter       | Type | Required | Description   |
|-----------------|------|----------|---------------|
| concertSeatList | List | O        | 예매 가능한 좌석 리스트 | 

### Error
1. 존재하지 않는 콘서트 Code 
2. 존재하지 않는 유저 UUID 
3. 현재 유저의 대기열 토큰과 일치하지 않음

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

### Request Body
| Parameter   | Type          | Required | Description |
|-------------|---------------|----------|-------------|
| userUUID    | String        | O        | 유저 ID       | 
| concertDate | LocalDateTime | O        | 콘서트 날짜      | 
| seatNumber  | int           | O        | 좌석 번호       | 

### Response Body
| Parameter | Type       | Required | Description |
|-----------|------------|----------|-------------|
| status    | HttpStatus | O        | HTTP 결과값    | 

### Error
1. 존재하지 않는 콘서트 Code
2. 존재하지 않는 좌석 (-1, 51, etc)
3. 존재하지 않는 유저 UUID
4. 현재 유저의 대기열 토큰과 일치하지 않음
5. 좌석 배정 관련 에러
   - 이미 임시 배정된 좌석(동시성) 
   - 현재 배정할 수 없는 좌석(결제가 되지 않은 시점으로 부터)

### 설계


## 3-2. 좌석 배정 상태 폴링 API

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
| Parameter | Type       | Required | Description |
|-----------|------------|----------|-------------|
| status    | HttpStatus | O        | HTTP 결과값    | 

### Error
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

### Response Body
| Parameter | Type       | Required | Description |
|-----------|------------|----------|-------------|
| status    | HttpStatus | O        | HTTP 결과값    | 

### Error
1. 존재하지 않는 유저 UUID
2. 현재 유저의 대기열 토큰과 일치하지 않음
3. 임시 배정된 좌석이 존재하지 않음
4. 배정 시간이 만료됨
5. 충전한 잔액이 콘서트 가격보다 적음

---
# ERD 설계서
![Concert ERD](https://github.com/shihaim/hhplus/blob/main/chapter2/erd/concert_erd.png)