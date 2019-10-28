// ~ CopyRight © 2016 AHZY  SOFTWARE CO.LTD All Rights Reserved.
package retrive;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.PostMethod;

import filehelper.helper.FileUtility;



/**
 * 描述：RetriveHttpClient
 * <br />@version:1.0.0
 * <br />@author 邓林峰
 * <br />@email： deng_xia@sina.cn
 * <br />@date： 2016年3月11日 下午4:43:47
 */
public class RetriveHttpClient {

	private static HttpClient httpClient=new HttpClient();
	
	static{
		//httpClient.getHostConfiguration().setProxy("192.168.4.1", 8083);
	}
	
	public static boolean downLoadPage(String path) throws HttpException, IOException{
		InputStream input=null;
		OutputStream output=null;
		
		PostMethod postMethod=new PostMethod(path);
		
		/*NameValuePair[] postData=new NameValuePair[2];
		postData[0]=new NameValuePair("name", "root");
		postData[1]=new NameValuePair("password","1234");
		postMethod.addParameters(postData);*/
		
		int satutsCode=httpClient.executeMethod(postMethod);
		
		if(satutsCode==HttpStatus.SC_OK){
			input=postMethod.getResponseBodyAsStream();
			String fileName="d:/test.txt";
			output=new FileOutputStream(fileName);
			
			int tempBytes=1;
			while((tempBytes=input.read())>0){
				output.write(tempBytes);
			}
			FileUtility.closeAll(input,output);
			return true;
		}
		
		
		return false;
	}
	
	
	public static void main(String[] args) {
		
		try {
			RetriveHttpClient.downLoadPage("http://www.lietu.com/");
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}
	}
}
