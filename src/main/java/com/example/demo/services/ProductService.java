package com.example.demo.services;

import com.example.demo.models.Product;
import com.example.demo.models.ResponseObject;
import com.example.demo.repositories.IProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IProductService {
    @Autowired
    private IProductRepository productRepository;
    @Override
    public ResponseEntity<ResponseObject> products() {
        List<Product> products = productRepository.findAll();

        return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", products));
    }

    @Override
    public ResponseEntity<ResponseObject> getProductDetail(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);

            if (!product.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", null));
            }

            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject("ok", product));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject("error", null));
        }
    }

    @Override
    public ResponseEntity<ResponseObject> delete(Long id) {
        try {
            Optional<Product> product = productRepository.findById(id);

            if (!product.isPresent()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject("error", null));
            }

            productRepository.deleteById(id);

            return ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseObject("ok", null));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new ResponseObject("error", null));
        }
    }
}
