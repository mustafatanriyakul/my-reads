package com.myreads.MyReads.services;

import com.myreads.MyReads.exceptions.BookNotFoundException;
import com.myreads.MyReads.exceptions.UserNotFoundException;
import com.myreads.MyReads.models.MyBook;
import com.myreads.MyReads.repositories.BookRepository;
import com.myreads.MyReads.repositories.MyBookRepository;
import com.myreads.MyReads.repositories.UserRepository;
import com.myreads.MyReads.requests.MyBookCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBookService {
    private final MyBookRepository myBookRepository;
    private final BookRepository bookRepository;
    private final UserRepository userRepository;

    public MyBookService(MyBookRepository myBookRepository, BookRepository bookRepository, UserRepository userRepository) {
        this.myBookRepository = myBookRepository;
        this.bookRepository = bookRepository;
        this.userRepository = userRepository;
    }

    public void addBookToMybooks(MyBookCreateRequest myBookCreateRequest) {

        if (userRepository.findById(myBookCreateRequest.getUserId()).isEmpty()){
            throw new UserNotFoundException(myBookCreateRequest.getUserId());
        }

        if (bookRepository.findById(myBookCreateRequest.getBookId()).isEmpty()){
            throw new BookNotFoundException(myBookCreateRequest.getBookId());
        }


        myBookRepository.save(new MyBook(myBookCreateRequest.getUserId(), myBookCreateRequest.getBookId(),
                myBookCreateRequest.getDateRead(), myBookCreateRequest.getDateAdded()));
    }

    public List<MyBook> getMybooksOfUser(Long userId) {

        if (userRepository.findById(userId).isEmpty()){
            throw new UserNotFoundException(userId);
        }

        return myBookRepository.findByUserId(userId);
    }
}
