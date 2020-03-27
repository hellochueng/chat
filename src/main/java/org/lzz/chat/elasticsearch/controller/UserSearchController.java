package org.lzz.chat.elasticsearch.controller;

import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.lucene.search.function.FunctionScoreQuery;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.functionscore.FunctionScoreQueryBuilder;
import org.elasticsearch.index.query.functionscore.ScoreFunctionBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.lzz.chat.elasticsearch.entity.GoodsInfo;
import org.lzz.chat.elasticsearch.entity.User;
import org.lzz.chat.elasticsearch.service.GoodsRepository;
import org.lzz.chat.elasticsearch.service.UserRepository;
import org.lzz.chat.util.RandomValue;
import org.lzz.chat.util.SnowFlakeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.data.elasticsearch.core.SearchResultMapper;
import org.springframework.data.elasticsearch.core.aggregation.AggregatedPage;
import org.springframework.data.elasticsearch.core.aggregation.impl.AggregatedPageImpl;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.data.elasticsearch.core.query.SearchQuery;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("userSearch")
public class UserSearchController {

    //每页数量
    private Integer PAGESIZE=10;
    @Autowired private SnowFlakeGenerator snowFlakeGenerator;
    @Autowired private UserRepository userRepository;
    @Autowired private ElasticsearchTemplate elasticsearchTemplate;

    //http://localhost:8888/save
    @GetMapping("save")
    public String save(){
        for (int j = 0; j < 500000; j++) {
            User user = new User(snowFlakeGenerator.generateLongId(), RandomValue.getTel(), "123456", RandomValue.getChineseName(), RandomValue.getEmail(8, 20), RandomValue.name_sex);
            userRepository.save(user);
        }
        return "success";
    }

    //http://localhost:8888/delete?id=1525415333329
    @GetMapping("delete")
    public String delete(Long id){
        User user = new User();
        user.setId(id);
        userRepository.delete(user);
        return "success";
    }

    //http://localhost:8888/update?id=1525417362754&name=修改&description=修改
    @GetMapping("update")
    public String update(long id,String name,String description){
        return "success";
    }

    //http://localhost:8888/getOne?id=1525417362754
    @GetMapping("getOne")
    public User getOne(long id){
        Optional<User> user = userRepository.findById(id);
        return user.get();
    }


    //http://localhost:8888/getGoodsList?query=商品
    //http://localhost:8888/getGoodsList?query=商品&pageNumber=1
    //根据关键字"商品"去查询列表，name或者description包含的都查询
    @GetMapping("getUserPageList")
    public List<User> getList(Integer pageNumber,String query){
        if(pageNumber==null) pageNumber = 0;
        //es搜索默认第一页页码是0
        SearchQuery searchQuery=getEntitySearchQuery(pageNumber,PAGESIZE,query);
        Page<User> users = userRepository.search(searchQuery);
        return users.getContent();
    }


    private SearchQuery getEntitySearchQuery(int pageNumber, int pageSize, String searchContent) {

        FunctionScoreQueryBuilder.FilterFunctionBuilder[] filterFunctionBuilder = new FunctionScoreQueryBuilder.FilterFunctionBuilder[2];

        filterFunctionBuilder[0] = new FunctionScoreQueryBuilder.FilterFunctionBuilder(QueryBuilders.matchPhraseQuery("user_name", searchContent),ScoreFunctionBuilders.weightFactorFunction(100));

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

    @GetMapping("getUserList")
    public List<User> getProductList(String query){

        QueryBuilder queryBuilder = QueryBuilders.boolQuery().should(QueryBuilders.matchQuery("user_name",query));

        Page<User> users = userRepository.search(new NativeSearchQueryBuilder().withQuery(queryBuilder).build());

        return users.getContent();
    }

    @GetMapping("getUserListHit")
    public List<User> getProductListHit(Integer index,String query){

        String field = "user_name";

        QueryBuilder queryBuilder = QueryBuilders.boolQuery()
                .should(QueryBuilders.matchQuery("user_name",query))
                .should(QueryBuilders.rangeQuery("pwd").gte(1));

        SearchQuery searchQuery = new NativeSearchQueryBuilder().withQuery(queryBuilder)
                .withPageable(PageRequest.of(index,PAGESIZE))
                .withHighlightFields(new HighlightBuilder.Field("user_name").preTags("<h2>").postTags("</h2>"))
                .build();
        Page<User> users = elasticsearchTemplate.queryForPage(searchQuery, User.class, new SearchResultMapper() {

            @Override
            public <T> AggregatedPage<T> mapResults(SearchResponse response, Class<T> clazz, Pageable pageable) {

                ArrayList<User> goodsInfos = new ArrayList<User>();
                SearchHits hits = response.getHits();
                for (SearchHit searchHit : hits) {
                    if (hits.getHits().length <= 0) {
                        return null;
                    }
                    User user = new User();
                    String highLightMessage = searchHit.getHighlightFields().get(field).fragments()[0].toString();
                    user.setId(Long.parseLong(searchHit.getId()));
                    user.setSex(String.valueOf(searchHit.getSourceAsMap().get("sex")));
                    user.setEmail(String.valueOf(searchHit.getSourceAsMap().get("email")));
                    user.setUsername(String.valueOf(searchHit.getSourceAsMap().get("username")));
                    // 反射调用set方法将高亮内容设置进去
                    try {
                        String setMethodName = parSetName(field);
                        Class<? extends User> poemClazz = user.getClass();
                        Method setMethod = poemClazz.getMethod(setMethodName, String.class);
                        setMethod.invoke(user, highLightMessage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    goodsInfos.add(user);
                }
                if (goodsInfos.size() > 0) {
                    return new AggregatedPageImpl<T>((List<T>) goodsInfos);
                }
                return null;
            }
        });

        return users.getContent();
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