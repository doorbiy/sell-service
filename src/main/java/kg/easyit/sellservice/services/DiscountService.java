package kg.easyit.sellservice.services;

import kg.easyit.sellservice.models.dtos.DiscountDto;
import kg.easyit.sellservice.models.dtos.ProductDto;
import org.springframework.http.ResponseEntity;

public interface DiscountService {

    ResponseEntity<?> saveDiscount(String token, DiscountDto discountDto);

    ResponseEntity<?> findDiscountByProduct(String token, ProductDto productDto);

    ResponseEntity<?> getAllDiscounts(String token);

    double getDiscountByProduct(ProductDto productDto);
}