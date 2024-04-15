package reading;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dto.ProductDto;
import entity.Product;
import mapper.ProductMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class JsonProductReader implements IJsonReader<Product> {

    private final String folderPath;
    private final ProductMapper productMapper;
    private final ObjectMapper jsonMapper;

    public JsonProductReader(String folderPath) {
        this.folderPath = folderPath;
        this.productMapper = new ProductMapper();
        this.jsonMapper = new ObjectMapper();
    }

    public List<Product> read() {
        jsonMapper.enable(SerializationFeature.INDENT_OUTPUT);

        List<Product> products = new ArrayList<>();

        File folder = new File(folderPath);

        if (!folder.isDirectory()) {
            throw new IllegalArgumentException("Provided path is not a directory.");
        }

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                if (file.length() == 0) {
                    // Якщо файл порожній, пропустити його
                    continue;
                }

                try {
                    ProductDto[] productsArray;
                    try {
                        productsArray = jsonMapper.readValue(file, ProductDto[].class);
                    } catch (IOException e) {
                        throw new RuntimeException("Error reading file: " + file.getAbsolutePath(), e);
                    }

                    List<Product> mappedProducts = new ArrayList<>();
                    for (ProductDto productDto : productsArray) {
                        Product product = productMapper.mapProductDtoToProduct(productDto);
                        mappedProducts.add(product);
                    }
                    products.addAll(mappedProducts);

                } catch (Exception e) {
                    throw new RuntimeException("Error processing file: " + file.getAbsolutePath(), e);
                }
            }
        }

        return products;
    }
}
