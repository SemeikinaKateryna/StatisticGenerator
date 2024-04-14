package entity;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;

@Getter
@Setter
public class Product {
    private String name;
    private Integer releaseYear;
    private Double price;
    private String manufacturer;
    private String[] categories;

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", releaseYear=" + releaseYear +
                ", price=" + price +
                ", manufacturer='" + manufacturer + '\'' +
                ", categories='" + Arrays.toString(categories) + '\'' +
                '}';
    }
}
