package com.motorny.repositories;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = CustomBookRepository.class)
class CustomBookRepositoryTest {

    @Autowired
    private CustomBookRepository customBookRepository;

    public void test() {

    }

}