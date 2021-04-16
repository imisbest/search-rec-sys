package com.csw.service;

import com.csw.entity.Poem;

import java.util.List;

public interface PoemService {
    List<Poem> findSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows);

    Long findTotalCountsSearch(String searchField, String searchString, String searchOper);

    List<Poem> findAll(Integer page, Integer rows);

    Long findTotalCounts();

    void save(Poem poem);

    void update(Poem poem);

    void delete(String id);

    List<Poem> findAll2();
}
