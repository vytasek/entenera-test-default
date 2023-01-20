package com.etnetera.hr;

import com.etnetera.hr.controller.JavaScriptFrameworkController;
import com.etnetera.hr.data.JavaScriptFramework;
import com.etnetera.hr.enums.HypeLevelEnum;
import com.etnetera.hr.repository.JavaScriptFrameworkRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.server.ResponseStatusException;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Class used for Spring Boot/MVC based tests.
 *
 * @author Etnetera
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class JavaScriptFrameworkTests {

    @Resource
    private JavaScriptFrameworkRepository javaScriptFrameworkRepository;
    @Autowired
    JavaScriptFrameworkController javaScriptFrameworkController;

    public static String FRAMEWORK1NAME = "framework1";
    public static String FRAMEWORK1VERSION = "1";


    @Before
    public void setup() {
        JavaScriptFramework framework1 = new JavaScriptFramework(FRAMEWORK1NAME, FRAMEWORK1VERSION, LocalDate.now(), HypeLevelEnum.MEDIUM);
        javaScriptFrameworkController.save(framework1);
    }

    @After
    public void cleanUp() {
        javaScriptFrameworkRepository.deleteAll();
    }

    @Test
    public void testFrameworkCreation() {
        JavaScriptFramework framework2 = new JavaScriptFramework("framework2", "0.1", LocalDate.now(), HypeLevelEnum.MEDIUM);
        javaScriptFrameworkController.save(framework2);

        JavaScriptFramework javaScriptFramework = javaScriptFrameworkRepository.findById(framework2.getId()).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertEquals(framework2, javaScriptFramework);
        assertEquals(2, javaScriptFrameworkController.frameworks().spliterator().getExactSizeIfKnown());
    }

    @Test
    public void testNoDuplicatedCreated() {
        JavaScriptFramework framework = new JavaScriptFramework(FRAMEWORK1NAME, FRAMEWORK1VERSION, LocalDate.now(), HypeLevelEnum.LOW);
        javaScriptFrameworkController.save(framework);

        assertEquals(1, javaScriptFrameworkRepository.findByName(FRAMEWORK1NAME).size());
    }

    @Test
    public void testSameNameDifferentVersions() {
        JavaScriptFramework framework = new JavaScriptFramework(FRAMEWORK1NAME, "2", LocalDate.of(2025, 6, 6), HypeLevelEnum.EXTREME);
        javaScriptFrameworkController.save(framework);

        assertEquals(2, javaScriptFrameworkController.getByName(FRAMEWORK1NAME).size());
    }

    @Test
    public void testFrameworkRename(){
        final String newName = "new name";
        JavaScriptFramework framework = javaScriptFrameworkController.getByNameAndVersion(FRAMEWORK1NAME, FRAMEWORK1VERSION);
        framework.setName(newName);
        javaScriptFrameworkController.save(framework);

        JavaScriptFramework renamed = javaScriptFrameworkRepository.findById(framework.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertEquals(newName, renamed.getName()) ;
    }
    @Test
    public void testFrameworkUpdate() {
        final HypeLevelEnum hypeLevel = HypeLevelEnum.LOW;
        final LocalDate deprecationDate = LocalDate.of(2023, 1, 10);

        JavaScriptFramework framework = javaScriptFrameworkController.getByNameAndVersion(FRAMEWORK1NAME, FRAMEWORK1VERSION);
        framework.setHypeLevel(hypeLevel);
        framework.setDeprecationDate(deprecationDate);
        javaScriptFrameworkController.save(framework);

        JavaScriptFramework javaScriptFramework = javaScriptFrameworkRepository.findById(framework.getId()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        assertEquals(hypeLevel, javaScriptFramework.getHypeLevel());
        assertEquals(deprecationDate, javaScriptFramework.getDeprecationDate());
    }

    @Test
    public void testFrameworkDelete() {
        JavaScriptFramework framework = javaScriptFrameworkController.getByNameAndVersion(FRAMEWORK1NAME, FRAMEWORK1VERSION);
        javaScriptFrameworkController.deleteById(framework.getId());

        assertEquals(0, javaScriptFrameworkRepository.findByName(FRAMEWORK1NAME).size());
    }

    @Test
    public void testFrameworkSearch(){
        javaScriptFrameworkRepository.save(new JavaScriptFramework("keyword at begining", "1", LocalDate.of(2025, 6, 6), HypeLevelEnum.MEDIUM));
        javaScriptFrameworkRepository.save(new JavaScriptFramework("ending keyword", "1", LocalDate.of(2025, 6, 6), HypeLevelEnum.MEDIUM));
        javaScriptFrameworkRepository.save(new JavaScriptFramework("middle keyword sentence", "1", LocalDate.of(2025, 6, 6), HypeLevelEnum.MEDIUM));

        List<JavaScriptFramework> foundFrameworks = javaScriptFrameworkController.findByName("keyword");
        assertEquals(3,foundFrameworks.size());
    }

    @Test
    public void checkNull(){
        JavaScriptFramework framework = new JavaScriptFramework("framework", "A",null, null);
        javaScriptFrameworkRepository.save(framework);

        JavaScriptFramework created = javaScriptFrameworkController.getByNameAndVersion("framework","A");
        assertEquals(framework, created);
    }

}
