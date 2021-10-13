package kg.easyit.sellservice.controllers;

import kg.easyit.sellservice.models.dtos.ProductDto;
import kg.easyit.sellservice.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/vq/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/save")
    public ResponseEntity<?> saveProduct(@RequestHeader String token, @RequestBody ProductDto productDto) {
        return productService.saveProduct(token, productDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateProduct(@RequestHeader String token, @RequestBody ProductDto productDto) {
        return productService.updateProduct(token, productDto);
    }

    @GetMapping("/getByBarcode")
    public ResponseEntity<?> getProductByBarcode (@RequestHeader String token, @RequestParam String barcode) {
        return productService.getProductByBarcode(token, barcode);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<?> getAllProducts(@RequestHeader String token) {
        return productService.getAllProducts(token);
    }
}