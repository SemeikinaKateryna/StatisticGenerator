import com.fasterxml.jackson.databind.ObjectMapper;
import entity.Product;
import mapper.ProductMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import reading.JsonProductReader;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class JsonProductReaderTest {
    @Mock
    private ProductMapper productMapper;
    @Mock
    private ObjectMapper jsonMapper;

    private static final String SUCCESSFUL_FOLDER_PATH = "./testDirectorySuccess";
    private static final String INVALID_FOLDER_PATH = "./invalid";
    private static final String EMPTY_DIRECTORY_FOLDER_PATH = "./testDirectoryEmpty";
    private static final String EMPTY_FILES_DIRECTORY_FOLDER_PATH = "./testDirectoryEmptyFiles";

//    @BeforeEach
//    public void setUp() {
//        MockitoAnnotations.openMocks(this);
//    }

    @Test
    public void testReadSuccessful() {
        Product product1 = new Product("Men's Casual Shirt", 2021, 45.99, "FashionHub", new String[] {"Men's", "Clothing"});
        Product product2 = new Product("Women's Summer Dress", 2020, 65.50, "StyleQueen", new String[] {"Women's", "Clothing"});
        Product product3 = new Product("Unisex Sneakers", 2022, 80.00, "FootwearWorld", new String[] {"Unisex", "Footwear"});
        Product product4 = new Product("Men's Winter Jacket", 2021, 120.00, "OutdoorGear", new String[] {"Men's", "Clothing"});
        Product product5 = new Product("Women's Leather Boots", 2020, 150.99, "ElegantFootwear", new String[] {"Women's", "Footwear"});

        JsonProductReader jsonProductReader = new JsonProductReader(SUCCESSFUL_FOLDER_PATH);

        List<Product> productList = jsonProductReader.read();

        assertNotNull(productList);
        assertEquals(5, productList.size());
        assertEquals(product1, productList.get(0));
        assertEquals(product2, productList.get(1));
        assertEquals(product3, productList.get(2));
        assertEquals(product4, productList.get(3));
        assertEquals(product5, productList.get(4));
    }



}
