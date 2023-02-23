package com.framework.pie.util;

import cn.hutool.core.util.StrUtil;
import cn.hutool.json.JSONUtil;
import com.framework.pie.http.HttpResult;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import reactor.core.publisher.Mono;

import java.nio.charset.Charset;

/**
 * @Author houjh
 * @Date 2021-07-29 13:30
 */
public class ResponseUtils {

    public static Mono writeErrorInfo(ServerHttpResponse response, Integer resultCode,String resultMsg){
        response.setStatusCode(HttpStatus.OK);
        response.getHeaders().set(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        String body = "";
        if(!StrUtil.isEmptyIfStr(resultCode)){
            body = JSONUtil.toJsonStr(HttpResult.error(resultCode,resultMsg));
        } else {
            body = JSONUtil.toJsonStr(HttpResult.error(resultMsg));
        }
        DataBuffer buffer = response.bufferFactory().wrap(body.getBytes(Charset.forName("UTF-8")));
        return response.writeWith(Mono.just(buffer))
                .doOnError(error -> DataBufferUtils.release(buffer));
    }

}
