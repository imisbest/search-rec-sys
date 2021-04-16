package com.csw.controller;

import com.csw.dao.PoemRepository;
import com.csw.entity.Poem;
import com.csw.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("do")
public class documentController {
    @Autowired
    private PoemRepository poemRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;
    @Autowired
    private PoemService poemService;

    @RequestMapping("inputclear")
    @ResponseBody
    public void inputclear() {
        poemRepository.deleteAll();
    }

    @RequestMapping("inputreload")
    @ResponseBody
    public void inputreload(){
        List<Poem> poemList=poemService.findAll2();
        poemRepository.saveAll(poemList);
       System.out.println("poemlist;;"+poemList);
//        for (Poem poem : poemList) {
//            poemRepository.save(poem);
//        }
        /*for (int i = 0; i < poemList.size(); i++) {
            System.out.println(poemList.get(i));
            poemRepository.save(poemList.get(i));
        }*/
    }
}
