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
}