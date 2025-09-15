package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.UserBookResponseDTO;
import com.myreads.MyReads.exceptions.BookNotFoundException;
import com.myreads.MyReads.exceptions.UserAlreadyHasThisBookException;
import com.myreads.MyReads.exceptions.UserNotFoundException;
import com.myreads.MyReads.models.Author;
import com.myreads.MyReads.models.Book;
import com.myreads.MyReads.models.UserBook;
import com.myreads.MyReads.repositories.AuthorRepository;
import com.myreads.MyReads.repositories.BookRepository;
import com.myreads.MyReads.repositories.UserBookRepository;
import com.myreads.MyReads.repositories.UserRepository;
import com.myreads.MyReads.dto.UserBookCreateRequest;
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

    public UserBookService(UserBookRepository userBookRepository, BookRepository bookRepository, UserRepository userRepository, AuthorRepository authorRepository) {
        this.userBookRepository = userBookRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.authorRepository = authorRepository;
    }

    public void addBookToUserBooks(UserBookCreateRequest userBookCreateRequest) {

        if (userRepository.findById(userBookCreateRequest.getUserId()).isEmpty()) {
            throw new UserNotFoundException(userBookCreateRequest.getUserId());
        }

        if (bookRepository.findById(userBookCreateRequest.getBookId()).isEmpty()) {
            throw new BookNotFoundException(userBookCreateRequest.getBookId());
        }

        if (userBookRepository.existsByUserIdAndBookId(userBookCreateRequest.getUserId(), userBookCreateRequest.getBookId())) {
            throw new UserAlreadyHasThisBookException(userBookCreateRequest.getBookId());
        }

        userBookRepository.save(new UserBook(userBookCreateRequest.getUserId(),
                userBookCreateRequest.getBookId(),
                userBookCreateRequest.getDateRead()));

    }

    public List<UserBookResponseDTO> getUserBookByUserId(Long userId) {

        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        List<UserBook> userBooks = userBookRepository.findByUserId(userId);
        List<UserBookResponseDTO> userBookResponseDTOS = new ArrayList<>();

        for (UserBook userBook : userBooks) {

            LocalDate dateRead = userBook.getDateRead();
            LocalDate dateAdded = userBook.getDateAdded();

            Optional<Book> book = bookRepository.findById(userBook.getBookId());

            if (book.isEmpty()){
                continue;
            }

            Optional<Author> author = authorRepository.findById(book.get().getAuthorId());

            if (author.isEmpty()){
                continue;
            }

            String bookTitle = book.get().getTitle();
            String authorName = book.get().getAuthor().getName();
            Long authorId = book.get().getAuthorId();

            UserBookResponseDTO userBookResponseDTO = new UserBookResponseDTO(
                        bookTitle,
                        authorName,
                        dateRead,
                        dateAdded,
                        authorId);

            userBookResponseDTOS.add(userBookResponseDTO);
        }

        return userBookResponseDTOS;
    }
}
