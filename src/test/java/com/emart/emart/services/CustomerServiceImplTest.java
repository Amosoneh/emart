package com.emart.emart.services;

import com.emart.emart.datas.dtos.request.CreateProductRequest;
import com.emart.emart.datas.dtos.request.CreateStoreRequest;
import com.emart.emart.datas.dtos.request.CustomerRegisterRequest;
import com.emart.emart.exceptions.EmailAlreadyExistException;
import com.emart.emart.exceptions.ProductNotFoundException;
import com.emart.emart.exceptions.UserNotFoundException;
import com.emart.emart.repositories.CustomerRepository;
import com.emart.emart.repositories.ProductRepository;
import com.emart.emart.repositories.StoreRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@Slf4j @SpringBootTest
class CustomerServiceImplTest {
    @Autowired
    private CustomerServiceImpl customerService;
    @Autowired
    private CustomerRepository customerRepository;
    @Autowired
    private ProductServiceImpl productService;
    @Autowired
    private CartServiceImpl cartService;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private StoreServiceImp storeService;
    @Autowired
    private PaymentServiceImpl paymentService;

    @Autowired
    private StoreRepository storeRepository;

    @Autowired
    private OrderServiceImpl orderService;

    CustomerRegisterRequest request;
    CreateProductRequest createProductRequest;
    CreateProductRequest createProductRequest2;

    CreateStoreRequest createStoreRequest;
    @BeforeEach
    void setUp() {
        customerRepository.deleteAll();
    }

    @Test
    void testTo_register() throws EmailAlreadyExistException {
        CustomerRegisterRequest request = buildCustomerRegisterRequest();
        var customer = customerService.register(request);
        var res = customerRepository.findCustomerByEmail(request.getEmail());
        log.info("result {}", res);
        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo(request.getName());
    }

    @Test
    void testTo_login() throws EmailAlreadyExistException, UserNotFoundException {
        CustomerRegisterRequest request = buildCustomerRegisterRequest();
        var customer = customerService.register(request);
        var token = customerService.login(request.getEmail(), request.getPassword());
        assertThat(customer).isNotNull();
        assertThat(customer.getName()).isEqualTo(request.getName());
        assertThat(token).isNotNull();
        assertThat(token.containsKey("token")).isTrue();
        assertThat(token.get("token")).isNotNull();
    }

    @Test
    void testTO_createStore() throws EmailAlreadyExistException, UserNotFoundException {
        CustomerRegisterRequest request = buildCustomerRegisterRequest();
        customerService.register(request);
        var customer = customerRepository.findCustomerByEmail(request.getEmail());
        createStoreRequest = CreateStoreRequest.builder().description("description").name("Amos Monday")
                .userId(customer.getId()).build();
        storeService.createStore(createStoreRequest);
        var store = storeRepository.findStoreByUserId(customer.getId());
        assertThat(store).isNotNull();
        assertThat(store.getName()).isEqualTo("Amos Monday");

    }

    @Test
    void testTo_createProduct() throws EmailAlreadyExistException, UserNotFoundException {
        CustomerRegisterRequest request = buildCustomerRegisterRequest();
        customerService.register(request);
        var customer = customerRepository.findCustomerByEmail(request.getEmail());
        customerRepository.findCustomerByEmail(request.getEmail());
        createStoreRequest = CreateStoreRequest.builder().description("description").name("Amos Monday")
                .userId(customer.getId()).build();
        storeService.createStore(createStoreRequest);
        var store = storeRepository.findStoreByUserId(customer.getId());
        log.info("result {}", store.getId());
        createProductRequest = CreateProductRequest.builder().description("description").name("product name")
                .price(400L).quantities(3).userId(customer.getId()).build();
        var product = productService.createProduct(createProductRequest);
        var count = productRepository.count();
        assertThat(count).isGreaterThanOrEqualTo(1);
        assertThat(product).isNotNull();
        assertThat(product.getId()).isNotNull();
    }

    @Test
    void testTo_addProductToStore() throws EmailAlreadyExistException, UserNotFoundException, ProductNotFoundException {
        CustomerRegisterRequest request = buildCustomerRegisterRequest();
        customerService.register(request);

        var customer = customerRepository.findCustomerByEmail(request.getEmail());
        log.info("customer {}", customer);

        createStoreRequest = CreateStoreRequest.builder().description("description").name("Amos Monday")
                .userId(customer.getId()).build();
        storeService.createStore(createStoreRequest);
        var store = storeRepository.findStoreByUserId(customer.getId());
        log.info("store {}", store);

        createProductRequest = CreateProductRequest.builder().description("description").name("Car name").userId(customer.getId())
                .quantities(3).price(200L).build();
        var product = productService.createProduct(createProductRequest);
//         = productRepository.findProductByName("Car name");
        log.info("product {}", product);

        var re = cartService.addProductToCart(product.getId(), customer.getId());
        assertThat(re).isNotNull();
        assertThat(re.contains("Product added to cart successfully")).isTrue();
    }

    @Test
    void testTo_makeOrder() throws EmailAlreadyExistException, UserNotFoundException, ProductNotFoundException {
        CustomerRegisterRequest request = buildCustomerRegisterRequest();
        customerService.register(request);
        var customer = customerRepository.findCustomerByEmail(request.getEmail());
        log.info("customer {}", customer);
        createStoreRequest = CreateStoreRequest.builder().description("description").name("Amos Monday")
                .userId(customer.getId()).build();
        storeService.createStore(createStoreRequest);
        storeRepository.findStoreByUserId(customer.getId());
        createProductRequest = CreateProductRequest.builder().description("description").name("Car name").userId(customer.getId())
                .quantities(3).price(200L).build();
        var product = productService.createProduct(createProductRequest);
//        var product = productRepository.findProductByName("Car name");
        log.info("product {}", product);
        var re = cartService.addProductToCart(product.getId(), customer.getId());
        var response = orderService.makeOrder(customer.getId());
        assertThat(response).isNotNull();
        assertThat(response.getOrderId()).isNotNull();
        assertThat(response.getAuthorization_url()).isNotNull();
        assertThat(response.getAccess_code()).isNotNull();
    }

    @Test
    void testTo_searchProduct() throws EmailAlreadyExistException, UserNotFoundException {
        CustomerRegisterRequest request = buildCustomerRegisterRequest();
        customerService.register(request);
        var customer = customerRepository.findCustomerByEmail(request.getEmail());
        log.info("customer {}", customer.getEmail());
        customerRepository.findCustomerByEmail(request.getEmail());
        createStoreRequest = CreateStoreRequest.builder().description("description").name("Amos Monday")
                .userId(customer.getId()).build();
        storeService.createStore(createStoreRequest);
        var store = storeRepository.findStoreByUserId(customer.getId());
        log.info("result {}", store.getId());
        createProductRequest = CreateProductRequest.builder().description("description").name("product name")
                .price(400L).quantities(3).userId(customer.getId()).build();
        var product = productService.createProduct(createProductRequest);
        var count = productRepository.count();
        createProductRequest2 = CreateProductRequest.builder().price(90L).quantities(5).userId(customer.getId())
                .name("Car name").build();
        var product2 = productService.createProduct(createProductRequest2);
        var products = productService.searchProducts("product");
        log.info("products {}", products);
        assertThat(count).isGreaterThanOrEqualTo(1);
        assertThat(product).isNotNull();
        assertThat(product2).isNotNull();
        assertThat(product.getName()).containsIgnoringCase("product");
        assertThat(product2.getName()).containsIgnoringCase("Car");
        assertThat(products).isNotNull();
        assertThat(products.size()).isGreaterThan(1);
    }


    private CustomerRegisterRequest buildCustomerRegisterRequest() {
        return CustomerRegisterRequest.builder()
                .accountType("seller")
                .address("lagos")
                .email("amosk@gmail.com")
                .name("Amos Monday")
                .password("amos")
                .phone("09090909090")
                .build();
    }


}