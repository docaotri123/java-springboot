package com.example.demo.controllers;

import com.example.demo.models.ResponseObject;
import com.example.demo.services.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "v1/products")
public class ProductController {
    @Autowired
    private IProductService productService;
    @GetMapping("")
    ResponseEntity<ResponseObject> products() {
        return productService.products();
    }

    @GetMapping("/{id}")
    ResponseEntity<ResponseObject> getById(@PathVariable Long id) {
        return productService.getProductDetail(id);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<ResponseObject> deleteById(@PathVariable Long id) {
        return productService.delete(id);
    }
}
