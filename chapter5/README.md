# [Chapter5 - 장애 대응]
### 콘서트 날짜 조회
![jmeter dashboard](https://github.com/shihaim/hhplus/blob/main/chapter5/dashboard/dashboard1.png)

 - 현재 로컬 PC의 물리적 메모리 크기가 8GB이며, 어떠한 메모리 설정을 건드리지 않았으므로, 초기 힙 크기는 128MB, 최대 힙 크기는 2GB
 - 2000명의 가상의 유저(Threads)를 1초(Ramp-up)에 부하를 가하여 테스트를 진행
 - 평균 1.6초 가량 소요되었고, Slow Query라고 판단
 - 콘서트 코드와 날짜는 콘서트 예약 진행시 변경되기 힘든 데이터이므로, 캐시를 통하여 조회 성능 증가

