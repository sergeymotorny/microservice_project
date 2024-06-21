package com.motorny.repositories;

import com.motorny.dto.PopularBookViewDto;

import java.util.List;

public interface CustomBookRepository {

    List<PopularBookViewDto> findMostPopularBooksForReadersUnderAge(Integer age, Integer outputLimit);
}
