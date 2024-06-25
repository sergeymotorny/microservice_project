package com.motorny.repositories;

import com.motorny.models.Book;
import com.motorny.models.projection.TitleBookView;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BookRepository extends JpaRepository<Book, Long>, BookRepositoryCustom {

    @Query(value = """
        SELECT NEW com.motorny.models.projection.TitleBookView(
            u.fullName,
            b.title)
        FROM Book b
        INNER JOIN b.users u
        WHERE u.id = ?1
        """)
    List<TitleBookView> findAllBooksByUserId(@Param("userId") Long userId);
}
