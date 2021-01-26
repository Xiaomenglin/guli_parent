package com.guli.edu.service;

import com.atguigu.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.guli.edu.query.TeacherQuery;

public interface TeacherService extends IService<EduTeacher> {

    void pageQuery(Page<EduTeacher> pageParam, TeacherQuery teacherQuery);
}