package com.org.kodvix.redbooks.serviceImp;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.org.kodvix.redbooks.dao.BookDao;
import com.org.kodvix.redbooks.dao.PublisherDao;
import com.org.kodvix.redbooks.dto.BookDto;
import com.org.kodvix.redbooks.exception.ResourceNotFoundException;
import com.org.kodvix.redbooks.mapper.BookMapper;
import com.org.kodvix.redbooks.repository.BookRepository;
import com.org.kodvix.redbooks.repository.PublisherRepository;
import com.org.kodvix.redbooks.service.BookService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

	private final BookRepository bookRepository;
	private final PublisherRepository publisherRepository;
	private final BookMapper bookMapper;
	private final ModelMapper modelMapper;

	@Override
	public BookDto addBook(BookDto bookDto, MultipartFile orderDocument) throws IOException {
		if (bookRepository.existsByIsbn(bookDto.getIsbn())) {
			throw new DataIntegrityViolationException("Duplicate ISBN: " + bookDto.getIsbn());
		}

		BookDao bookDao = bookMapper.toDao(bookDto);

		// Use publisherId directly here
		if (bookDto.getPublisherId() != null) {
			PublisherDao publisher = publisherRepository.findById(bookDto.getPublisherId()).orElseThrow(
					() -> new ResourceNotFoundException("Publisher not found with ID: " + bookDto.getPublisherId()));
			bookDao.setPublisherRef(publisher);
		}

		if (orderDocument != null && !orderDocument.isEmpty()) {
			bookDao.setOrderDocument(orderDocument.getBytes());
			bookDao.setOrderDocumentName(orderDocument.getOriginalFilename());
			bookDao.setOrderDocumentType(orderDocument.getContentType());
		}

		return bookMapper.toDto(bookRepository.save(bookDao));
	}

	@Override
	public BookDto getBookById(Long id) {
		BookDao book = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));
		return bookMapper.toDto(book);
	}

	@Override
    public List<BookDto> getBookByClassName(String className) {
        List<BookDao> bookdao = bookRepository.findByClassName(className);
        		if(bookMapper == null) {
        			throw new ResourceNotFoundException("Class Book not found with ID: " + className);
        		}
            
        return convertToDtoList(bookdao);
    }

	@Override
	public List<BookDto> getAllBooks() {
		return bookRepository.findAll().stream().map(bookMapper::toDto).toList();
	}

	@Override
	public BookDto updateBook(Long id, BookDto bookDto, MultipartFile orderDocument) throws IOException {
		BookDao existingBook = bookRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Book not found with ID: " + id));

		existingBook.setTitle(bookDto.getTitle());
		existingBook.setAuthor(bookDto.getAuthor());
		existingBook.setIsbn(bookDto.getIsbn());

		if (bookDto.getPublisherId() != null) {
			PublisherDao publisher = publisherRepository.findById(bookDto.getPublisherId()).orElseThrow(
					() -> new ResourceNotFoundException("Publisher not found with ID: " + bookDto.getPublisherId()));
			existingBook.setPublisherRef(publisher);
		}

		existingBook.setPublicationYear(bookDto.getPublicationYear());
		existingBook.setStockQuantity(bookDto.getStockQuantity());
		existingBook.setBookPrice(bookDto.getBookPrice());
		existingBook.setCategory(bookDto.getCategory());
		existingBook.setLanguage(bookDto.getLanguage());
		existingBook.setNumberOfPages(bookDto.getNumberOfPages());
		existingBook.setEdition(bookDto.getEdition());

		if (orderDocument != null && !orderDocument.isEmpty()) {
			existingBook.setOrderDocument(orderDocument.getBytes());
			existingBook.setOrderDocumentName(orderDocument.getOriginalFilename());
			existingBook.setOrderDocumentType(orderDocument.getContentType());
		}

		return bookMapper.toDto(bookRepository.save(existingBook));
	}

	@Override
	public void deleteBook(Long id) {
		if (!bookRepository.existsById(id)) {
			throw new ResourceNotFoundException("Book not found with ID: " + id);
		}
		bookRepository.deleteById(id);
	}

	@Override
	public List<BookDto> getBooksByPublisher(String publisherName) {
		List<BookDao> books = bookRepository.findByPublisherRef_PublisherName(publisherName);
		return books.stream().map(bookMapper::toDto).toList();
	}

	private BookDto convertToDto(BookDao orderdao) {
		return modelMapper.map(orderdao, BookDto.class);
	}

	private List<BookDto> convertToDtoList(List<BookDao> orderdaolist) {
		return orderdaolist.stream().map(this::convertToDto).collect(Collectors.toList());
	}

	private BookDao convertToDao(BookDto orderDto) {
		return modelMapper.map(orderDto, BookDao.class);
	}
}
