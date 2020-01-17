package com.framework.pie.consumer.feignInterface;

import org.springframework.stereotype.Service;

@Service
public class PieProducerServiceImpl implements PieProducerService {
    @Override
    public String hello() {
        return "远程调用失败！";
    }
}
