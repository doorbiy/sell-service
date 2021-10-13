package kg.easyit.sellservice.mappers;

import kg.easyit.sellservice.models.dtos.ProductDto;
import kg.easyit.sellservice.models.entities.Product;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ProductMapper {

    ProductMapper INSTANCE = Mappers.getMapper(ProductMapper.class);

    Product mapToProduct(ProductDto productDto);

    ProductDto mapToProductDto(Product product);
}