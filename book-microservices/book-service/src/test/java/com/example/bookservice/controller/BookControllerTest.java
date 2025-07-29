package com.example.bookservice.controller;

import com.example.bookservice.dto.BookRequestDTO;
import com.example.bookservice.dto.BookResponseDTO;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.service.BookService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookController.class)
class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCreateBook() throws Exception {
        BookRequestDTO request = BookRequestDTO.builder().title("New Book").author("Tony").price(100.0).build();
        BookResponseDTO response = BookResponseDTO.builder().id(1L).title("New Book").author("Tony").price(100.0).build();

        when(bookService.createBook(any())).thenReturn(response);

        mockMvc.perform(post("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("New Book"));
    }

    @Test
    void testGetBook_success() throws Exception {
        BookResponseDTO response = BookResponseDTO.builder().id(1L).title("Book").author("Author").price(50.0).build();
        when(bookService.getBookById(1L)).thenReturn(response);

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Book"));
    }

    @Test
    void testGetBook_notFound() throws Exception {
        when(bookService.getBookById(1L)).thenThrow(new BookNotFoundException());

        mockMvc.perform(get("/books/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.error").value("Not Found"));
    }

    @Test
    void testUpdateBook() throws Exception {
        BookRequestDTO request = BookRequestDTO.builder().title("Updated").author("A").price(99.0).build();
        BookResponseDTO response = BookResponseDTO.builder().id(1L).title("Updated").author("A").price(99.0).build();

        when(bookService.updateBook(eq(1L), any())).thenReturn(response);

        mockMvc.perform(put("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Updated"));
    }

    @Test
    void testPatchBook() throws Exception {
        BookRequestDTO patch = BookRequestDTO.builder().price(500.0).build();
        BookResponseDTO response = BookResponseDTO.builder().id(1L).title("A").author("B").price(500.0).build();

        when(bookService.patchBook(eq(1L), any())).thenReturn(response);

        mockMvc.perform(patch("/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(patch)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(500.0));
    }

    @Test
    void testDeleteBook() throws Exception {
        doNothing().when(bookService).deleteBook(1L);

        mockMvc.perform(delete("/books/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Book deleted successfully"));
    }

    @Test
    void testGetAllBooks() throws Exception {
        List<BookResponseDTO> list = List.of(
                BookResponseDTO.builder().id(1L).title("One").author("A").price(10.0).build()
        );

        when(bookService.getAllBooks()).thenReturn(list);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(1));
    }
}
