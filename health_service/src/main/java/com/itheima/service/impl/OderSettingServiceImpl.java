package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.OrderSettingMappper;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OderSettingService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-12 19:22
 */
@Service(interfaceClass = OderSettingService.class)
public class OderSettingServiceImpl implements OderSettingService {
    @Autowired
    private OrderSettingMappper orderSettingMappper;

    @Override
    public void importOrderSettings(List<OrderSetting> orderSettings) {
        for (OrderSetting orderSetting : orderSettings) {
            Integer count = orderSettingMappper.findCountByDate(orderSetting.getOrderDate());
            if (count > 0) {
                //已存在就更新预约数量
                orderSettingMappper.updateNumberByOrderDate(orderSetting);
            } else {
                //不存在就新增
                orderSettingMappper.add(orderSetting);
            }
        }
    }

    @Override
    public List<Map> findOrderSettingByMonth(String date) {
        //定义月份第一天
        String dateBegin = date + "-1";//2019-7-1
        //定义月份的最后一天
        String dateEnd = date + "-31";//2019-7-31
        //同当前月份的第一天和最后一天查询当前月所有的预约设置数据
        List<OrderSetting> orderSettings = orderSettingMappper.findOrderSettingByMonth(dateBegin, dateEnd);
        List list = new ArrayList();
        //遍历每一个OrderSetting，将数据封装到Map中
        for (OrderSetting orderSetting : orderSettings) {
            Map map = new HashMap<>();
            map.put("date", orderSetting.getOrderDate().getDate());//获取几号
            map.put("number", orderSetting.getNumber());
            map.put("reservations", orderSetting.getReservations());
            //将所有map封装到list中
            list.add(map);
        }
        return list;
    }

    @Override
    public void updateNumberByOrderDate(OrderSetting orderSetting) {
        int count = orderSettingMappper.findCountByDate(orderSetting.getOrderDate());
        if (count>0){
            //如果设置过预约数据，执行更新
            orderSettingMappper.updateNumberByOrderDate(orderSetting);
        }else {
            //如果之前没有设置过预约数据，执行新增
            orderSettingMappper.add(orderSetting);
        }
    }
}
