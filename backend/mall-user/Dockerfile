FROM openjdk:11-jre
COPY wait-for-it.sh /root/wait-for-it.sh
RUN chmod +x /root/wait-for-it.sh
COPY target/mall-user.jar /root/mall-user.jar
ENTRYPOINT [ "/bin/bash", "-c", "/root/wait-for-it.sh ${NACOS_URL:-127.0.0.1}:${NACOS_PORT:-8848} -t 180 --strict -- java -jar /root/mall-user.jar"]