package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.mapper.MemberMapper;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author ShiXiaoyu
 * @date 2020-05-16 18:09
 */
@Service(interfaceClass = MemberService.class)
public class MemberServiceimpl implements MemberService{
    @Autowired
    private MemberMapper memberMapper;
    @Override
    public Member findByTelephone(String telephone) {
        return memberMapper.findByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        memberMapper.add(member);
    }

    @Override
    public Map getMemberReport() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH,-12);
        //定义一个list集合，存放过去12个月每个月的月份
        ArrayList<String> months = new ArrayList<>();
        //定义一个list集合，存放过去12个月每个月的会员数量
        ArrayList<Integer> memberCounts = new ArrayList<>();
        //遍历过去12个月的每一个月
        for (int i = 0; i < 12; i++) {
            Date time = calendar.getTime();
            //获取每个月的月份
            String month = new SimpleDateFormat("yyyy-MM").format(time);

            //定义每个月开始日期
            String monthBegin = month+"-1";
            //定义每个月结束时间
            String monthEnd = month+"-31";
            //统计每个月人数
            int count = memberMapper.findMemberCountByMonth(monthBegin,monthEnd);
            //添加每一个月
            months.add(month);

            //添加每一个月的会员数量
            memberCounts.add(count);

            //每次循环都在日历的月份上+1，如-11月,-10月，-9月，依次类推
            calendar.add(Calendar.MONTH,+1);
        }
        //创建一个map，将月份的集合以及每个月会员数量的集合存入其中
        Map<String, List> map = new HashMap<>();
        map.put("months",months);
        map.put("memberCounts",memberCounts);

        return map;
    }
}
