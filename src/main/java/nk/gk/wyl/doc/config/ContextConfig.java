package nk.gk.wyl.doc.config;

import nk.gk.wyl.doc.impl.DocToTxtServiceImpl;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * 文件初始化数据
 */
@Component
public class ContextConfig {

    @Value("${file_path}")
    private String  file_path;

    @Bean
    public int initStatic(){
        DocToTxtServiceImpl.setFile_path(getContextPath()+file_path);
        return 0;
    }

    /**
     * 获取项目路径
     * @return
     */
    public String getContextPath(){
        String path = System.getProperty("user.dir");
        return path;
    }
}
