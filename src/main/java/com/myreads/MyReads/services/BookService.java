package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.BookResponseDTO;
import com.myreads.MyReads.exceptions.AuthorNotFoundException;
import com.myreads.MyReads.exceptions.BookAlreadyExistsException;
import com.myreads.MyReads.models.Book;
import com.myreads.MyReads.repositories.AuthorRepository;
import com.myreads.MyReads.repositories.BookRepository;
import com.myreads.MyReads.dto.BookCreateRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    private final AuthorService authorService;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository, AuthorService authorService) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.authorService = authorService;
    }

    public void createBook(BookCreateRequest bookCreateRequest) {

        if (authorRepository.findById(bookCreateRequest.getAuthorId()).isEmpty()) {
            throw new AuthorNotFoundException(bookCreateRequest.getAuthorId());
        }

        if (bookRepository.findByTitle(bookCreateRequest.getTitle()).isPresent()) {
            throw new BookAlreadyExistsException(bookCreateRequest.getTitle());
        }


        Book book = new Book(bookCreateRequest.getTitle(),
                bookCreateRequest.getAuthorId(),
                bookCreateRequest.getIsbn(),
                bookCreateRequest.getDatePublished());

        bookRepository.save(book);
    }

    public List<BookResponseDTO> getAllBooks() {

        List<BookResponseDTO> bookResponseDTOS = new ArrayList<>();
        List<Book> books = bookRepository.findAll();

        for (Book book : books) {
            String authorName = authorService.getAuthorNameByBookId(book.getId());

            BookResponseDTO bookResponse = new BookResponseDTO(
                    book.getTitle(),
                    authorName,
                    book.getIsbn(),
                    book.getDatePublished());

            bookResponseDTOS.add(bookResponse);
        }

        return bookResponseDTOS;
    }
}
