package com.example.bookservice.controller;

import com.example.bookservice.dto.BookRequestDTO;
import com.example.bookservice.dto.BookResponseDTO;
import com.example.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookController {

    private final BookService bookService;

    @PostMapping
    public ResponseEntity<BookResponseDTO> createBook(@RequestBody BookRequestDTO requestDTO) {
        return ResponseEntity.ok(bookService.createBook(requestDTO));
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDTO> getBook(@PathVariable Long id) {
        return ResponseEntity.ok(bookService.getBookById(id));
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDTO>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @PutMapping("/{id}")
    public ResponseEntity<BookResponseDTO> updateBook(
            @PathVariable Long id,
            @RequestBody BookRequestDTO requestDTO) {
        return ResponseEntity.ok(bookService.updateBook(id, requestDTO));
    }

    @PatchMapping("/{id}")
    public ResponseEntity<BookResponseDTO> patchBook(
            @PathVariable Long id,
            @RequestBody BookRequestDTO requestDTO) {
        return ResponseEntity.ok(bookService.patchBook(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return ResponseEntity.ok("Book deleted successfully");
    }
}
