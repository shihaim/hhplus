# [Chapter4-1 - 동시성 문제와 극복]
### 1. 잔액 충전 따닥 문제
 - ConcurrentHashMap 이용
   - 장점: Application 레벨에서 Lock을 걸어줘서 성능도 좋고, DB에 부하가 없음.
   - 단점: 다수의 서버 인스턴스에서는 Locking이 제대로 실행되지 않음.
 - 낙관적 락 이용
   - 장점: 따닥 방어가 가능하다.
   - 단점: 유저가 따닥 충전을 원할 수 있다. -> retry 로직이 필요할 수 있음. -> retry는 DB 성능에 영향을 끼칠 수 있음
 - 비관적 락 이용
   - 장점: retry 로직없이 따닥 잔액을 원하는 경우 동시성 제어가 가능하다.
   - 단점: 따닥을 막는 목적이라면 xLock + execute row를 함께 사용해야하고, xLock을 걸기 때문에 성능에 영향을 미침
 - Redisson의 redlock을 이용
   - 장점: 다수 서버 인스턴스에서도 동시성을 보장하는 분산 락을 이용할 수 있으며, Pessmistic Lock/Optimistic Lock 둘 다 구현이 가능
   - 단점: Redis 인스턴스를 더 두는 것은 리소스 자원 낭비(오버 엔지니어링)가 될 수 있으며, Cluster를 설정하지 않는다면 In-Memory 환경이여서 휘발적
#### 구현
 - Redisson redlock을 이용하여 따닥시 순차 충전이 되도록 진행
 - LockHandler와 TransactionHandler를 분리하여 구현

### 2. 결제 따닥 문제
 - 낙관적 락 이용
   - 장점: 따닥 방어가 가능하다.
   - 단점: 사실 없어보입니다??..
     - 선점한 좌석 예약 건에 대해 결제를 진행하므로 retry도 필요가 없고, 결제가 완료되면 다음 결제는 당연히 실패를 시켜야한다.
 - Redisson의 redlock을 이용
   - 장점: 다수 서버 인스턴스에서도 동시성을 보장하는 분산 락을 이용할 수 있으며, Pessmistic Lock/Optimistic Lock 둘 다 구현이 가능
   - 단점: Redis 인스턴스를 더 두는 것은 리소스 자원 낭비(오버 엔지니어링)가 될 수 있으며, Cluster를 설정하지 않는다면 In-Memory 환경이여서 휘발적
#### 구현
 - Redisson redlock을 이용하여 따닥시 OptimisticLockException이 발생되도록 구현

### 3. 대기열 순번 동시 접근 문제
 - RDBMS INSERT를 이용하여 순차적으로 대기열 순번 토큰을 생성
   - 장점: 다른 부가 인스턴스가 필요하지 않음.
   - 단점: DB에 너무 많은 부하가 생김.
 - Redis Sorted Set을 이용하여 동시에 대기열 접근시 대기열 순번을 차례대로 생성
   - 장점: In-Memory이므로 성능이 좋고, 싱글 스레드(단일 인스턴스의 경우)이므로 동시성 제어가 가능하다.
   - 단점: Redis 인스턴스를 더 두기 때문에 리소스 자원이 추가되어야 한다. 그리고 Redis 서버가 죽게 되는 경우 큰 문제가 발생한다.
#### 구현
 - Sorted Set과 Skip List를 이용하여 대기열 순번 동시성 제어 및 순차 보장과 expire을 통한 토큰 만료 기능 구현
