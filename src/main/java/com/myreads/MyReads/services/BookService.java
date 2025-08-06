package com.myreads.MyReads.services;

import com.myreads.MyReads.models.Author;
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

    public Book createBook(BookCreateRequest bookCreateRequest) {
        Author author = authorRepository.findById(bookCreateRequest.getAuthorId()).orElseThrow(() -> new IllegalArgumentException("Author not found with this ID"));


        Book book = new Book(bookCreateRequest.getTitle(), author.getId(), bookCreateRequest.getIsbn(), bookCreateRequest.getDatePublished());

        return bookRepository.save(book);
    }


}
