### Описание проекта
Проект тестирования страницы авторизации miro.com

### Конфигурация для запуска тестов
Изменить путь до chromedriver в файле src/test/resources/conf.properties

### Список тест-кейсов
- открытие страницы авторизации *(openPageTest)*
- авторизация с пустыми полями email и password *(emptyAuthorizationTest)*
- заполнение только поля email *(wrongPasswordTest)*
- некорректное заполнение поля email *(incorrectEmailTest)*
- заполнение только поля password *(wrongEmailTest)*
- ввод неверных значений email и password *(wrongAuthorizationTest)*
- ввод корректных значений email и password *(rightAuthorizationTest)*

### Запуск тестов
Для запуска всех тестов выполнить команду:

```mvn clean test```

Для запуска определенного кейса выполнить команду:

```mvn clean test -Dtest=org.example.AuthTest#CASE_NAME```
