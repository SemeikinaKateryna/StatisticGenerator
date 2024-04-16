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

import static org.junit.jupiter.api.Assertions.*;

public class JsonProductReaderTest {

    private static final String SUCCESSFUL_FOLDER_PATH = "./testDirectorySuccess";
    private static final String INVALID_FOLDER_PATH = "./invalid";
    private static final String FILE_NOT_FOLDER_PATH = "./testFileNotDirectory";
    private static final String EMPTY_DIRECTORY_FOLDER_PATH = "./testDirectoryEmpty";
    private static final String EMPTY_FILES_IN_DIRECTORY_FOLDER_PATH = "./testDirectoryEmptyFiles";
    private static final String EMPTY_JSON_FOLDER_PATH = "./testDirectoryEmptyJsons";
    private static final String INVALID_JSON_FOLDER_PATH = "./testDirectoryInvalidJsons";

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

    @Test
    public void testReadInvalidPath() {
        JsonProductReader jsonProductReader = new JsonProductReader(INVALID_FOLDER_PATH);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, jsonProductReader::read);

        assertEquals(
                "Provided path is not a valid directory.",
                exception.getMessage());
    }

    @Test
    public void testReadFileNotFolderPath() {
        JsonProductReader jsonProductReader = new JsonProductReader(FILE_NOT_FOLDER_PATH);

        IllegalArgumentException exception =
                assertThrows(IllegalArgumentException.class, jsonProductReader::read);

        assertEquals(
                "Provided path is not a valid directory.",
                exception.getMessage());
    }

    @Test
    public void testReadEmptyDirectory() {
        JsonProductReader jsonProductReader = new JsonProductReader(EMPTY_DIRECTORY_FOLDER_PATH);

        List<Product> products = jsonProductReader.read();

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    public void testReadEmptyFilesInDirectory() {
        JsonProductReader jsonProductReader = new JsonProductReader(EMPTY_FILES_IN_DIRECTORY_FOLDER_PATH);

        List<Product> products = jsonProductReader.read();

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    public void testReadEmptyJsons() {
        JsonProductReader jsonProductReader = new JsonProductReader(EMPTY_JSON_FOLDER_PATH);

        List<Product> products = jsonProductReader.read();

        assertNotNull(products);
        assertTrue(products.isEmpty());
    }

    @Test
    public void testReadInvalidJsons() {
        JsonProductReader jsonProductReader = new JsonProductReader(INVALID_JSON_FOLDER_PATH);

        Product product3 = new Product("Unisex Sneakers", 2022, 80.00, "FootwearWorld", new String[] {"Unisex", "Footwear"});
        Product product5 = new Product("Women's Leather Boots", 2020, 150.99, "ElegantFootwear", new String[] {"Women's", "Footwear"});

        List<Product> products = jsonProductReader.read();

        assertNotNull(products);
        assertFalse(products.isEmpty());
        assertEquals(2, products.size());
        assertEquals(product3, products.get(0));
        assertEquals(product5, products.get(1));
    }
}
