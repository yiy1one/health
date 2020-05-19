package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MemberMapper;
import com.itheima.mapper.OrderMapper;
import com.itheima.service.ReportService;
import com.itheima.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-19 12:05
 */
@Service(interfaceClass = ReportService.class)
public class ReportServiceImpl implements ReportService {
    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private OrderMapper orderMapper;

    @Override
    public Map getBusinessReport() throws Exception {
        //获取今日日期
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        //获取本周一
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        //获取本月第一天
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());

        //今日新增会员数
        Integer todayNewMember = memberMapper.findTodayNewMember(today);
        //总会员数
        Integer totalMember =  memberMapper.findTotalMember();

        //本周新增会员数
        Integer thisWeekNewMember = memberMapper.findNewMemberCountAfterDate(thisWeekMonday);

        //本月新增会员数
        Integer thisMonthNewMember = memberMapper.findNewMemberCountAfterDate(firstDay4ThisMonth);

        //今日预约数
        Integer todayOrderNumber = orderMapper.findTodayOrderNumber(today);

        //本周预约数
        Integer thisWeekOrderNumber = orderMapper.findOrderNumberAfterDate(thisWeekMonday);

        //本月预约数
        Integer thisMonthOrderNumber = orderMapper.findOrderNumberAfterDate(firstDay4ThisMonth);

        //今日到诊数
        Integer todayVisitsNumber = orderMapper.findTodayVisitsNumber(today);

        //本周到诊数
        Integer thisWeekVisitsNumber = orderMapper.findVisitsNumberAfterDate(thisWeekMonday);

        //本月到诊数
        Integer thisMonthVisitsNumber = orderMapper.findVisitsNumberAfterDate(firstDay4ThisMonth);

        //热门套餐4个
        List<Map<String,Object>> hotSetmeal  = orderMapper.findHotSetmeal();

        //定义map封装页面所需数据
        Map<String,Object> result = new HashMap<>();

        result.put("reportDate",today);
        result.put("todayNewMember",todayNewMember);
        result.put("totalMember",totalMember);
        result.put("thisWeekNewMember",thisWeekNewMember);
        result.put("thisMonthNewMember",thisMonthNewMember);
        result.put("todayOrderNumber",todayOrderNumber);
        result.put("thisWeekOrderNumber",thisWeekOrderNumber);
        result.put("thisMonthOrderNumber",thisMonthOrderNumber);
        result.put("todayVisitsNumber",todayVisitsNumber);
        result.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        result.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        result.put("hotSetmeal",hotSetmeal);

        return result;

    }
}
