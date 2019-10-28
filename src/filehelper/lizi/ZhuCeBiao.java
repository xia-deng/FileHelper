package filehelper.lizi;

import com.ice.jni.registry.Registry;
import com.ice.jni.registry.RegistryKey;

public class ZhuCeBiao {
	
	public ZhuCeBiao() {
		// TODO Auto-generated constructor stub
	}
	
	

	//try
	//RegistryKey software=Registry.HKEY_CURRENT_CONFIG.openSubKey("SYSTEM");
	public String readFromRegistry(){
		try {
			RegistryKey software=Registry.HKEY_CURRENT_CONFIG.openSubKey("SYSTEM");
			System.out.println(software.getNumberSubkeys());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return "";
	}
}
