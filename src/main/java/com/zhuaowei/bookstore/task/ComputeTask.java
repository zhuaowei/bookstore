package com.zhuaowei.bookstore.task;

import com.zhuaowei.bookstore.mapper.BookMapper;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @ClassName: ComputeTask
 * @Description: TODO: add description
 * @Author: zhuaowei
 * @Date: 2021/8/26 10:40
 * @Version: 1.0
 **/
@Component
public class ComputeTask {
    @Resource
    private BookMapper bookMapper;

    /** 任务调度，每分钟更新 */
    @Scheduled(cron = "0 * * * * ?")
    public void autoUpdateEvaluation() {
        bookMapper.updateEvaluation();
        System.out.println("updated");
    }
}
