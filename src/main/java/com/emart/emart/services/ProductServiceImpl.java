package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.CreateProductRequest;
import com.emart.emart.datas.models.Product;
import com.emart.emart.exceptions.ProductNotFoundException;
import com.emart.emart.exceptions.UserNotFoundException;
import com.emart.emart.repositories.CustomerRepository;
import com.emart.emart.repositories.ProductRepository;
import com.emart.emart.repositories.StoreRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@AllArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper = new ModelMapper();
    private final CustomerRepository customerRepository;
    private final StoreRepository storeRepository;
    private final ElasticsearchOperations elasticsearchOperations;
    private final ElasticSearchQueryService elasticSearchQueryService;
    @Override
    public Product createProduct(CreateProductRequest request) throws UserNotFoundException {
        var customer = customerRepository.findCustomerById(request.getUserId());
        if(customer == null){
            throw new UserNotFoundException("User not found");
        }
        var customerStore = storeRepository.findStoreByUserId(customer.getId());

        Product product = modelMapper.map(request, Product.class);
        product.setPrice(BigDecimal.valueOf(request.getPrice()));
        var savedProduct = productRepository.save(product);
        elasticsearchOperations.save(product);
        customerStore.getProducts().add(savedProduct);
        storeRepository.save(customerStore);
        return savedProduct;
    }

    @Override
    public void deleteProduct(Long productId) throws ProductNotFoundException {
        var product = getProduct(productId);
        productRepository.delete(product);
        elasticsearchOperations.delete(product);
    }

    @Override
    public Product getProduct(Long productId) throws ProductNotFoundException {
        var product =  productRepository.findProductById(productId);
        if (product == null) {
            throw new ProductNotFoundException("Product not found");
        }
        return product;
    }

    @Override
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> searchProducts(String keyword) {
        return elasticSearchQueryService.processSearch(keyword);
    }
}
