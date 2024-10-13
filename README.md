

## prerequisites

```shell
export SPRING_AI_OPENAI_API_KEY=<INSERT KEY HERE>
docker compose up -d
```


## how to test

```
GET http://localhost:8080/event/create?prompt=<your prompt>
```
