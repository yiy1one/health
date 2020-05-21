package com.itheima.service;

import com.itheima.pojo.Member;

import java.text.ParseException;
import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-16 18:07
 */
public interface MemberService {
    Member findByTelephone(String telephone);

    void add(Member member);

    Map getMemberReport();


    Map queryByMonth(String start, String end) throws ParseException;

    Map getMember2Report();
}
