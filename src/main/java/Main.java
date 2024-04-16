import calculation.StatisticGenerator;
import entity.Product;
import reading.IJsonReader;
import reading.JsonProductReader;
import writing.IXmlWriter;
import writing.XmlWriter;

import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {

        IJsonReader<Product> jsonReader = new JsonProductReader("./in");
        List<Product> products = jsonReader.read();

        StatisticGenerator<Product> generator = new StatisticGenerator<>(Product.class, "manufacturer");
        Map<Object, Integer> statisticsMap = generator.generateStatisticByAttribute(products);

        IXmlWriter xmlWriter = new XmlWriter();
        System.out.println(xmlWriter.write(statisticsMap, generator.getAttribute()));
    }
}
