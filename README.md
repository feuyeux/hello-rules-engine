# Hello Rule Engine

fork from <https://github.com/Rameshkatiyar/rules-engine>

- [What is Rule-Engine?](https://medium.com/@er.rameshkatiyar/what-is-rule-engine-86ea759ad97d)
- [Implement your own Rule-Engine (Java8 + SpringBoot + MVEL)](https://medium.com/@er.rameshkatiyar/implement-your-own-rule-engine-java8-springboot-mvel-5928474e1ba5)

## Design

Rule’s Language = MVEL + DSL

![](https://miro.medium.com/v2/1*Il9wJN3CvkETuANrgc-2RA.png)

## Run

```sh
docker compose down
docker compose up -d
```

```sh
# Test database connection
docker exec -it re_postgres psql -h localhost -U postgres -c "\l"

# Run application tests
mvn clean test
```
