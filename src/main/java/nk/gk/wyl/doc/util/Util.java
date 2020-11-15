package nk.gk.wyl.doc.util;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.*;

/**
 * @Description: 工具类
 * @Author: zhangshuailing
 * @CreateDate: 2020/8/13 21:23
 * @UpdateUser: zhangshuailing
 * @UpdateDate: 2020/8/13 21:23
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class Util {
    /**
     * title 获取唯一流水号
     *
     * @return string
     */
    public static String getResourceId() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    /**
     * title 获取当前时间
     *
     * @return 时间
     */
    public static Date getDate() {
        return new Date();
    }

    /**
     * 获取字符串时间
     *
     * @return 返回字符串时间
     */
    public static String getStrDate() {
        return DateFormatUtils.format(getDate(), "yyyy-MM-dd HH:mm:ss");
    }

    public static void main(String[] args) {
        String project_path = System.getProperty("user.dir");
        System.out.println(project_path);
    }

    /**
     * 生成路径.
     * @return String
     */
    public static String createPath(){
        String nyr  = DateFormatUtils.format(getDate(),"yyyyMMddHHmmss/");
        return nyr;
    }



}
