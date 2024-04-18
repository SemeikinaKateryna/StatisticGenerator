package calculation;

import entity.Product;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class StatisticGenerator {
    private final List<Product> objects;
    private final Map<Object, Integer> statisticMap;

    public StatisticGenerator(List<Product> objects) {
        this.objects = objects;
        statisticMap = new HashMap<>();
    }

    public Map<Object,Integer> generateStatisticByAttribute(String attribute) {
        if(objects.isEmpty()){
            System.out.println("List of objects is empty!");
            return statisticMap;
        }

        Field field;
        try {
            field = Product.class.getDeclaredField(attribute);
        } catch (NoSuchFieldException e) {
            System.out.println("No such field in this class exists!");
            return statisticMap;
        }
        field.setAccessible(true);

        // Заповнення мапи
        for (Product pr : objects) {
            try {
                Object attributeInClass = field.get(pr);
                if (attributeInClass.getClass().isArray()) {
                    int length = Array.getLength(attributeInClass);
                    for (int i = 0; i < length; i++) {
                        Object arrayElement = Array.get(attributeInClass, i);
                        if(statisticMap.containsKey(arrayElement)) {
                            statisticMap.replace(arrayElement, statisticMap.get(arrayElement)+1);
                        }else{
                            statisticMap.put(arrayElement, statisticMap.getOrDefault(arrayElement, 0) + 1);
                        }
                    }
                } else {
                    statisticMap.put(attributeInClass, statisticMap.getOrDefault(attributeInClass,0) + 1);
                }
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Error accessing field value!", e);
            }
        }

        return sortByValue(statisticMap);
    }

    private <K, V extends Comparable<? super V>> Map<K, V> sortByValue( Map<K, V> map )
    {
        Map<K,V> result = new LinkedHashMap<>();
        Stream<Map.Entry<K,V>> st = map.entrySet().stream();

        st.sorted(Map.Entry.<K, V>comparingByValue().reversed())
                .forEach(e -> result.put(e.getKey(), e.getValue()));

        return result;
    }
}
