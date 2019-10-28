package filehelper.sys.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import filehelper.helper.ConfigHelper;
import filehelper.helper.TxtHelper;

/**
 * 文件类型Model类
 * 描述：FileTypeIn
 * <br />@version:1.0.0
 * <br />@author 邓林峰
 * <br />@email： deng_xia@sina.cn
 * <br />@date： 2016年3月24日 下午8:32:07
 */
public class FileTypeIn implements Serializable,Comparable<FileTypeIn>{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String filePath;
	private String date;
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getDate() {
		return date;
	}
	public void setDate(String date) {
		this.date = date;
	}
	@Override
	public String toString() {
		return "FileTypeIn [filePath=" + filePath + ", date=" + date + "]";
	}
	/**
	 * @param filePath
	 * @param date
	 */
	public FileTypeIn(String filePath, String date) {
		super();
		this.filePath = filePath;
		this.date = date;
	}
	
	public FileTypeIn() {
		// TODO Auto-generated constructor stub
	}
	@Override
	public int compareTo(FileTypeIn fileIn) {
		// TODO Auto-generated method stub
		return fileIn.getDate().compareTo(this.getDate());
	}
	
	/**
	 * 根据目录名查找文件
	 * 
	 * @param value
	 * @return
	 */
	public Map<String, Object> findDirByValue(String value, String date) {
		FileTypeIn fileIn=null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<FileTypeIn> filesIn = TxtHelper.readFromTxt(ConfigHelper.fileTypePath());
		for (int i = 0; i < filesIn.size(); i++) {
			String dir = filesIn.get(i).getFilePath();
			if (((dir == value || dir.contains(value))||(dir.contains(value)))) {
				map.put("index", i);
				map.put("obj", filesIn.get(i));
				break;
			}
		}
		return map;
	}
}
