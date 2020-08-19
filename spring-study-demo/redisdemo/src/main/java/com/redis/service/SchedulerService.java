package com.redis.service;



import java.util.List;

/**
 * @author: chenzz
 * @date: 2020/08/06 下午 17:30
 */
public interface SchedulerService {

    /**
     * 获取去重后所有的表信息
     */


    /**
     * 刷新控制对象表数据
     * @param controlBatchId
     * @return
     */
    boolean refreshTargetCache(Integer controlBatchId);
}
