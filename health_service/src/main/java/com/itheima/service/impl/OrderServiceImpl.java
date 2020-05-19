package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.constant.MessageConstant;
import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.mapper.OrderSettingMappper;
import com.itheima.pojo.Member;
import com.itheima.pojo.Order;
import com.itheima.pojo.OrderSetting;
import com.itheima.service.OrderService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-14 16:50
 */
@Service(interfaceClass = OrderService.class)
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private OrderSettingMappper orderSettingMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Override
    public Order add(Map map) throws Exception {
        //获取日期
        String orderDate = (String) map.get("orderDate");
        //将字符串日期转换成日期对象
        Date date = DateUtils.parseString2Date(orderDate);
//通过日期查询是否可以预约
        OrderSetting orderSetting = orderSettingMapper.findByOrderDate(date);
        if (orderSetting == null) {
            //如果无法预约就抛出异常
            throw new RuntimeException(MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }

        //判断预约是否已满
        if (orderSetting.getReservations() >= orderSetting.getNumber()) {
            //如果无法预约就抛出异常
            throw new RuntimeException(MessageConstant.ORDER_FULL);
        }
        //通过手机号查询会员
        String telephone = (String) map.get("telephone");
        Member member = memberMapper.findByTelephone(telephone);
        if (member == null) {
            //如果不是会员，注册为会员
            member = new Member();
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(new Date());
            member.setSex((String) map.get("sex"));
            member.setName((String) map.get("name"));
            //执行注册
            memberMapper.add(member);
        } else {
            Order order = new Order();
            order.setMemberId(member.getId());
            order.setOrderDate(date);
            order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));

            //如果是会员，查询是否重复预约
            Order queryOrder = orderMapper.findByCondition(order);
            if (queryOrder != null) {
                throw new RuntimeException(MessageConstant.HAS_ORDERED);
            }
        }
//添加预约
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setSetmealId(Integer.parseInt((String) map.get("setmealId")));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setOrderType((String) map.get("orderType"));

        orderMapper.add(order);
        //预约成功后更新预约数量
        orderSettingMapper.updateReservationsByOrderDate(date);

        return order;

    }

    @Override
    public Map findById(Integer id) throws Exception {
        //通过id查询order数据和Member以及setmeal数据
        Map map = orderMapper.findById(id);
        //转换日期格式，否则页面上的日期会有时分秒
        Date orderDate = (Date) map.get("orderDate");
        map.put("orderDate",DateUtils.parseDate2String(orderDate));

        return map;

    }


}
