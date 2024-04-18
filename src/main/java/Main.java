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

        long startTime = System.currentTimeMillis();
        String folderPath = "D:\\IdeaProjects\\StatisticGenerator\\input";
        String attributeName = "categories";

        IJsonReader<Product> jsonReader = new JsonProductReader(folderPath);
        List<Product> products = jsonReader.read(8);

        StatisticGenerator generator = new StatisticGenerator(products);
        Map<Object, Integer> statisticsMap = generator.generateStatisticByAttribute(attributeName);

        IXmlWriter xmlWriter = new XmlWriter();
        xmlWriter.write(statisticsMap, attributeName);

        long endTime = System.currentTimeMillis();
        System.out.println("Час виконання: " + (endTime - startTime) + " мс" );
    }
}
