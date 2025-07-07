// ✅ OrderMapper.java
package com.org.kodvix.redbooks.mapper;

import com.org.kodvix.redbooks.dao.ClassEntity;
import com.org.kodvix.redbooks.dao.Customer;
import com.org.kodvix.redbooks.dao.Order;
import com.org.kodvix.redbooks.dao.OrderStatus;
import com.org.kodvix.redbooks.dto.OrderRequest;
import com.org.kodvix.redbooks.dto.OrderResponse;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
public class OrderMapper {

    public OrderResponse toResponse(Order order) {
        var books = order.getClassEntity().getBooks().stream()
                .map(book -> new OrderResponse.BookSummary(
                        book.getBookId(),
                        book.getTitle(),
                        book.getAuthor(),
                        book.getSubject()
                ))
                .collect(Collectors.toList());

        return new OrderResponse(
                order.getOrderId(),
                order.getStatus().name(),
                order.getCustomer().getCustomerId(),
                order.getCustomer().getUser().getName(),
                order.getClassEntity().getClassId(),
                order.getClassEntity().getClassName(),
                order.getClassEntity().getSchool().getId(),
                order.getClassEntity().getSchool().getName(),
                books
        );
    }

}
