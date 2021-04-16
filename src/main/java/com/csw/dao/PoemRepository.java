package com.csw.dao;

import com.csw.entity.Poem;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

/**
 * Created by HIAPAD on 2019/11/20.启动报错把库删了
 */
//使用自定义接口继承ElasticsearchRepository  泛型:当前操作对象类型  泛型:对象中主键类型
public interface PoemRepository extends ElasticsearchRepository<Poem,String> {

}
