# 1. OpenJDK 이미지를 기반으로 설정
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle 빌드 파일과 Wrapper 파일을 컨테이너 안으로 복사
COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle/wrapper/ /app/gradle/wrapper/
COPY src /app/src/

# 4. Gradle 실행 권한 부여
RUN chmod +x gradlew

# 5. Gradle을 사용하여 종속성을 설치하고 애플리케이션을 빌드
RUN ./gradlew build -x test

# 6. 빌드된 JAR 파일을 컨테이너 안으로 복사 (둘 중 하나 선택)
COPY build/libs/investement-0.0.1-SNAPSHOT.jar app.jar
# 또는
# COPY build/libs/investement-0.0.1-SNAPSHOT-plain.jar app.jar

# 7. 실행 스크립트 추가
COPY start.sh /app/start.sh
RUN chmod +x /app/start.sh

# 8. 애플리케이션 실행
ENTRYPOINT ["/app/start.sh"]
