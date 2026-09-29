# java and maven
FROM maven:3-eclipse-temurin-21

# linux package manager
RUN apt-get update 

# version check
RUN java -version
RUN mvn -version



# Bash shell
CMD ["bash"]