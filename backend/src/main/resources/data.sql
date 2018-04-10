
DELETE FROM LINKS;
DELETE FROM USER_AUTHORITY;
DELETE FROM AUTHORITY;
DELETE FROM USERS;

INSERT INTO USERS (id, username, password, name, email, enabled, last_password_reset_date) VALUES
(1, 'admin', '$2a$10$zuI3P8hoZNkFGR2dDPW9juA1C1xIHBUNrKMGqjjaEKsLTwjJkKoNa', 'Admin', 'admin@gmail.com', true, '2018-01-01 18:57:58.508-07'),
(2, 'siva', '$2a$10$LskLrNP6m.dEpXYjT41lRePseXJEjhd6.sPH2Z5GbbShtaFRWoeYq', 'Siva', 'siva@gmail.com', true, '2018-01-01 21:58:58.508-07')
;

INSERT INTO AUTHORITY (id, name) VALUES
(1, 'ROLE_USER'),
(2, 'ROLE_ADMIN')
;

INSERT INTO USER_AUTHORITY (user_id, authority_id) VALUES
(1, 1),
(2, 1),
(2, 2)
;

INSERT INTO LINKS(id, title, url, created_by, created_on) VALUES
(1, 'MicroServices – Part 6 : Distributed Tracing with Spring Cloud Sleuth and Zipkin','https://sivalabs.in/2018/03/microservices-part-6-distributed-tracing-with-spring-cloud-sleuth-and-zipkin/',1,now()),
(2, 'MicroServices – Part 5 : Spring Cloud Zuul Proxy as API Gateway','https://sivalabs.in/2018/03/microservices-part-5-spring-cloud-zuul-proxy-as-api-gateway/',2,now()),
(3, 'MicroServices – Part 4 : Spring Cloud Circuit Breaker using Netflix Hystrix','https://sivalabs.in/2018/03/spring-cloud-netflix-circuit-breaker/',2,now())
;