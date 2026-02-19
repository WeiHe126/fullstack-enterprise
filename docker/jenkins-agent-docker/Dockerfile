# Dockerfile Jenkins Agent con Maven + Docker
FROM maven:3.9.6-eclipse-temurin-21

USER root

# Instalar Docker CLI
RUN apt-get update && \
    apt-get install -y docker.io && \
    rm -rf /var/lib/apt/lists/*

USER 1000
