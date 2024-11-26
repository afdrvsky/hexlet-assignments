package exercise.service;

import exercise.dto.BookCreateDTO;
import exercise.dto.BookDTO;
import exercise.dto.BookUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.BookMapper;
import exercise.repository.AuthorRepository;
import exercise.repository.BookRepository;
import jakarta.validation.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@Service
public class BookService {
    // BEGIN
    @Autowired
    private BookRepository bookRepository;

    @Autowired
    private BookMapper bookMapper;

    @Autowired
    private AuthorRepository authorRepository;

    public List<BookDTO> getAllBooks() {
        var books = bookRepository.findAll();
        return books.stream().map(bookMapper::map).toList();
    }

    public BookDTO getBookById(Long id) {
        var book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        return bookMapper.map(book);
    }

    public BookDTO createBook(BookCreateDTO bookCreateDTO) {
        var book = bookMapper.map(bookCreateDTO);
        authorRepository.findById(bookCreateDTO.getAuthorId()).orElseThrow(() -> new ConstraintViolationException(null));
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public BookDTO updateBook(Long id, BookUpdateDTO bookUpdateDTO) {
        var book = bookRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Book not found"));
        authorRepository.findById(bookUpdateDTO.getAuthorId().get()).orElseThrow(() -> new ConstraintViolationException(null));
        bookMapper.update(bookUpdateDTO, book);
        bookRepository.save(book);
        return bookMapper.map(book);
    }

    public void deleteBook(Long id) {
        bookRepository.deleteById(id);
    }
    // END
}
