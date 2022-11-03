package com.example.demo.service;

import com.example.demo.share.utils.ResponseObject;
import org.springframework.http.ResponseEntity;

public interface IProductService {
    ResponseEntity<ResponseObject> products(int page, int size);
    ResponseEntity<ResponseObject> getById(Long id);
    ResponseEntity<ResponseObject> deleteById(Long id);
}
