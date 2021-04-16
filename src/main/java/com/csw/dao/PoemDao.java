package com.csw.dao;

import com.csw.entity.Poem;

import java.util.List;

public interface PoemDao{
    Long findTotalCountsSearch(String searchField, String searchString, String searchOper);

    List<Poem> findSearch(String searchField, String searchString, String searchOper, int start, Integer rows);

    void delete(String id);

    void update(Poem poem);

    void save1(Poem poem);

    Long findTotalCounts();

    List<Poem> findByPage(int start, Integer rows);

    List<Poem> findAll();

    List<Poem> findAll2();
}
