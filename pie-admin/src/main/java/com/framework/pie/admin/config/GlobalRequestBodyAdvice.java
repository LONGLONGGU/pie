package com.framework.pie.admin.config;

import org.springframework.core.MethodParameter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.RequestBodyAdvice;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 *  全局请求参数处理类
 * @ Author     ：longlong
 * @ Date       ：Created in 10:24 2021/4/29
 */

@ControllerAdvice
public class GlobalRequestBodyAdvice implements RequestBodyAdvice {

    /** 此处如果返回false , 则不执行当前Advice的业务 */
    @Override
    public boolean supports(MethodParameter methodParameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return true;
    }

    /**
     * @title 读取参数前执行
     * @description 在此做些编码 / 解密 / 封装参数为对象的操作
     *
     *
     */
    @Override
    public HttpInputMessage beforeBodyRead(HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) throws IOException {
        return new XHttpInputMessage(inputMessage, "UTF-8");
    }

    /**
     * @title 读取参数后执行
     */
    @Override
    public Object afterBodyRead(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

    /**
     * @title 无请求时的处理
     */
    @Override
    public Object handleEmptyBody(Object body, HttpInputMessage inputMessage, MethodParameter parameter, Type targetType, Class<? extends HttpMessageConverter<?>> converterType) {
        return body;
    }

}

//这里实现了HttpInputMessage 封装一个自己的HttpInputMessage
class XHttpInputMessage implements HttpInputMessage {
    private final HttpHeaders headers;
    private final InputStream body;

    public XHttpInputMessage(HttpInputMessage httpInputMessage, String encode) throws IOException {
        this.headers = httpInputMessage.getHeaders();
        this.body = encode(httpInputMessage.getBody(), encode);
    }

    private InputStream encode(InputStream body, String encode) {
        //省略对流进行编码的操作
        return body;
    }

    @Override
    public InputStream getBody() {
        return body;
    }

    @Override
    public HttpHeaders getHeaders() {
        return headers;
    }
}
