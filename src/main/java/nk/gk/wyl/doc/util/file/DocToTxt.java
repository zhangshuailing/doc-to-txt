package nk.gk.wyl.doc.util.file;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

public class DocToTxt {
    private static Logger logger = LoggerFactory.getLogger(DocToTxt.class);
    private static String DOC = "doc";
    private static String DOCX = "docx";

    public static void main(String[] args) {
        /*String path = "F:\\opt\\KB-REST接口规范-v2.2.docx";
        File file  = new File(path);
        System.out.println(file);*/
        String path1 = "F:\\opt\\KB-REST接口规范-v2.2.docx";
        File file1  = new File(path1);
        try {
            FileInputStream fileInputStream = new FileInputStream(file1);
            System.out.println(wordToTxt(fileInputStream,DOCX));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 将word文件流转成txt文本文件
     * @param fileInputStream 文件流
     * @param suffix 文件前缀
     * @param path 路径
     */
    public static String wordToTxtFile(FileInputStream fileInputStream,String suffix,String path) throws Exception {
        String content = wordToTxt(fileInputStream,suffix);
        try {
            Output.write(content,path);
        } catch (IOException e) {
            throw new Exception("写入文件失败！"+e.getMessage());
        }
        return content;
    }

    /**
     * 根据文件流，文件后缀获取文本信息
     * @param fileInputStream 文件输入流
     * @param suffix 文件后缀
     * @return
     */
    public static String wordToTxt(FileInputStream fileInputStream,String suffix) throws Exception {
        String  content = "";
        if(suffix.equals(DOC)){
            content = docToTxt(fileInputStream);
        }else if(suffix.equals(DOCX)){
            content = docxToTxt(fileInputStream);
        }
        return content;
    }


    /**
     * 后缀doc 转txt
     * @param fileInputStream 文件流
     * @return 字符串
     */
    public static String docToTxt(FileInputStream fileInputStream) throws Exception {
        // 文本内容
        String content = null;
        // doc
        HWPFDocument doc = null;
        try {
            doc = new HWPFDocument(fileInputStream);
        } catch (IOException e) {
            throw new Exception("文件流异常："+e.getMessage());
        }
        content = doc.getDocumentText();
        if(content!=null){
            // 将 doc中的换行符缓存 txt 文件里面的换行符
            content = content.replaceAll("\r","\r\n");
        }
        return content==null?"":content;
    }

    /**
     * 后缀docx 转txt
     * @param fileInputStream 文件流
     * @return 字符串
     */
    public static String docxToTxt(FileInputStream fileInputStream) throws Exception {
        // 文本内容
        String content = null;
        // docx
        XWPFDocument xdoc = null;
        try {
            xdoc = new XWPFDocument(fileInputStream);
        } catch (IOException e) {
            throw new Exception("文件流异常："+e.getMessage());
        }
        XWPFWordExtractor extractor = new XWPFWordExtractor(xdoc);
        content = extractor.getText();
        if(content!=null){
            // 将 docx 中的换行符缓存 txt 文件里面的换行符
            content = content.replaceAll("\n","\r\n");
        }
        return content==null?"":content;
    }

}
