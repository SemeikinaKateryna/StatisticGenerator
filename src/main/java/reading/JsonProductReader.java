package reading;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dto.ProductDto;
import entity.Product;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class JsonProductReader implements IJsonReader<Product>{

        private final String folderPath;

        public JsonProductReader(String folderPath) {
            this.folderPath = folderPath;
        }

        public List<Product> read() {
            ObjectMapper jsonMapper = new ObjectMapper();
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
                        ProductDto[] productsArray = jsonMapper.readValue(file, ProductDto[].class);

                        // Розділити рядок categories на масив стрінгів
                        for (ProductDto productDto : productsArray) {
                            Product product = new Product();
                            if (productDto.getCategories() != null) {
                                product.setName(productDto.getName());
                                product.setReleaseYear(productDto.getReleaseYear());
                                product.setPrice(productDto.getPrice());
                                product.setManufacturer(productDto.getManufacturer());
                                product.setCategories(productDto.getCategories().split(",\\s*"));
                                products.add(product);
                            }
                        }

                    } catch (Exception e) {
                        throw new RuntimeException("Error reading file: " + file.getAbsolutePath(), e);
                    }
                }
            }

            return products;
        }
}
