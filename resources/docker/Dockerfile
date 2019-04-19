FROM clojure:openjdk-8-tools-deps-alpine

MAINTAINER Yunior Alayo Rondon "rondon.sarnik@gmail.com"

ENV DATOMIC_VERSION 0.9.5656
ENV DATOMIC_HOME /opt/datomic-pro-$DATOMIC_VERSION
ENV DATOMIC_DATA $DATOMIC_HOME/data
ENV APP_HOME /usr/src/app
ENV MY_DATOMIC_USERNAME "rondon.sarnik@gmail.com"
ENV MY_DATOMIC_PASSWORD "7eef0fde-5268-40c3-9f5f-36525bd8a4b2"

RUN apk add --no-cache unzip curl

RUN curl -u "$MY_DATOMIC_USERNAME:$MY_DATOMIC_PASSWORD" -SL https://my.datomic.com/repo/com/datomic/datomic-pro/$DATOMIC_VERSION/datomic-pro-$DATOMIC_VERSION.zip -o /tmp/datomic.zip \
  && unzip /tmp/datomic.zip -d /opt \
  && rm -f /tmp/datomic.zip

COPY sql-transactor-template.properties $DATOMIC_HOME/config

RUN mkdir -p $APP_HOME

WORKDIR $DATOMIC_HOME

#COPY settings.xml /root/.m2/

EXPOSE 3000

CMD ["sh", "-c", "java -server -Dserver.port=$PORT -cp resources:lib/*:datomic-transactor-pro-0.9.5656.jar:samples/clj:bin -Xmx1g -Xms1g -XX:+UseG1GC -XX:MaxGCPauseMillis=50 clojure.main --main datomic.launcher config/sql-transactor-template.properties", "&"]