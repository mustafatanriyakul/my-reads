package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.AuthorResponseDTO;
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

  private final AuthorGenreService authorGenreService;

  public AuthorService(
      AuthorRepository authorRepository,
      BookRepository bookRepository,
      AuthorGenreService authorGenreService) {
    this.authorRepository = authorRepository;
    this.bookRepository = bookRepository;
    this.authorGenreService = authorGenreService;
  }

  public void create(AuthorCreateRequest authorCreateRequest) {

    if (authorRepository.findByName(authorCreateRequest.getName()).isPresent()) {
      throw new AuthorAlreadyExistsException(authorCreateRequest.getName());
    }
    authorRepository.save(
        new Author(authorCreateRequest.getName(), authorCreateRequest.getBirthplace()));
  }

  public List<Author> getAll() {
    return authorRepository.findAll();
  }

  public List<BookResponseDTO> getBookListByAuthorId(Long authorId) {

    List<Book> booksOfAuthor = bookRepository.findAllByAuthorId(authorId);

    List<BookResponseDTO> booksOfAuthorResponse = new ArrayList<>();

    for (Book book : booksOfAuthor) {

      BookResponseDTO bookResponseDTO =
          new BookResponseDTO(
              book.getId(),
              book.getTitle(),
              book.getAuthor().getName(),
              book.getIsbn(),
              book.getDatePublished(),
              book.getAuthorId());

      booksOfAuthorResponse.add(bookResponseDTO);
    }

    return booksOfAuthorResponse;
  }

  public AuthorResponseDTO getAuthorDetailsByAuthorId(Long authorId) {

    Optional<Author> author = authorRepository.findById(authorId);

    if (author.isEmpty()) {
      return null;
    }

    List<String> genres = authorGenreService.getAuthorGenreByAuthorId(authorId);

    AuthorResponseDTO authorDetails =
        new AuthorResponseDTO(author.get().getName(), author.get().getBirthplace(), genres);

    return authorDetails;
  }
}
