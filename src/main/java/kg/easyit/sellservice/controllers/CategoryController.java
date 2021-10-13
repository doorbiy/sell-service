package kg.easyit.sellservice.controllers;

import kg.easyit.sellservice.models.dtos.CategoryDto;
import kg.easyit.sellservice.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/category")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @PostMapping("/save")
    public ResponseEntity<?> saveCategory (@RequestHeader String token, @RequestBody CategoryDto categoryDto) {
        return categoryService.saveCategory(token, categoryDto);
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCategory(@RequestHeader String token, @RequestBody CategoryDto categoryDto) {
        return categoryService.saveCategory(token,categoryDto);
    }

    @GetMapping("/getByName")
    public ResponseEntity<?> getByName (@RequestHeader String token,@RequestParam String name) {
        return categoryService.getByName(token, name);
    }

    @GetMapping("/getAllCategories")
    public ResponseEntity<?> getAllCategories (@RequestHeader String token) {
        return categoryService.getAllCategories(token);
    }
}