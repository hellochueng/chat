FROM java:8
VOLUME /tmp
COPY target/chat-1.0-SNAPSHOT.jar chat.jar
RUN bash -c "touch /chat.jar"
ENTRYPOINT ["java","-jar","chat.jar"]