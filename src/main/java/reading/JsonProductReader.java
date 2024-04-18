package reading;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.ProductDto;
import entity.Product;
import mapper.ProductMapper;

public class JsonProductReader implements IJsonReader<Product> {

    private final String folderPath;
    private final ProductMapper productMapper;
    private final ObjectMapper jsonMapper;

    public JsonProductReader(String folderPath) {
        this.folderPath = folderPath;
        this.productMapper = new ProductMapper();
        this.jsonMapper = new ObjectMapper();
    }

    public List<Product> read(int threadCount) {
        ExecutorService executorService = Executors.newFixedThreadPool(threadCount);
        List<Product> products = new ArrayList<>();

        File folder = new File(folderPath);
        // Перевірка, чи існує директорія
        if (!folder.exists() || !folder.isDirectory()) {
            throw new IllegalArgumentException("Provided path is not a valid directory.");
        }
        File[] files = folder.listFiles((dir, name) -> name.toLowerCase().endsWith(".json"));

        if (files != null) {
            List<Future<List<Product>>> futures = new ArrayList<>();

            for (File file : files) {
                if (file.length() == 0) {
                    continue;
                }

                futures.add(executorService.submit(() -> {
                    List<Product> productsFromFile = new ArrayList<>();

                    try (JsonParser jsonParser = jsonMapper.getFactory().createParser(file)) {
                        while (jsonParser.nextToken() != null) {
                            if (jsonParser.currentToken() == JsonToken.START_OBJECT) {
                                ProductDto productDto = jsonMapper.readValue(jsonParser, ProductDto.class);

                                if (productDto.getName() == null || productDto.getReleaseYear() == null || productDto.getPrice() == null
                                        || productDto.getManufacturer() == null || productDto.getCategories() == null) {
                                    System.out.println("Skipped invalid ProductDto: " + productDto);
                                    continue;
                                }

                                Product product = productMapper.mapProductDtoToProduct(productDto);
                                productsFromFile.add(product);
                            }
                        }
                    } catch (IOException e) {
                        throw new RuntimeException("Error parsing file: " + file.getAbsolutePath(), e);
                    } catch (Exception e) {
                        throw new RuntimeException("Error reading file: " + file.getAbsolutePath(), e);
                    }

                    return productsFromFile;
                }));
            }

            for (Future<List<Product>> future : futures) {
                try {
                    products.addAll(future.get()); // Очікуємо завершення потоку та додаємо результат до загального списку
                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

            executorService.shutdown();
            try {
                executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        return products;
    }

}