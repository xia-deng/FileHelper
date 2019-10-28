package filehelper.helper;

import java.io.File;
import java.io.IOException;

public class ModelFileHelper {

	private static final String freePath=ConfigHelper.tempModelPath();
	private static final String fileType=".doc";
	
	/**
	 * 新建文件類型
	 * @param fileName
	 */
	public static File createModel(String fileName){
		
		String path=freePath+File.separator+fileName+fileType;
		File file=new File(path);
		if(file.exists()){
			file.delete();
		}
		try {
			file.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return file;
	}
	
	/**
	 * 如果文件不存在則生成文件
	 * @param fileName
	 */
	public static void createExitsModel(String fileName){
		String path=freePath+File.separator+fileName+fileType;
		File file=new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 返回被选中得文件类型的路径
	 * @param fileName
	 * @return
	 */
	public static File returnSelectedFile(String fileName){
		String path=freePath+File.separator+fileName+fileType;
		File file=new File(path);
		if(!file.exists()){
			try {
				file.createNewFile();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return file;
	}
}
