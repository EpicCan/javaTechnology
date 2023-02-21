package cn.bugstack.chatbot.api.test;

import cn.bugstack.chatbot.api.domain.ai.IOpenAI;
import cn.bugstack.chatbot.api.domain.ai.IOpenAI2;
import cn.bugstack.chatbot.api.domain.zhihu.model.vo.Top50;
import cn.bugstack.chatbot.api.domain.zsxq.IZsxqApi;
import cn.bugstack.chatbot.api.domain.zsxq.model.aggregates.UnAnsweredQuestionsAggregates;
import cn.bugstack.chatbot.api.domain.zsxq.model.vo.Topics;
import cn.bugstack.chatbot.api.infrastructure.IRedisApi;
import cn.bugstack.chatbot.api.infrastructure.RedisUtils;
import com.alibaba.fastjson.JSON;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * @author 小傅哥，微信：fustack
 * @description
 * @github https://github.com/fuzhengwei
 * @Copyright 公众号：bugstack虫洞栈 | 博客：https://bugstack.cn - 沉淀、分享、成长，让自己和他人都能有所收获！
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class SpringBootRunTest {

    private Logger logger = LoggerFactory.getLogger(SpringBootRunTest.class);

    @Value("${chatbot-api.groupId}")
    private String groupId;
    @Value("${chatbot-api.cookie}")
    private String cookie;

    @Resource
    private IZsxqApi zsxqApi;
    @Resource
    private IOpenAI openAI;
    @Resource
    private IOpenAI2 openAI2;

    @Resource
    private IRedisApi iRedisApi;

    @Autowired
    private JedisPool jedisPool;

    @Test
    public   void contextLoads() {
        System.out.println(jedisPool);
        //在连接池中得到Jedis连接
        Jedis jedis = jedisPool.getResource();
        jedis.set("haha", "你好");
        jedis.set("name", "wangpengcheng");
        //关闭当前连接
        jedis.close();

    }

    @Test
    public void test_zsxqApi() throws IOException {
        UnAnsweredQuestionsAggregates unAnsweredQuestionsAggregates = zsxqApi.queryUnAnsweredQuestionsTopicId(groupId, cookie);
        logger.info("测试结果：{}", JSON.toJSONString(unAnsweredQuestionsAggregates));

        List<Topics> topics = unAnsweredQuestionsAggregates.getResp_data().getTopics();
        for (Topics topic : topics) {
            String topicId = topic.getTopic_id();
            String text = topic.getQuestion().getText();
            logger.info("topicId：{} text：{}", topicId, text);

            // 回答问题
            zsxqApi.answer(groupId, cookie, topicId, text, false);
        }
    }

    @Test
    public void test_openAi() throws IOException {
        String response = openAI.doChatGPT("帮我写一个java冒泡排序");
        logger.info("测试结果：{}", response);
    }


    @Test
    public void test_zhihuopenAi() throws IOException {
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
            logger.info("问题：{}", title);
            String anwser = openAI2.doChatGPT2(title);
//            System.out.println("the anwser is: "+anwser);
            logger.info("回答：{}", anwser);


        } else {
            System.out.println(response.getStatusLine().getStatusCode());
        }

    }

    @Test
    public void  testRedis() {
      iRedisApi.setValue("name","can");

    }
}
