package com.org.kodvix.redbooks.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.org.kodvix.redbooks.dao.ContactDao;

public interface ContactRepository extends JpaRepository<ContactDao,Long>{

}
