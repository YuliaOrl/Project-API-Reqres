# Проект по автоматизации API тестирования для сайта Reqres.in

## :green_book: Содержание:

- [Использованный стек технологий](#computer-использованный-стек-технологий)
- [Варианты запуска тестов](#running_woman-варианты-запуска-тестов)
- [Сборка в Jenkins](#-сборка-в-jenkins)
- [Allure-отчет](#-allure-отчет)
- [Интеграция с Allure TestOps](#-интеграция-с-allure-testops)
- [Уведомление в Telegram с использованием бота](#-уведомление-в-telegram-с-использованием-бота)

## :computer: Использованный стек технологий

<p align="center">
<img width="6%" title="Java" src="images/logo/Java.svg">
<img width="6%" title="Allure Report" src="images/logo/Allure_Report.svg">
<img width="6%" title="Allure Report" src="images/logo/AllureTestOps.svg">
<img width="6%" title="IntelliJ IDEA" src="images/logo/Intelij_IDEA.svg">
<img width="6%" title="Selenide" src="images/logo/Selenide.svg">
<img width="6%" title="Selenoid" src="images/logo/Selenoid.svg">
<img width="6%" title="Gradle" src="images/logo/Gradle.svg">
<img width="6%" title="JUnit5" src="images/logo/JUnit5.svg">
<img width="6%" title="JUnit5" src="images/logo/RestAssured.svg">
<img width="6%" title="GitHub" src="images/logo/GitHub.svg">
<img width="6%" title="Jenkins" src="images/logo/Jenkins.svg">
<img width="6%" title="Telegram" src="images/logo/Telegram.svg">
</p>

Автотесты для REST API написаны на <code>Java</code> с использованием <code>Gradle</code> и <code>JUnit 5</code>.
Для API-тестов используется библиотека Rest-Assured, модели Lombok и спецификации.
Сборка в <code>Jenkins</code> реализована с формированием Allure-отчета и отправкой уведомления с результатами тестирования в <code>Telegram</code> после завершения прохождения тестов.

Allure-отчет включает в себя:
* названия тестов с шагами выполнения;
* тело запроса;
* статус код;
* тело ответа.

## :running_woman: Варианты запуска тестов

### Локальный запуск тестов
Для локального запуска необходимо выполнить команду в терминале:
```
gradle clean test
```

## <img width="4%" style="vertical-align:middle" title="Jenkins" src="images/logo/Jenkins.svg"> Сборка в Jenkins
### <a target="_blank" href="https://jenkins.autotests.cloud/job/Auto%20testing%20main%20page%20Pobeda.aero/">*Jenkins job*</a>

<p align="center">
<img title="Jenkins Build" src="images/screenshots/jenkinsBuild.png">
</p>

## <img width="4%" style="vertical-align:middle" title="Allure Report" src="images/logo/Allure_Report.svg"> Allure-отчет
### <a target="_blank" href="https://jenkins.autotests.cloud/job/Auto%20testing%20main%20page%20Pobeda.aero/10/allure/">*Overview*</a>

<p align="center">
<img title="Allure Overview" src="images/screenshots/allureReportMain.png">
</p>

### *Результат прохождения параметризованных тестов с описанием  и шагами выполнения*

<p align="center">
<img title="Test Results in Alure" src="images/screenshots/allureReportTests.png">
</p>

### *Графики*
<p align="center">
<img title="Allure Overview" src="images/screenshots/allureReportGraphs1.png">
</p>
<p align="center">
<img title="Allure Overview" src="images/screenshots/allureReportGraphs2.png">
</p>

## <img width="4%" style="vertical-align:middle" title="Telegram" src="images/logo/AllureTestOps.svg"> Интеграция с Allure TestOps
### *Allure TestOps* <a target="_blank" href="https://allure.autotests.cloud/project/1669/dashboards">*Dashboard*</a>

<p align="center">  
<img title="Allure TestOps Dashboard" src="images/screenshots/AllureTestOpsDashboard.png">  
</p>  

### *Тест кейсы*

<p align="center">  
<img title="Allure TestOps Tests" src="images/screenshots/AllureTestOpsTestCases.png">  
</p>

## <img width="4%" style="vertical-align:middle" title="Telegram" src="images/logo/Telegram.svg"> Уведомление в Telegram с использованием бота

После завершения сборки специальный бот, созданный в <code>Telegram</code>, автоматически отправляет сообщение с отчетом прохождения тестов.

<p align="center">
<img width="70%" title="Telegram Notifications" src="images/screenshots/notificationTelegram.png">
</p>
