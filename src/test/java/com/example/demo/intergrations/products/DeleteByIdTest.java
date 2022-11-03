package com.example.demo.intergrations.products;

import com.example.demo.model.Product;
import com.example.demo.repository.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.logging.Logger;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@PropertySource("classpath:application-test.properties")
class DeleteByIdTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductRepository productRepository;
    Logger logger = Logger.getLogger(DeleteByIdTest.class.getName());

    private Product createdProduct;

    @BeforeEach
    void initEach() {
        logger.info("initEach");
        Product product = new Product("Test", 12.2);
        createdProduct = productRepository.save(product);
        logger.info("init product: " + createdProduct);
    }

    @AfterEach
    void afterEach() {
        logger.info("afterEach");
    }

    @Test
    void deleteProductByIdSuccessfully() throws Exception {
        mockMvc.perform(delete("/v1/products/" + createdProduct.getId())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void deleteProductByIdNotFound() throws Exception {
        mockMvc.perform(delete("/v1/products/-1")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
