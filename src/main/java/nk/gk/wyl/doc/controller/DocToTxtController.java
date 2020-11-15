package nk.gk.wyl.doc.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import nk.gk.wyl.doc.api.DocToTxtService;
import nk.gk.wyl.doc.entity.result.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("api/v1/doctotxt")
@Api(tags = "doc docx 转txt文本接口")
public class DocToTxtController {
    @Autowired
    private DocToTxtService docToTxtService;

    @PostMapping(value = "upload",consumes = "multipart/*",headers = "content-type=multipart/form-data")
    @ApiOperation(value = "word文件上传转txt")
    public @ResponseBody Response upload(MultipartFile file){
        try {
            return new Response().success(docToTxtService.upload(file));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }


    @PostMapping(value = "uploadTxt",consumes = "multipart/*",headers = "content-type=multipart/form-data")
    @ApiOperation(value = "word文件上传返回文本")
    public @ResponseBody Response uploadTxt(MultipartFile file){
        try {
            return new Response().success(docToTxtService.uploadTxt(file));
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }

    /**
     * 下载
     * @param id 文件
     * @param request
     * @param response
     * @return
     */
    @GetMapping(value = "download")
    @ApiOperation(value = "下载文件")
    public @ResponseBody Response download(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response){
        try {
            docToTxtService.download(id,request,response);
            return null;
        } catch (Exception e) {
            e.printStackTrace();
            return new Response().error(e.getMessage());
        }
    }
}
