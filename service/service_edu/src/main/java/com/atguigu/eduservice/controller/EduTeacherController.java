package com.atguigu.eduservice.controller;


import com.atguigu.commonutils.R;
import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.service.EduTeacherService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.guli.edu.query.TeacherQuery;
import com.guli.edu.service.TeacherService;
import com.guli.edu.service.impl.TeacherServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2021-01-23
 */
@Api(description = "讲师管理")
@RestController
@RequestMapping("/eduservice/teacher")
@CrossOrigin
public class EduTeacherController {

    //把service注入
    @Autowired
    private EduTeacherService eduTeacherService;

    //查询讲师所有数据
    //rest风格
    @ApiOperation(value = "所有讲师列表")
    @GetMapping("findAll")
    public R findAllteacher(){
        //调用service的方法实现查询所有的操作
        List<EduTeacher> list = eduTeacherService.list(null);
        return R.ok().data("items",list);
    }

    //讲师逻辑删除
    @ApiOperation(value = "根据id删除讲师.")
    @DeleteMapping("{id}")
    public R removeTeacher(@ApiParam(name = "id", value = "讲师ID", required = true) @PathVariable String id){
        boolean flag = eduTeacherService.removeById(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

    //current 当前页
    //limit 每页记录数
    @ApiOperation(value = "分页讲师列表")
    @GetMapping("{current}/{limit}")
    public R pageList(
            @ApiParam(name = "current", value = "当前页码", required = true)
            @PathVariable Long current,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit){
        try {
            int a = 10/0;
        }catch(Exception e) {
            throw new GuliException(20001,"出现自定义异常GuliException");
        }

        Page<EduTeacher> pageParam = new Page<>(current, limit);

        eduTeacherService.page(pageParam, null);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "分页条件讲师列表查询")
    @PostMapping ("pageTeacherCondition/{page}/{limit}")
    public R pageQuery(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,

            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,

            @ApiParam(name = "teacherQuery", value = "查询对象", required = false)
                    @RequestBody(required = false) TeacherQuery teacherQuery){
        //创建page对象
        Page<EduTeacher> pageParam = new Page<>(page, limit);
        //构建条件
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            wrapper.like("name", name);
        }

        if (level!=null) {
            wrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            wrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            wrapper.le("gmt_create", end);
        }
        //默认根据添加时间降序排序
        wrapper.orderByDesc("gmt_create");

        //调用分页条件查询
        eduTeacherService.page(pageParam, wrapper);
        List<EduTeacher> records = pageParam.getRecords();
        long total = pageParam.getTotal();

        return  R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation(value = "新增讲师")
    @PostMapping("addTeacher")
    public R addTeacher(
            @ApiParam(name = "teacher", value = "讲师对象", required = true)
            @RequestBody EduTeacher eduTeacher){

        boolean flag = eduTeacherService.save(eduTeacher);
        if (flag){
            return R.ok();
        }else {
            return R.error();
        }

    }

    @ApiOperation(value = "根据ID查询讲师")
    @GetMapping("getTeacher/{id}")
    public R getTeacher(
            @ApiParam(name = "id", value = "讲师ID", required = true)
            @PathVariable String id){

        EduTeacher eduTeacher = eduTeacherService.getById(id);
        return R.ok().data("item", eduTeacher);
    }

    @ApiOperation(value = "讲师修改功能")
    @PostMapping("updateTeacher")
    public R updateTeacher(@ApiParam(name = "teacher", value = "讲师对象", required = true)
                           @RequestBody EduTeacher eduTeacher){
        boolean flag = eduTeacherService.updateById(eduTeacher);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }

    }



}

