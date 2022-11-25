package com.example.demo.controller;

import com.example.demo.service.IProductService;
import com.example.demo.share.utils.ResponseObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "v1/products")
public class ProductController {
    @Autowired
    private IProductService productService;

    @GetMapping("")
    ResponseEntity<ResponseObject> products(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "5") int size) {
        return productService.products(page, size);
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        return productService.getById(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteById(@PathVariable Long id) {
        return productService.deleteById(id);
    }

    @GetMapping("/tutorials/{id}")
    ResponseEntity<ResponseObject> getTutorialById(@PathVariable Long id) {
        return productService.getTutorialById(id);
    }
}
