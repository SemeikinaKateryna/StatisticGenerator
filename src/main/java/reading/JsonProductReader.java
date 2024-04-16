package reading;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import dto.ProductDto;
import entity.Product;
import mapper.ProductMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
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

        File folder = new File(folderPath);

        List<Product> products = new ArrayList<>();

        // Перевірка, чи існує директорія
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Provided path is not a valid directory.");
        }

        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (files != null) {
            for (File file : files) {
                if (file.length() == 0) {
                    // Якщо файл порожній, пропустити його
                    continue;
                }

                try {
                    String fileContent = new String(Files.readAllBytes(file.toPath()));

                    // Перевірка на наявність даних в JSON-файлі
                    if ("{}".equals(fileContent.trim())) {
                        continue; // Пропустити файл, якщо він містить порожній об'єкт
                    }

//                    ProductDto[] productsArray;
//                    try {
//                        productsArray = jsonMapper.readValue(file, ProductDto[].class);
//                    } catch (Exception e) {
//                        // Пропустити невалідний JSON і продовжити обробку наступного файлу
//                        System.out.println("Skipped invalid JSON file: " + file.getAbsolutePath());
//                        continue;
//                    }

                    try (JsonParser jsonParser = jsonMapper.getFactory().createParser(file)) {
                        while (jsonParser.nextToken() != null) {
                            if (jsonParser.currentToken() == JsonToken.START_OBJECT) {
                                ProductDto productDto = jsonMapper.readValue(jsonParser, ProductDto.class);

                                if (productDto.getName() == null || productDto.getReleaseYear() == null || productDto.getPrice() == null
                                        || productDto.getManufacturer() == null || productDto.getCategories() == null) {
                                    System.out.println("Skipped invalid ProductDto: " + productDto);
                                    continue;
                                }

                                // Мапимо ProductDto на Product
                                Product product = productMapper.mapProductDtoToProduct(productDto);

                                // Додавання об'єкта Product до списку продуктів
                                products.add(product);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("Error processing file: " + file.getAbsolutePath(), e);
                    }


                } catch (Exception e) {
                    throw new RuntimeException("Error reading file: " + file.getAbsolutePath(), e);
                }
            }
        }

        return products;
    }
}
