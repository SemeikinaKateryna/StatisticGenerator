import calculation.StatisticGenerator;
import entity.Manufacturer;
import entity.Product;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class StatisticGeneratorTest {
    private StatisticGenerator statisticGenerator;

    @BeforeEach
    public void setUp() {
        statisticGenerator = new StatisticGenerator(List.of(
                new Product("Men's Casual Shirt", 2021, 45.99, new Manufacturer("FashionHub"), new String[] {"Men's", "Clothing"}),
                new Product("Women's Summer Dress", 2020, 65.50, new Manufacturer("StyleQueen"), new String[] {"Women's", "Clothing"}),
                new Product("Unisex Sneakers", 2022, 80.00, new Manufacturer("FootwearWorld"), new String[] {"Unisex", "Footwear"}),
                new Product("Men's Winter Jacket", 2021, 120.00, new Manufacturer("StyleQueen"), new String[] {"Men's", "Clothing"}),
                new Product("Women's Leather Boots", 2020, 150.99, new Manufacturer("FootwearWorld"), new String[] {"Women's", "Footwear"})
        ));
    }

    @Test
    public void testGenerateStatisticEmptyList(){
        StatisticGenerator statisticGenerator = new StatisticGenerator(new ArrayList<>());

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, ()-> statisticGenerator.generateStatisticByAttribute("name"));

        assertEquals("List of objects is empty!", exception.getMessage());
    }

    @Test
    public void testGenerateStatisticByUnknownField(){
        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, ()-> statisticGenerator.generateStatisticByAttribute("abbccc"));

        assertEquals("No such field in this class exists!", exception.getMessage());
    }
    @Test
    public void testGenerateStatisticByYear(){
        Map<Object, Integer> map = statisticGenerator.generateStatisticByAttribute("releaseYear");

        assertNotNull(map);
        assertFalse(map.isEmpty());
        assertEquals(2, map.get(2020));
        assertEquals(2, map.get(2021));
        assertEquals(1, map.get(2022));
    }

    @Test
    public void testGenerateStatisticByManufacturer(){
        Map<Object, Integer> map = statisticGenerator.generateStatisticByAttribute("manufacturer");

        assertNotNull(map);
        assertFalse(map.isEmpty());
        assertEquals(1, map.get(new Manufacturer("FashionHub")));
        assertEquals(2, map.get(new Manufacturer("StyleQueen")));
        assertEquals(2, map.get(new Manufacturer("FootwearWorld")));
    }

    @Test
    public void testGenerateStatisticByCategories(){
        Map<Object, Integer> map = statisticGenerator.generateStatisticByAttribute("categories");

        assertNotNull(map);
        assertFalse(map.isEmpty());
        assertEquals(2, map.get("Men's"));
        assertEquals(3, map.get("Clothing"));
        assertEquals(2, map.get("Women's"));
        assertEquals(1, map.get("Unisex"));
        assertEquals(2, map.get("Footwear"));
    }

    @Test
    public void testGenerateStatisticBySomeAttributeSorted(){
        Map<Object, Integer> map = statisticGenerator.generateStatisticByAttribute("categories");
        List<Integer> sortedValues = new ArrayList<>(map.values());

        assertEquals(Arrays.asList(3, 2, 2, 2, 1), sortedValues);
    }
}
