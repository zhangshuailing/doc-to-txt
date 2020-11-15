package nk.gk.wyl.doc.util.file;

import java.io.*;


public class Output {
	//测试
	public static void main(String[] args){
		String json = "null";
		try {
			json = readJsonData("I:\\History_Project\\echarts\\life-expectancy.json");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(json);
	}

	public static String readJsonData(String pactFile) throws IOException {
		// 读取文件数据
		//System.out.println("读取文件数据util");

		StringBuffer strbuffer = new StringBuffer();
		File myFile = new File(pactFile);//"D:"+File.separatorChar+"DStores.json"
		if (!myFile.exists()) {
			System.err.println("Can't Find " + pactFile);
		}
		try {
			FileInputStream fis = new FileInputStream(pactFile);
			InputStreamReader inputStreamReader = new InputStreamReader(fis, "UTF-8");
			BufferedReader in  = new BufferedReader(inputStreamReader);

			String str;
			while ((str = in.readLine()) != null) {
				strbuffer.append(str);  //new String(str,"UTF-8")
			}
			in.close();
		} catch (IOException e) {
			e.getStackTrace();
		}
		//System.out.println("读取文件结束util");
		return strbuffer.toString();
	}

	/**
	 * 写入文件
	 * @param json
	 * @param path
	 * @throws IOException
	 */
	public static void write(String json,String path) throws IOException {
		File file = new File(path);
		String parenPath = file.getParent();
		File file1 = new File(parenPath);
		if(!file1.exists()){
			file1.mkdirs();
		}
		FileWriter fw = new FileWriter(new File(path));
		BufferedWriter bw = new BufferedWriter(fw);
		bw.write(json);
		bw.flush();
		bw.close();
	}

	/**
	 * 写入文件
	 * @param json
	 * @param path
	 * @throws IOException
	 */
	public static void writeAppend(String json,String path) throws IOException {
		File file = new File(path);
		FileOutputStream fos = null;
		OutputStreamWriter osw = null;
		try {
			if (!file.exists()) {
				boolean hasFile = file.createNewFile();
				if(hasFile){
					System.out.println("file not exists, create new file");
				}
				fos = new FileOutputStream(file);
			} else {
				//System.out.println("file exists");
				fos = new FileOutputStream(file, true);
			}

			osw = new OutputStreamWriter(fos, "utf-8");
			osw.write(json); //写入内容
			osw.write("\r\n");  //换行
		} catch (Exception e) {
			e.printStackTrace();
		}finally {   //关闭流
			try {
				if (osw != null) {
					osw.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				if (fos != null) {
					fos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}



}
