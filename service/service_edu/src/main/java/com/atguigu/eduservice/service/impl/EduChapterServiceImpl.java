package com.atguigu.eduservice.service.impl;

import com.atguigu.eduservice.entity.EduChapter;
import com.atguigu.eduservice.entity.EduVideo;
import com.atguigu.eduservice.entity.chapter.ChapterVo;
import com.atguigu.eduservice.entity.chapter.VideoVo;
import com.atguigu.eduservice.mapper.EduChapterMapper;
import com.atguigu.eduservice.service.EduChapterService;
import com.atguigu.eduservice.service.EduVideoService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2021-05-30
 */
@Service
public class EduChapterServiceImpl extends ServiceImpl<EduChapterMapper, EduChapter> implements EduChapterService {

    @Autowired
    private EduVideoService eduVideoService;

    @Override
    public List<ChapterVo> getChapterVideoByCourseId(String courseId) {
        //根据课程ID查询章节
        QueryWrapper<EduChapter> chapterid = new QueryWrapper<>();
        chapterid.eq("course_id",courseId);
        List<EduChapter> eduChapterList = baseMapper.selectList(chapterid);

        //根据课程ID查询小节
        QueryWrapper<EduVideo> videoid = new QueryWrapper<>();
        videoid.eq("course_id",courseId);
        List<EduVideo> videoVoList = eduVideoService.list(videoid);

        //最终数据的封装
        ArrayList<ChapterVo> finalList = new ArrayList<>();

        //遍历list对章节进行封装
        //遍历所有list章节
        for (int i = 0; i < eduChapterList.size(); i++) {
            //每个章节
            EduChapter eduChapter = eduChapterList.get(i);
            ChapterVo chapterVo = new ChapterVo();
            //将eduChaper中的值复制到chapterVo中
            BeanUtils.copyProperties(eduChapter,chapterVo);
            //将chapterVo放到chapterVoArrayList中
            finalList.add(chapterVo);

            //封装最终的章节
            ArrayList<VideoVo> voArrayList = new ArrayList<>();

            for (int i1 = 0; i1 < videoVoList.size(); i1++) {
                EduVideo eduVideo = videoVoList.get(i1);
                VideoVo videoVo = new VideoVo();
                //判断小节里面的chapterId是否等于章节的ID
                if(eduVideo.getChapterId().equals(eduChapter.getId())){
                    //进行封装
                    BeanUtils.copyProperties(eduVideo,videoVo);
                    //放到小节封装集合中
                    voArrayList.add(videoVo);
                }

            }
            chapterVo.setChildren(voArrayList);
        }

        //遍历list对小节进行封装

        return finalList;
    }
}
