# Simple to-do list application
Приложение для списка задач (to-do)

## Описание
- Backend service:
    - api для создания задач;
    - api для обновления задач;
    - api для получения задач по фильтру (active, completed, all);
    - api для изменения статуса у задачи (всех задач);
    - api для удаления задачи (задач);
    - api для количества задач (по фильтрам) и наличие завершенных задач;
    - обработка exception;
    - настройка CORS для frontend части;
    - написаны Unit-тесты сервиса задач (сохранение, обновление);
    - упаковано в docker-контейнер.
- Frontend service:
    - использован готовый проект, но дописанны запросы в бэкенд;
    - отдается статически из docker-конейнера;
    - оригинал кода: [TodoMVC App Written in Vanilla JS in 2022](https://github.com/1Marc/modern-todomvc-vanillajs)

## Технологии
- Java 17;
- Spring Boot, Jersey;
- Spring Data: Hibernate, JPA;
- PostgreSQL, SQL;
- Junit, Mockito;
- Maven, Docker, Docker-compose.

## Запуск проекта
**приложение использует порты 8080 (frontend), 9090 (backend), 6542 (db).**
1. git clone git@github.com:notbadcodecom/hh-school-task-todo.git
2. cd hh-school-task-todo
3. docker-compose up
4. открыть http://localhost:8080/
