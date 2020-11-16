package nk.gk.wyl.doc.api;

import nk.gk.wyl.doc.entity.search.TxtModel;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.InputStream;

/**
 * 接口
 */
public interface DocToTxtService {

    /**
     * 根据文件流返回文件编号和文件内容
     * @param file
     * @return
     */
    TxtModel upload(MultipartFile file) throws Exception;

    /**
     * 据文件流返回文件内容
     * @param file
     * @return
     */
    String uploadTxt(MultipartFile file) throws Exception;

    /**
     * 下载文件
     * @param id 文件名称
     * @param request
     * @param response
     * @throws Exception
     */
    void download(String id, HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 通过文件流获取文本
     * @param fileInputStream 文件流
     * @param suffix 文件后缀
     * @return 返回文本
     * @throws Exception 异常信息
     */
    String getTxtByDocOrDocxInputStream(InputStream fileInputStream, String suffix) throws Exception;
}
