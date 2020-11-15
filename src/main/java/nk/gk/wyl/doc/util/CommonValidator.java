package nk.gk.wyl.doc.util;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

/**
 * @Description: 参数校验
 * @Author: zhangshuailing
 * @CreateDate: 2020/8/29 9:45
 * @UpdateUser: zhangshuailing
 * @UpdateDate: 2020/8/29 9:45
 * @UpdateRemark: 修改内容
 * @Version: 1.0
 */
public class CommonValidator {

    private static String DOC = "doc";
    private static String DOCX = "docx";
    private static String TXT = "txt";

    /**
     * 校验doc docx
     * @param file 文件
     * @throws Exception 异常信息
     */
    public static String checkFile(MultipartFile file) throws Exception {
        String name = file.getOriginalFilename();

        if(name.lastIndexOf(".")==-1){
            throw new Exception("上传文件格式必须是 "+DOC+"、"+DOCX+"、"+TXT);
        }
        String suffix = name.substring(name.lastIndexOf(".")+1,name.length());
        // 判断文件后缀 doc docx
        if(!DOC.equals(suffix.toLowerCase())&&!DOCX.equals(suffix.toLowerCase())&&!TXT.equals(suffix.toLowerCase())){
            throw new Exception("上传文件格式必须是 "+DOC+"、"+DOCX+"、"+TXT);
        }
        long size = file.getSize();
        if(size==0){
            throw new Exception("上传的文件为空");
        }
        return suffix.toLowerCase();
    }

}
