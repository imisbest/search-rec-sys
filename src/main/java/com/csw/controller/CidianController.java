package com.csw.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("d")
public class CidianController {
    @RequestMapping("qac")
    @ResponseBody
    public List<String> qa() throws IOException {
        List<String> list = new ArrayList<>();
        Reader r = new FileReader("D:\\IdeaProjects\\RUN\\search-rec-sys\\src\\main\\webapp\\init.text");
        BufferedReader br = new BufferedReader(r);
        while (true) {
            String str = br.readLine();//读取一行文字,达到文件结尾返回null
            if (str == null) break;
            list.add(str);
            System.out.println(str);
        }
        System.out.println("list;;" + list);
        r.close();
       return list;
    }

    @RequestMapping("add")
    @ResponseBody
    public void add(String tag) throws IOException {
        List<String> list = new ArrayList<>();
        Reader r = new FileReader("D:\\IdeaProjects\\RUN\\search-rec-sys\\src\\main\\webapp\\init.text");
        BufferedReader br = new BufferedReader(r);
        while (true) {
            String str = br.readLine();//读取一行文字,达到文件结尾返回null
            if (str == null) break;
                list.add(str);
            System.out.println(str);
        }
        list.add(tag);//
        System.out.println("list;;" + list);

        Writer w = new FileWriter("D:\\IdeaProjects\\RUN\\search-rec-sys\\src\\main\\webapp\\init.text");
        BufferedWriter bw = new BufferedWriter(w);
        for (int i = 0; i < list.size(); i++) {
            bw.write(list.get(i));//\n表示newLine \r 表示return \r\n   \r  \n
            bw.newLine();//换行
        }
        r.close();
        bw.close();
    }

    @RequestMapping("remove")
    @ResponseBody
    public void remove(String removeword) throws IOException {
        System.out.println("removeword;;"+removeword);
        List<String> list = new ArrayList<>();
        Reader r = new FileReader("D:\\IdeaProjects\\RUN\\search-rec-sys\\src\\main\\webapp\\init.text");
        BufferedReader br = new BufferedReader(r);
        while (true) {
            String str = br.readLine();//读取一行文字,达到文件结尾返回null
            if (str == null) break;
            System.out.println("yanzheng;;"+str.equals(removeword));
            if (!str.equals(removeword)) {
                list.add(str);
            }
            System.out.println(str);
        }
        System.out.println("list1;;" + list);

        //
        Writer w = new FileWriter("D:\\IdeaProjects\\RUN\\search-rec-sys\\src\\main\\webapp\\init.text");
        BufferedWriter bw = new BufferedWriter(w);
        for (int i = 0; i < list.size(); i++) {
            bw.write(list.get(i));//\n表示newLine \r 表示return \r\n   \r  \n
            bw.newLine();//换行
        }
        r.close();
        bw.close();
    }
}
