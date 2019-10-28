package filehelper.sys.model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.swing.plaf.metal.MetalIconFactory.FileIcon16;

import filehelper.helper.ConfigHelper;
import filehelper.helper.TxtHelper;

public class DirAndFilesIn implements Serializable, Comparable<DirAndFilesIn> {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String dir;
	private List<String> files;
	private String filePath;
	private String date;

	public String getDir() {
		return dir;
	}

	public void setDir(String dir) {
		this.dir = dir;
	}

	public List<String> getFiles() {
		return files;
	}

	public void setFiles(List<String> files) {
		this.files = files;
	}

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
		return "DirAndFilesIn [dir=" + dir + ", files=" + files + ", filePath="
				+ filePath + ", date=" + date + "]";
	}

	/**
	 * 删除文件夹下的某一个文件
	 * 
	 * @param fileName
	 *            文件名
	 * @param dirIn
	 *            DirAndFilesIn对象
	 * @return
	 */
	public DirAndFilesIn deleteFileInDir(String fileName, DirAndFilesIn dirIn) {

		List<String> filesIn = dirIn.getFiles();
		if (filesIn != null && filesIn.size() > 0) {
			for (int i = 0; i < filesIn.size(); i++) {
				if (filesIn.get(i) == fileName
						|| filesIn.get(i).equals(fileName)) {
					filesIn.remove(fileName);
				}
			}
			dirIn.setFiles(filesIn);
		}
		return dirIn;
	}

	/**
	 * 根据目录名查找文件
	 * 
	 * @param value
	 * @return
	 */
	public Map<String, Object> findDirByValue(String value, String date) {
		DirAndFilesIn tempDir = null;
		Map<String, Object> map = new HashMap<String, Object>();
		List<DirAndFilesIn> dirIns = TxtHelper.readFromTxt(ConfigHelper
				.rightTaskPath());
		for (int i = 0; i < dirIns.size(); i++) {
			String dir = dirIns.get(i).getDir();
			String dateIn = dirIns.get(i).getDate();
			if (((dir == value || dir.contains(value)||dir.equals(value)))) {
				map.put("index", i);
				map.put("obj", dirIns.get(i));
				break;
			}
		}
		return map;
	}
	

	@Override
	public int compareTo(DirAndFilesIn dirIn) {
		// TODO Auto-generated method stub
		return dirIn.getDate().compareTo(this.getDate());
	}
}
