## Описание проекта

Проект построен по модульной архитектуре с использованием PostgreSQL и реплик для горизонтального масштабирования. В системе два основных сервиса:

- **DataService** — отвечает за запись справочников и фактов продаж. Также в этом сервисе формируется `materialized view` для удобного и быстрого чтения данных из другого сервиса.

- **AnalysisService** — обрабатывает запросы к агрегированным данным из `materialized view`, реализует логику её обновления и использует Redis для кэширования и ускорения отклика.

### Дополнительно:

- Добавлены индексы как в таблицах `DataService`, так и в `materialized view` для повышения эффективности запросов.
- Реализовано переключение между основной базой и репликами.
- Используется кэш Redis для ускорения обработки повторных запросов.
- Используется **Liquibase** для управления миграциями.
- Добавлено логирование и глобальная обработка ошибок через `@ControllerAdvice`.
