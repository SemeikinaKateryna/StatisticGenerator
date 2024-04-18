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
//        if (args.length != 2) {
//            System.out.println("Usage: java Main <folderPath> <attributeName>");
//            return;
//        }
//
//        String folderPath = args[0];
//        String attributeName = args[1];

        String folderPath = "D:\\IdeaProjects\\StatisticGenerator\\in";
        String attributeName = "name";

        IJsonReader<Product> jsonReader = new JsonProductReader(folderPath);
        List<Product> products = jsonReader.read();

        StatisticGenerator generator = new StatisticGenerator(products);
        Map<Object, Integer> statisticsMap = generator.generateStatisticByAttribute(attributeName);

        IXmlWriter xmlWriter = new XmlWriter();
        System.out.println(xmlWriter.write(statisticsMap, attributeName));
    }
}
