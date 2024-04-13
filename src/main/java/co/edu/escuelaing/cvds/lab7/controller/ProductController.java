package co.edu.escuelaing.cvds.lab7.controller;


import co.edu.escuelaing.cvds.lab7.model.Product;

import co.edu.escuelaing.cvds.lab7.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping(value = "/api")
public class ProductController {
    private final ProductService productService;
    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }
    @GetMapping("/products")
    @ResponseBody
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
    @GetMapping("/product/:{id}")
    public ResponseEntity<Product> getProductById(@PathVariable String id) {
        Optional<Product> product = productService.getProduct(id);

        if (product.isPresent()) {
            return ResponseEntity.ok(product.get());
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    @PostMapping("/product")
    public ResponseEntity<Product> createProduct(@RequestBody Product product){
        Product savedProduct = productService.addProduct(product);
        return new ResponseEntity<>(savedProduct, HttpStatus.CREATED);
    }
    @PutMapping("/product")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") String id, @RequestBody Product product){
        product.setId(id);
        Product updateProduct = productService.updateProduct(product);
        return new ResponseEntity<Product>(updateProduct, HttpStatus.OK);
    }
    @DeleteMapping("product/:{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable("id") String id){
        productService.deleteProduct(id);
        return new ResponseEntity<String>("Product deleted", HttpStatus.OK);
    }
}
