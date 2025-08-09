package com.myreads.MyReads.exceptions;

public class AuthorAlreadyExistsException extends RuntimeException{
    public AuthorAlreadyExistsException(String name){
        super("Author '" + name + "' already exist in database.");
    }
}
