# 1. OpenJDK 20 이미지를 기반으로 설정
FROM openjdk:17-jdk-slim

# 2. 작업 디렉토리 설정
WORKDIR /app

# 3. Gradle 빌드 파일과 Wrapper 파일을 컨테이너 안으로 복사
COPY build.gradle settings.gradle gradlew gradlew.bat /app/
COPY gradle/wrapper/ /app/gradle/wrapper/

# 4. 모든 소스 코드 및 파일 복사
COPY . .

# 5. Gradle 실행 권한 부여
RUN chmod +x gradlew

# 6. Gradle을 사용하여 종속성을 설치하고 애플리케이션을 빌드
RUN ./gradlew build -x test

# 7. 빌드된 JAR 파일을 컨테이너 안으로 복사
COPY build/libs/*.jar app.jar

# 8. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]
