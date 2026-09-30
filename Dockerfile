# java and maven
FROM maven:3-eclipse-temurin-21

# linux package manager and make
RUN apt-get update && apt-get install -y \
    make \
    && rm -rf /var/lib/apt/lists/*

# version check
RUN java -version
RUN mvn -version



# Bash shell
CMD ["bash"]