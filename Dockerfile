# Temel imaj
FROM eclipse-temurin:17-jdk-alpine

# Çalışma dizini
WORKDIR /app

# Jar dosyasını kopyala
COPY target/deliveryapp-0.0.1-SNAPSHOT.jar app.jar

# Uygulamayı başlat
ENTRYPOINT ["java", "-jar", "app.jar"] 