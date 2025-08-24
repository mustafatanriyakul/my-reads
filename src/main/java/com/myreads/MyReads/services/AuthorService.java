package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.BookResponseDTO;
import com.myreads.MyReads.exceptions.AuthorAlreadyExistsException;
import com.myreads.MyReads.models.Author;
import com.myreads.MyReads.models.Book;
import com.myreads.MyReads.repositories.AuthorRepository;
import com.myreads.MyReads.dto.AuthorCreateRequest;
import com.myreads.MyReads.repositories.BookRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;
    private final BookRepository bookRepository;

    public AuthorService(AuthorRepository authorRepository, BookRepository bookRepository) {
        this.authorRepository = authorRepository;
        this.bookRepository = bookRepository;
    }

    public void createAuthor(AuthorCreateRequest authorCreateRequest) {

        if (authorRepository.findByName(authorCreateRequest.getName()).isPresent()) {
            throw new AuthorAlreadyExistsException(authorCreateRequest.getName());
        }
        authorRepository.save(new Author(authorCreateRequest.getName()));
    }

    public String getAuthorNameByBookId(Long bookId) {
        Optional<Book> book = bookRepository.findById(bookId);
        Optional<Author> author = authorRepository.findById(book.map(Book::getAuthorId).orElse(0L));

        return author.map(Author::getName).orElse("Unknown Author");
    }

    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<BookResponseDTO> getAuthorBookListByAuthorId(Long authorId) {

        List<Book> bookList = bookRepository.findAllByAuthorId(authorId);

        List<BookResponseDTO> bookResponseDTOList = new ArrayList<>();

        for (Book book : bookList) {

            BookResponseDTO bookResponseDTO = new BookResponseDTO(
                    book.getTitle(),
                    getAuthorNameByBookId(book.getId()),
                    book.getIsbn(),
                    book.getDatePublished()
            );

            bookResponseDTOList.add(bookResponseDTO);
        }

        return bookResponseDTOList;
    }
}
