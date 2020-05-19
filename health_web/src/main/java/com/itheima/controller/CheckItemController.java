package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckItem;
import com.itheima.service.CheckItemService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-06 12:24
 */
@RestController
@RequestMapping("checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;

    //添加检查项方法
    @PreAuthorize("hasAuthority('CHECKITEM_ADD')")
    @RequestMapping("add")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
            return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    //查询检查项
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = checkItemService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
    }

    //删除检查项
    @PreAuthorize("hasAuthority('CHECKITEM_DELETE')")
    @RequestMapping("deleteById")
    public Result deleteById(@RequestParam("id") Integer id) {
        try {
            checkItemService.deleteById(id);
            return new Result(true, MessageConstant.DELETE_CHECKITEM_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    //id查询检查项
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("findById")
    public Result findById(@RequestParam("id") Integer id) {
        try {
            CheckItem checkItem = checkItemService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }
    //编辑检查项
    @PreAuthorize("hasAuthority('CHECKITEM_EDIT')")
    @RequestMapping("edit")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.EDIT_CHECKITEM_FAIL);
        }

    }
    //检查项查询所有
    @PreAuthorize("hasAuthority('CHECKITEM_QUERY')")
    @RequestMapping("findAll")
    public Result findAll(){
        try {
            List<CheckItem> list = checkItemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

}
