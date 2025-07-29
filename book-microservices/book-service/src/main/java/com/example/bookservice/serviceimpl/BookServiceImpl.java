package com.example.bookservice.serviceimpl;

import com.example.bookservice.dto.BookRequestDTO;
import com.example.bookservice.dto.BookResponseDTO;
import com.example.bookservice.exception.BookNotFoundException;
import com.example.bookservice.model.Book;
import com.example.bookservice.repository.BookRepository;
import com.example.bookservice.service.BookService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BookServiceImpl implements BookService {

    private final BookRepository bookRepository;
    private final ModelMapper modelMapper;

    @Override
    public BookResponseDTO createBook(BookRequestDTO requestDTO) {
        Book book = modelMapper.map(requestDTO, Book.class);
        Book savedBook = bookRepository.save(book);
        return modelMapper.map(savedBook, BookResponseDTO.class);
    }

    @Override
    public BookResponseDTO getBookById(Long id) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        return modelMapper.map(book, BookResponseDTO.class);
    }

    @Override
    public List<BookResponseDTO> getAllBooks() {
        return bookRepository.findAll().stream()
                .map(book -> modelMapper.map(book, BookResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public BookResponseDTO updateBook(Long id, BookRequestDTO requestDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        book.setTitle(requestDTO.getTitle());
        book.setAuthor(requestDTO.getAuthor());
        book.setPrice(requestDTO.getPrice());

        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookResponseDTO.class);
    }

    @Override
    public BookResponseDTO patchBook(Long id, BookRequestDTO requestDTO) {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);

        if (requestDTO.getTitle() != null) {
            book.setTitle(requestDTO.getTitle());
        }
        if (requestDTO.getAuthor() != null) {
            book.setAuthor(requestDTO.getAuthor());
        }
        if (requestDTO.getPrice() != null) {
            book.setPrice(requestDTO.getPrice());
        }

        Book updatedBook = bookRepository.save(book);
        return modelMapper.map(updatedBook, BookResponseDTO.class);
    }

    @Override
    public void deleteBook(Long id) {
        if (!bookRepository.existsById(id)) {
            throw new BookNotFoundException();
        }
        bookRepository.deleteById(id);
    }
}
