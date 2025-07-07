package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.dto.*;
import com.org.kodvix.redbooks.exception.*;
import com.org.kodvix.redbooks.mapper.OrderMapper;
import com.org.kodvix.redbooks.repository.*;
import com.org.kodvix.redbooks.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ClassEntityRepository classRepository;
    private final OrderRepository orderRepository;

    private final OrderMapper orderMapper;

    @Override
    public List<Book> getBooksForCustomer(String customerEmail) {
        User user = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Customer details not found"));

        List<ClassEntity> classes = classRepository.findBySchoolId(getSchoolIdByName(customer.getSchoolName()));

        ClassEntity matchedClass = classes.stream()
                .filter(c -> c.getClassName().equalsIgnoreCase(customer.getStudentClass()))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Class not found for customer"));

        return List.copyOf(matchedClass.getBooks());
    }

    private Long getSchoolIdByName(String schoolName) {
        return userRepository.findAll().stream()
                .filter(u -> u instanceof School && u.getName().equalsIgnoreCase(schoolName))
                .map(School.class::cast)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("School not found"))
                .getId();
    }
    @Override
    public List<Book> getBooksByClassNameAndSchool(String className, String schoolName) {
        return classRepository.findByClassNameIgnoreCaseAndSchool_NameIgnoreCase(className, schoolName)
                .map(classEntity -> List.copyOf(classEntity.getBooks()))
                .orElseThrow(() -> new ClassNotFoundForSchoolException("No such class found for given school."));
    }


    @Override
    @Transactional
    public Order placeOrder(String customerEmail, Long classId) {
        User user = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CustomerDetailsNotFoundException("Customer details not found"));

        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new ClassNotFoundCustomException("Class not found"));

        Order order = Order.builder()
                .customer(customer)
                .classEntity(classEntity)
                .status(OrderStatus.PLACED)
                .build();

        return orderRepository.save(order);
    }

    @Override
    public List<Order> getOrders(String customerEmail) {
        User user = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CustomerDetailsNotFoundException("Customer details not found"));

        return orderRepository.findOrdersWithDetailsByCustomerId(customer.getCustomerId());
    }
    @Override
    @Transactional
    public ApiResponse<CustomerProfileResponseDto> updateCustomerProfile(String email, CustomerProfileUpdateRequest request) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CustomerDetailsNotFoundException("Customer details not found"));

        //  schoolName exists in the system
        boolean schoolExists = userRepository.findAll().stream()
                .anyMatch(u -> u.getRole().name().equals("SCHOOL") &&
                        u.getName().equalsIgnoreCase(request.getSchoolName()));

        if (!schoolExists) {
            throw new SchoolNotFoundException("School '" + request.getSchoolName() + "' is not registered in the system.");
        }

        // Proceed with updating
        customer.setSchoolName(request.getSchoolName());
        customer.setStudentClass(request.getStudentClass());
        customer.setAddress(request.getAddress());

        Customer updated = customerRepository.save(customer);

        CustomerProfileResponseDto dto = CustomerProfileResponseDto.builder()
                .customerId(updated.getCustomerId())
                .name(user.getName())
                .email(user.getEmail())
                .schoolName(updated.getSchoolName())
                .studentClass(updated.getStudentClass())
                .address(updated.getAddress())
                .build();

        return ApiResponse.<CustomerProfileResponseDto>builder()
                .message("Customer profile updated successfully.")
                .data(dto)
                .build();
    }


    @Override
    public CustomerBooksResponse getBooksWithCustomerNameById(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        Long schoolId = getSchoolIdByName(customer.getSchoolName());

        List<ClassEntity> classes = classRepository.findBySchoolId(schoolId);
        ClassEntity matchedClass = classes.stream()
                .filter(c -> c.getClassName().equalsIgnoreCase(customer.getStudentClass()))
                .findFirst()
                .orElseThrow(() -> new CustomerClassNotFoundException("Class not found for customer"));

        List<BookBriefDto> books = matchedClass.getBooks().stream()
                .map(book -> BookBriefDto.builder()
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .subject(book.getSubject())
                        .build())
                .toList();

        return CustomerBooksResponse.builder()
                .customerName(customer.getUser().getName())
                .className(customer.getStudentClass())
                .schoolName(customer.getSchoolName())
                .books(books)
                .build();
    }
    @Override
    public ApiResponse<List<OrderResponse>> getOrdersByCustomerId(Long customerId) {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer not found"));

        List<Order> orders = orderRepository.findOrdersWithDetailsByCustomerId(customerId);

        List<OrderResponse> responseList = orders.stream()
                .map(orderMapper::toResponse)
                .toList();

        String msg = String.format("%d orders retrieved for customer %s (ID: %d)",
                responseList.size(),
                customer.getUser().getName(),
                customerId
        );

        return ApiResponse.<List<OrderResponse>>builder()
                .message(msg)
                .data(responseList)
                .build();
    }
    @Override
    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }
    @Override
    public void cancelOrder(Long orderId, String customerEmail) {
        User user = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CustomerDetailsNotFoundException("Customer not found"));

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found"));

        if (!order.getCustomer().getCustomerId().equals(customer.getCustomerId())) {
            throw new UnauthorizedOrderCancellationException("You are not authorized to cancel this order");
        }

        orderRepository.deleteById(orderId);
    }
    @Override
    @Transactional
    public void deleteOwnAccount(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CustomerDetailsNotFoundException("Customer details not found"));

        // Delete customer row first
        customerRepository.delete(customer);
        // Then delete user row
        userRepository.delete(user);
    }
    @Override
    @Transactional
    public OrderSuccessResponse placeOrderByClassName(String customerEmail, String className, String schoolName) {
        User user = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new CustomerDetailsNotFoundException("Customer details not found"));

        if (!customer.getStudentClass().equalsIgnoreCase(className)) {
            throw new ClassMismatchException("You are only allowed to place orders for your assigned class: " + customer.getStudentClass());
        }

        Long schoolId = getSchoolIdByName(schoolName);

        ClassEntity classEntity = classRepository.findBySchoolId(schoolId).stream()
                .filter(c -> c.getClassName().equalsIgnoreCase(className))
                .findFirst()
                .orElseThrow(() -> new ClassNotFoundCustomException("Class not found for given name and school"));

        Order order = Order.builder()
                .customer(customer)
                .classEntity(classEntity)
                .status(OrderStatus.PLACED)
                .build();

        order = orderRepository.save(order);

        // Build list of BookBriefDto
        List<BookBriefDto> books = classEntity.getBooks().stream()
                .map(book -> BookBriefDto.builder()
                        .title(book.getTitle())
                        .author(book.getAuthor())
                        .subject(book.getSubject())
                        .build())
                .toList();

        return OrderSuccessResponse.builder()
                .orderId(order.getOrderId())
                .school(schoolName)
                .className(className)
                .customerName(user.getName())
                .customerEmail(user.getEmail())
                .deliveryAddress(customer.getAddress())
                .orderedBooks(books)
                .build();
    }


}
