package com.org.kodvix.redbooks.dao;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;

public class OrderDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "order_id")
	private Long orderId;

	@Column(name = "date")
	private Date orderDate;

	@ManyToMany
    @JoinTable(
        name = "order_books",
        joinColumns = @JoinColumn(name = "order_id"),
        inverseJoinColumns = @JoinColumn(name = "book_id")
    )
	private List<BookDao> books;

	@ManyToOne
	@JoinColumn(name = "publisher_id")
	private PublisherDao publisher;

	@ManyToOne
	@JoinColumn(name = "customer_id")
	private CustomerProfileDao customer;

}
