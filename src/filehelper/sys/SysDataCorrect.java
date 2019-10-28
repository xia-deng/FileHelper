package filehelper.sys;

import java.io.File;
import java.io.FileFilter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import filehelper.helper.CreateDirAndFile;
import filehelper.helper.DateUtil;
import filehelper.helper.FileUtility;
import filehelper.helper.TxtHelper;
import filehelper.staticvariable.ConfigVariable;
import filehelper.sys.model.DirAndFilesIn;

/**
 * 系统数据校准
 * 主要用于统一系统存放文件的文件夹和系统数据之间的一致性
 * @author Administrator
 *
 */
public class SysDataCorrect {

	/**
	 * 校准系统存放的文件夹和指定目录的文件夹
	 * @param path
	 * @param baseSys
	 * @return 
	 */
	public static void sysDataCorrectForDir(String path,String baseSys){
		
		List<File> lists=new ArrayList<File>();
		List<String>resultList=new ArrayList<String>();
		FileFilter tempFilter=new FileFilter() {
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				if(pathname.isFile()){
					return false;
				}else{
					return true;
				}
				
			}
		};
		lists=FileUtility.listDirs(new File(baseSys), lists,tempFilter);
		System.out.println(lists);
		List<DirAndFilesIn> dirIns=TxtHelper.readFromTxt(path);
		/*List<String> */
		
		for (DirAndFilesIn dirAndFilesIn : dirIns) {
			
		}
		List<String> listFileNames=new ArrayList<String>();
		for (File file : lists) {
			String name=file.getName();
			listFileNames.add(name);
			System.out.println(name);
			DirAndFilesIn dirIn=new DirAndFilesIn();
			//不是我们想要的文件夹
			if(name.contains(ConfigVariable.SYS_FIRST_DIR)||name.contains(ConfigVariable.SYS_SECOND_DIR)){
				continue;
			}else{
				Map<String, Object>map=dirIn.findDirByValue(name, null);
				if(map.get("obj")==null){
					dirIn.setDir(name);
					dirIn.setFilePath(file.getAbsolutePath());
					dirIn.setFiles(returnStirngLists(file));
					dirIn.setDate(DateUtil.longToDateString(file.lastModified(), ConfigVariable.SYS_TIME) );
					TxtHelper.writeIntoTxt(path	, dirIn);
				}else{
					continue;
				}
			}
			
		}
		for (DirAndFilesIn dirAndFilesIn : dirIns) {
			if(listFileNames.contains(dirAndFilesIn.getDir())){
				continue;
			}else{
				resultList.add(dirAndFilesIn.getDir());
				Map<String, Object>map=dirAndFilesIn.findDirByValue(dirAndFilesIn.getDir(), null);
				CreateDirAndFile.createDir(dirAndFilesIn,Integer.valueOf(map.get("index").toString()));
			}
		}
		
		//return resultList;
	}
	
	
	
	private static List<String> returnStirngLists(File file){
		
		List<String> lists=new ArrayList<String>();
		File[] files=file.listFiles();
		if(files.length>0){
			for (File temp : files) {
				String name=temp.getName();
				String result=name.substring(0,name.lastIndexOf('.'));
				lists.add(result);
			}
		}
		return lists;
	}
}
