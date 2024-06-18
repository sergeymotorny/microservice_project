package com.motorny.repositories;

import com.motorny.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    //List<Book> findTop3MostPopularBooksForReadersUnder10(Long id);

    //@Query("SELECT ")
    //List<Book> findAllByIdOrderByUsers(Long id);
}
