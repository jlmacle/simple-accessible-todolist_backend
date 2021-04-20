FROM ubuntu:18.04
RUN /usr/bin/mvn package
CMD ["java", "-jar", "/target/AccessibleTodoList_BackEnd-0.0.1-SNAPSHOT.jar"]