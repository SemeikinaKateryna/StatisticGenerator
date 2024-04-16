package entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Arrays;
import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Product {
    private String name;
    private Integer releaseYear;
    private Double price;
    private Manufacturer manufacturer;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Objects.equals(name, product.name) && Objects.equals(releaseYear, product.releaseYear) && Objects.equals(price, product.price) && Objects.equals(manufacturer, product.manufacturer) && Arrays.equals(categories, product.categories);
    }

    @Override
    public int hashCode() {
        int result = Objects.hash(name, releaseYear, price, manufacturer);
        result = 31 * result + Arrays.hashCode(categories);
        return result;
    }
}
