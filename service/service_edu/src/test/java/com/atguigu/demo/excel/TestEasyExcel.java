package com.atguigu.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

public class TestEasyExcel {
    public static void main(String[] args){
//        //实现写操作
//        String filename = "/Users/zhoupeipei/文稿/Springboot/01copy0123.xlsx";
//
//        EasyExcel.write(filename,DemoData.class).sheet("学生列表").doWrite(getData());
        //实现读操作
        String filename = "/Users/zhoupeipei/文稿/Springboot/01copy0123.xlsx";

        EasyExcel.read(filename,DemoData.class,new ExcelListener()).sheet().doRead();
    }

    //创建方法返回list集合
    private static List<DemoData> getData(){
        List<DemoData> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            DemoData demoData = new DemoData();
            demoData.setSno(i);
            demoData.setSname("lucy"+i);
            list.add(demoData);
        }
        return list;
    }
}
