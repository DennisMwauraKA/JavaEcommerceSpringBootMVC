package com.dennis.java_ecommerce_backend.service;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dennis.java_ecommerce_backend.model.Product;
import com.dennis.java_ecommerce_backend.repo.ProductRepo;

@Service
public class ProductService {
	@Autowired
	ProductRepo repo;

	public List<Product> getAllProducts() {

		return repo.findAll();
	}

	public void addProduct(Product prod) {
		repo.save(prod);

	}

	public Product getProductById(int id) {

		return repo.findById(id).orElse(null);
	}

	public Product addProduct(Product product, MultipartFile imageFile) throws IOException {
		product.setImageName(imageFile.getOriginalFilename());
		product.setImageType(imageFile.getContentType());
		product.setImageData(imageFile.getBytes());
		return repo.save(product);

	}

	public Product updateProduct(int id, Product product1, MultipartFile imageFile) throws IOException {
		product1.setImageData(imageFile.getBytes());
		product1.setImageName(imageFile.getOriginalFilename());
		product1.setImageType(imageFile.getContentType());
		return repo.save(product1);
	}

	public void deleteProduct(int id) {
		repo.deleteById(id);

	}

	public List<Product> searchProducts(String keyword) {

		return repo.searchProducts(keyword);
	}

}
