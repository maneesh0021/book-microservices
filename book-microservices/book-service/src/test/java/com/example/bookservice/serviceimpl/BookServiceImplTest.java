package com.example.bookservice.serviceimpl;

import com.example.bookservice.dto.BookRequestDTO;
import com.example.bookservice.dto.BookResponseDTO;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    private BookRepository bookRepository;
    private ModelMapper modelMapper;
    private BookServiceImpl bookService;

    @BeforeEach
    void setup() {
        bookRepository = mock(BookRepository.class);
        modelMapper = new ModelMapper();
        bookService = new BookServiceImpl(bookRepository, modelMapper);
    }

    @Test
    void testCreateBook() {
        BookRequestDTO request = BookRequestDTO.builder().title("Book").author("A").price(100.0).build();
        Book saved = Book.builder().id(1L).title("Book").author("A").price(100.0).build();

        when(bookRepository.save(any())).thenReturn(saved);

        BookResponseDTO result = bookService.createBook(request);

        assertEquals("Book", result.getTitle());
        verify(bookRepository).save(any());
    }

    @Test
    void testGetBookById_success() {
        Book book = Book.builder().id(1L).title("T").author("A").price(10.0).build();
        when(bookRepository.findById(1L)).thenReturn(Optional.of(book));

        BookResponseDTO result = bookService.getBookById(1L);
        assertEquals("T", result.getTitle());
    }

    @Test
    void testGetBookById_notFound() {
        when(bookRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(BookNotFoundException.class, () -> bookService.getBookById(1L));
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = List.of(Book.builder().id(1L).title("B1").author("A1").price(1.0).build());
        when(bookRepository.findAll()).thenReturn(books);

        List<BookResponseDTO> result = bookService.getAllBooks();
        assertEquals(1, result.size());
    }

    @Test
    void testUpdateBook() {
        Book existing = Book.builder().id(1L).title("Old").author("A").price(10.0).build();
        BookRequestDTO update = BookRequestDTO.builder().title("New").author("B").price(50.0).build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any())).thenReturn(existing);

        BookResponseDTO result = bookService.updateBook(1L, update);
        assertEquals("New", result.getTitle());
    }

    @Test
    void testPatchBook_someFields() {
        Book existing = Book.builder().id(1L).title("Old").author("A").price(10.0).build();
        BookRequestDTO patch = BookRequestDTO.builder().price(100.0).build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existing));
        when(bookRepository.save(any())).thenReturn(existing);

        BookResponseDTO result = bookService.patchBook(1L, patch);
        assertEquals(100.0, result.getPrice());
    }

    @Test
    void testDeleteBook_success() {
        when(bookRepository.existsById(1L)).thenReturn(true);
        doNothing().when(bookRepository).deleteById(1L);

        bookService.deleteBook(1L);
        verify(bookRepository).deleteById(1L);
    }

    @Test
    void testDeleteBook_notFound() {
        when(bookRepository.existsById(1L)).thenReturn(false);
        assertThrows(BookNotFoundException.class, () -> bookService.deleteBook(1L));
    }
}
