package com.guli.edu.service.impl;

import com.atguigu.eduservice.entity.EduTeacher;
import com.atguigu.eduservice.mapper.EduTeacherMapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.guli.edu.query.TeacherQuery;
import com.guli.edu.service.TeacherService;
import org.springframework.stereotype.Service;

@Service
public class TeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements TeacherService {

    @Override
    public void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery) {

        QueryWrapper<EduTeacher> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("sort");

        if (teacherQuery == null){
            baseMapper.selectPage(pageParam, queryWrapper);
            return;
        }

        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();

        if (!StringUtils.isEmpty(name)) {
            queryWrapper.like("name", name);
        }

        if (!StringUtils.isEmpty(level.toString()) ) {
            queryWrapper.eq("level", level);
        }

        if (!StringUtils.isEmpty(begin)) {
            queryWrapper.ge("gmt_create", begin);
        }

        if (!StringUtils.isEmpty(end)) {
            queryWrapper.le("gmt_create", end);
        }

        baseMapper.selectPage(pageParam, queryWrapper);
    }
}