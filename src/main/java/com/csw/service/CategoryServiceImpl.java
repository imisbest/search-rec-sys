package com.csw.service;

import com.csw.dao.Category1Dao;
import com.csw.entity.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
/* @Transactional(propagation = Propagation.SUPPORTS)*/
public class CategoryServiceImpl implements CategoryService{
    @Autowired
    private Category1Dao category1Dao;

    @Override
    public List<Category> qa() {
        return category1Dao.queryAll();
    }
}
