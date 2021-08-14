package com.hhp;

import com.alibaba.fastjson.JSON;
import com.hhp.pojo.User;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.bulk.BulkResponse;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.Client;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.MatchAllQueryBuilder;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.elasticsearch.search.fetch.subphase.highlight.HighlightBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@SpringBootTest
@RunWith(SpringRunner.class)
public class ElasticSearchApplicationTest {


    @Autowired
    private RestHighLevelClient restHighLevelClient;

    //创建索引
    @Test
    public void testCreateIndex() throws IOException {
        //1. 创建索引请求
        CreateIndexRequest request = new CreateIndexRequest("hhp_index");
        //2. 执行请求
        CreateIndexResponse createIndexResponse =
                restHighLevelClient.indices().create(request, RequestOptions.DEFAULT);
        //
        System.out.println(createIndexResponse);
    }

    //获取索引
    @Test
    public void testExistIndex() throws IOException {
        GetIndexRequest request = new GetIndexRequest("hhp");
        boolean exists = restHighLevelClient.indices().exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //删除索引
    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest request = new DeleteIndexRequest("hhp1");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(request, RequestOptions.DEFAULT);
        System.out.println(delete.isAcknowledged());
    }

    //创建文档
    @Test
    public void addDocument() throws IOException {
        //创建对象
        User user = new User("海鹏说", 4);
        //创建请求
        IndexRequest request = new IndexRequest("hhp_index");
        //规则
        request.id("1");
        request.timeout(TimeValue.timeValueSeconds(1));
        request.timeout("1s");
        //将数据放入请求，json
        request.source(JSON.toJSONString(user), XContentType.JSON);
        //客户端发送请求
        IndexResponse indexResponse = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        System.out.println(indexResponse.toString());
        System.out.println(indexResponse.status());
    }

    //文档是否存在
    @Test
    public void IsExistDocument() throws IOException {
        GetRequest request = new GetRequest("hhp_index", "1");
        //不获取返回的_source的上下文了
        request.fetchSourceContext(new FetchSourceContext(false));
        request.storedFields("_none_");

        boolean exists = restHighLevelClient.exists(request, RequestOptions.DEFAULT);
        System.out.println(exists);
    }

    //获取文档
    @Test
    public void getDocument() throws IOException {
        GetRequest request = new GetRequest("hhp_index", "1");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        //打印文档的内容
        System.out.println(response.getSourceAsString());
        System.out.println(response);
    }

    //更新文档
    @Test
    public void updateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("hhp_index", "1");
        //
        request.timeout("1s");
        //
        User user = new User("刘籽鑫", 18);
        request.doc(JSON.toJSONString(user), XContentType.JSON);
        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //删除文档
    @Test
    public void deleteDocument() throws IOException {
        DeleteRequest request = new DeleteRequest("hhp_index", "1");
        request.timeout("1s");

        DeleteResponse response = restHighLevelClient.delete(request, RequestOptions.DEFAULT);
        System.out.println(response.status());
    }

    //批量插入文档
    @Test
    public void bulkDocument() throws IOException {
        BulkRequest request = new BulkRequest();
        request.timeout("10s");
        List<User> users = new ArrayList<>();
        users.add(new User("hhp", 28));
        users.add(new User("liuzixin", 24));
        users.add(new User("hhp1", 28));
        users.add(new User("liuzixin1", 24));

        for (int i = 0; i < users.size(); i++) {
            request.add(
                    new IndexRequest("hhp_index")
                            .id("" + (i + 1))
                            .source(JSON.toJSONString(users.get(i)), XContentType.JSON));
        }
        BulkResponse response = restHighLevelClient.bulk(request, RequestOptions.DEFAULT);
        System.out.println(response.hasFailures());
    }

    //批量查询
    //SearchRequest 搜素请求
    //SearchSourceBuilder 条件构造
    //TermQueryBuilder精确查询,HighlightBuilder 高亮, MatchAllQueryBuilder 
    @Test
    public void SearchDocument() throws IOException {
        SearchRequest request = new SearchRequest("hhp_index");
        //构建搜索条件
        SearchSourceBuilder builder = new SearchSourceBuilder();
        //查询条件,
        //QueryBuilders.termQuery精确匹配
        //QueryBuilders.matchAllQuery()匹配所有
        TermQueryBuilder termQueryBuilder = QueryBuilders.termQuery("name", "hhp");
//        MatchAllQueryBuilder matchAllQueryBuilder = QueryBuilders.matchAllQuery();
        builder.query(termQueryBuilder);
        builder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        request.source(builder);
        SearchResponse response = restHighLevelClient.search(request, RequestOptions.DEFAULT);
        System.out.println(JSON.toJSONString(response.getHits()));
        System.out.println("=================");
        for (SearchHit hit : response.getHits().getHits()) {
            System.out.println(hit.getSourceAsMap());
        }
    }
}
