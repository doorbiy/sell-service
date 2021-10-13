package kg.easyit.sellservice.services;

import kg.easyit.sellservice.models.dtos.PriceDto;
import kg.easyit.sellservice.models.dtos.ProductDto;
import org.springframework.http.ResponseEntity;

public interface PriceService {

    ResponseEntity<?> savePrice(String token, PriceDto priceDto);

    ResponseEntity<?> getPriceByProduct(String token, ProductDto productDto);

    ResponseEntity<?> getAllPrices(String token);

    double findPriceByProductForOperationDetails(ProductDto productDto);
}