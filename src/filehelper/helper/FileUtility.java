package filehelper.helper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.text.DecimalFormat;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


/**
 * 文件操作帮助类
 * @author Administrator
 *
 */


public class FileUtility {

	/**
	 * 生成文件和文件夹
	 * 
	 * @param path
	 *            存放新文件的文件夹
	 * @param name
	 *            新文件
	 * @return
	 * @throws Exception
	 */
	public static File creatFile(String path, String name) throws Exception {

		File mk = new File(path);
		if (!mk.exists()) {
			mk.mkdirs();
		} else {
			mk.delete();
			mk.mkdirs();
		}

		File file = new File(path + "/" + name);
		if (!file.exists()) {
			file.createNewFile();
		} else {
			file.delete();
			file.createNewFile();
		}
		return file;

	}

	/**
	 * 生成文件和文件夹
	 * 
	 * @param path
	 *            完整路径的文件
	 * @throws Exception
	 */
	public static File creatFile(String path) throws Exception {

		File file = new File(path);
		if (!file.mkdirs()) {

			String fileName = file.getName();
			String fileDir = getDirName(file);
			return creatFile(fileDir, fileName);
		}

		return null;
	}

	public static String getDirName(File file) {
		String path = file.getAbsolutePath();

		path = path.replace('\\', File.separatorChar);
		path = path.replace('/', File.separatorChar);
		String fileDir = path
				.substring(0, path.lastIndexOf(File.separatorChar));
		return fileDir;

	}

	/**
	 * 获取文件的输出流（根据path和name生成新文件然后获取输出流）
	 * 
	 * @param path
	 * @param name
	 * @return
	 */
	public static OutputStream createOutStream(String path, String name) {
		OutputStream newClass = null;
		try {
			newClass = new FileOutputStream(creatFile(path, name));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeAll(newClass);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			closeAll(newClass);
		}
		// newClass.

		return newClass;
	}

	/**
	 * 获取指定文件的输出流
	 * 
	 * @param file
	 * @return
	 */
	public static OutputStream createOutStream(File file) {
		OutputStream newClass = null;

		try {
			newClass = new FileOutputStream(file);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return newClass;
	}

	/**
	 * 把指定目录下的所有文件和文件夹压入ZIP文件中，根据deleteFlag判断是否删除被压入的文件和文件夹
	 * 
	 * @param basePath
	 * @param distPath
	 * @param deleteFlag
	 */
	public static File zip(String basePath, String distPath, Boolean deleteFlag) {
		return zip(new File(basePath), new File(distPath), deleteFlag);
	}

	/**
	 * 把指定目录下的所有文件和文件夹压入ZIP文件中，根据deleteFlag判断是否删除被压入的文件和文件夹
	 * 
	 * @param baseFile
	 * @param distFile
	 * @param deleteFlag
	 */
	public static File zip(File baseFile, File distFile, Boolean deleteFlag) {

		if (distFile.exists()) {
			distFile.delete();
		}

		// 创建文件输出对象out,提示:注意中文支持
		FileOutputStream out = null;
		ZipOutputStream zOut = null;
		try {
			out = new FileOutputStream(new String(distFile.getAbsolutePath()
					.getBytes("UTF-8")));
			// 將文件輸出ZIP输出流接起来
			zOut = new ZipOutputStream(out);

			zipMethod(zOut, baseFile, "");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(zOut, out);
			if (deleteFlag) {
				//deleteWithOutEndStr(baseFile, "zip");
				delete(baseFile, new FileFilter() {
					
					@Override
					public boolean accept(File pathname) {
						// TODO Auto-generated method stub
						if(pathname.isDirectory()){
							return true;
						}else if(pathname.getAbsolutePath().endsWith("zip")){
							return false;
						}
						return true;
					}
				});
			}
			// List<File> lists=new ArrayList<File>();
			// System.out.println( listFiles2(baseFile.getParentFile(),lists));
		}

		return distFile;

	}

	/**
	 * 删除指定目录下的所有文件
	 * 
	 * @param path
	 */
	public static void delete(String path){
		delete(new File(path));
	}

	/**
	 * 删除指定目录下的所有文件
	 * 
	 * @param path
	 */
	public static void delete(File path){
		delete(path, new FileFilter() {
			
			@Override
			public boolean accept(File pathname) {
				// TODO Auto-generated method stub
				return true;
			}
		});
	}
	
	/**
	 * 删除指定的文件夹
	 * @param file
	 */
	public static void deleteDir(File file){
		
		delete(file);
		file.delete();
	}
	
	public void deleteDir(String path){
		deleteDir(new File(path));
	}

	/**
	 * 删除指定文件夹下符合过滤器的所有文件 
	 * @param path 指定文件夹
	 * @param filter 过滤器
	 */
	public static void delete(File path,FileFilter filter){
		if(path.isFile()||path.listFiles().length<1){
			path.delete();
		}else{
			File[] files=path.listFiles(filter);
			for (int i = 0; i < files.length; i++) {
				delete(files[i],filter);
				files[i].delete();
			}
		}
	}
	/**
	 * 删除指定文件夹下符合过滤器的所有文件 
	 * @param path 指定文件夹
	 * @param filter 过滤器
	 */
	public static void delete(String path,FileFilter fileFilter){
		delete(new File(path), fileFilter);
	}
	

	/**
	 * 生成ZIP的私有方法
	 * 
	 * @param zout
	 * @param baseFile
	 * @param base
	 */
	private static void zipMethod(ZipOutputStream zout, File baseFile, String base) {
		try {
			if (baseFile.isDirectory()) {

				File[] listFiles = baseFile.listFiles();

				zout.putNextEntry(new ZipEntry(base + File.separator));

				base = base.trim().length() < 1 ? "" : base + File.separator;

				for (int i = 0; i < listFiles.length; i++) {
					if (listFiles[i].getName().endsWith("zip")) {
						continue;
					} else {
						zipMethod(zout, listFiles[i],
								base + listFiles[i].getName());
					}
				}
			} else {
				if (base.equals("")) {
					base = baseFile.getName();
				}
				zout.putNextEntry(new ZipEntry(base));

				writeFileToZip(zout, baseFile);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	/**
	 * 写入文件到ZIP中
	 * 
	 * @param zout
	 * @param file
	 */
	public static void writeFileToZip(ZipOutputStream zout, File file) {
		BufferedInputStream in = null;
		try {
			in = new BufferedInputStream(new FileInputStream(file));
			int len = 0;
			byte[] buff = new byte[1024];
			while ((len = in.read(buff)) != -1) {
				zout.write(buff, 0, len);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(in);
		}
	}

	/**
	 * 
	 * 获取指定目录下的所有文件
	 * 
	 * @param baseDir
	 * @param lists
	 * @return
	 */
	public static List<File> listFiles(File baseDir, List<File> lists) {
		if (baseDir.isDirectory()) {
			File[] listFiles = baseDir.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isFile()) {
					lists.add(listFiles[i]);
				} else {
					listFiles(listFiles[i], lists);
				}
			}
		} else {
			lists.add(baseDir);
		}
		return lists;
	}

	/**
	 * 根据FiltFilter来选择获取文件
	 * 
	 * @param baseDir
	 * @param lists
	 * @param filter
	 * @return
	 * 
	 */
	public static List<File> listFiles(File baseDir, List<File> lists,
			FileFilter filter) {
		if (baseDir.isDirectory()) {
			File[] listFiles = baseDir.listFiles(filter);
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isFile()) {

					lists.add(listFiles[i]);
				} else {
					listFiles(listFiles[i], lists, filter);
				}
			}
		} else {
			lists.add(baseDir);
		}
		return lists;
	}
	
	/**
	 * 根据FiltFilter来选择获取文件夹
	 * 
	 * @param baseDir
	 * @param lists
	 * @param filter
	 * @return
	 * 
	 */
	public static List<File> listDirs(File baseDir, List<File> lists,
			FileFilter filter) {
		if (baseDir.isDirectory()) {
			File[] listFiles = baseDir.listFiles(filter);
			for (int i = 0; i < listFiles.length; i++) {
				if (listFiles[i].isDirectory()) {

					lists.add(listFiles[i]);
					listDirs(listFiles[i], lists, filter);
				} 
			}
		} 
		return lists;
	}

	/**
	 * 获得文件的大小，转换为合适的单位表示
	 * 
	 * @param t
	 * @param precision
	 *            数字精度，保留的小数位数
	 * @return
	 */
	public static <T extends Number> String fileSize(T t, int precision) {

		String strPre = ".";
		for (int i = 0; i < precision; i++) {
			strPre = strPre + "#";
		}

		DecimalFormat df = new DecimalFormat(strPre);

		if (t.longValue() < 1024) {
			return df.format(t) + " b";
		} else if (t.longValue() < 1024 * 1024) {
			return df.format(t.floatValue() / 1024F) + " kb";
		} else if (t.longValue() < 1024 * 1024 * 1024) {
			return (df.format(t.floatValue() / (1024F * 1024F))) + " Mb";
		} else {
			return (df.format(t.floatValue() / (1024F * 1024F * 1024F)))
					+ " Gb";
		}
	}

	/**
	 * 文件下载
	 * 
	 * @param request
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param filename
	 *            要下载的文件名
	 * @param realPath
	 *            要下载的文件真实路径
	 */
	/*public static void downFile( String filename, String realPath) {

		// 设置MIME类型
		response.setContentType(request.getServletContext().getMimeType(
				filename));
		// 设置response头Content-Disposition
		response.setHeader("Content-Disposition", "attachment;filename="
				+ filename);

		// 读取文件，写入到response响应流
		InputStream in = null;
		OutputStream out = null;
		// 读取文件
		try {
			in = new FileInputStream(realPath);
			out = response.getOutputStream();
			int len = 0;
			byte[] buff = new byte[1024];
			while ((len = in.read(buff)) != -1) {
				out.write(buff, 0, len);
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			closeAll(in, out);
		}
	}*/

	/**
	 * 重命名文件或文件夾
	 * 
	 * @param file
	 *            需要重命名的文件或文件夾
	 * @param newName
	 *            新名字，不包括後綴名
	 */
	public static void reNameFile(File file, String newName) {

		if (file.exists()) {
			String realPath = file.getAbsolutePath();

			String endType = realPath.lastIndexOf('.') > 0 ? realPath
					.substring(realPath.lastIndexOf('.'), realPath.length())
					: "";
			String newPath = realPath.substring(0,
					realPath.lastIndexOf(File.separatorChar))
					+ File.separator + newName + endType;

			File newFile = new File(newPath);
			try {
				file.renameTo(newFile);
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e.getMessage());
			}
		}

	}

	

	/**
	 * 基於路徑的文件夾拷貝
	 * 
	 * @param srcPath
	 * @param distPath
	 */
	public static void copyDir(String srcPath, String distPath) {

		File srcFile = new File(srcPath);

		// System.out.println(srcFile.getName());

		File distFile = new File(distPath);

		copyDir(srcFile, distFile);
	}

	/**
	 * 基於文件的文件夾拷貝
	 * 
	 * @param srcFile
	 * @param distFile
	 */
	public static void copyDir(File srcFile, File distFile) {
		if (!srcFile.isDirectory()) {
			try {
				throw new Exception("源文件不是一个文件夹！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		File[] files = srcFile.listFiles();
		distFile = new File(distFile.getAbsolutePath() + File.separator
				+ srcFile.getName());
		// newFile.mkdirs();
		distFile = returnFileForName(distFile);

		createChildDir(files, distFile, srcFile.getName());
	}

	/**
	 * 獲取子孫文件夾和文件
	 * 
	 * @param files
	 * @param distFile
	 * @param srcPath
	 */
	public static void createChildDir(File[] files, File distFile, String srcPath) {


		for (File file : files) {
			if (file.isDirectory() && file.canRead()) {
				File newFile = new File(distFile.getAbsolutePath()
						+ File.separator + file.getName());
				if (!newFile.exists()) {
					newFile.mkdirs();
					File[] newFiles = file.listFiles();
					createChildDir(newFiles, newFile, srcPath);
				}
			} else if (file.isFile() && file.canRead()) {

				copyFile(file, new File(distFile.getAbsolutePath()
						+ File.separator + file.getName()));
			}
		}

	}

	/**
	 * 文件拷贝
	 * 
	 * @param srcPath
	 *            源文件地址 path
	 * @param destPath
	 *            目标文件地址path
	 */
	public static void copyFile(String srcPath, String destPath) {

		/*
		 * File resource = new File(srcPath); File out = returnFileForName(new
		 * File(destPath));
		 */

		copyFile(new File(srcPath), returnFileForName(new File(destPath)));
	}

	/**
	 * 文件拷贝
	 * 
	 * @param resource
	 *            源文件File
	 * @param out
	 *            目标文件File
	 */
	public static void copyFile(File resource, File out) {
		if (!resource.exists() || !resource.isFile()) {

			try {
				throw new Exception("源文件：" + resource.getAbsolutePath()
						+ "不正确！");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		InputStream is = null;
		OutputStream os = null;

		// String outPath = "d:/dianying/xiuzui.rmvb";

		out = returnFileForName(out);
		File file = new File(out.getAbsolutePath().substring(0,
				out.getAbsolutePath().lastIndexOf(File.separator)));
		if (!file.exists()) {
			file.mkdirs();
		}

		try {
			is = new BufferedInputStream(new FileInputStream(resource));
			os = new BufferedOutputStream(new FileOutputStream(out));

			int len = 0;
			byte[] flush = new byte[1024];

			while (-1 != (len = is.read(flush))) {

				os.write(flush, 0, len);
			}

			os.flush();
			// System.out.println("success");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {

			closeAll(os, is);
		}
	}

	/**
	 * 给目标文件命名of copy
	 * 
	 * @param out
	 *            目标文件File
	 * @return
	 */
	private static File returnFileForName(File out) {

		// File out = new File(destPath);
		// String path=destPath;
		if (out.isFile()) {
			while (out.exists()) {
				String path = out.getAbsolutePath().substring(0,
						out.getAbsolutePath().lastIndexOf("."));
				path = path + " of copy";
				path = path
						+ out.getAbsolutePath().substring(
								out.getAbsolutePath().lastIndexOf("."),
								out.getAbsolutePath().length());
				out = new File(path);

				// returnFileForName(path);
			}
		} else if (out.isDirectory()) {
			while (out.exists() && isNotRoot(out)) {
				String path = out.getAbsolutePath() + " of copy";
				out = new File(path);
			}
		}

		return out;
	}

	/**
	 * 判断文件夹不是根目录
	 * 
	 * @param file
	 * @return
	 */
	private static boolean isNotRoot(File file) {

		File[] files = File.listRoots();
		for (File file2 : files) {
			if (file.getAbsolutePath().equals(file2.getAbsolutePath())) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 关闭流
	 */
	public static <T extends Closeable> void closeAll(T... io) {
		for (Closeable temp : io) {
			try {
				if (null != temp) {
					temp.close();
				}
			} catch (Exception e) {
			}
		}
	}

}
