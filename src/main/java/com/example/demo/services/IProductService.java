package com.example.demo.services;

import com.example.demo.models.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IProductService {
    ResponseEntity<ResponseObject> products();
    ResponseEntity<ResponseObject> getProductDetail(Long id);
    ResponseEntity<ResponseObject> delete(Long id);
}
