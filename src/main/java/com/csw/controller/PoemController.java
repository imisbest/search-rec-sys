package com.csw.controller;

import com.csw.entity.Poem;
import com.csw.service.CategoryService;
import com.csw.service.PoemService;
import org.apache.commons.codec.binary.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Controller
@RequestMapping("p")
public class PoemController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    PoemService poemService;



    //处理编辑操作
    @RequestMapping("edit")
    @ResponseBody
    public void edit(Poem poem, String oper) {
        System.out.println("EEEEEEEEEEEEEEEEpoem//" + poem);
        System.out.println("EEEEEEEEEEEEEEEEEEEoper//" + oper);

        if (StringUtils.equals("add", oper)) {
            String rrer = UUID.randomUUID().toString();
            /**
             * 添加员工
             */
            System.out.println("id rrer//" + rrer);
            poem.setId(rrer);
            poem.setCategoryId(poem.getCategoryId());//////////////////
            poemService.save(poem);

        }
        //处理修改
        if (StringUtils.equals("edit", oper)) {
            poem.setCategoryId(poem.getCategoryId());
            poemService.update(poem);

        }
        //处理删除
        if (StringUtils.equals("del", oper)) {
            poemService.delete(poem.getId());

        }
    }

    //查询所有
    @RequestMapping("findAll")
    @ResponseBody  //jackson  gson  //jqgrid  table 分页  key  total 总页数  page 当前页  rows:当前页数据集  records 总记录数
    public Map<String, Object> findAll(String searchField, String searchString, String searchOper, Integer page, Integer rows, Boolean _search) {
        System.out.println("searchField/" + searchField);
        System.out.println("searchString/" + searchString);
        System.out.println("searchOper/" + searchOper);
        System.out.println("page/" + page);
        System.out.println("rows/" + rows);
        System.out.println("_search/" + _search);
        //不做搜索处理
        Map<String, Object> map = new HashMap<>();
        List<Poem> lists = null;
        Long totalCounts = null;
        if (_search) {
            System.out.println("进入1");
            //根据搜索条件查询集合
            lists = poemService.findSearch(searchField, searchString, searchOper, page, rows);
            lists.forEach(lists1 -> System.out.println("$$$$$$$$$$$$$$$$$$listsearch/" + lists1));
            //根据搜索条件查询总条数
            totalCounts = poemService.findTotalCountsSearch(searchField, searchString, searchOper);
        } else {
            //进行分页查询
            lists = poemService.findAll(page, rows);
            lists.forEach(lists1 -> System.out.println("lists/" + lists1));
            //获取总记录数
            totalCounts = poemService.findTotalCounts();
        }
        //公共代码
        Long totalPage = totalCounts % rows == 0 ? totalCounts / rows : totalCounts / rows + 1;

        System.out.println("rows数据//" + lists);
        map.put("rows", lists);

        System.out.println("total总页数//" + totalPage);
        map.put("total", totalPage);

        System.out.println("page当前页//" + page);
        map.put("page", page);

        System.out.println("records总条数//" + totalCounts);
        map.put("records", totalCounts);

        return map;
    }
}
