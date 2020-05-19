package com.itheima.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.constant.MessageConstant;
import com.itheima.entity.PageResult;
import com.itheima.entity.QueryPageBean;
import com.itheima.entity.Result;
import com.itheima.pojo.CheckGroup;
import com.itheima.service.CheckGroupService;
import com.itheima.service.CheckItemService;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author ShiXiaoyu
 * @date 2020-05-09 14:15
 */
@RestController
@RequestMapping("checkgroup")
public class CheckGroupController {
    @Reference
    private CheckGroupService checkGroupService;


    /**
     * 新增检查组
     *
     * @param checkItemIds
     * @param checkGroup
     * @return
     */
    @RequestMapping("add")
    public Result add(@RequestParam(value = "checkItemIds") Integer[] checkItemIds,
                      @RequestBody CheckGroup checkGroup) {

        try {
            checkGroupService.add(checkItemIds, checkGroup);
            return new Result(true, MessageConstant.ADD_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("findPage")
    public Result findPage(@RequestBody QueryPageBean queryPageBean) {
        try {
            PageResult pageResult = checkGroupService.findPage(queryPageBean.getCurrentPage(), queryPageBean.getPageSize(), queryPageBean.getQueryString());
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, pageResult);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }

    @RequestMapping("findById")
    public Result findById(@RequestParam Integer id) {
        try {
            CheckGroup checkGroup = checkGroupService.findById(id);
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_SUCCESS, checkGroup);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_CHECKGROUP_FAIL);
        }
    }

    @RequestMapping("findCheckItemIdsByCheckGroupId")
    public Result findCheckItemIdsByCheckGroupId(@RequestParam("id") Integer id) {
        try {
            List<Integer> checkItemIds = checkGroupService.findCheckItemIdsByCheckGroupId(id);
            return new Result(true, MessageConstant.QUERY_CHECKITEM_SUCCESS, checkItemIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    @RequestMapping("edit")
    public Result edit(@RequestParam("checkItemIds") Integer[] checkItemIds, @RequestBody CheckGroup checkGroup) {
        try {
            checkGroupService.edit(checkItemIds, checkGroup);
            //响应成功信息
            return new Result(true, MessageConstant.EDIT_CHECKGROUP_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            //响应错误信息
            return new Result(false, MessageConstant.EDIT_CHECKGROUP_FAIL);
        }
    }
    @RequestMapping("deleteById")
    public Result deleteById(@RequestParam("id") Integer id){
        try {
            checkGroupService.deleteById(id);
            return new Result(true,MessageConstant.DELETE_CHECKGROUP_SUCCESS);
        } catch (RuntimeException e) {
            e.printStackTrace();
            return new Result(false,e.getMessage());
        }catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKGROUP_FAIL);
        }
    }
    @RequestMapping("findAll")
    public Result findAll(){
        try {
            List<CheckGroup> list = checkGroupService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKGROUP_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKGROUP_FAIL);
        }

    }
}
