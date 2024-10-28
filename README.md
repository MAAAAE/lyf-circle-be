![](./merlian.webp "logo")

## prerequisites

```shell
# for using openai 
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
# for postgres vector and mongodb
docker compose up -d
```


## how to run

```
mvn clean install
mvn package -DskipTests
java -jar lyf-circle-0.0.1-SNAPSHOT.jar
```
