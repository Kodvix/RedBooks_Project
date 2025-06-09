package com.org.kodvix.redbooks.dao;

import java.util.Date;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Data;

@Data
public class CustomerProfileDao {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name="customer_id")
	private Long customerId;

	private String customerName;

	private String email;

	private String mobileNo;

	private String address;

	private Integer pinCode;

	private String distric;

	private String city;

	private Date createdAt;

	private String createdBy;

	private Date updatedAt;

	private String updatedBy;

	@OneToMany(mappedBy = "customer")
	private List<OrderDao> orders;
}
