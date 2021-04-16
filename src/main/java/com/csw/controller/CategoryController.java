package com.csw.controller;

import com.csw.entity.Category;
import com.csw.service.CategoryService;
import com.csw.service.PoemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("c")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    PoemService poemService;

    @RequestMapping("qa")
    @ResponseBody
    public List<Category> qa() {
        List<Category> categories = categoryService.qa();
        categories.forEach(dept -> System.out.println("qa/dept/" + dept));
        return categories;
    }
}
