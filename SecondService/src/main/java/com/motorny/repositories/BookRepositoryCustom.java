package com.motorny.repositories;

import com.motorny.models.projection.PopularBookView;

import java.util.List;

public interface BookRepositoryCustom {

    List<PopularBookView> mostPopularBooksForReadersUnderAge(Integer age, Integer outputLimit);
}
