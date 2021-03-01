FROM adoptopenjdk:11-jre-hotspot

VOLUME /tmp

ENV TZ=Europe/Berlin
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime && echo $TZ > /etc/timezone

ENV USER toonfeed
RUN useradd -m ${USER}

RUN mkdir /opt/app
WORKDIR /opt/app

COPY target/toonfeed.jar /opt/app/

USER ${USER}
ENTRYPOINT ["java", "-cp", "/opt/app/toonfeed.jar"]
CMD ["-Dloader.main=com.mrazjava.toonfeed.ToonfeedApplication", "org.springframework.boot.loader.PropertiesLauncher"]

EXPOSE 8080