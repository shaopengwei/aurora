package com.aurora.red_netty_server.util;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.handler.codec.http.multipart.DefaultHttpDataFactory;
import io.netty.handler.codec.http.multipart.HttpPostRequestDecoder;
import io.netty.handler.codec.http.multipart.InterfaceHttpData;
import io.netty.handler.codec.http.multipart.MemoryAttribute;
import lombok.experimental.UtilityClass;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * @author shaopengwei@hotmail.com
 * @Description
 * @create 2021-08-11 21:12
 */
@UtilityClass
public class HttpRequestUtil {

    public Map<String, String> getHttpRequestParam(ChannelHandlerContext ctx, HttpRequest req){
        Map<String, String>requestParams=new HashMap<>();
        // 处理get请求
        if (req.method() == HttpMethod.GET) {
            QueryStringDecoder decoder = new QueryStringDecoder(req.uri());
            Map<String, List<String>> params = decoder.parameters();
            Iterator<Map.Entry<String, List<String>>> iterator = params.entrySet().iterator();
            while(iterator.hasNext()){
                Map.Entry<String, List<String>> next = iterator.next();
                requestParams.put(next.getKey(), next.getValue().get(0));
            }
        }
        // 处理POST请求
        if (req.method() == HttpMethod.POST) {
            HttpPostRequestDecoder decoder = new HttpPostRequestDecoder(new DefaultHttpDataFactory(false), req);
            List<InterfaceHttpData> postData = decoder.getBodyHttpDatas();
            for(InterfaceHttpData data:postData){
                if (data.getHttpDataType() == InterfaceHttpData.HttpDataType.Attribute) {
                    MemoryAttribute attribute = (MemoryAttribute) data;
                    requestParams.put(attribute.getName(), attribute.getValue());
                }
            }
        }
        return requestParams;
    }

    public String getHttpRequestHostname(ChannelHandlerContext ctx, HttpRequest req) {
        String host = (String) req.headers().get("host");
        String[] hostInfo = host.split(":");
        return hostInfo[0];
    }

    public int getHttpRequestPort(ChannelHandlerContext ctx, HttpRequest req) {
        int port = 80;
        String host = (String) req.headers().get("host");
        String[] hostInfo = host.split(":");
        if (hostInfo.length > 1) {
            port = Integer.parseInt(hostInfo[1]);
        } else {
            if (req.uri().indexOf("https") == 0) {
                port = 443;
            }
        }
        return port;
    }
}
