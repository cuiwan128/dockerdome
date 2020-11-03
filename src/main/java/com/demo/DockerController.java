package com.demo;

import com.alibaba.excel.EasyExcel;
import com.demo.utils.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.ResourceUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

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

}
