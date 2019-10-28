package filehelper.helper;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;


public class TxtHelper {

	// 存放txt文档得路径
	//private final static String txtPath = ConfigHelper.rightTaskPath();

	/**
	 * 读取txt文档
	 * 
	 * @param <T>
	 * @return 一行一个String
	 */
	public static <T> List<T> readFromTxt(String txtPath) {
		List<T> list = new ArrayList<>();
		ObjectInputStream reader = null;
		File file = new File(txtPath);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileInputStream in = new FileInputStream(file);
			reader = new ObjectInputStream(in);
			T lineTxt = null;
			while (in.available() > 0) {// 表示还有对象
				// String temp=reader.readLine();
				lineTxt = (T) reader.readObject();
				System.out.println(lineTxt);
				list.add(lineTxt);

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			FileUtility.closeAll(reader);
		}
		return list;
	}
	
	/**
	 * 根据Index获取第I行得对象
	 * @param <T>
	 * @param index
	 * @return
	 */
	public static <T> T getRowByIndex(String txtPath,int index){
		
		T dirAndIn=null;
		
		List<T> lists=readFromTxt(txtPath);
		for (int i = 0; i < lists.size(); i++) {
			if(i==index){
				dirAndIn=  lists.get(i);
				break;
			}
		}
		return dirAndIn;
	}
	
	

	/**
	 * 向文本中写入新的一行数据
	 * 
	 * @param <T>
	 * @param newStr
	 */
	public static <T> void writeIntoTxt(String txtPath, T obj) {

		File file = new File(txtPath);
		ObjectOutputStream writer = null;
		FileOutputStream fos = null;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			fos=new FileOutputStream(file, true);
			if (file.length() < 1) {
				writer = new ObjectOutputStream(fos);
			} else {
				writer = new NoHeaderObjectOutputStream(fos);
			}
			writer.writeObject(obj);
			// writer.write(null);
			writer.flush();
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			FileUtility.closeAll(writer);
		}
	}

	/**
	 * 删除第Index行的文本行
	 * 
	 * @param <T>
	 * @param index
	 */
	public static <T> void deleteFromTxt(String txtPath,int index) {
		List<T> lists = readFromTxt(txtPath);
		deleteAllTxt(txtPath);
		String strTemp = "";
		for (int i = 0; i < lists.size(); i++) {
			if (i != index) {
				writeIntoTxt(txtPath,lists.get(i));
			}
		}
		// System.out.println(strTemp);
		// writeIntoTxt(strTemp);
	}
	

	
	/**
	 * 清空文本内容
	 * @param txtPath
	 */
	public static void deleteAllTxt(String txtPath){
		File file = new File(txtPath);
		OutputStream out=null;
		try {
			out=new FileOutputStream(file,false);
			out.write("".getBytes());
			out.flush();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			FileUtility.closeAll(out);
		}
	}

	/**
	 * 修改第Index行的文本行
	 * 
	 * @param <T>
	 * @param index
	 */
	public static <T> List<T> editFromTxt(String txtPath,int index, T objNew) {
		File file = new File(txtPath);
		List<T> lists = readFromTxt(txtPath);
		//file.delete();
		String strTemp = "";
		for (int i = 0; i < lists.size(); i++) {
			if (i == index) {
				Object objTemp = lists.get(i);
				lists.remove(objTemp);
				lists.add(i, objNew);
			}
			
		}
		deleteAllTxt( txtPath);
		for (T t : lists) {
			writeIntoTxt(txtPath, t);
		}
		// System.out.println(strTemp);
		// writeIntoTxt(strTemp);
		return lists;
	}
	
	/**
	 * 修改所有文本行
	 * 
	 * @param <T>
	 * @param index
	 */
	public static <T> void editAllTxt(String txtPath,List<T> t) {
		deleteAllTxt(txtPath);
		for (T t2 : t) {
			writeIntoTxt(txtPath, t2);
		}
	}
	
	
}
