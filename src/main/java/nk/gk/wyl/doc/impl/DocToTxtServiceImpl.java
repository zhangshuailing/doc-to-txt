package nk.gk.wyl.doc.impl;

import nk.gk.wyl.doc.api.DocToTxtService;
import nk.gk.wyl.doc.entity.search.TxtModel;
import nk.gk.wyl.doc.util.CommonValidator;
import nk.gk.wyl.doc.util.Util;
import nk.gk.wyl.doc.util.file.DocToTxt;
import nk.gk.wyl.doc.util.file.FileUtil;
import nk.gk.wyl.doc.util.file.UploadFile;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

@Service
public class DocToTxtServiceImpl implements DocToTxtService {
    // 文件存放路径
    private static String  file_path;

    public static String getFile_path() {
        return file_path;
    }

    public static void setFile_path(String file_path) {
        DocToTxtServiceImpl.file_path = file_path;
    }

    /**
     * 根据文件流返回文件编号和文件内容
     *
     * @param file
     * @return
     */
    @Override
    public TxtModel upload(MultipartFile file) throws Exception {
        // 文件后缀
        String suffix = CommonValidator.checkFile(file);
       /* String upload_file_path = UploadFile.uploadFile(file,file_path,suffix);
        File file1 = new File(file_path+upload_file_path);
        FileInputStream inputStream = new FileInputStream(file1);*/
        String id = Util.getResourceId()+".txt";
        String content = DocToTxt.wordToTxtFile(file.getInputStream(),suffix,file_path+"txt/"+id);
        // 返回值
        TxtModel txtModel = new TxtModel();
        txtModel.setContent(content);
        txtModel.setFileId(id);
        return txtModel;
    }

    /**
     * 据文件流返回文件内容
     *
     * @param file
     * @return
     */
    @Override
    public String uploadTxt(MultipartFile file) throws Exception {
        // 文件后缀
        String suffix = CommonValidator.checkFile(file);
       /* String upload_file_path = UploadFile.uploadFile(file,file_path,suffix);
        File file1 = new File(file_path+upload_file_path);
        FileInputStream inputStream = new FileInputStream(file1);*/
        String text = getTxtByDocOrDocxInputStream(file.getInputStream(),suffix);
        return text;
    }

    /**
     * 下载文件
     *
     * @param id
     * @throws Exception
     */
    @Override
    public void download(String id, HttpServletRequest request, HttpServletResponse response) throws Exception {
        String root = file_path+"word/";
        File file = new File(root+id);
        if(!file.exists()){
            root = file_path+"txt/";
            file = new File(root+id);
        }
        if(!file.exists()){
            throw new Exception("文件已清除");
        }
        FileUtil.download(request,response,root,id);
    }

    /**
     * 通过文件流获取文本
     *
     * @param fileInputStream FileInputStream文件流
     * @return 返回文本
     * @throws Exception 异常信息
     */
    @Override
    public String getTxtByDocOrDocxInputStream(InputStream fileInputStream,String suffix) throws Exception {
        return  DocToTxt.wordToTxt(fileInputStream,suffix);
    }


}
