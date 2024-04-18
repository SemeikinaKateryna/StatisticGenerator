Опис основних сутностей

У програмі існують такі сутності як Product і Manufacturer. Product основна сутність, відноситься до другорядної як багато-до-одного, тобто у одного виробника може бути багато продуктів.
Основна сутність має кілька атрибутів: назва, рік випуску, ціна, виробник та категорії, до яких цей товар відносится. Виробник має тільки ім'я.


Приклади вхідних і вихідних файлів

Файли вхідні містятся в папці input проекту, 
приклад products.json:https://github.com/SemeikinaKateryna/StatisticGenerator/blob/master/input/products.json

Вихідні файли в свою чергу містятся в папці output проекту, 
приклад statistics_by_releaseYear.xml: https://github.com/SemeikinaKateryna/StatisticGenerator/blob/master/output/statistics_by_releaseYear.xml


Результати експериментів з різною кількістю потоків

При обробці 24 файлів, в кожному з яких приблизно 10-15 JSON-об'єктів програма показала такі результати


1 поток:     461 мс

2 потоки:    399 мс

4 потоки:    397 мс

8 потоків:   386 мс


Інструкція по запуску програми

java -jar StatisticGenerator.jar folderPath attributeName
