package com.dennis.java_ecommerce_backend.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dennis.java_ecommerce_backend.model.Product;
import com.dennis.java_ecommerce_backend.service.ProductService;

@RestController
@RequestMapping("/api")
@CrossOrigin

public class ProductController {

	private ProductService service;

	@Autowired
	public ProductController(ProductService service) {
		super();
		this.service = service;
	}

	@GetMapping("/products")

	public ResponseEntity<List<Product>> getAllProducts() {
		return new ResponseEntity<>(service.getAllProducts(), HttpStatus.OK);
	}

	/*
	 * @PostMapping("/product") public void addProduct(@RequestBody Product prod) {
	 * service.addProduct(prod); }
	 */
	@GetMapping("/product/{id}")
	public ResponseEntity<Product> getProduct(@PathVariable int id) {
		Product product = service.getProductById(id);

		if (product != null) {
			return new ResponseEntity<Product>(product, HttpStatus.OK);
		}

		else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@PostMapping("/product")
	public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile) {

		try {
			Product product1 = service.addProduct(product, imageFile);
			return new ResponseEntity<>(product1, HttpStatus.CREATED);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}

	@GetMapping("/product/{id}/image")
	public ResponseEntity<byte[]> getImageByProductId(@PathVariable int id) {
		Product product = service.getProductById(id);
		byte[] imageFile = product.getImageData();
		return ResponseEntity.ok().contentType(MediaType.valueOf(product.getImageType())).body(imageFile);
	}

	@PutMapping("/product/{id}")
	public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestPart Product product,

			@RequestPart MultipartFile imageFile) {
		Product product1 = null;
		try {

			product1 = service.updateProduct(id, product1, imageFile);
		} catch (IOException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		if (product1 != null)
			return new ResponseEntity<>(HttpStatus.OK);
		else
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

	}

	@DeleteMapping("/product/{id}")

	public ResponseEntity<String> deleteProduct(@PathVariable int id) {

		Product product = service.getProductById(id);
		if (product != null) {
			service.deleteProduct(id);
			return new ResponseEntity<>(HttpStatus.OK);
		} else
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);

	}

	@GetMapping("/product/search")

	public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword) {
		List<Product> products = service.searchProducts(keyword);
		return new ResponseEntity<>(products, HttpStatus.OK);

	}
}
