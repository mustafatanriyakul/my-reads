package com.myreads.MyReads.exceptions;

public class BookAlreadyExistsException extends  RuntimeException{
    public BookAlreadyExistsException(String title){
        super("Book with title '" + title + "' already exists in database.");
    }
}
