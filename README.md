#블로그 검색 서비스
---

##실행
1. rabbitMq 실행
2. H2 DB: SCHEMA=BLOG_SERVICE
3. jar 실행
> java -jar ./blog-api-1.0-SNAPSHOT.jar 
> java -jar ./blog-keyword-1.0-SNAPSHOT.jar

##api 명세
1. 블로그 검색
> curl -X GET http://localhost:8084/api/v1/blog/search?keyword=test&&sort=&&page&&size=
2. 키워드 top N 조회(default=10)
> curl -X GET http://localhost:8085/api/v1/keyword/top?limit=10

##dependency
- H2 1.4.199
- RabbitMQ 3.11.10
- flyway 7.15.0
- Open feign 11.10
- springBoot 2.7.6
- springCloudVersion 2021.0.5
- springDependencyManagementVersion 1.0.15.RELEASE
