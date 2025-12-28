package com.myreads.MyReads.services;

import com.myreads.MyReads.dto.AuthorGenreCreateRequest;
import com.myreads.MyReads.exceptions.AuthorGenreAlreadyExistsException;
import com.myreads.MyReads.exceptions.AuthorNotFoundException;
import com.myreads.MyReads.exceptions.GenreNotFoundException;
import com.myreads.MyReads.models.AuthorGenre;
import com.myreads.MyReads.repositories.AuthorGenreRepository;
import com.myreads.MyReads.repositories.AuthorRepository;
import com.myreads.MyReads.repositories.GenreRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthorGenreService {

  private final AuthorGenreRepository authorGenreRepository;
  private final AuthorRepository authorRepository;
  private final GenreRepository genreRepository;

  public AuthorGenreService(
      AuthorGenreRepository authorGenreRepository,
      AuthorRepository authorRepository,
      GenreRepository genreRepository) {
    this.authorGenreRepository = authorGenreRepository;
    this.authorRepository = authorRepository;
    this.genreRepository = genreRepository;
  }

  public void createAuthorGenre(AuthorGenreCreateRequest authorGenreCreateRequest) {

    Long authorId = authorGenreCreateRequest.getAuthorId();
    Long genreId = authorGenreCreateRequest.getGenreId();

    if (!authorRepository.existsById(authorId)) {
      throw new AuthorNotFoundException(authorId);
    }

    if (!genreRepository.existsById(genreId)) {
      throw new GenreNotFoundException(genreId);
    }

    if (authorGenreRepository.existsByAuthorIdAndGenreId(authorId, genreId)) {
      throw new AuthorGenreAlreadyExistsException();
    }

    authorGenreRepository.save(new AuthorGenre(authorId, genreId));
  }

  public List<AuthorGenre> getAllAuthorGenres() {
    return authorGenreRepository.findAll();
  }

  public List<String> getAuthorGenreByAuthorId(Long authorId) {

    return authorGenreRepository.findGenreNamesByAuthorId(authorId);
  }
}
