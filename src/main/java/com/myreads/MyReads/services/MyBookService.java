package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.MyBookResponseDTO;
import com.myreads.MyReads.exceptions.BookNotFoundException;
import com.myreads.MyReads.exceptions.UserAlreadyHasThisBookException;
import com.myreads.MyReads.exceptions.UserNotFoundException;
import com.myreads.MyReads.models.Book;
import com.myreads.MyReads.models.MyBook;
import com.myreads.MyReads.repositories.BookRepository;
import com.myreads.MyReads.repositories.MyBookRepository;
import com.myreads.MyReads.repositories.UserRepository;
import com.myreads.MyReads.dto.MyBookCreateRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MyBookService {
    private final MyBookRepository myBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    private final AuthorService authorService;

    public MyBookService(MyBookRepository myBookRepository, BookRepository bookRepository, UserRepository userRepository, AuthorService authorService) {
        this.myBookRepository = myBookRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
        this.authorService = authorService;
    }

    public void addBookToMyBooks(MyBookCreateRequest myBookCreateRequest) {

        if (userRepository.findById(myBookCreateRequest.getUserId()).isEmpty()) {
            throw new UserNotFoundException(myBookCreateRequest.getUserId());
        }

        if (bookRepository.findById(myBookCreateRequest.getBookId()).isEmpty()) {
            throw new BookNotFoundException(myBookCreateRequest.getBookId());
        }

        if (myBookRepository.existsByUserIdAndBookId(myBookCreateRequest.getUserId(), myBookCreateRequest.getBookId())) {
            throw new UserAlreadyHasThisBookException(myBookCreateRequest.getBookId());
        }

        myBookRepository.save(new MyBook(myBookCreateRequest.getUserId(),
                myBookCreateRequest.getBookId(),
                myBookCreateRequest.getDateRead()));

    }

    public List<MyBookResponseDTO> getMyBooksByUserId(Long userId) {

        if (userRepository.findById(userId).isEmpty()) {
            throw new UserNotFoundException(userId);
        }

        List<MyBook> myBooks = myBookRepository.findByUserId(userId);
        List<MyBookResponseDTO> myBookResponseDTOS = new ArrayList<>();

        for (MyBook myBook : myBooks) {

            Optional<Book> book = bookRepository.findById(myBook.getBookId());

            String bookTitle = book.map(Book::getTitle).orElse("Unknown Book");
            String authorName = authorService.getAuthorNameByBookId(myBook.getBookId());
            LocalDate dateRead = myBook.getDateRead();
            LocalDate dateAdded = myBook.getDateAdded();

            MyBookResponseDTO myBookResponseDTO = new MyBookResponseDTO(
                    bookTitle,
                    authorName,
                    dateRead,
                    dateAdded);

            myBookResponseDTOS.add(myBookResponseDTO);
        }

        return myBookResponseDTOS;
    }
}
