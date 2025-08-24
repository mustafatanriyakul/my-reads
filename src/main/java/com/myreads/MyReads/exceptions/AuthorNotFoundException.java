package com.myreads.MyReads.exceptions;

public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(Long id){
        super("Author with this '" + id + "' not found.");
    }
}
