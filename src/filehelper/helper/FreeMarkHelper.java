package filehelper.helper;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Map;

import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.Version;

public class FreeMarkHelper {

	private static final String freePath=ConfigHelper.tempModelPath();
	private static final String fileType=".ftl";
	private static Configuration cfg;
	
	/**
	 * 如果文件夹不存在或者文件夹是文件出错，生成文件夹
	 */
	static{
		File file=new File(freePath);
		if(!file.exists()||file.isFile()){
			file.mkdirs();
		}
		
        try {
        	//初始化FreeMarker配置
            //创建一个Configuration实例
            cfg = new Configuration(new Version("2.3"));
            //cfg.setObjectWrapper(new DefaultObjectWrapper());
            //设置FreeMarker的模版文件夹位置
			cfg.setDirectoryForTemplateLoading(file);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	public static void createFtlFile(Map<String, Object> mapDatas,String tempModelName){
		try {
			Template t = cfg.getTemplate(tempModelName,"utf-8");
			t.process(mapDatas, new OutputStreamWriter(System.out) );
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
