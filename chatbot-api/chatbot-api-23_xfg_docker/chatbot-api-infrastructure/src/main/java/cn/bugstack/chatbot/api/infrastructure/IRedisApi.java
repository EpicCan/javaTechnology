package cn.bugstack.chatbot.api.infrastructure;

import java.io.IOException;

public interface IRedisApi {

    void setValue(String key, Object value);

    Object getValue(String key);

    void deleteValue(String key);

}
