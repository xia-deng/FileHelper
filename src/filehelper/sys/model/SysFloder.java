package filehelper.sys.model;

public class SysFloder {

	private String dirCollection;
	private String sysDir;
	private String isUsed;
	private String time;
	private String dir;
	public String getDirCollection() {
		return dirCollection;
	}
	public void setDirName(String dirCollection) {
		this.dirCollection = dirCollection;
	}
	public String getSysDir() {
		return sysDir;
	}
	public void setSysDir(String sysDir) {
		this.sysDir = sysDir;
	}
	public String getIsUsed() {
		return isUsed;
	}
	public void setIsUsed(String isUsed) {
		this.isUsed = isUsed;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public String getDir() {
		return dir;
	}
	public void setDir(String dir) {
		this.dir = dir;
	}
	/**
	 * @param isUsed
	 * @param time
	 * @param dir
	 */
	public SysFloder(String isUsed, String time, String dir) {
		super();
		this.isUsed = isUsed;
		this.time = time;
		this.dir = dir;
	}
	
	
}
