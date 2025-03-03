# 1. Build Stage: Gradle 빌드 실행
FROM amazoncorretto:21 AS builder

WORKDIR /app

# 프로젝트 모든 파일 복사
COPY . .

# Gradle 실행 권한 부여 (Windows 환경에서 필요할 수도 있음)
RUN chmod +x gradlew

# Gradle 빌드 수행 (테스트 제외)
RUN ./gradlew clean build -x test

# 2. Run Stage: 빌드된 JAR 파일 실행
FROM amazoncorretto:21

WORKDIR /app

# 빌드된 JAR 파일 복사
COPY --from=builder /app/build/libs/*.jar app.jar

EXPOSE 8080

ENTRYPOINT ["java","-jar","app.jar"]
