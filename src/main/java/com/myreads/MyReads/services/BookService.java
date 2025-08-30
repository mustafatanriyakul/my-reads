package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.BookResponseDTO;
import com.myreads.MyReads.models.Book;
import com.myreads.MyReads.repositories.BookRepository;
import com.myreads.MyReads.dto.BookCreateRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    private final BookRepository bookRepository;


    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void createBook(BookCreateRequest bookCreateRequest) {

        if (authorRepository.findById(bookCreateRequest.getAuthorId()).isEmpty()) {
            throw new AuthorNotFoundException(bookCreateRequest.getAuthorId());
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

            BookResponseDTO bookResponse = new BookResponseDTO(
                    book.getTitle(),
                    book.getAuthor().getName(),
                    book.getIsbn(),
                    book.getDatePublished(),
                    book.getAuthorId()
            );

            bookResponseDTOS.add(bookResponse);
        }

        return bookResponseDTOS;
    }
}
