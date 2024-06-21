package com.motorny.repositories;

import com.motorny.dto.BookProjectionDto;
import com.motorny.models.projection.BookProjection;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomBookRepository {

    List<BookProjectionDto> findMostPopularBooksForReadersUnderAge(@Param("age") Integer age,
                                                                   @Param("outputLimit") Integer outputLimit);
}
