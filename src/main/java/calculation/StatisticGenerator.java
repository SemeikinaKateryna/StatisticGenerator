package calculation;

import lombok.Getter;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Stream;

public class StatisticGenerator<T> {
    private final Class<T> clazz;

    @Getter
    private final String attribute;

    public StatisticGenerator(Class<T> clazz, String attribute) {
        this.clazz = clazz;
        this.attribute = attribute;
    }

    public Map<Object,Integer> generateStatisticByAttribute(List<T> objects) {
        Field field;
        try {
            field = clazz.getDeclaredField(this.attribute);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException("No such field in this class exists!", e);
        }
        field.setAccessible(true);

        // Створення мапи для зберігання статистики
        Map<Object, Integer> statisticMap = new HashMap<>();

        // Заповнення мапи
        for (T t : objects) {
            try {
                Object attributeInClass = field.get(t);
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

        Map<Object, Integer> sortedStatisticMap = sortByValue(statisticMap);

        return sortedStatisticMap;
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
