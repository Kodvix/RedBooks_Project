package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.dao.*;
import com.org.kodvix.redbooks.repository.*;
import com.org.kodvix.redbooks.service.CustomerService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

    private final UserRepository userRepository;
    private final CustomerRepository customerRepository;
    private final ClassEntityRepository classRepository;
    private final OrderRepository orderRepository;

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
    @Transactional
    public Order placeOrder(String customerEmail, Long classId) {
        User user = userRepository.findByEmail(customerEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        Customer customer = customerRepository.findByUserId(user.getId())
                .orElseThrow(() -> new RuntimeException("Customer details not found"));

        ClassEntity classEntity = classRepository.findById(classId)
                .orElseThrow(() -> new RuntimeException("Class not found"));

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
                .orElseThrow(() -> new RuntimeException("Customer details not found"));

        return orderRepository.findByCustomerId(customer.getId());
    }
}
