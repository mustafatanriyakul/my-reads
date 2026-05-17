package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.BookResponseDTO;
import com.myreads.MyReads.exceptions.BookAlreadyExistsException;
import com.myreads.MyReads.exceptions.BookNotFoundException;
import com.myreads.MyReads.models.Book;
import com.myreads.MyReads.models.UserBook;
import com.myreads.MyReads.repositories.BookRepository;
import com.myreads.MyReads.dto.BookCreateRequest;
import com.myreads.MyReads.repositories.UserBookRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookService {
  private final BookRepository bookRepository;
  private final UserBookRepository userBookRepository;

  public BookService(BookRepository bookRepository, UserBookRepository userBookRepository) {
    this.bookRepository = bookRepository;
    this.userBookRepository = userBookRepository;
  }

  public void createBook(BookCreateRequest bookCreateRequest) {

    if (bookRepository.findByTitle(bookCreateRequest.getTitle()).isPresent()) {

      throw new BookAlreadyExistsException(bookCreateRequest.getTitle());
    }

    Book book =
        new Book(
            bookCreateRequest.getTitle(),
            bookCreateRequest.getAuthorId(),
            bookCreateRequest.getIsbn(),
            bookCreateRequest.getDatePublished());

    bookRepository.save(book);
  }

  public List<BookResponseDTO> getAllBooks(Long userId) {

    List<BookResponseDTO> bookResponseDTOS = new ArrayList<>();
    List<Book> books = bookRepository.findAll();
    List<UserBook> userBooks = userBookRepository.findByUserId(userId);

    for (Book book : books) {

      BookResponseDTO bookResponse =
          new BookResponseDTO(
              book.getId(),
              book.getTitle(),
              book.getAuthorId(),
              book.getAuthor().getName(),
              book.getIsbn(),
              book.getDatePublished(),
              book.getCoverImageData(),
              book.getCoverImageType());

      for (UserBook userBook : userBooks) {
        if (userBook.getBookId().equals(book.getId())) {
          bookResponse.setStatus(userBook.getStatus());
          break;
        }
      }

      bookResponseDTOS.add(bookResponse);
    }

    return bookResponseDTOS;
  }

  public BookResponseDTO getBookDetailsByBookId(Long bookId) {
    Optional<Book> book = bookRepository.findById(bookId);

    if (book.isEmpty()) {
      throw new BookNotFoundException(bookId);
    }

    BookResponseDTO bookResponseDTO =
        new BookResponseDTO(
            book.get().getId(),
            book.get().getTitle(),
            book.get().getAuthorId(),
            book.get().getAuthor().getName(),
            book.get().getIsbn(),
            book.get().getDatePublished(),
            book.get().getCoverImageData(),
            book.get().getCoverImageType());

    return bookResponseDTO;
  }

  public void uploadBookCoverImage(Long bookId, MultipartFile file) throws IOException {
    Optional<Book> book = bookRepository.findById(bookId);

    if (book.isEmpty()) {
      throw new BookNotFoundException(bookId);
    }

    book.get().setCoverImageData(file.getBytes());
    book.get().setCoverImageType(file.getContentType());
    bookRepository.save(book.get());
  }

  public List<BookResponseDTO> searchBooks(String query) {

    List<Book> searchedBooks = bookRepository.findTop5ByTitleContainingIgnoreCase(query);

    List<BookResponseDTO> bookResponseDTOS = new ArrayList<>();

    for (Book book : searchedBooks) {
      BookResponseDTO bookResponseDTO =
          new BookResponseDTO(
              book.getId(),
              book.getTitle(),
              book.getAuthorId(),
              book.getAuthor().getName(),
              book.getIsbn(),
              book.getDatePublished(),
              book.getCoverImageData(),
              book.getCoverImageType());

      bookResponseDTOS.add(bookResponseDTO);
    }

    return bookResponseDTOS;
  }
}
