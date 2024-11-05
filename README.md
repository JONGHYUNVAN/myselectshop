## 실행하기 전에

mySQL 을 사용하므로 mySQL이 실행중이어야 작동합니다.  
application.properties 의 database 변수들에 맞추어 mySQL 의 DB 를 생성해주세요.
```
spring.datasource.url=jdbc:mysql://localhost:3306/shop
spring.datasource.username=root
spring.datasource.password=qwe123
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
```
## 실행방법
![image](https://github.com/user-attachments/assets/abf5cacf-bd82-4f3a-aa32-f1b1b314290e)
MyselectshopApplication 에서 샐행 아이콘 클릭   
혹은  
![image](https://github.com/user-attachments/assets/8fb48897-c103-40ba-9fcf-93cbdb5a1f23)
상단 실행 아이콘

## 버전 및 의존성
Java 및 Spring Boot  
- Java: 17  
- Spring Boot: 3.3.5  

JWT (JSON Web Token)  
- jjwt-api: 0.11.5  
- jjwt-impl: 0.11.5  
- jjwt-jackson: 0.11.5  

JSON  
- org.json: 20230227

Spring Boot Starters  
- spring-boot-starter-data-jpa  
- spring-boot-starter-security  
- spring-boot-starter-thymeleaf  
- spring-boot-starter-validation  
- spring-boot-starter-web  

Thymeleaf Extras  
- thymeleaf-extras-springsecurity6  
- Lombok  
- lombok  

MySQL  
- mysql-connector-j

Testing Libraries  
- spring-boot-starter-test  
- spring-security-test  
- junit-platform-launcher  

## Api Documentation  
https://documenter.getpostman.com/view/24689794/2sAY4yegBH
