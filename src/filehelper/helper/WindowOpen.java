package filehelper.helper;

import java.io.File;
import java.io.IOException;



public class WindowOpen {

	public static void openInWin(File file){
		if (System.getProperties().getProperty("os.name").toLowerCase()
				.contains("win")) {

			try {
				String exec = "cmd /c start explorer " + file + "";
				Runtime.getRuntime().exec(exec);
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				
			}
		}
	}
}
