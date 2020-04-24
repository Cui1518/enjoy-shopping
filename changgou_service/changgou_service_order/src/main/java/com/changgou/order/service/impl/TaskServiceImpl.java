package com.changgou.order.service.impl;

import com.changgou.order.dao.TaskHisMapper;
import com.changgou.order.dao.TaskMapper;
import com.changgou.order.pojo.Task;
import com.changgou.order.pojo.TaskHis;
import com.changgou.order.service.TaskService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author cxl
 * @date 2020-04-13 20:37
 */
@Service
public class TaskServiceImpl implements TaskService {

    @Autowired
    private TaskMapper taskMapper;
    @Autowired
    private TaskHisMapper taskHisMapper;

    @Override
    @Transactional
    public void delTask(Task task) {
        //1.记录删除时间
        task.setDeleteTime(new Date());
        Long taskId = task.getId();
        task.setId(null);
        //Bean拷贝
        TaskHis taskHis = new TaskHis();
        //将task数据备份到taskHits
        BeanUtils.copyProperties(task,taskHis); //两个实体类属性名称完全一样,否则不一样的属性无法拷贝

        //记录历史任务数据
        taskHisMapper.insertSelective(taskHis);

        //删除原有任务数据
        task.setId(taskId);
        taskMapper.deleteByPrimaryKey(task);


    }
}
