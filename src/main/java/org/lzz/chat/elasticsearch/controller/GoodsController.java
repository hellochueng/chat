package org.lzz.chat.elasticsearch.controller;

import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MultiMatchQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.lzz.chat.elasticsearch.entity.GoodsInfo;
import org.lzz.chat.elasticsearch.service.GoodsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Description
 * @project springboot_learn
 * @package com.dalaoyang.controller
 * @email yangyang@dalaoyang.cn
 * @date 2018/5/4
 */
@RestController
public class GoodsController {

    //每页数量
    private Integer PAGESIZE=10;

    @Autowired
    private GoodsRepository goodsRepository;

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;

    //http://localhost:8888/save
    @GetMapping("save")
    public String save(String name,String description){
        GoodsInfo goodsInfo = new GoodsInfo(System.currentTimeMillis(),name,description);
        goodsRepository.save(goodsInfo);
        return "success";
    }

    //http://localhost:8888/delete?id=1525415333329
    @GetMapping("delete")
    public String delete(Long id){
        GoodsInfo goodsInfo = new GoodsInfo();
        goodsInfo.setId(id);
        goodsRepository.delete(goodsInfo);
        return "success";
    }

    //http://localhost:8888/update?id=1525417362754&name=修改&description=修改
    @GetMapping("update")
    public String update(long id,String name,String description){
        GoodsInfo goodsInfo = new GoodsInfo(id,name,description);
        goodsRepository.save(goodsInfo);
        return "success";
    }

    //http://localhost:8888/getOne?id=1525417362754
    @GetMapping("getOne")
    public GoodsInfo getOne(long id){
        Optional<GoodsInfo> goodsInfo = goodsRepository.findById(id);
        return goodsInfo.get();
    }


    //http://localhost:8888/getGoodsList?query=商品
    //http://localhost:8888/getGoodsList?query=商品&pageNumber=1
    //根据关键字"商品"去查询列表，name或者description包含的都查询
    @GetMapping("getGoodsList")
    public List<GoodsInfo> getList(Integer pageNumber,String query){
        if(pageNumber==null) pageNumber = 0;
        //es搜索默认第一页页码是0
        SearchQuery searchQuery=getEntitySearchQuery(pageNumber,PAGESIZE,query);
        Page<GoodsInfo> goodsPage = goodsRepository.search(searchQuery);
        return goodsPage.getContent();
    }


    private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {

        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilder = new FunctionScoreQueryBuilder.FilterFunctionBuilder[2];

        filterFunctionBuilder[0] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchPhraseQuery("name", searchContent),ScoreFunctionBuilders.weightFactorFunction(100));
        filterFunctionBuilder[1] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchPhraseQuery("description", searchContent),ScoreFunctionBuilders.weightFactorFunction(100));

        FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(filterFunctionBuilder)
                //设置权重分 求和模式
                .scoreMode(FunctionScoreQuery.ScoreMode.SUM)
                //设置权重分最低分
                .setMinScore(10);

        // 设置分页
        Pageable pageable = new PageRequest(pageNumber, pageSize);
        return new NativeSearchQueryBuilder()
                .withPageable(pageable)
                .withQuery(functionScoreQueryBuilder).build();
    }

    @GetMapping("getProductList")
    public List<GoodsInfo> getProductList(String query){

        QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name",query)).should(QueryBuilders.matchQuery("description",query));

        Page<GoodsInfo> goodsPage = goodsRepository.search(new NativeSearchQueryBuilder().withQuery(queryBuilder).build());

        return goodsPage.getContent();
    }

    @GetMapping("getProductListHit")
    public List<GoodsInfo> getProductListHit(String query){

//        HighlightBuilder highlightBuilder=new HighlightBuilder();
//
//        highlightBuilder.preTags("<h2>");
//        highlightBuilder.postTags("</h2>");
//        highlightBuilder.field("name");

//        QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("name",query)).should(QueryBuilders.matchQuery("description",query));

        String field = "name";

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("name",query))
                .should(QueryBuilders.matchQuery("description",query));

//        highlightBuilder.highlightQuery(queryBuilder);

//        Page<GoodsInfo> goodsPage = goodsRepository.search(new NativeSearchQueryBuilder().withQuery(queryBuilder).
//                withHighlightFields(new HighlightBuilder.Field("name").postTags("\"<h2>\"").postTags("</h2>"))
////                withHighlightBuilder(highlightBuilder)
//                .build());

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder).
                withHighlightFields(new HighlightBuilder.Field("name").preTags("<h2>").postTags("</h2>"))
                .build();

        Page<GoodsInfo> goodsPage = elasticsearchTemplate.queryForPage(searchQuery, GoodsInfo.class, new SearchResultMapper() {

            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {

                ArrayList<GoodsInfo> goodsInfos = new ArrayList<GoodsInfo>();

                SearchHits hits = response.getHits();

                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    GoodsInfo goodsInfo = new GoodsInfo();
                    String highLightMessage = searchHit.getHighlightFields().get(field).fragments()[0].toString();
                    goodsInfo.setId(Long.parseLong(searchHit.getId()));
                    goodsInfo.setName(String.valueOf(searchHit.getSourceAsMap().get("name")));
                    goodsInfo.setDescription(String.valueOf(searchHit.getSourceAsMap().get("description")));
                    // 反射调用set方法将高亮内容设置进去
                    try {
                        String setMethodName = parSetName(field);
                        Class<? extends GoodsInfo> poemClazz = goodsInfo.getClass();
                        Method setMethod = poemClazz.getMethod(setMethodName, String.class);
                        setMethod.invoke(goodsInfo, highLightMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    goodsInfos.add(goodsInfo);
                }
                if (goodsInfos.size() > 0) {
                    return new AggregatedPageImpl<T>((List<T>) goodsInfos);
                }
                return null;
            }
        });

        return goodsPage.getContent();
    }

    /**
     * 拼接在某属性的 set方法
     *
     * @param fieldName
     * @return String
     */
    private static String parSetName(String fieldName) {
        if (null == fieldName || "".equals(fieldName)) {
            return null;
        }
        int startIndex = 0;
        if (fieldName.charAt(0) == '_')
            startIndex = 1;
        return "set" + fieldName.substring(startIndex, startIndex + 1).toUpperCase()
                + fieldName.substring(startIndex + 1);
    }
}