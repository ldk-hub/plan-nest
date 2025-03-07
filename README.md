# 프로젝트명 : plan-nest 포트폴리오
 - 포트폴리오 - 여행 &amp; 부동산 등 계획이 모이는 통합 플랫폼
 - 여행, 부동산 등 각종 계획을 하나의 사이트에서 관리할 수 있는 통합플랫폼 구축을 목표로 개발

### 개발스택
 - front-end : react
 - back-end : java, spring boot, spring security, Spring Boot Actuator, jpa, querydsl, junit
 - database : mysql, redis

### 라이브러리
 - monitoring : Prometheus, Grafana
 - CI/CD : github actions
 - container : docker
 - TOOL : sourcetree, postman, git
 - mem-cache : redis

## 개발 예정 순서
 - 단위 테스트 코드 구성
 - BE 로그인 프로세스 엔드포인트 및 테스트 코드 작성
 - FE react 로그인 프로세스 
 - 메인 대시보드 화면 개발(템플릿 발굴)
 - 회원 정보 페이지
 - kpi 통계 산출 페이지
 - 비즈니스 로직 테스트 코드 구현
 - 깃허브 액션 CI/CD 자동배포 구성

## 개발완료내역
 - naver OAuth2.0 Login api 인증 테스트 완료
 - http://localhost:8080/oauth2/authorization/naver 로 로그인 시 네이버 2차인증 로그인으로 랜딩 완료
 - redis config 설정 완료 및 캐싱구성 완료
 - k6 부하테스트 진행 시 20배 빨라짐 확인
 - 레디스 구성, TTL 설정 또는 이벤트 트리거 추가
 - 도커 compose 구성 완료



# 개발 수행 내역

## 도커 
 - 도커 실행 시 postgresql, redis 통합 서비스 올리도록 compose 구성
 -  실행 명령어 `docker-compose up --build -d`

## 레디스 캐시 구성
 - 쿼리 수행 최적화 목적 캐싱구성, 성능최적화 시뮬레이션 목적

### 모니터링 파트
| **모니터링 종류**     | **설명**                                   | **추천 솔루션**                                            |
|------------------|--------------------------------|--------------------------------------------------|
| **애플리케이션 모니터링** | API 응답 속도, 에러율, DB 성능            | `Spring Boot Actuator + Micrometer + Prometheus + Grafana` |
| **트래픽 모니터링**     | HTTP 요청 수, 상태 코드, API 응답 시간      | `Prometheus + Nginx Exporter or Blackbox Exporter` |
| **컨테이너 모니터링**   | Docker 컨테이너 리소스 사용량               | `cAdvisor + Prometheus + Grafana` |

## K6 부하테스트
- 테스트 시나리오 redis 캐싱처리 적용 전 1회 수행
```
script.js
import http from 'k6/http';
import { sleep } from 'k6';

export let options = {
vus: 30, // 동시 사용자 30명
duration: '10s', // 테스트 지속 시간 10초
};

export default function () {
http.get('http://localhost:8080/boards');
sleep(1);
}
```
- redis 캐싱처리 적용 후 1회 수행 후 속도 비교



