package dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Getter;
import lombok.Setter;

@JsonAutoDetect
@Getter
@Setter
public class ProductDto {
    private String name;
    private Integer releaseYear;
    private Double price;
    private String manufacturer;
    private String categories;

    public ProductDto() {
    }

    public ProductDto(String name, Integer releaseYear, Double price, String manufacturer, String categories) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.price = price;
        this.manufacturer = manufacturer;
        this.categories = categories;
    }
}