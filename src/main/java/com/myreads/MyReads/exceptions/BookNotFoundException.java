package com.myreads.MyReads.exceptions;

public class BookNotFoundException extends RuntimeException{
    public BookNotFoundException(Long id){
        super("Book was not found with this id '" + id + "'.");
    }
}
