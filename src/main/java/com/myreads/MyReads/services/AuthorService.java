package com.myreads.MyReads.services;

import com.myreads.MyReads.exceptions.AuthorAlreadyExistsException;
import com.myreads.MyReads.models.Author;
import com.myreads.MyReads.repositories.AuthorRepository;
import com.myreads.MyReads.dto.AuthorCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public void createAuthor(AuthorCreateRequest authorCreateRequest){

        if (authorRepository.findByName(authorCreateRequest.getName()).isPresent()){
            throw new AuthorAlreadyExistsException(authorCreateRequest.getName());
        }
         authorRepository.save(new Author(authorCreateRequest.getName()));
    }
}
