package com.motorny.services.impl;

import com.motorny.dto.PopularBookViewDto;
import com.motorny.mappers.BookMapper;
import com.motorny.models.projection.PopularBookView;
import com.motorny.repositories.CustomBookRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class CustomBookRepositoryImpl implements CustomBookRepository {

    private final BookMapper bookMapper;

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<PopularBookViewDto> findMostPopularBooksForReadersUnderAge(Integer age, Integer outputLimit) {

        @SuppressWarnings("unchecked")
        List<PopularBookView> resultList = entityManager
                .createNamedQuery("BookProjection.findBooksForUserUnderAge")
                .setParameter(1, age)
                .setParameter(2, outputLimit)
                .getResultList();

        return resultList.stream()
                .map(bookMapper::toPopularBookViewDto)
                .collect(Collectors.toList());
    }
}
