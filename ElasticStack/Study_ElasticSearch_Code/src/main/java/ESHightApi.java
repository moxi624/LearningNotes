import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.HttpHost;
import org.apache.http.util.EntityUtils;
import org.elasticsearch.action.ActionListener;
import org.elasticsearch.action.delete.DeleteRequest;
import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.*;
import org.elasticsearch.common.Strings;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * ES高级客户端
 *
 * @author: 陌溪
 * @create: 2020-09-23-16:56
 */
public class ESHightApi {
    private RestHighLevelClient client;

    public void init() {
        RestClientBuilder restClientBuilder = RestClient.builder(
                new HttpHost("202.193.56.222", 9200, "http"));
        this.client = new RestHighLevelClient(restClientBuilder);
    }

    public void after() throws Exception {
        this.client.close();
    }

    /**
     * 新增文档，同步操作
     *
     * @throws Exception
     */
    public void testCreate() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "2002");
        data.put("title", "南京西路 拎包入住 一室一厅");
        data.put("price", "4500");
        IndexRequest indexRequest = new IndexRequest("haoke", "house")
                .source(data);
        IndexResponse indexResponse = this.client.index(indexRequest,
                RequestOptions.DEFAULT);
        System.out.println("id->" + indexResponse.getId());
        System.out.println("index->" + indexResponse.getIndex());
        System.out.println("type->" + indexResponse.getType());
        System.out.println("version->" + indexResponse.getVersion());
        System.out.println("result->" + indexResponse.getResult());
        System.out.println("shardInfo->" + indexResponse.getShardInfo());
    }

    /**
     * 异步创建文档
     * @throws Exception
     */
    public void testCreateAsync() throws Exception {
        Map<String, Object> data = new HashMap<>();
        data.put("id", "2003");
        data.put("title", "南京东路 最新房源 二室一厅");
        data.put("price", "5500");
        IndexRequest indexRequest = new IndexRequest("haoke", "house")
                .source(data);
        this.client.indexAsync(indexRequest, RequestOptions.DEFAULT, new
                ActionListener<IndexResponse>() {
                    @Override
                    public void onResponse(IndexResponse indexResponse) {
                        System.out.println("id->" + indexResponse.getId());
                        System.out.println("index->" + indexResponse.getIndex());
                        System.out.println("type->" + indexResponse.getType());
                        System.out.println("version->" + indexResponse.getVersion());
                        System.out.println("result->" + indexResponse.getResult());
                        System.out.println("shardInfo->" + indexResponse.getShardInfo());
                    }
                    @Override
                    public void onFailure(Exception e) {
                        System.out.println(e);
                    }
                });
        System.out.println("ok");
        Thread.sleep(20000);
    }

    /**
     * 查询
     * @throws Exception
     */
    public void testQuery() throws Exception {
        GetRequest getRequest = new GetRequest("haoke", "house",
                "GkpdE2gBCKv8opxuOj12");
        // 指定返回的字段
        String[] includes = new String[]{"title", "id"};
        String[] excludes = Strings.EMPTY_ARRAY;
        FetchSourceContext fetchSourceContext =
                new FetchSourceContext(true, includes, excludes);
        getRequest.fetchSourceContext(fetchSourceContext);
        GetResponse response = this.client.get(getRequest, RequestOptions.DEFAULT);
        System.out.println("数据 -> " + response.getSource());
    }

    /**
     * 判断是否存在
     *
     * @throws Exception
     */
    public void testExists() throws Exception {
        GetRequest getRequest = new GetRequest("haoke", "house",
                "GkpdE2gBCKv8opxuOj12");
// 不返回的字段
        getRequest.fetchSourceContext(new FetchSourceContext(false));
        boolean exists = this.client.exists(getRequest, RequestOptions.DEFAULT);
        System.out.println("exists -> " + exists);
    }
    /**
     * 删除数据
     *
     * @throws Exception
     */
    public void testDelete() throws Exception {
        DeleteRequest deleteRequest = new DeleteRequest("haoke", "house",
                "GkpdE2gBCKv8opxuOj12");
        DeleteResponse response = this.client.delete(deleteRequest,
                RequestOptions.DEFAULT);
        System.out.println(response.status());// OK or NOT_FOUND
    }
    /**
     * 更新数据
     *
     * @throws Exception
     */
    public void testUpdate() throws Exception {
        UpdateRequest updateRequest = new UpdateRequest("haoke", "house",
                "G0pfE2gBCKv8opxuRz1y");
        Map<String, Object> data = new HashMap<>();
        data.put("title", "张江高科2");
        data.put("price", "5000");
        updateRequest.doc(data);
        UpdateResponse response = this.client.update(updateRequest,
                RequestOptions.DEFAULT);
        System.out.println("version -> " + response.getVersion());
    }
    /**
     * 测试搜索
     *
     * @throws Exception
     */
    public void testSearch() throws Exception {
        SearchRequest searchRequest = new SearchRequest("haoke");
        searchRequest.types("house");
        SearchSourceBuilder sourceBuilder = new SearchSourceBuilder();
        sourceBuilder.query(QueryBuilders.matchQuery("title", "拎包入住"));
        sourceBuilder.from(0);
        sourceBuilder.size(5);
        sourceBuilder.timeout(new TimeValue(60, TimeUnit.SECONDS));
        searchRequest.source(sourceBuilder);
        SearchResponse search = this.client.search(searchRequest,
                RequestOptions.DEFAULT);
        System.out.println("搜索到 " + search.getHits().totalHits + " 条数据.");
        SearchHits hits = search.getHits();
        for (SearchHit hit : hits) {
            System.out.println(hit.getSourceAsString());
        }
    }

    public static void main(String[] args) throws Exception {
        ESHightApi esHightApi = new ESHightApi();
        esHightApi.init();
        esHightApi.testCreate();
    }
}
