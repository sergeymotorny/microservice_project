package com.motorny.repositories.impl;

import com.motorny.models.projection.PopularBookView;
import com.motorny.repositories.BookRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import java.util.List;

public class BookRepositoryCustomImpl implements BookRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @SuppressWarnings("unchecked")
    public List<PopularBookView> mostPopularBooksForReadersUnderAge(Integer age, Integer limit) {

        return entityManager
                .createNamedQuery("PopularBookView.findBooksForUserUnderAge")
                .setParameter(1, age)
                .setParameter(2, limit)
                .getResultList();
    }
}
