package com.etnetera.hr.repository;

import org.springframework.data.repository.CrudRepository;

import com.etnetera.hr.data.JavaScriptFramework;

import java.util.List;
import java.util.Optional;

/**
 * Spring data repository interface used for accessing the data in database.
 * 
 * @author Etnetera
 *
 */
public interface JavaScriptFrameworkRepository extends CrudRepository<JavaScriptFramework, Long> {

    List<JavaScriptFramework> findByName(String name);
    List<JavaScriptFramework> findByNameLike(String name);
    Optional<JavaScriptFramework> findOneByNameAndVersion(String name, String version);

}
