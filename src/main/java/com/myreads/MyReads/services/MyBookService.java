package com.myreads.MyReads.services;

import com.myreads.MyReads.models.MyBook;
import com.myreads.MyReads.repositories.MyBookRepository;
import com.myreads.MyReads.requests.MyBookCreateRequest;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MyBookService {
    private final MyBookRepository myBookRepository;

    public MyBookService(MyBookRepository myBookRepository) {
        this.myBookRepository = myBookRepository;
    }

    public MyBook addBookToMybooks(MyBookCreateRequest myBookCreateRequest) {

        MyBook myBook = new MyBook(myBookCreateRequest.getUserId(), myBookCreateRequest.getBookId(), myBookCreateRequest.getDateRead(), myBookCreateRequest.getDateAdded());

        return myBookRepository.save(myBook);

    }

    public List<MyBook> getMybooksOfUser(Long userId) {
        return myBookRepository.findByUserId(userId);
    }
}
