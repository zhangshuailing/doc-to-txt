package nk.gk.wyl.doc.util.file;

import nk.gk.wyl.doc.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public class UploadFile {

    /**
     * 文件上传
     * @param file 文件
     * @param suffix 后缀
     * @return 返回文件名称
     */
    public static String uploadFile(MultipartFile file, String file_path,String suffix) throws Exception {
        // 文件编号
        String id = Util.getResourceId()+"."+suffix;
        File file_ = new File(file_path+"word/");
        // 判断文件夹
        if(!file_.exists()){
            file_.mkdirs();
        }
        try {
            file.transferTo(new File(file_path+"word/"+id));
        } catch (IOException e) {
           throw new Exception("文件复制失败");
        }
        return "word/"+id;
    }
}
