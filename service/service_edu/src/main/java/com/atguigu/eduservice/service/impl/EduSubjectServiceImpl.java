package com.atguigu.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.atguigu.eduservice.entity.EduSubject;
import com.atguigu.eduservice.entity.excel.SubjectData;
import com.atguigu.eduservice.entity.subject.OneSubject;
import com.atguigu.eduservice.entity.subject.TwoSubject;
import com.atguigu.eduservice.listener.SubjectExcelListener;
import com.atguigu.eduservice.mapper.EduSubjectMapper;
import com.atguigu.eduservice.service.EduSubjectService;
import com.atguigu.servicebase.exceptionhandler.GuliException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-06
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {
    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file,EduSubjectService subjectService) {
        try {
            //1 获取文件输入流
            InputStream inputStream = file.getInputStream();

            // 这里 需要指定读用哪个class去读，然后读取第一个sheet 文件流会自动关闭
            EasyExcel.read(inputStream, SubjectData.class, new SubjectExcelListener(subjectService)).sheet().doRead();
        }catch(Exception e) {
            e.printStackTrace();
            throw new GuliException(20002,"添加课程分类失败");
        }
    }
    //课程分类列表(树形)
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //查询所有一级分类
        QueryWrapper<EduSubject> wrapperOne = new QueryWrapper<>();
        wrapperOne.eq("parent_id","0");
        List<EduSubject> oneSubjectList = baseMapper.selectList(wrapperOne);
        //查询所有二级分类
        QueryWrapper<EduSubject> wrapperTwo = new QueryWrapper<>();
        wrapperOne.ne("parent_id","0");
        List<EduSubject> twoSubjectList = baseMapper.selectList(wrapperTwo);

        //创建list集合，用于最终的封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //封装所有一级分类
        //封装到要求的list集合里面
        for(int i=0;i<oneSubjectList.size();i++){
            //得到oneSubjectList里面的每个oneSubject对象
            EduSubject eduSubject = oneSubjectList.get(i);

            //把EduSubject里面的对象获取出来，放在oneSubject里面
            OneSubject oneSubject = new OneSubject();
            BeanUtils.copyProperties(eduSubject,oneSubject);

            finalSubjectList.add(oneSubject);

            //在一级分类中遍历查询所有的二级分类
            //创建list结合封装每个一级分类的二级分类
            List<TwoSubject> twoFinalSubject = new ArrayList<>();

            for (int m = 0; m < twoSubjectList.size(); m++) {
                EduSubject teduSubject = twoSubjectList.get(m);

                if (teduSubject.getParentId().equals(eduSubject.getId())){
                    //将teduSubject的值复制到TwoSubject，然后放到twoFinalSubject里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(teduSubject,twoSubject);
                    twoFinalSubject.add(twoSubject);
                }
            }

            //把一级下面的所有二级分类放到一级分类里面
            oneSubject.setChildren(twoFinalSubject);

        }
        return finalSubjectList;
        //封装所有二级分类
    }
}
