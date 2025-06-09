package com.org.kodvix.redbooks.serviceImp;

import com.org.kodvix.redbooks.repository.PublisherRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.org.kodvix.redbooks.dao.BookDao;
import com.org.kodvix.redbooks.dao.ClassBookDao;
import com.org.kodvix.redbooks.dao.SchoolClassDao;
import com.org.kodvix.redbooks.dto.BasicBookDto;
import com.org.kodvix.redbooks.exception.ResourceNotFoundException;
import com.org.kodvix.redbooks.repository.BasicBookRepository;
import com.org.kodvix.redbooks.repository.BookRepository;
import com.org.kodvix.redbooks.repository.SchoolClassRepository;
import com.org.kodvix.redbooks.service.BasicBookService;

@Service
public class BasicBookServiceImpl implements BasicBookService {

    @Autowired
    public BasicBookRepository bookRepo;

    @Autowired
    public SchoolClassRepository classRepo;

    @Autowired
    public BookRepository masterBookRepo;  // Inject BookRepository

    @Autowired
    public ModelMapper mapper;

    @Autowired
    private PublisherRepository publisherRepo;

    @Override
    public BasicBookDto addBook(BasicBookDto book) {
        // Validate master book exists
        BookDao masterBook = masterBookRepo.findById(book.getMasterBookId())
                .orElseThrow(() -> new ResourceNotFoundException("Master book not found with ID: " + book.getMasterBookId()));

        SchoolClassDao schoolClass = classRepo.findById(book.getClassId())
                .orElseThrow(() -> new ResourceNotFoundException("Class not found with ID: " + book.getClassId()));

        if (bookRepo.existsBySchoolClassAndMasterBook(schoolClass, masterBook)) {
            throw new IllegalArgumentException("This book is already assigned to the class");
        }
        boolean publisherExists = publisherRepo.existsByPublisherName(book.getPublisher());
        if (!publisherExists) {
            throw new ResourceNotFoundException("Publisher not found with name: " + book.getPublisher());
        }

        // Optional: Validate publisher name matches the master book's publisher name
        String masterBookPublisherName = masterBook.getPublisherRef().getPublisherName();
        if (!masterBookPublisherName.equals(book.getPublisher())) {
            throw new IllegalArgumentException("Publisher name does not match the master book's publisher");
        }



        ClassBookDao classBookDao = convertToDao(book);
        classBookDao.setMasterBook(masterBook); // Set the relation explicitly

        return convertToDto(bookRepo.save(classBookDao));
    }

    @Override
    public BasicBookDto getBookById(Long id) {
        ClassBookDao classBookDao = bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class Book not found with ID: " + id));
        return convertToDto(classBookDao);
    }
    
    @Override
    public BasicBookDto updateBook(Long id, BasicBookDto book) {
        ClassBookDao existing = bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class Book not found with ID: " + id));

        // Update fields
        existing.setBookName(book.getBookName());
        existing.setAuthor(book.getAuthor());
        existing.setDescription(book.getDescription());
        existing.setEdition(book.getEdition());
        existing.setIsbn(book.getIsbn());
        existing.setLanguage(book.getLanguage());
        existing.setPublishedYear(book.getPublishedYear());
        existing.setPublisher(book.getPublisher());
        existing.setSubject(book.getSubject());

        // Update school class if changed
        if (book.getClassId() != null) {
            SchoolClassDao schoolClass = classRepo.findById(book.getClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("Class not found with ID: " + book.getClassId()));
            existing.setSchoolClass(schoolClass);
        }

        // Update master book if changed
        if (book.getMasterBookId() != null) {
            BookDao masterBook = masterBookRepo.findById(book.getMasterBookId())
                    .orElseThrow(() -> new ResourceNotFoundException("Master book not found with ID: " + book.getMasterBookId()));
            existing.setMasterBook(masterBook);
        }

        return convertToDto(bookRepo.save(existing));
    }

    @Override
    public void deleteBookById(Long id) {
        ClassBookDao classBookDao = bookRepo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Class Book not found with ID: " + id));
        bookRepo.deleteById(id);
    }

    private BasicBookDto convertToDto(ClassBookDao classBookDao) {
        BasicBookDto dto = mapper.map(classBookDao, BasicBookDto.class);
        dto.setClassId(classBookDao.getSchoolClass() != null ? classBookDao.getSchoolClass().getClassId() : null);
        dto.setMasterBookId(classBookDao.getMasterBook() != null ? classBookDao.getMasterBook().getId() : null);
        return dto;
    }

    private ClassBookDao convertToDao(BasicBookDto bookDto) {
        ClassBookDao classBookDao = mapper.map(bookDto, ClassBookDao.class);

        if (bookDto.getClassId() != null) {
            SchoolClassDao schoolClass = classRepo.findById(bookDto.getClassId())
                    .orElseThrow(() -> new ResourceNotFoundException("Class not found with ID: " + bookDto.getClassId()));
            classBookDao.setSchoolClass(schoolClass);
        }

        // masterBook is set explicitly in addBook() and updateBook()

        return classBookDao;
    }
}
