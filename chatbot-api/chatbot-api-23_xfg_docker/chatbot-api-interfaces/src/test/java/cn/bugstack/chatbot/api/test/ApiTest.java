package cn.bugstack.chatbot.api.test;

import cn.bugstack.chatbot.api.domain.ai.IOpenAI;
import cn.bugstack.chatbot.api.domain.zhihu.model.vo.Top50;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 * @author 小傅哥，微信：fustack
 * @description 单元测试
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiTest {

    @Resource
    private IOpenAI openAI;

    @Test
    public void query_unanswered_questions() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpGet get = new HttpGet("https://api.zsxq.com/v2/groups/28885518425541/topics?scope=unanswered_questions&count=20");

        get.addHeader("cookie", "");
        get.addHeader("Content-Type", "application/json;charset=utf8");

        CloseableHttpResponse response = httpClient.execute(get);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void answer() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.zsxq.com/v2/topics/412884248251548/answer");
        post.addHeader("cookie", "__cuid=5330a556392a4c5b8084b4cbc165e0f3; amp_fef1e8=930aec23-e22e-4f11-8864-0389bd5095d1R...1g55hl79m.1g55hl79t.1.1.2; UM_distinctid=183e61195d535b-0bddac94679c75-19525635-1aeaa0-183e61195d7c52; sensorsdata2015jssdkcross={\"distinct_id\":\"241858242255511\",\"first_id\":\"17ebd0b4317ecb-0b27f672c2d3af-133a6253-1296000-17ebd0b4318ba7\",\"props\":{\"$latest_traffic_source_type\":\"直接流量\",\"$latest_search_keyword\":\"未取到值_直接打开\",\"$latest_referrer\":\"\"},\"$device_id\":\"17ebd0b4317ecb-0b27f672c2d3af-133a6253-1296000-17ebd0b4318ba7\",\"identities\":\"eyIkaWRlbnRpdHlfY29va2llX2lkIjoiMTgwMmQ2YjZiOWIxZjMtMGQ4YzMzZjhmYTA3YmEtMzU3MzZhMDMtMTI5NjAwMC0xODAyZDZiNmI5YzEwODYiLCIkaWRlbnRpdHlfbG9naW5faWQiOiIyNDE4NTgyNDIyNTU1MTEifQ==\",\"history_login_id\":{\"name\":\"$identity_login_id\",\"value\":\"241858242255511\"}}; abtest_env=product; zsxqsessionid=8fae9a083a4874ab833c2158a44deb82; zsxq_access_token=5D862869-1229-A9B6-1BC1-C662EC4B16DD_D625BA7FD9CBBDFA");
        post.addHeader("Content-Type", "application/json;charset=utf8");

        String paramJson = "{\n" +
                "  \"req_data\": {\n" +
                "    \"text\": \"自己去百度！\\n\",\n" +
                "    \"image_ids\": [],\n" +
                "    \"silenced\": false\n" +
                "  }\n" +
                "}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

    @Test
    public void test_chatGPT() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();

        HttpPost post = new HttpPost("https://api.openai.com/v1/completions");
        post.addHeader("Content-Type", "application/json");
        post.addHeader("Authorization", "Bearer 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");

        String paramJson = "{\"model\": \"text-davinci-003\", \"prompt\": \"帮我写一个java冒泡排序\", \"temperature\": 0, \"max_tokens\": 1024}";

        StringEntity stringEntity = new StringEntity(paramJson, ContentType.create("text/json", "UTF-8"));
        post.setEntity(stringEntity);

        CloseableHttpResponse response = httpClient.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            System.out.println(res);
        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }


    @Test
    public void query_zhihu_hotone() throws IOException {
        CloseableHttpClient httpClient = HttpClientBuilder.create().build();
        // https://www.zhihu.com/api/v3/feed/topstory/hot-lists/total?limit=50&desktop=true
        HttpGet get = new HttpGet("https://www.zhihu.com/api/v3/feed/topstory/hot-lists/total?limit=50&desktop=true");

        get.addHeader("cookie", "d_c0=\"ABAe7cp_pxGPTkxzTWBTZuGEbRqpZ5wYG-8=|1596078456\"; _zap=2ee34037-e839-4ad7-bc16-aa2959b73907; _9755xjdesxxd_=32; YD00517437729195:WM_TID=+BAUkxkLjxdEEUERRVeAWb9roIO/Xt0E; __snaker__id=0202RWZfaYFT1B5Z; q_c1=f1ecd1374ab04f92ac39bbe377f16abe|1662529710000|1662529710000; YD00517437729195:WM_NI=IRG2Vf1l2WP8QJ3+YTOfy4s/qEHxxdrsrrVrn530TCW8WjrENLdSeELQcmJz/zppX1MpquhiKx2ZBqNlDXPwkP3Sb4uGoPUQxAFc6CPrIIG2Kc/AEE76bAWd3rTcUGBoS2M=; YD00517437729195:WM_NIKE=9ca17ae2e6ffcda170e2e6eeccca6fa7eabfb8d480909e8ea3c54b928f8facd5439cb48bb6cc52afade5d0bc2af0fea7c3b92a8b8e89d3d56ef3b4feb8d3349396fdccdc608dbab7d5f225b39b8899fb70b2adbe93d57aaeebfdd1d74995e8998bf14f8e94acb4e84f908fad8ec972eda6c091db3b8fb0bf96eb6389a8febbc17b8eb48cb9d37da990fdb4e140b799a1b1d85bbc9f8a84b54994ae87aac63a89bde1a2c84ff5eaaea2d670f1ae9c84d84d82b19dd3d037e2a3; _xsrf=KXCfbzMhnk1jzotEfWlcC4MG0hFgR6tI; z_c0=2|1:0|10:1676526745|4:z_c0|80:MS4xVlM5UkNnQUFBQUFtQUFBQVlBSlZUWmtTMjJUTzRma3RPOWZqSVUwZ3d3eHJNVUFhMGpONFFBPT0=|7238b82b2fc15de817708c20ebce29ae06967afa744999fab0d41b65c5106ace; Hm_lvt_98beee57fd2ef70ccdd5ca52b9740c49=1675643693,1675817867,1676357241,1676526641; Hm_lpvt_98beee57fd2ef70ccdd5ca52b9740c49=1676526641; tst=h; arialoadData=false; SESSIONID=5akaRUacWe0uQ0NyWNQB4UL5t5PH70X6IBMTPtakjAs; KLBRSID=cdfcc1d45d024a211bb7144f66bda2cf|1676526751|1676526740; JOID=UVwXAklctC5je0a2N16iPpG3OxcnbtNGASgU0VcK1hgVKXf_SMJ1wwJ9R7M3AW0YuQYbvZSo0OG8JNOD77UJp4k=; osd=W18RAkJWtyhjcEy1MV6pNJKxOxwtbdVGCiIX11cB3BsTKXz1S8R1yAh-QbM8C24euQ0RvpKo2-u_ItOI5bYPp4I=");
        get.addHeader("Content-Type", "application/json;charset=utf8");
        CloseableHttpResponse response = httpClient.execute(get);



        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            String res = EntityUtils.toString(response.getEntity());
            Top50 top50 = new Top50();
            Top50 top501 = JSON.parseObject(res, Top50.class);
            List<Top50.DataBean> data = top501.getData();
            Top50.DataBean dataBean = data.get(0);
            String title = dataBean.getTarget().getTitle();
            System.out.println(title);
            String openAiQuestion = openAI.doChatGPT(title);
            System.out.println(openAiQuestion);

        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }
    }

}
