package filehelper.helper;

import java.io.File;
import java.util.Calendar;
import java.util.List;

import filehelper.staticvariable.ConfigVariable;
import filehelper.sys.SysXMLHandler;
import filehelper.sys.model.DirAndFilesIn;

public class CreateDirAndFile {
	
	
	private static String createBasePath(){
		SysXMLHandler sysXml=new SysXMLHandler();
		Calendar cal = Calendar.getInstance();
		String yearDir = cal.get(Calendar.YEAR)+ConfigVariable.SYS_FIRST_DIR;
		String monthDir = cal.get(Calendar.MONTH)+1+ConfigVariable.SYS_SECOND_DIR;
		String usedSysDir=sysXml.getUsedSysDirName();
		String path=usedSysDir+File.separator+yearDir+File.separator+monthDir+File.separator;
		return path;
	}

	public static String createDir(DirAndFilesIn dir,int index) {

		String path=createBasePath()+dir.getDir();
		File file=new File(path);
		dir.setFilePath(file.getAbsolutePath());
		TxtHelper.editFromTxt(ConfigHelper.rightTaskPath(), index, dir);
		file.mkdirs();
		return path;
	}
	
	public static String createDir(String underDir) {

		String path=createBasePath()+underDir;
		File file=new File(path);
		file.mkdirs();
		return path;
	}
	
	public static void createModelFile(String fileName,String underDir){
		
		String dirPath=createDir(underDir);
		String tempType="";
		File resourceFile=null;
		File baseDir=new File(ConfigHelper.tempModelPath());
		File[] files=baseDir.listFiles();
		for (int i = 0; i < files.length; i++) {
			String tempName=files[i].getName();
			String tempSubName=tempName.substring(0,tempName.lastIndexOf('.'));
			if(tempSubName.equals(fileName)||tempSubName==fileName){
				tempType=tempName.substring(tempName.lastIndexOf('.'), tempName.length());
				resourceFile=files[i];
				break;
			}
		}
		String filePath=dirPath+File.separator+fileName+tempType;
		File tempGoFile=new File(filePath);
		if(!tempGoFile.exists()){
			FileUtility.copyFile(resourceFile,tempGoFile );
		}
	}
	
	public static String createModelFileList(List<String> fileNames,String underDir){
		
		String dirPath=createDir(underDir);
		String tempType="";
		File resourceFile=null;
		File baseDir=new File(ConfigHelper.tempModelPath());
		File[] files=baseDir.listFiles();
		first: for (int i = 0; i < files.length; i++) {
			String tempName=files[i].getName();
			String tempSubName=tempName.substring(0,tempName.lastIndexOf('.'));
			/*if(tempSubName.equals(fileName)||tempSubName==fileName){
				tempType=tempName.substring(tempName.lastIndexOf('.'), tempName.length());
				resourceFile=files[i];
				break;
			}*/
			for (String str : fileNames) {
				if(tempSubName.equals(str)||tempSubName==str){
					tempType=tempName.substring(tempName.lastIndexOf('.'), tempName.length());
					resourceFile=files[i];
					String filePath=dirPath+File.separator+str+tempType;
					File tempGoFile=new File(filePath);
					if(!tempGoFile.exists()){
						FileUtility.copyFile(resourceFile,tempGoFile );
					}
					continue first;
				}
			}
		}
		return dirPath;
	}
}
