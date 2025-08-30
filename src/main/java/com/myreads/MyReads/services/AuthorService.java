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
        authorRepository.save(new Author(
                authorCreateRequest.getName(),
                authorCreateRequest.getBirthplace()));
    }


    public List<Author> getAllAuthors() {
        return authorRepository.findAll();
    }

    public List<BookResponseDTO> getAuthorBookListByAuthorId(Long authorId) {

        List<Book> booksOfAuthor = bookRepository.findAllByAuthorId(authorId);

        List<BookResponseDTO> booksOfAuthorResponse = new ArrayList<>();

        for (Book book : booksOfAuthor) {

            Optional<Author> author = authorRepository.findById(book.getAuthorId());

            if (author.isEmpty()){
                continue;
            }

            String authorName = author.get().getName();

            BookResponseDTO bookResponseDTO = new BookResponseDTO(
                    book.getTitle(),
                    authorName,
                    book.getIsbn(),
                    book.getDatePublished()
            );

            booksOfAuthorResponse.add(bookResponseDTO);
        }

        return booksOfAuthorResponse;
    }
}
