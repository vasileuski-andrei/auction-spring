# auction-spring
Auction is a spring web application that allows to post items for sale and take part in other sales according the auction rules.
The auction has a telegram bot, which is represented by an isolated microservice. Kafka - used for communication.
Bot provides to get the information about the account and number of lots.
Technologies - Java17, Spring(Boot, MVC, Data JPA, Security), Kafka, Docker, PostgresQL, Thymeleaf, Gradle, JUnit, Mockito, AssertJ, Lombok, Liquibase

Functionality - registration account(auth with google - OAuth2), login, email account activation, security(roles and permissions), 
place a bid, delete account, change password, captcha, db migration, unit and integration tests in testcontainers, logging, TG bot.

Аукцион - это spring веб приложение, которое позволяет размещать товары на продажу и принимать участие в торгах по правилам аукциона. 
Аукцион имеет телеграм бота, который представлен отдельным микросервисом. Сообщение между веб аукционом и ботом осуществляется с помощью kafka. 
В боте можно посмотреть информацию об аккаунте, количество выставленных лотов на продажу, количество всех лотов на площадке.

[![imageup.ru](https://imageup.ru/img242/thumb/market3942061.jpg)](https://imageup.ru/img242/3942061/market.png.html)
[![imageup.ru](https://imageup.ru/img76/thumb/registr3942059.jpg)](https://imageup.ru/img76/3942059/registr.png.html)
[![imageup.ru](https://imageup.ru/img17/thumb/index3942060.jpg)](https://imageup.ru/img17/3942060/index.png.html)
[![imageup.ru](https://imageup.ru/img116/thumb/login3952360.jpg)](https://imageup.ru/img116/3952360/login.png.html)
[![imageup.ru](https://imageup.ru/img172/thumb/tg-pc3960933.jpg)](https://imageup.ru/img172/3960933/tg-pc.jpg.html)
[![imageup.ru](https://imageup.ru/img236/thumb/tg-phone3960934.jpg)](https://imageup.ru/img236/3960934/tg-phone.jpg.html)
[![imageup.ru](https://imageup.ru/img254/thumb/db3960935.jpg)](https://imageup.ru/img254/3960935/db.png.html)

