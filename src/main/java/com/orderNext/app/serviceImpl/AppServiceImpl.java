package com.orderNext.app.serviceImpl;

import com.orderNext.app.dto.*;
import com.orderNext.app.entity.*;
import com.orderNext.app.reposetory.*;
import com.orderNext.app.service.AppService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class AppServiceImpl implements AppService {

    @Autowired
    private CustomerRepository customerRepo;
    @Autowired private AddressRepository addressRepo;
    @Autowired private ProductRepository productRepo;
    @Autowired private OrderRepository orderRepo;
    @Autowired private OrderItemRepository itemRepo;

    // CUSTOMER
    public void createCustomer(CustomerDTO dto) {
        Customer c = new Customer();
        c.setCustomerId(dto.getCustomerId());
        c.setName(dto.getName());
        c.setEmail(dto.getEmail());
        c.setPhone(dto.getPhone());
        customerRepo.save(c);
    }

    public CustomerDTO getCustomer(String id) {
        Customer c = customerRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found"));

        CustomerDTO dto = new CustomerDTO();
        dto.setCustomerId(c.getCustomerId());
        dto.setName(c.getName());
        dto.setEmail(c.getEmail());
        dto.setPhone(c.getPhone());
        return dto;
    }

    // ADDRESS
    public void addAddress(AddressDTO dto) {
        Address a = new Address();
        a.setCustomerId(dto.getCustomerId());
        a.setStreet(dto.getStreet());
        a.setCity(dto.getCity());
        a.setState(dto.getState());
        a.setZipCode(dto.getZipCode());
        addressRepo.save(a);
    }

    public List<AddressDTO> getAddresses(String customerId) {
        return addressRepo.findByCustomerId(customerId).stream().map(a -> {
            AddressDTO d = new AddressDTO();
            d.setId(a.getId());
            d.setCustomerId(a.getCustomerId());
            d.setStreet(a.getStreet());
            d.setCity(a.getCity());
            d.setState(a.getState());
            d.setZipCode(a.getZipCode());
            return d;
        }).toList();
    }

    // PRODUCT
    public void createProduct(ProductDTO dto) {
        Product p = new Product();
        p.setProductId(dto.getProductId());
        p.setProductName(dto.getProductName());
        p.setCategory(dto.getCategory());
        p.setPrice(dto.getPrice());
        productRepo.save(p);
    }

    public List<ProductDTO> getProducts() {
        return productRepo.findAll().stream().map(p -> {
            ProductDTO dto = new ProductDTO();
            dto.setProductId(p.getProductId());
            dto.setProductName(p.getProductName());
            dto.setCategory(p.getCategory());
            dto.setPrice(p.getPrice());
            return dto;
        }).toList();
    }

    // ORDER
    public String placeOrder(PlaceOrderRequestDTO dto) {

        if (!customerRepo.existsById(dto.getCustomerId()))
            throw new RuntimeException("Customer not found");

        Address address = addressRepo.findById(dto.getAddressId())
                .orElseThrow(() -> new RuntimeException("Address not found"));

        if (!address.getCustomerId().equals(dto.getCustomerId()))
            throw new RuntimeException("Invalid address");

        double total = 0;

        for (OrderItemDTO item : dto.getItems()) {
            Product p = productRepo.findById(item.getProductId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
            total += p.getPrice() * item.getQuantity();
        }

        String orderId = UUID.randomUUID().toString();

        Order order = new Order();
        order.setOrderId(orderId);
        order.setCustomerId(dto.getCustomerId());
        order.setAddressId(dto.getAddressId());
        order.setTotalAmount(total);
        order.setOrderDate(LocalDateTime.now());

        orderRepo.save(order);

        List<OrderItem> items = dto.getItems().stream().map(i -> {
            Product p = productRepo.findById(i.getProductId()).get();

            OrderItem oi = new OrderItem();
            oi.setOrderId(orderId);
            oi.setProductId(i.getProductId());
            oi.setQuantity(i.getQuantity());
            oi.setPrice(p.getPrice());
            return oi;
        }).toList();

        itemRepo.saveAll(items);

        return orderId;
    }

    public OrderResponseDTO getOrder(String orderId) {

        Order order = orderRepo.findById(orderId)
                .orElseThrow(() -> new RuntimeException("Order not found"));

        List<OrderItem> items = itemRepo.findByOrderId(orderId);

        List<OrderItemDetailDTO> itemDTOs = items.stream().map(i -> {
            Product p = productRepo.findById(i.getProductId()).get();

            OrderItemDetailDTO dto = new OrderItemDetailDTO();
            dto.setProductId(p.getProductId());
            dto.setProductName(p.getProductName());
            dto.setQuantity(i.getQuantity());
            dto.setPrice(i.getPrice());
            return dto;
        }).toList();

        OrderResponseDTO res = new OrderResponseDTO();
        res.setOrderId(orderId);
        res.setCustomerId(order.getCustomerId());
        res.setAddressId(order.getAddressId());
        res.setTotalAmount(order.getTotalAmount());
        res.setItems(itemDTOs);

        return res;
    }
}
