package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.BookReviewRequest;
import com.myreads.MyReads.dto.UserBookResponseDTO;
import com.myreads.MyReads.exceptions.BookNotFoundException;
import com.myreads.MyReads.exceptions.InvalidReviewDateException;
import com.myreads.MyReads.exceptions.UserAlreadyHasThisBookException;
import com.myreads.MyReads.exceptions.UserNotFoundException;
import com.myreads.MyReads.models.Author;
import com.myreads.MyReads.models.Book;
import com.myreads.MyReads.models.UserBook;
import com.myreads.MyReads.models.UserBookStatus;
import com.myreads.MyReads.repositories.AuthorRepository;
import com.myreads.MyReads.repositories.BookRepository;
import com.myreads.MyReads.repositories.UserBookRepository;
import com.myreads.MyReads.repositories.UserRepository;
import com.myreads.MyReads.dto.BookStatusRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserBookService {
  private final UserBookRepository userBookRepository;
  private final BookRepository bookRepository;
  private final UserRepository userRepository;
  private final AuthorRepository authorRepository;

  public UserBookService(
      UserBookRepository userBookRepository,
      BookRepository bookRepository,
      UserRepository userRepository,
      AuthorRepository authorRepository) {
    this.userBookRepository = userBookRepository;
    this.bookRepository = bookRepository;
    this.userRepository = userRepository;
    this.authorRepository = authorRepository;
  }

  public void addBookToUserBooks(BookStatusRequest bookStatusRequest, Long userId) {

    if (userRepository.findById(userId).isEmpty()) {
      throw new UserNotFoundException(userId);
    }

    if (bookRepository.findById(bookStatusRequest.getBookId()).isEmpty()) {
      throw new BookNotFoundException(bookStatusRequest.getBookId());
    }

    if (userBookRepository.existsByUserIdAndBookId(userId, bookStatusRequest.getBookId())) {
      throw new UserAlreadyHasThisBookException(bookStatusRequest.getBookId());
    }

    userBookRepository.save(
        new UserBook(userId, bookStatusRequest.getBookId(), bookStatusRequest.getStatus()));
  }

  public List<UserBookResponseDTO> getUserBookByUserId(Long userId) {

    if (userRepository.findById(userId).isEmpty()) {
      throw new UserNotFoundException(userId);
    }

    List<UserBook> userBooks = userBookRepository.findByUserId(userId);
    List<UserBookResponseDTO> userBookResponseDTOS = new ArrayList<>();

    for (UserBook userBook : userBooks) {

      LocalDate dateStarted = userBook.getDateStarted();
      LocalDate dateFinished = userBook.getDateFinished();
      LocalDate dateAdded = userBook.getDateAdded();

      Optional<Book> book = bookRepository.findById(userBook.getBookId());

      if (book.isEmpty()) {
        continue;
      }

      Optional<Author> author = authorRepository.findById(book.get().getAuthorId());

      if (author.isEmpty()) {
        continue;
      }

      Long bookId = book.get().getId();
      String bookTitle = book.get().getTitle();
      Long authorId = book.get().getAuthorId();
      String authorName = book.get().getAuthor().getName();
      UserBookStatus status = userBook.getStatus();

      byte[] coverImage = book.get().getCoverImageData();
      String coverImageType = book.get().getCoverImageType();

      UserBookResponseDTO userBookResponseDTO =
          new UserBookResponseDTO(
              bookId,
              bookTitle,
              authorId,
              authorName,
              dateStarted,
              dateFinished,
              dateAdded,
              status,
              coverImage,
              coverImageType);

      userBookResponseDTOS.add(userBookResponseDTO);
    }

    return userBookResponseDTOS;
  }

  public void updateUserBookStatus(BookStatusRequest bookStatusRequest, Long userId) {

    UserBook userBook =
        userBookRepository.findUserBookByUserIdAndBookId(userId, bookStatusRequest.getBookId());

    userBook.setStatus(bookStatusRequest.getStatus());
    userBookRepository.save(userBook);
  }

  public void saveReview(BookReviewRequest bookReviewRequest, Long userId) {

    if (bookReviewRequest.getDateStarted() == null
        || bookReviewRequest.getDateFinished() == null
        || bookReviewRequest.getDateFinished().isBefore(bookReviewRequest.getDateStarted())) {
      throw new InvalidReviewDateException();
    }

    UserBook userBook =
        userBookRepository.findUserBookByUserIdAndBookId(userId, bookReviewRequest.getBookId());

    if (userBook == null) {
      userBook = new UserBook(userId, bookReviewRequest.getBookId(), UserBookStatus.READ);
    }

    userBook.setStatus(UserBookStatus.READ);
    userBook.setDateStarted(bookReviewRequest.getDateStarted());
    userBook.setDateFinished(bookReviewRequest.getDateFinished());

    userBookRepository.save(userBook);
  }
}
