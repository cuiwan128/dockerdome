package com.demo;

import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.ExcelWriter;
import com.alibaba.excel.write.metadata.WriteSheet;
import com.alibaba.excel.write.metadata.fill.FillConfig;
import com.demo.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.*;

@RestController
public class DockerController {
    static Log log = LogFactory.getLog(DockerController.class);

    @RequestMapping("/")
    public String index() {
        log.info("Hello Docker!");
        return "Hello Docker!";
    }

    @RequestMapping("/test")
    public String test() {
        log.info("Hello Docker test!");
        return "Hello Docker test!";
    }

    @RequestMapping("/demo")
    public String demo() {
        log.info("Hello Docker demo!");
        return "Hello Docker demo!";
    }

    @RequestMapping("/ok")
    public String ok() {
        log.info("Hello Docker ok!");
        return "Hello Docker ok!";
    }
    @RequestMapping("/importExcel")
    public String importExcel() throws Exception {
        String path = ResourceUtils.getURL("").getPath();
        path = (path.substring(1,path.length())+"src/main/resources/").replace("/", File.separator);
        String fileName =  path+"demo.xls";
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 50; i++) {
            students.add(new Student(i+"","张三"+i, StringUtils.getIdNo(true),i,(i%2)==1?"男":"女"));
        }
        //EasyExcel.write(fileName, Student.class).sheet("Sheet1").doWrite(students);
        //File file = new File(path+ Calendar.getInstance().getTimeInMillis()+".xlsx");
        //file.createNewFile();
        // 写出 Excel 文件
        // out 可以是文件路径也可以是 OutputStream
        EasyExcel.write(path+ Calendar.getInstance().getTimeInMillis()+".xlsx")
                // 选择模板，可以是文件路径也可以传 InputStream
                .withTemplate(fileName)
                // 选择显示 sheet
                .sheet()
                // 填充数据
                .doFill(students);
        log.info("importExcel success!");
        return "importExcel success!";
    }
    @RequestMapping("/importModeExcel")
    public String importModeExcel() throws Exception {
        String path = ResourceUtils.getURL("").getPath();
        path = (path.substring(1,path.length())+"src/main/resources/").replace("/", File.separator);
        String fileName =  path+"demomode.xls";
        List<Student> students = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            students.add(new Student(i+"","张三"+i, StringUtils.getIdNo(false),i,(i%2)==1?"男":"女"));
        }
        // 设置输出目标和模板，out和template 可以是文件路径或流
        ExcelWriter excelWriter = EasyExcel.write(path+ Calendar.getInstance().getTimeInMillis()+".xls")
                .withTemplate(fileName)
                .build();
        // 创建 Sheet
        WriteSheet writeSheet = EasyExcel.writerSheet().build();
        // 这里注意 入参用了forceNewRow 代表在写入list的时候不管list下面有没有空行 都会创建一行，然后下面的数据往后移动。
        // 默认 是false，会直接使用下一行，如果没有则创建。但是这个就会把所有数据放到内存 会很耗内存
        FillConfig fillConfig = FillConfig.builder().forceNewRow(Boolean.TRUE).build();
        excelWriter.fill(students, fillConfig, writeSheet);
        // 填充普通变量
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("className", "软件工程3班");
        map.put("date", "2020年1月5号");
        excelWriter.fill(map, writeSheet);
        excelWriter.finish();
        log.info("importModeExcel success!");
        return "importModeExcel success!";
    }

}
