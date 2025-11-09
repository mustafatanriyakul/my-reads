package com.myreads.MyReads.services;


import com.myreads.MyReads.dto.GenreCreateRequest;
import com.myreads.MyReads.exceptions.GenreAlreadyExistsException;
import com.myreads.MyReads.models.Genre;
import com.myreads.MyReads.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GenreService {

    private final GenreRepository genreRepository;

    public GenreService(GenreRepository genreRepository){
        this.genreRepository = genreRepository;
    }

    public void createGenre(GenreCreateRequest genreCreateRequest){

        String genreName = genreCreateRequest.getName();

        if (genreRepository.existsByName(genreName)){

            throw new GenreAlreadyExistsException(genreName);
        }

        genreRepository.save(new Genre(genreName));
    }

    public List<Genre> getAllGenres(){
        return genreRepository.findAll();
    }

}
