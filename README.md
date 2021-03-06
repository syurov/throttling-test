**Цель: проверить навыки написания алгоритмов и навык владения Java SDK и библиотеками Spring, Spring Boot**

Описание: 
написать spring-boot приложение, которое будет содержать 1 контроллер с одним методом, который возвращает HTTP 200 и пустую строку
Написать функционал, который будет ограничивать количество запросов с одного IP адреса на этот метод в размере 50 штук в минуту. Если количество запросов больше, то должен возвращаться 502 код ошибки, до тех пор, пока количество обращений за последнюю минуту не станет ниже 50.
Сделать так, чтобы это ограничение можно было применять быстро к новым методам и не только к контроллерам, а также к сервисам.
Реализация должна учитывать многопоточную высоконагруженную среду исполнения и потреблять как можно меньше ресурсов.
Проект должен собираться при помощи maven командой mvn clean package и запускаться командой java -jar test-1.jar. Порт приложения должен быть 8080.
Использовать Java 8 & maven 3
Не использовать сторонних библиотек для троттлинга

**Реализация**

Задача решена с использованием АОП. Был написан аспект ThrottlingAspect,
в котором определен метод с анатацией Around, данный аспект реагирует на методы помеченные анатацией @Throttling

С помощью @DeclareParents я расщиряю this до интерфейса ConcurrentMap<String, LinkedList<Long>>, где ключ - название метода
А данные - линкованный список, где Long - Date.getTime().

В методе throttle при конкурентном доступе к елементу ConcurrentMap я сначала добавляю <key, путой список>, если ключ отсутвует
Далее в конкуретнотном доступе произвожу следующие изменения:
  1. удаляю елементы, которые истикли по сроку годности (прошло более одной минуты)
  2. проверяю размер массива, если он меньше 50 (размер массива определяется в анатации Throttling, но по умолчанию 50)
     то вставляю в массив текущее время вызова
     иначе бросаю exception


LinkedList был выбран, потому что предполагается много операций удаления, но при этом проседаем на производительности считая size :(

Данный аспект можно легко применить для любого класса в пакете приложения.



**Структура проекта**

- pom.xml - главный pom файл проекта
  |
  server - серверная часть проекта
    |
    test - модуль приложения Spring, содержит точку входа в приложение и базовые конфигурационные файлы
    throttling-api - модуль слоя контроллеров REST-API
    throttling-service - модуль бизнес логики
    throttling-common - модуль содержит общие интерфейсы бизнес логики, а также DTO объекты

  Проект реализован на SPRING Boot 2.1.1.RELEASE, написаны unit тесты и интеграционные тесты

**BUILD**
   :java_version: 1.8
    Для сборки необходимо выполнить **mvn clean install**

**RUN**

    Для запуска приложения необходимо запустить
      В IDE - com.easy.throttling.application.Application
      Командной строкой:
      java -jar test-1.jar

   После запуска можно открыть страницу по умолчанию http://localhost:8080/