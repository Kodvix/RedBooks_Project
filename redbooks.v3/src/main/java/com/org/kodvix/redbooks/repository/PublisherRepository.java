package com.org.kodvix.redbooks.repository;

import com.org.kodvix.redbooks.dao.PublisherDao;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PublisherRepository extends JpaRepository<PublisherDao, Long> {

    List<PublisherDao> findByPublisherName(String publisherName);

    Optional<PublisherDao> findFirstByPublisherName(String publisherName);
    List<PublisherDao> findByPublisherNameIgnoreCase(String publisherName);
    boolean existsByPublisherName(String publisherName);

}
