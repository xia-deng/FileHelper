package filehelper.lizi;

import java.io.IOException;
import java.util.Properties;

import org.junit.Test;

public class TestUnit {

	@Test
	public void test() {
		/*
		 * System.out.println("smwaner"); ZhuCeBiao zhu=new ZhuCeBiao();
		 * zhu.readFromRegistry();
		 */

		Properties props = System.getProperties();
		/*
		 * for (Object key : props.keySet()) {
		 * System.out.println("系统关键字Key："+key
		 * +"，对应的值是："+props.getProperty(key.toString())); }
		 */
	/*	if (props.get("os.name").toString().toLowerCase().contains("windows")) {
			String wei = props.getProperty("os.arch").toLowerCase();
			String base=props.getProperty("java.library.path");
			if (wei.indexOf("64") != -1) {
				System.out.println("64位系统");
				File tempFile = new File(base+"/ICE_JNIRegistry.DLL");
				if (tempFile.exists()) {
					tempFile.delete();
				}
				FileUtility.copyFile(new File(base+"/ICE_JNIRegistry-64.DLL"), tempFile);

			} else {
				System.out.println("32位系统");
			}
		}*/
		/*ZhuCeBiao zhu = new ZhuCeBiao();
		zhu.readFromRegistry();*/
		
		String exec = "java -version";
		try {
			Runtime.getRuntime().exec(exec);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
