package com.motorny.repositories;

import com.motorny.models.Book;
import com.motorny.models.projection.BookProjection;
import org.hibernate.query.spi.Limit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    @Query(value = """
        SELECT NEW com.motorny.models.projection.BookProjection(
            u.fullName,
            b.title)
        FROM Book b
        INNER JOIN b.users u
        WHERE u.id = ?1
        """)
    List<BookProjection> findAllBooksByUserId(@Param("userId") Long userId);

@Query(value = """
        SELECT u.full_name, u.age, b.title
            FROM Book b
            INNER JOIN record r on b.id = r.book_id
            INNER JOIN _user u ON r.user_id = u.id
            WHERE u.age < :age
            GROUP BY u.full_name, u.age, b.title
            ORDER BY u.age
            LIMIT :outputLimit
        """, nativeQuery = true)
    List<Object[]> findTop3MostPopularBooksForReadersUnder10(@Param("age") Integer age,
                                                             @Param("outputLimit") Integer outputLimit);
}
