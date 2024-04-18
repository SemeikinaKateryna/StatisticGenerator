Опис основних сутностей
У програмі існують такі сутності як Product і Manufacturer. Product основна сутність, відноситься до другорядної як багато-до-одного, тобто у одного виробника може бути багато продуктів.
Основна сутність має кілька атрибутів: назва, рік випуску, ціна, виробник та категорії, до яких цей товар відносится. Виробник має тільки ім'я.

Приклади вхідних і вихідних файлів
Файли вхідні містятся в папці input проекту, 
приклад products.json:
[
  {
    "name": "Men's Casual Shirt",
    "releaseYear": 2021,
    "price": 45.99,
    "manufacturer": "FashionHub",
    "categories": "Men's, Clothing"
  },
  {
    "name": "Women's Summer Dress",
    "releaseYear": 2020,
    "price": 65.50,
    "manufacturer": "StyleQueen",
    "categories": "Women's, Clothing"
  },
  ...
]
Вихідні файли в свою чергу містятся в папці output проекту, 
приклад statistics_by_releaseYear.xml:
<?xml version="1.0" encoding="UTF-8" standalone="yes"?>
<statistics>
    <item>
        <value>2022</value>
        <count>196</count>
    </item>
    <item>
        <value>2023</value>
        <count>166</count>
    </item>
    <item>
        <value>2020</value>
        <count>10</count>
    </item>
    <item>
        <value>2021</value>
        <count>10</count>
    </item>
</statistics>

Результати експериментів з різною кількістю потоків
При обробці 24 файлів, в кожному з яких приблизно 10-15 JSON-об'єктів програма показала такі результати
1 поток:     461 мс
2 потоки:    399 мс
4 потоки:    397 мс
8 потоків:   386 мс

Інструкція по запуску програми
<Path to .jar file> java -jar StatisticGenerator.jar <folderPath> <attributeName>
