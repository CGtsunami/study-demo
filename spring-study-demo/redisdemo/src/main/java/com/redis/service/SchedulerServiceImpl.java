package com.redis.service;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.SessionCallback;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.text.MessageFormat;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * @author: chenzz
 * @date: 2020/08/06 下午 17:32
 */
@Service
@Slf4j
public class SchedulerServiceImpl implements SchedulerService {


    @Autowired
    private StringRedisTemplate redisTemplate;

    private static final String QUERY_SQL = "SELECT {0} mkey FROM {1}";


    @Override
    public boolean refreshTargetCache(Integer controlBatchId) {
        List<String> dataList = new ArrayList<String>();
        if (CollectionUtils.isEmpty(dataList)) {
            log.info("批量布控表中未配置数据，无需继续执行");
            return true;
        }
        dataList.stream().forEach(d -> {
            //表名
            String tableName = "xx";
            //字段名
            String columnName = "d.getControlColumnEn()";
            //访问链接
            String jdbcInfo = "d.getControlTableJdbc()";
            String redisPrefix= "";
//            String redisPrefix = MessageFormat.format(DictRedisKeyConstant.KEY_PAP_CONTROL_TARGET_PREFIX,
//                    tableName,
//                    columnName
//            );
            log.info("refreshTargetCache tableName:{}, columnName:{}, redisKey:{} PROCESS",
                    tableName,
                    columnName,
                    redisPrefix
            );
            List<Map> resultList = null;
            try {
                //连接数据库获取数据
                String sql = MessageFormat.format(QUERY_SQL, columnName, tableName);
                //获取数据
//                resultList = this.getData(jdbcInfo, sql);
//                //刷新缓存
//                this.refreshCache(resultList, redisPrefix);
            } catch (Exception e) {
                log.error("refreshTargetCache tableName:{}, columnName:{}, redisKey:{} CACHE ERROR,MESSAGE:",
                        tableName,
                        columnName,
                        redisPrefix,
                        e
                );
            }
            log.info("refreshTargetCache tableName:{}, columnName:{}, redisKey:{} CACHE SUCCESS, CacheCount:{}",
                    tableName,
                    columnName,
                    redisPrefix,
                    Optional.ofNullable(resultList).orElseGet(ArrayList::new).size()
            );
        });
        return true;
    }

    /**
     * 连接jdbc查询数据
     *
     * @param jdbcInfo
     * @param sql
     * @return
     */
//    public List<Map> getData(String jdbcInfo, String sql) {
//        AssetConnectInfoFeignDTO connectDTO = JsonUtil.parse(jdbcInfo, AssetConnectInfoFeignDTO.class);
//        List dataList = DBProxy.query(connectDTO.getUrl(),
//                connectDTO.getUserName(), Base64.decodeStr(connectDTO.getPwd()), sql,
//                connectDTO.getJaasRoute(), connectDTO.getKrbRoute()
//        );
//        return dataList;
//    }

    /**
     * 构建数据刷新redis
     *
     * @param resultList
     * @param redisPrefix
     */
    /*public void refreshCache(List<Map> resultList, String redisPrefix) {
        //获取keys
        Set<String> keys = redisTemplate.keys(redisPrefix + "*");
        if (CollectionUtils.isNotEmpty(keys)) {
            //删除缓存
            redisTemplate.delete(keys);
        }
        if (CollectionUtils.isEmpty(resultList)) {
            log.info("redisPrefix:{} has NO DATA", redisPrefix);
            return;
        }
        redisTemplate.executePipelined(new SessionCallback<Object>() {
            @Override
            public Object execute(RedisOperations operations) throws DataAccessException {
                resultList.stream().forEach(e -> {
                    String redisKey = redisPrefix + String.valueOf(e.get("mkey"));
                    operations.opsForValue().set(
                            redisKey,
                            "1",
                            CommonConstant.EXPIRE_SEVEN_WEEK,
                            TimeUnit.DAYS
                    );
//                        log.info("refreshTargetCache cache key:{} success", redisKey);
                });
                return null;
            }
        });
    }*/
}
