package com.csw.controller;

import com.csw.entity.Poem;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightField;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("h")
public class HotWordsController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;
    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    @RequestMapping("hotwords")
    @ResponseBody
    public List<Poem> hotwords(String hotwords) {
        System.out.println("hotwords;;" + hotwords);
        HashOperations<String, Object, Object> ss = stringRedisTemplate.opsForHash();
        String o = (String) ss.get("maps", hotwords);
        if (o == null) {
            ss.put("maps", hotwords, "1");
        } else {
            Double o1 = Double.parseDouble(o) + 1;
            ss.put("maps", hotwords, o1.toString());
        }
        /**
         *
         */
        List<Poem> list = new ArrayList<>();
        HighlightBuilder.Field field = new HighlightBuilder.Field("*")
                .requireFieldMatch(false)//关闭检索字段匹配
                .preTags("<span style='color:red'>")
                .postTags("</span>");
        SearchQuery searchQuery = new NativeSearchQueryBuilder()
                .withIndices("poem")
                .withTypes("poem")
                .withQuery(QueryBuilders.queryStringQuery(hotwords).field("name").field("content").field("author").field("type").field("authordes"))
                .withHighlightFields(field)
                .build();
        AggregatedPage<Poem> poems = elasticsearchTemplate.queryForPage(searchQuery, Poem.class, new SearchResultMapper() {
            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse searchResponse, Class<T> aClass, Pageable pageable) {
                List<Poem> poems = new ArrayList<>();
                SearchHit[] hits = searchResponse.getHits().getHits();
                for (SearchHit hit : hits) {
                    Poem poem = new Poem();
                    Map<String, Object> sourceAsMap = hit.getSourceAsMap();
                    Map<String, HighlightField> highlightFields = hit.getHighlightFields();
                    poem.setId(sourceAsMap.get("id").toString());
                    poem.setName(sourceAsMap.get("name").toString());
                    poem.setType(sourceAsMap.get("type").toString());
                    if (highlightFields.containsKey("name")) {
                        poem.setName(highlightFields.get("name").fragments()[0].toString());
                    }
                    poem.setContent(sourceAsMap.get("content").toString());
                    if (highlightFields.containsKey("content")) {
                        poem.setContent(highlightFields.get("content").fragments()[0].toString());
                    }
                    poem.setAuthor(sourceAsMap.get("author").toString());
                    poem.setAuthordes(sourceAsMap.get("authordes").toString());
                    if (highlightFields.containsKey("authordes")) {
                        poem.setAuthordes(highlightFields.get("authordes").fragments()[0].toString());
                    }
                    list.add(poem);
                    poems.add(poem);
                }
                return new AggregatedPageImpl<T>((List<T>) poems);
            }
        });
        return list;
    }

    @RequestMapping("add")
    @ResponseBody
    public Map<Object, Object> qa() {
        HashOperations<String, Object, Object> ss = stringRedisTemplate.opsForHash();

        return ss.entries("maps");
        /*Map<Object,Object> map=new  LinkedHashMap<>();
        ZSetOperations<String,String> zset=stringRedisTemplate.opsForZSet();
        Set<String> set=zset.reverseRange("zset",0,-1);
        for (String s : set) {
            map.put(s,zset.score("zset",s));

        }
        System.out.println("map;;///////"+map);
        return map;*/
    }

}
