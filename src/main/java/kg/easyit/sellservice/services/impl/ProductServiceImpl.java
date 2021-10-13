package kg.easyit.sellservice.services.impl;

import kg.easyit.sellservice.dao.ProductRepo;
import kg.easyit.sellservice.mappers.ProductMapper;
import kg.easyit.sellservice.models.dtos.ProductDto;
import kg.easyit.sellservice.models.entities.Product;
import kg.easyit.sellservice.models.responses.ErrorResponse;
import kg.easyit.sellservice.services.ProductService;
import kg.easyit.sellservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserService userService;

    @Override
    public ResponseEntity<?> saveProduct(String token, ProductDto productDto) {

        ResponseEntity<?> responseEntity =
                userService
                        .verifyLogin(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        Product product =
                ProductMapper
                        .INSTANCE
                        .mapToProduct(productDto);

        if (Objects.isNull(productRepo.findByNameOrBarcode(product.getName(), product.getBarcode()))) {
            productRepo.save(product);
        } else {
            return new ResponseEntity<>(
                    new ErrorResponse("Такой товар уже существует!", null)
                    , HttpStatus.CONFLICT);
        }

        return ResponseEntity.ok(
                "Товар успешно сохранен!" +
                        ProductMapper
                                .INSTANCE
                                .mapToProductDto(product));
    }

    @Override
    public ResponseEntity<?> updateProduct(String token, ProductDto productDto) {

        ResponseEntity<?> responseEntity =
                userService
                        .verifyLogin(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        Product product =
                ProductMapper
                        .INSTANCE
                        .mapToProduct(productDto);

        productRepo.save(product);


        return ResponseEntity.ok(
                "Товар успешно обновлен!" +
                        ProductMapper
                                .INSTANCE
                                .mapToProductDto(product));
    }

    @Override
    public ResponseEntity<?> getProductByBarcode(String token, String barcode) {

        ResponseEntity<?> responseEntity =
                userService
                        .verifyLogin(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        Product product =
                productRepo
                        .findByBarcode(barcode);

        if (Objects.isNull(product)) {
            return new ResponseEntity<>(
                    new ErrorResponse("Товара с таким штрихкодом нет!", null)
                    , HttpStatus.NOT_FOUND);
        }

        return ResponseEntity.ok(
                ProductMapper
                        .INSTANCE
                        .mapToProductDto(product));
    }

    @Override
    public ResponseEntity<?> getAllProducts(String token) {

        ResponseEntity<?> responseEntity =
                userService
                        .verifyLogin(token);

        if (!responseEntity.getStatusCode().equals(HttpStatus.OK)) {

            return responseEntity;
        }

        List<Product> productList =
                productRepo.findAll();

        return ResponseEntity.ok(productList
                .stream()
                .map(ProductMapper
                        .INSTANCE::mapToProductDto)
                .collect(
                        Collectors
                                .toList()));
    }

    @Override
    public ProductDto findProductByBarcodeForOperationDetails(String barcode) {

        return ProductMapper
                .INSTANCE
                .mapToProductDto(
                        productRepo
                                .findByBarcode(barcode)
                );
    }
}
