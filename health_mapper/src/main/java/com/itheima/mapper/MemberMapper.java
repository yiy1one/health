package com.itheima.mapper;

import com.itheima.pojo.Member;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * @author ShiXiaoyu
 * @date 2020-05-14 17:00
 */
public interface MemberMapper {
    public Member findByTelephone(@Param("telephone") String telephone);

    void add(Member member);

    int findMemberCountByMonth(@Param("monthBegin") String monthBegin, @Param("monthEnd") String monthEnd);

    Integer findTodayNewMember(@Param("today") String today);

    Integer findTotalMember();

    Integer findNewMemberCountAfterDate(@Param("date") String date);

    List<Map> getMember2Report();
}
