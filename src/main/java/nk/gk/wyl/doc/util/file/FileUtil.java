package nk.gk.wyl.doc.util.file;


import nk.gk.wyl.doc.util.Util;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.channels.FileChannel;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class FileUtil {
    /**
     * 删除文件
     *
     * @param file_path
     */
    public static Map<String,Object> _deleteFile(String root_path,String file_path){
        String path = root_path + file_path;
        // 返回值  key : value  一共两组   is_delete:false,msg:未删除原因
        Map<String,Object> map = new HashMap<>();
        // 表示删除成功
        String status = "0";
        File file = new File(path);
        // 错误信息
        String msg = "";
        //  判断文件是否存在
        if(file.exists()){
            if(file.isFile()){// 判断是否是文件
                // 删除文件夹(截取最后一个下划线)
                String path_directory = file.getParent();
                if(!file.delete()){
                    msg = "删除失败";
                    status = "1";
                }
                // 获取该文件下面文件数量
                File[] list_file = file.listFiles();
                if(list_file == null || list_file.length == 0){
                    // 判断该文件夹下面手否有其他文件，下面文件为空时，删除该文件夹
                    _deleteDirectory(path_directory);
                }
            }else{
                msg = "该路径是文件夹不是文件！";
                status = "2";
            }
        }else{
            msg = "文件不存在！";
            status = "3";
        }
        map.put("is_delete",status);
        map.put("msg",msg);
        map.put("file_path",file_path);
        return map;
    }

    public static void main(String[] args) {
        /*String root_path = "G:\\golaxy\\zzbz\\project\\zsbz\\";
        String path = "uploadFile\\upload_info\\20191029171625";
        String  msg = _deleteDirectory(root_path+path);
        System.out.println(msg);*/

        try {
            String base64Code =encodeBase64File("/Users/Crazy/Pictures/zyb2.jpg");
            System.out.println(base64Code);
            decoderBase64File(base64Code, "/Users/Crazy/Desktop/zyb.png");
            toFile(base64Code, "/Users/Crazy/Desktop/zyb.txt");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    /**
     * 删除文件夹
     * @return
     */
    public static String _deleteDirectory(String directory){
        String  error = "";
        File file = new File(directory);
        if(file.exists()){
            if(file.isDirectory()){
               boolean bl = file.delete();
               if(!bl){
                   error = "删除失败";
               }
            }else{
                error = "该路径是文件夹不是文件夹!";
            }
        }else{
            error = "文件夹不存在";
        }
        return error;
    }

    /**
     * copy文件
     * @param source  源
     * @param dest
     * @throws IOException
     */
    public static void copyFileUsingFileChannels(File source, File dest) throws IOException {
        FileChannel inputChannel = null;
        FileChannel outputChannel = null;
        try {
            inputChannel = new FileInputStream(source).getChannel();
            outputChannel = new FileOutputStream(dest).getChannel();
            outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
        } finally {
            inputChannel.close();
            outputChannel.close();
        }
    }

    /**
     * 下载文件
     * @param request
     * @param response
     * @param file 文件
     * @throws Exception
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, File file) throws Exception{
        if(!file.exists()){
            throw new Exception("文件不存在！");
        }
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 执行下载功能
        response.setCharacterEncoding("GBK");
        //设置ContentType字段值
        response.setContentType("text/html;charset=utf-8");
        // 获取所要下载的文件名称
        String filename = request.getParameter("filename");
        //通知浏览器以下载的方式打开
        response.addHeader("Content-type", "appllication/octet-stream");
        response.setCharacterEncoding("UTF-8");
        try {
            response.addHeader("Content-Disposition", "attachment; filename="+new String(file.getName().getBytes("UTF-8"),"ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fileName= Util.getResourceId()+"."+suffix;
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
        }
        //通知文件流读取文件
        InputStream in = new FileInputStream(file);
        // 删除生成的文件
        //file.delete();
        //获取response对象的输出流
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        //循环取出流中的数据
        while((len = in.read(buffer)) != -1){
            out.write(buffer,0,len);
        }
        // 关闭流
        in.close();
        out.close();
    }

    /**
     * 下载文件
     * @param request
     * @param response
     * @param root_path
     * @param path
     * @throws Exception
     */
    public static void download(HttpServletRequest request, HttpServletResponse response, String root_path, String path) throws Exception{
        File file = new File(root_path+path);
        if(!file.exists()){
            throw new Exception("文件不存在！");
        }
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 执行下载功能
        response.setCharacterEncoding("GBK");
        //设置ContentType字段值
        response.setContentType("text/html;charset=utf-8");
        // 获取所要下载的文件名称
        String filename = request.getParameter("filename");
        //通知浏览器以下载的方式打开
        response.addHeader("Content-type", "appllication/octet-stream");
        response.setCharacterEncoding("UTF-8");
        try {
            response.addHeader("Content-Disposition", "attachment; filename="+new String(file.getName().getBytes("UTF-8"),"ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fileName= Util.getResourceId()+"."+suffix;
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
        }
        //通知文件流读取文件
        InputStream in = new FileInputStream(file);
        // 删除生成的文件
        //file.delete();
        //获取response对象的输出流
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        //循环取出流中的数据
        while((len = in.read(buffer)) != -1){
            out.write(buffer,0,len);
        }
        // 关闭流
        in.close();
        out.close();
    }

    /**
     * <p>将文件转成base64 字符串</p>
     * @param path 文件路径
     * @return
     * @throws Exception
     */
    public static String encodeBase64File(String path) throws Exception {
        File file = new File(path);
        FileInputStream inputFile = new FileInputStream(file);
        byte[] buffer = new byte[(int)file.length()];
        inputFile.read(buffer);
        inputFile.close();
        return new BASE64Encoder().encode(buffer);
    }

    /**
     * 本地图片转换Base64的方法
     *
     * @param imgPath     
     */

    public static String ImageToBase64(String imgPath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgPath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        // 对字节数组Base64编码
        BASE64Encoder encoder = new BASE64Encoder();
        // 返回Base64编码过的字节数组字符串
       return encoder.encode(Objects.requireNonNull(data));
    }
    /**
     * <p>将base64字符解码保存文件</p>
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void decoderBase64File(String base64Code,String targetPath) throws Exception {
        byte[] buffer = new BASE64Decoder().decodeBuffer(base64Code);
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
    /**
     * <p>将base64字符保存文本文件</p>
     * @param base64Code
     * @param targetPath
     * @throws Exception
     */
    public static void toFile(String base64Code,String targetPath) throws Exception {
        byte[] buffer = base64Code.getBytes();
        FileOutputStream out = new FileOutputStream(targetPath);
        out.write(buffer);
        out.close();
    }
    /**
     * 生成zip 文件
     * @param list
     * @param file_name
     * @param root_path
     * @return
     */
    public static String createZip( String root_path,String file_name,List<String> list) throws Exception{
        if(list==null || list.size()==0){
            throw new Exception("生成zip所需要的文件list不能为空！");
        }
        // 文件夹
        String file_path = "zip/"+ Util.createPath();

        if("".equals(file_name)){
            file_name="批量下载";
        }
        // 创建文件夹
        File file = new File(root_path+file_path);
        if(!file.exists()){
            file.mkdirs();
        }
        // 文件路径
        file_path = file_path + file_name+".zip";
        byte[] buffer = new byte[1024];
        String zPath = root_path + file_path;
        File file_d = new File(zPath);
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zPath));
            // 循环下载文件
            for (String file_path_one:list){
                File file1 = new File(file_path_one);
                if(!"".equals(file_path_one)){
                    FileInputStream fis = new FileInputStream(root_path+file_path_one);
                    out.putNextEntry(new ZipEntry(file1.getName()));
                    int len;
                    // 读入需要下载的文件和内容，打包到zip 中
                    while((len = fis.read(buffer)) > 0){
                        out.write(buffer,0,len);
                    }
                    out.closeEntry();
                    if(fis!=null){

                        fis.close();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            throw new Exception("文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){

                out.close();
            }
        }
        return file_path;
    }
    /**
     * 生成zip 文件
     * @param list
     * @param root_path
     * @return
     */
    public static String createZip(List<Map> list, String root_path,String file_name) throws Exception{
        if(list==null || list.size()==0){
            throw new Exception("生成zip所需要的文件list不能为空！");
        }
        // 文件夹
        String file_path = "zip/"+Util.createPath();

        if("".equals(file_name)){
            file_name="批量下载";
        }
        // 创建文件夹
        File file = new File(root_path+file_path);
        if(!file.exists()){
            file.mkdirs();
        }
        // 文件路径
        file_path = file_path + file_name+".zip";
        byte[] buffer = new byte[1024];
        String zPath = root_path + file_path;
        File file_d = new File(zPath);
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zPath));
            // 循环下载文件
            for (Map map:list){
                String file_path_one = map.get("file_path") == null ? "" : map.get("file_path").toString();
                File file1 = new File(file_path_one);
                if(!"".equals(file_path_one) ){
                    FileInputStream fis = null;
                    try{
                        fis = new FileInputStream(root_path+file_path_one);
                        out.putNextEntry(new ZipEntry(file1.getName()));
                        int len;
                        // 读入需要下载的文件和内容，打包到zip 中
                        while((len = fis.read(buffer)) > 0){
                            out.write(buffer,0,len);
                        }
                        out.closeEntry();
                        if(fis!=null){
                            fis.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        continue;
                    }finally {
                        out.closeEntry();
                        if(fis!=null){
                            fis.close();
                        }
                    }

                }
            }
        } catch (FileNotFoundException e) {
            throw new Exception("文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }
        return file_path;
    }

    /**
     *
     * @param list 生成zip 的 文件集合
     * @param root_path list 中配置的文件总路径
     * @param file_path 生成的zip文件路径 可为空
     * @param file_name 生成zip文件的名称
     * @param create_bw_path 指定生成的文件路径
     * @throws Exception
     */
    public static void createZipFileBw(List<Map> list,String root_path,String file_path,String file_name,String create_bw_path) throws Exception{
        if(list==null || list.size()==0){
            throw new Exception("生成zip所需要的文件list不能为空！");
        }
        // 文件夹
        if("".equals(file_path)){
            file_path = create_bw_path;
        }else{
            file_path = create_bw_path + file_path;
        }
        if("".equals(file_name)){
            file_name="批量下载";
        }
        // 创建文件夹
        File file = new File(file_path);
        if(!file.exists()){
            file.mkdirs();
        }
        // 文件路径
        file_path = file_path + "/"+file_name+".zip";
        byte[] buffer = new byte[1024];
        String zPath = file_path;
        File file_d = new File(zPath);
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zPath));
            // 循环下载文件
            for (Map map:list){
                String file_path_one = map.get("file_path") == null ? "" : map.get("file_path").toString();
                File file1 = new File(root_path+file_path_one);
                if(!"".equals(file_path_one) ){
                    FileInputStream fis = null;
                    try{
                        fis = new FileInputStream(root_path+file_path_one);
                        out.putNextEntry(new ZipEntry(file1.getName()));
                        int len;
                        // 读入需要下载的文件和内容，打包到zip 中
                        while((len = fis.read(buffer)) > 0){
                            out.write(buffer,0,len);
                        }
                        out.closeEntry();
                        if(fis!=null){
                            fis.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        continue;
                    }finally {
                        out.closeEntry();
                        if(fis!=null){
                            fis.close();
                        }
                    }

                }
            }
        } catch (FileNotFoundException e) {
            throw new Exception("文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }
    }

    /**
     * 生成zip 文件
     * @param list
     * @param file_path  将file_name  文件放在下方
     * @param root_path  配置的路径
     * @param file_name  文件名
     * @return
     */
    public static void createZipFile(List<Map> list,String root_path,String file_path,String file_name) throws Exception{
        if(list==null || list.size()==0){
            throw new Exception("生成zip所需要的文件list不能为空！");
        }
        // 文件夹
        if("".equals(file_path)){
            file_path = "zip/"+Util.createPath();
        }
        if("".equals(file_name)){
            file_name="批量下载";
        }
        // 创建文件夹
        File file = new File(file_path);
        if(!file.exists()){
            file.mkdirs();
        }
        // 文件路径
        file_path = file_path + "/"+file_name+".zip";
        byte[] buffer = new byte[1024];
        String zPath = file_path;
        File file_d = new File(zPath);
        ZipOutputStream out = null;
        try {
            out = new ZipOutputStream(new FileOutputStream(zPath));
            // 循环下载文件
            for (Map map:list){
                String file_path_one = map.get("file_path") == null ? "" : map.get("file_path").toString();
                File file1 = new File(root_path+file_path_one);
                if(!"".equals(file_path_one) ){
                    FileInputStream fis = null;
                    try{
                        fis = new FileInputStream(root_path+file_path_one);
                        out.putNextEntry(new ZipEntry(file1.getName()));
                        int len;
                        // 读入需要下载的文件和内容，打包到zip 中
                        while((len = fis.read(buffer)) > 0){
                            out.write(buffer,0,len);
                        }
                        out.closeEntry();
                        if(fis!=null){
                            fis.close();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        continue;
                    }finally {
                        out.closeEntry();
                        if(fis!=null){
                            fis.close();
                        }
                    }

                }
            }
        } catch (FileNotFoundException e) {
            throw new Exception("文件不存在");
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(out!=null){
                out.close();
            }
        }
    }

    /**
     * 下载文件
     * @param request
     * @param response
     * @param root_path
     * @param path
     * @throws Exception
     */
    public static void downloadAndDelete(HttpServletRequest request, HttpServletResponse response, String root_path, String path) throws Exception{
        File file = new File(root_path+path);
        if(!file.exists()){
            throw new Exception("文件不存在！");
        }
        String fileName = file.getName();
        String suffix = fileName.substring(fileName.lastIndexOf(".") + 1);
        // 执行下载功能
        response.setCharacterEncoding("GBK");
        //设置ContentType字段值
        response.setContentType("text/html;charset=utf-8");
        // 获取所要下载的文件名称
        String filename = request.getParameter("filename");
        //通知浏览器以下载的方式打开
        response.addHeader("Content-type", "appllication/octet-stream");
        response.setCharacterEncoding("UTF-8");
        try {
            response.addHeader("Content-Disposition", "attachment; filename="+new String(file.getName().getBytes("UTF-8"),"ISO8859-1"));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            fileName= Util.getResourceId()+"."+suffix;
            response.addHeader("Content-Disposition", "attachment; filename="+fileName);
        }
        //通知文件流读取文件
        InputStream in = new FileInputStream(file);
        // 删除生成的文件
        //file.delete();
        //获取response对象的输出流
        OutputStream out = response.getOutputStream();
        byte[] buffer = new byte[1024];
        int len;
        //循环取出流中的数据
        while((len = in.read(buffer)) != -1){
            out.write(buffer,0,len);
        }
        // 关闭流
        in.close();
        out.close();
        _deleteFile(root_path,path);
    }
}
