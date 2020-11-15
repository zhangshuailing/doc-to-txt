package nk.gk.wyl.doc.util.file;

import java.io.*;

/**
 * txt工具类
 */
public class TxtUtil {
    public static void main(String[] args) {
        String path ="G:\\demo.txt";
        System.out.println(readTxt(path));
    }
    /**
     * txt文件读取
     * @param txtFilePath txt文件路径
     * @return
     */
    public static String readTxt(String txtFilePath){
        StringBuffer strbuffer = new StringBuffer();
        File myFile = new File(txtFilePath);
        if (!myFile.exists()) {
            System.err.println("Can't Find " + txtFilePath);
        }
        try {
            FileInputStream fis = new FileInputStream(txtFilePath);
            InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
            BufferedReader in  = new BufferedReader(inputStreamReader);

            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str+"\r\n");  //new String(str,"UTF-8")
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        //System.out.println("读取文件结束util");
        return strbuffer.toString();
    }

    /**
     * txt文件读取
     * @param fileInputStream txt文件流
     * @return
     */
    public static String readTxt(FileInputStream fileInputStream){
        StringBuffer strbuffer = new StringBuffer();
        String encoding = "GBK"; // 字符编码(可解决中文乱码问题 )
        try {
            InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encoding);
            BufferedReader in  = new BufferedReader(inputStreamReader);
            String str;
            while ((str = in.readLine()) != null) {
                strbuffer.append(str+"\r\n");  //new String(str,"UTF-8")
            }
            in.close();
        } catch (IOException e) {
            e.getStackTrace();
        }
        return strbuffer.toString();
    }


}
