package writing;

import statistics.Statistics;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class XmlWriter implements IXmlWriter{
    @Override
    public boolean write(Map<Object, Integer> statisticsMap, String attribute) {
        // Перетворення мапи на список елементів Statistics.Item
        List<Statistics.Item> items = new ArrayList<>();
        for (Map.Entry<Object, Integer> entry : statisticsMap.entrySet()) {
            Statistics.Item item = new Statistics.Item();
            item.setValue(entry.getKey().toString());
            item.setCount(entry.getValue());
            items.add(item);
        }

        Statistics statistics = new Statistics();
        statistics.setItems(items);

        // Маршалінг об'єкта в XML-файл
        try {
            JAXBContext context = JAXBContext.newInstance(Statistics.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);

            // Створення директорії, якщо вона не існує
            File directory = new File("./out");
            if (!directory.exists()) {
                directory.mkdirs();
            }

            marshaller.marshal(statistics, new File("./out/statistics_by_" + attribute + ".xml"));

            return true;
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }
    }
}
