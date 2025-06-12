package com.org.kodvix.redbooks.mapper;

import com.org.kodvix.redbooks.dao.ClassEntity;
import com.org.kodvix.redbooks.dao.Customer;
import com.org.kodvix.redbooks.dao.Order;
import com.org.kodvix.redbooks.dao.OrderStatus;
import com.org.kodvix.redbooks.dto.OrderRequest;

public class OrderMapper {
    private final BookMapper bookMapper;

    public OrderMapper(BookMapper bookMapper) {
        this.bookMapper = bookMapper;
    }
    public OrderRequest toDto(Order order) {
        OrderRequest dto = new OrderRequest();
        dto.setClassId(dto.getClassId());


        return dto;
    }
    public Order toDao(OrderRequest request, Customer customer, ClassEntity classEntity) {
        return Order.builder()
                .customer(customer)
                .classEntity(classEntity)
                .status(OrderStatus.PLACED)
                .build();
    }
}
