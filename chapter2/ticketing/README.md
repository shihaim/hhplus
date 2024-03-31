# [Chapter2 - 콘서트 예매 서비스]
## 1. 유저 토큰 발급 API
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
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
|           |      |          |             | 

### Error

## 2-1. 해당 날짜의 좌석 조회 API
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
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
|           |      |          |             | 

### Error

---

## 3. 좌석 예약 요청 API
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
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
|           |      |          |             | 

### Error

---

## 4-1. 잔액 조회 API
### EndPoint
| Method | Request URL                 |
|--------|-----------------------------|
| GET    | api/v1/users/{UUID}/balance |

### Response Body
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
|           |      |          |             |  

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
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
|           |      |          |             | 

### Error

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
| Parameter | Type | Required | Description |
|-----------|------|----------|-------------|
|           |      |          |             | 

### Error