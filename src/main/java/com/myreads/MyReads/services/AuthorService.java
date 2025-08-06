package com.myreads.MyReads.services;

import com.myreads.MyReads.models.Author;
import com.myreads.MyReads.repositories.AuthorRepository;
import com.myreads.MyReads.requests.AuthorCreateRequest;
import org.springframework.stereotype.Service;

@Service
public class AuthorService {

    private final AuthorRepository authorRepository;

    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public Author createAuthor(AuthorCreateRequest authorCreateRequest){
        Author author = new Author(authorCreateRequest.getName());

        return authorRepository.save(author);
    }
}
