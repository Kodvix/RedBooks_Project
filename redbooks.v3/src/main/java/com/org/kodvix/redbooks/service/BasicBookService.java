package com.org.kodvix.redbooks.service;

import com.org.kodvix.redbooks.dto.BasicBookDto;

public interface BasicBookService {

    public BasicBookDto addBook(BasicBookDto book);

    public BasicBookDto getBookById(Long id);

    public BasicBookDto updateBook(Long id, BasicBookDto book);

    public void deleteBookById(Long id);

}
