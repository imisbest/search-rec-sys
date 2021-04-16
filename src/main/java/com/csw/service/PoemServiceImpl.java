package com.csw.service;

import com.csw.dao.PoemDao;
import com.csw.entity.Poem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
/* @Transactional(propagation = Propagation.SUPPORTS)*/
@Service
@Transactional
public class PoemServiceImpl implements PoemService {
    @Autowired
    private PoemDao poemDao;

    public PoemServiceImpl(PoemDao poemDao) {
        this.poemDao = poemDao;
    }

    public Long findTotalCountsSearch(String searchField, String searchString, String searchOper) {
        if (searchField.equals("category.id")){
            searchField="c.name";
            System.out.println("service searchField1;;"+searchField);
        }else{
            searchField="p."+searchField;
            System.out.println("service searchField2;;"+searchField);
        }
        return poemDao.findTotalCountsSearch(searchField,searchString,searchOper);
    }

    @Override
    public List<Poem> findSearch(String searchField, String searchString, String searchOper, Integer page, Integer rows) {
//        if (searchField.equals("category.id")){
//            searchField="c.name";
//        }else{
            searchField="p."+searchField;
       // }

        System.out.println("!!searchoper;;"+searchOper);
        System.out.println("!!searchString;;"+searchString);
        System.out.println("!!service searchField0;;"+searchField);
        int start = (page-1)*rows;
        System.out.println("!!start;;"+start);
        System.out.println("!!rows;;"+rows);
        return poemDao.findSearch(searchField,searchString,searchOper,start,rows);
    }

    @Override
    public void delete(String id) {
        poemDao.delete(id);
    }

    @Override
    public List<Poem> findAll2() {
        return poemDao.findAll2();
    }

    @Override
    public void update(Poem Poem) {
        poemDao.update(Poem);
    }

    @Override
    public void save(Poem Poem) {
        poemDao.save1(Poem);
    }

    @Override
    public Long findTotalCounts() {
        return poemDao.findTotalCounts();
    }

    @Override
    public List<Poem> findAll(Integer page, Integer rows) {
        System.out.println("service page//SSSSSSSSSSS//"+page);
        System.out.println("service rows//SSSSSSSSSSS//"+rows);
        int start = (page-1)*rows;
        System.out.println("service stars//SSSSSSSSSSS//"+start);
        return poemDao.findByPage(start,rows);
    }
}
