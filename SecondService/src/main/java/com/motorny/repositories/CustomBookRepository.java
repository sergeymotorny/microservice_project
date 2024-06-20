package com.motorny.repositories;

import com.motorny.dto.BookProjectionDto;
import com.motorny.models.projection.BookProjection;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomBookRepository {

//    @Query(value = """
//        SELECT u.full_name, u.age, b.title
//            FROM Book b
//            INNER JOIN record r on b.id = r.book_id
//            INNER JOIN _user u ON r.user_id = u.id
//            WHERE u.age < :age
//            GROUP BY u.full_name, u.age, b.title
//            ORDER BY u.age
//            LIMIT :outputLimit
//        """, nativeQuery = true)

    List<BookProjectionDto> findMostPopularBooksForReadersUnderAge(@Param("age") Integer age,
                                                                   @Param("outputLimit") Integer outputLimit);
}
