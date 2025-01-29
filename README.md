# 2025 개발 계획 포트폴리오 목표
# 프로젝트명 : plan-nest
 - 포트폴리오 - 여행 &amp; 부동산 등 계획이 모이는 통합 플랫폼
 - 여행, 부동산 등 각종 계획을 하나의 사이트에서 관리할 수 있는 통합플랫폼 구축을 목표로 개발


### 개발스택
 - front-end : react
 - back-end : java, spring boot, spring security, Spring Boot Actuator, jpa, querydsl, junit
 - database : mysql

### 라이브러리(추가예정)
 - monitoring : Prometheus, Grafana
 - CI/CD : github actions
 - container : docker

### 모니터링 파트
| **모니터링 종류**     | **설명**                                   | **추천 솔루션**                                            |
|------------------|--------------------------------|--------------------------------------------------|
| **애플리케이션 모니터링** | API 응답 속도, 에러율, DB 성능            | `Spring Boot Actuator + Micrometer + Prometheus + Grafana` |
| **트래픽 모니터링**     | HTTP 요청 수, 상태 코드, API 응답 시간      | `Prometheus + Nginx Exporter or Blackbox Exporter` |
| **컨테이너 모니터링**   | Docker 컨테이너 리소스 사용량               | `cAdvisor + Prometheus + Grafana` |

### OPEN API 연계 파트
- OAuth 2.0 로그인 : 네이버, 카카오
- 여행관련 API : Google Places API, Weatherstack API
