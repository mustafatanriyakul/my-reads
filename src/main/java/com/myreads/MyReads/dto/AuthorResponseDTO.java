package com.myreads.MyReads.dto;
import lombok.Data;

@Data
public class AuthorResponseDTO {

    private String authorName;

    private String genreName;

    private String birthplace;


    public AuthorResponseDTO(String authorName, String genreName, String birthplace) {
        this.authorName = authorName;
        this.genreName = genreName;
        this.birthplace = birthplace;
    }
}


