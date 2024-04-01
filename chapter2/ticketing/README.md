# [Chapter2 - 콘서트 예매 서비스]
## 1-1. 유저 토큰 발급 API
### EndPoint
| Method | Request URL                         |
|--------|-------------------------------------|
| POST   | api/v1/concerts/{concertCode}/token |

### Request Body
| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| UUID      | String | O        | 유저 ID       |

### Response Body
| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| token     | String | O        | 대기열 토큰      | 

### Error
1. 존재하지 않는 콘서트 Code
2. 존재하지 않는 유저 UUID

## 1-2. 대기열 폴링 API

---

## 2-1. 예약 가능 날짜 조회 API
### EndPoint
| Method | Request URL                   |
|--------|-------------------------------|
| GET    | api/v1/concerts/{concertCode} |

### Request Headers
| Parameter     | Type   | Required | Description |
|---------------|--------|----------|-------------|
| Authorization | String | O        | 대기열 토큰      |

### Request Body
| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| UUID      | String | O        | 유저 ID       |

### Response Body
| Parameter       | Type | Required | Description   |
|-----------------|------|----------|---------------|
| concertDateList | List | O        | 예매 가능한 날짜 리스트 | 

### Error
1. 존재하지 않는 콘서트 Code 
2. 존재하지 않는 유저 UUID 
3. 현재 유저의 대기열 토큰과 일치하지 않음

## 2-2. 해당 날짜의 좌석 조회 API
### EndPoint
| Method | Request URL                         |
|--------|-------------------------------------|
| GET    | api/v1/concerts/{concertCode}/seats |

### Request Headers
| Parameter     | Type   | Required | Description |
|---------------|--------|----------|-------------|
| Authorization | String | O        | 대기열 토큰      |

### Request Body
| Parameter   | Type          | Required | Description |
|-------------|---------------|----------|-------------|
| UUID        | String        | O        | 유저 ID       |
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

## 3-1. 좌석 예약 요청 API
### EndPoint
| Method | Request URL                   |
|--------|-------------------------------|
| PATCH  | api/v1/concerts/{concertCode} |

### Request Headers
| Parameter     | Type   | Required | Description |
|---------------|--------|----------|-------------|
| Authorization | String | O        | 토큰          |

### Request Body
| Parameter   | Type          | Required | Description |
|-------------|---------------|----------|-------------|
| UUID        | String        | O        | 유저 ID       | 
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

## 3-2. 좌석 배정 상태 폴링 API

---

## 4-1. 잔액 조회 API
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
### EndPoint
| Method | Request URL                 |
|--------|-----------------------------|
| PATCH  | api/v1/users/{UUID}/balance |

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
### EndPoint
| Method | Request URL     |
|--------|-----------------|
| POST   | api/v1/payments |

### Request Headers
| Parameter     | Type   | Required | Description |
|---------------|--------|----------|-------------|
| Authorization | String | O        | 대기열 토큰      |

### Request Body
| Parameter | Type   | Required | Description |
|-----------|--------|----------|-------------|
| UUID      | String | O        | 유저 ID       |

### Response Body
| Parameter | Type       | Required | Description |
|-----------|------------|----------|-------------|
| status    | HttpStatus | O        | HTTP 결과값    | 

### Error
1. 존재하지 않는 유저 UUID
2. 현재 유저의 대기열 토큰과 일치하지 않음
3. 배정된 좌석이 존재하지 않음
4. 콘서트 가격보다 충전한 잔액이 적음