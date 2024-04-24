# [Chapter3 - CI/CD 배포 파이프라인 구축]

## Git Branch 전략
- main(master)
- release : main 브랜치로 병합되기 전 개발한 기능을 테스트하고, 안정적이라고 판단을 할 수 있는 브랜치
- feature/기능명 : 기능을 개발하는 브랜치(하나 단위의 기능만을 명시합니다.)
- hotfix : 배포버전에 문제가 발생하는 경우 빠른 대응을 위한 브랜치

### github workflow
#### develop
![develp workflow](https://github.com/shihaim/hhplus/blob/main/chapter3/github-workflow/develop.png)

#### hotfix
![develp workflow](https://github.com/shihaim/hhplus/blob/main/chapter3/github-workflow/hotfix.png)

## 서버 환경 분리
- dev
- stg
- prod

