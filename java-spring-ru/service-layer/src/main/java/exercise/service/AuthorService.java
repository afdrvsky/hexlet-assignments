package exercise.service;

import exercise.dto.AuthorCreateDTO;
import exercise.dto.AuthorDTO;
import exercise.dto.AuthorUpdateDTO;
import exercise.exception.ResourceNotFoundException;
import exercise.mapper.AuthorMapper;
import exercise.repository.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Service
public class AuthorService {
    // BEGIN
    @Autowired
    private AuthorRepository authorRepository;

    @Autowired
    private AuthorMapper authorMapper;

    public List<AuthorDTO> getAllAuthors() {
        var authors = authorRepository.findAll();
        return authors.stream().map(authorMapper::map).toList();
    }

    public AuthorDTO getAuthorById(Long id) {
        var author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        return authorMapper.map(author);
    }

    public AuthorDTO createAuthor(AuthorCreateDTO authorCreateDTO) {
        var author = authorMapper.map(authorCreateDTO);
        authorRepository.save(author);
        return authorMapper.map(author);
    }

    public AuthorDTO updateAuthor(Long id, AuthorUpdateDTO authorUpdateDTO) {
        var author = authorRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Author not found"));
        authorMapper.update(authorUpdateDTO, author);
        return authorMapper.map(authorRepository.save(author));
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
    // END
}
