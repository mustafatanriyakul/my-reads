package com.myreads.MyReads.services;

import com.myreads.MyReads.exceptions.AuthorNotFoundException;
import com.myreads.MyReads.exceptions.BookAlreadyExistsException;
import com.myreads.MyReads.models.Book;
import com.myreads.MyReads.repositories.AuthorRepository;
import com.myreads.MyReads.repositories.BookRepository;
import com.myreads.MyReads.requests.BookCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class BookService {
    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookService(BookRepository bookRepository, AuthorRepository authorRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
    }

    public void createBook(BookCreateRequest bookCreateRequest) {

        if (authorRepository.findById(bookCreateRequest.getAuthorId()).isEmpty()) {
            throw new AuthorNotFoundException(bookCreateRequest.getAuthorId());
        }

        if (bookRepository.findByTitle(bookCreateRequest.getTitle()).isPresent()){
            throw new BookAlreadyExistsException(bookCreateRequest.getTitle());
        }



        Book book = new Book(bookCreateRequest.getTitle(), bookCreateRequest.getAuthorId(), bookCreateRequest.getIsbn(),
                bookCreateRequest.getDatePublished());

        bookRepository.save(book);
    }


}
