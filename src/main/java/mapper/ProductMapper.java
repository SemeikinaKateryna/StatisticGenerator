package mapper;

import dto.ProductDto;
import entity.Manufacturer;
import entity.Product;

public class ProductMapper {
    public Product mapProductDtoToProduct(ProductDto productDto){
        Product product = new Product();
        product.setName(productDto.getName());
        product.setReleaseYear(productDto.getReleaseYear());
        product.setPrice(productDto.getPrice());
        product.setManufacturer(new Manufacturer(productDto.getManufacturer()));
        product.setCategories(productDto.getCategories().split(",\\s*"));
        return product;
    }
}
