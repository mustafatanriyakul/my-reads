package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.BookGenreCreateRequest;
import com.myreads.MyReads.exceptions.*;
import com.myreads.MyReads.models.AuthorGenre;
import com.myreads.MyReads.models.BookGenre;
import com.myreads.MyReads.repositories.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookGenreService {

  private final BookGenreRepository bookGenreRepository;
  private final BookRepository bookRepository;
  private final GenreRepository genreRepository;

  public BookGenreService(
      BookGenreRepository bookGenreRepository,
      BookRepository bookRepository,
      GenreRepository genreRepository) {
    this.bookGenreRepository = bookGenreRepository;
    this.bookRepository = bookRepository;
    this.genreRepository = genreRepository;
  }

  public void createBookGenre(BookGenreCreateRequest bookGenreCreateRequest) {

    Long bookId = bookGenreCreateRequest.getBookId();
    Long genreId = bookGenreCreateRequest.getGenreId();

    if (!bookRepository.existsById(bookId)) {
      throw new BookNotFoundException(bookId);
    }

    if (!genreRepository.existsById(genreId)) {
      throw new GenreNotFoundException(genreId);
    }

    if (bookGenreRepository.existsByBookIdAndGenreId(bookId, genreId)) {
      throw new BookGenreAlreadyExistsException();
    }

    bookGenreRepository.save(new BookGenre(bookId, genreId));
  }

  public List<BookGenre> getAllBookGenres() {
    return bookGenreRepository.findAll();
  }

  public List<String> getBookGenreByBookId(Long bookId) {

    return bookGenreRepository.findGenreNamesByBookId(bookId);
  }
}
