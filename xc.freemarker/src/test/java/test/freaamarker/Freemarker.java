package test.freaamarker;

import com.xuecheng.test.freemarker.model.Student;
import freemarker.cache.StringTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * @Author: Lacne
 * @Date: 2019/1/18 10:26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class Freemarker {

    /**
     * 测试静态化,基于ftl模板文件生成html文件
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void testGenerateHtml() throws IOException, TemplateException {
        //定义配置类
        Configuration configuration = new Configuration(Configuration.getVersion());
        //得到classpath的路径
        String path = this.getClass().getResource("/").getPath();
        //定义模板路径
        configuration.setDirectoryForTemplateLoading(new File(path + "/templates/"));
        //设置字符集
        configuration.setDefaultEncoding("utf-8");
        //获取模板文件的内容
        Template template = configuration.getTemplate("test1.ftl");
        //定义数据模型
        Map map = getMap();
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        //测试查看
        System.out.println(content);
        //生成io流
        InputStream is = IOUtils.toInputStream(content);
        FileOutputStream ops = new FileOutputStream(new File("D:\\JetBrains\\IdeaWorkSpace\\E-Learning\\test-freemarker\\src\\result"));
        //输出文件
        IOUtils.copy(is, ops);
        //关流
        ops.close();
        is.close();
    }

    /**
     * 获取数据模型
     * @return
     */
    private Map getMap() {
        Map map = new HashMap();
        //向数据模型放数据
        map.put("name","test");
        Student stu1 = new Student();
        stu1.setName("小明");
        stu1.setAge(18);
        stu1.setMoney(1000.86f);
        stu1.setBirthday(new Date());
        Student stu2 = new Student();
        stu2.setName("小红");
        stu2.setMoney(200.1f);
        stu2.setAge(19);
        stu2.setBirthday(new Date());
        List<Student> friends = new ArrayList<>();
        friends.add(stu1);
        stu2.setFriends(friends);
        stu2.setBestFriend(stu1);
        List<Student> stus = new ArrayList<>();
        stus.add(stu1);
        stus.add(stu2);
        //向数据模型放数据
        map.put("stus",stus);
        //准备map数据
        HashMap<String,Student> stuMap = new HashMap<>();
        stuMap.put("stu1",stu1);
        stuMap.put("stu2",stu2);
        //向数据模型放数据
        map.put("stu1",stu1);
        //向数据模型放数据
        map.put("stuMap",stuMap);
        map.put("point", 102920122);
        return map;
    }

    /**
     * 基于模板字符串生成静态化文件
     * @throws IOException
     * @throws TemplateException
     */
    @Test
    public void testGenerateHtmlByString() throws IOException, TemplateException {
        //创建配置类
        Configuration configuration=new Configuration(Configuration.getVersion());
        //模板内容，这里测试时使用简单的字符串作为模板
        String templateString="" +
                "<html>\n" +
                " <head></head>\n" +
                " <body>\n" +
                " 名称：${name}\n" +
                " </body>\n" +
                "</html>";
        //模板加载器
        StringTemplateLoader stringTemplateLoader = new StringTemplateLoader();
        stringTemplateLoader.putTemplate("template",templateString);
        configuration.setTemplateLoader(stringTemplateLoader);
        //得到模板
        Template template = configuration.getTemplate("template","utf‐8");
        //数据模型
        Map<String,Object> map = new HashMap<>();
        map.put("name","黑马程序员");
        //静态化
        String content = FreeMarkerTemplateUtils.processTemplateIntoString(template, map);
        //测试查看
        System.out.println(content);
        //生成io流
        InputStream is = IOUtils.toInputStream(content);
        FileOutputStream ops = new FileOutputStream(new File("D:\\JetBrains\\IdeaWorkSpace\\E-Learning\\test-freemarker\\src\\result"));
        //输出文件
        IOUtils.copy(is, ops);
        //关流
        ops.close();
        is.close();
    }

}
