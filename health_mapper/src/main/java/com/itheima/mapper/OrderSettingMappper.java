package com.itheima.mapper;

import com.itheima.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;

import java.util.Date;
import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-12 19:24
 */
public interface OrderSettingMappper {


    void updateNumberByOrderDate(OrderSetting orderSetting);

    void add(OrderSetting orderSetting);

    Integer findCountByDate(@Param("orderDate") Date orderDate);

    List<OrderSetting> findOrderSettingByMonth(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd);

    OrderSetting findByOrderDate(@Param("orderDate") Date date);

    void updateReservationsByOrderDate(@Param("orderDate") Date date);

    void clearOrderSettingByMonth(@Param("dateBegin") String dateBegin, @Param("dateEnd") String dateEnd);
}
