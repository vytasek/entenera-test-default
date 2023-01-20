package com.etnetera.hr.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

/**
 * Simple REST controller for accessing application logic.
 *
 * @author Etnetera
 */
@RestController
@RequestMapping("/frameworks")
public class JavaScriptFrameworkController {

    private final JavaScriptFrameworkRepository repository;

    @Autowired
    public JavaScriptFrameworkController(JavaScriptFrameworkRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/")
    public Iterable<JavaScriptFramework> frameworks() {
        return repository.findAll();
    }

    @GetMapping("/get-by-name")
    public List<JavaScriptFramework> getByName(String name) {
        return repository.findByName(name);
    }

    @GetMapping("/find-by-name")
    public List<JavaScriptFramework> findByName(String name) {
        return repository.findByNameLike("%" + name + "%");
    }

    @GetMapping("/get-by-name-and-version")
    public JavaScriptFramework getByNameAndVersion(String name, String version) {
        return repository.findOneByNameAndVersion(name, version).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/save")
    public JavaScriptFramework save(JavaScriptFramework javaScriptFramework) {
        Optional<JavaScriptFramework> existing = repository.findOneByNameAndVersion(javaScriptFramework.getName(), javaScriptFramework.getVersion());
        existing.ifPresent(framework -> javaScriptFramework.setId(framework.getId()));
        return repository.save(javaScriptFramework);
    }

    @DeleteMapping("/delete-by-id")
    public void deleteById(long javaScriptFrameworkId) {
        repository.deleteById(javaScriptFrameworkId);
    }

}
