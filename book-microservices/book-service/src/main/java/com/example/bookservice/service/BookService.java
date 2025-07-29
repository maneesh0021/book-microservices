package com.example.bookservice.service;


import com.example.bookservice.dto.BookRequestDTO;
import com.example.bookservice.dto.BookResponseDTO;

import java.util.List;


public interface BookService {
    BookResponseDTO createBook(BookRequestDTO requestDTO);
    BookResponseDTO getBookById(Long id);
    List<BookResponseDTO> getAllBooks();
    BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO);
    BookResponseDTO patchBook(Long id, BookRequestDTO requestDTO);
    void deleteBook(Long id);
}
