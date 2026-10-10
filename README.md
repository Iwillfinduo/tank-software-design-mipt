# tank-software-design

Уровень можно создать генератором или загрузить из текстового файла:

```java
GameWorld generated = GameWorld.random(10, 8, 15);
GameWorld loaded = GameWorld.fromFile(Path.of("level.txt"));
```

В файле одна строка соответствует строке клеток уровня. Поддерживаются символы
`T` (дерево), `X` (единственная начальная позиция игрока) и `_` (пустая клетка).

Карты приложения находятся в `src/main/resources/maps`. При запуске по умолчанию
загружается `maps/default.txt`, а фон игрового поля — `maps/level.tmx`.