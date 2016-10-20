package com.camber.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Font
 */
public class ReadContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	/**
	 * 本地地址
	 * tangyx
	 */
//	private final static String BashPath = "/Users/tangyx/Documents/eclipseJavaWeb/Work/Camber/WebContent/img/";
	/**
	 * 远程地址
	 */
	private final static String BashPath = "D:/Work/html/Camber/img/";
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String menu = request.getParameter("menu");
		String title = request.getParameter("title");
		String callback = request.getParameter("callback");
		String language = request.getParameter("language");
		String path;
		String imgPath;
		if(title==null){
			path = BashPath+menu+File.separator+menu+".txt";
			imgPath = BashPath+menu;
		}else{
			path = BashPath+menu+File.separator+title+File.separator+title+".txt";
			imgPath = BashPath+menu+File.separator+title;
			if(language!=null){
				path = BashPath+menu+File.separator+title+File.separator+language+File.separator+title+".txt";
				imgPath = BashPath+menu+File.separator+title+File.separator+language;
			}
		}
		System.out.println("txt path:"+path+"\nimg path"+imgPath+"\n"+callback);
		String content = readData(path);
		JSONArray array = getImgName(imgPath);
		try {
			JSONObject json = new JSONObject();
			json.put("content",content);
			json.put("img", array);
			PrintWriter out = response.getWriter();
			out.write(callback+"("+json.toString()+")");
			out.flush();
			out.close();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 读取内容
	 */
	private String readData(String path) {
		File file = new File(path);
		if(!file.exists()){
			return "";
		}
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String s = "";
			while ((s = br.readLine()) != null) {
				s+="<br/>";
				sb.append(s);
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				br.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 获取图片名字
	 */
	private JSONArray getImgName(String path) {
		File file = new File(path);
		JSONArray imgList = new JSONArray();
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (File list : listFiles) {
				String listName = list.getName();
				String name = listName.toLowerCase();
				if (name.contains(".jpg") || name.contains(".png") || name.contains(".gif")) {
					imgList.put(name);
				}
			}
		}
		return imgList;
	}
	public static void main(String[] args) {
//		ReadContentServlet readContentServlet = new ReadContentServlet();
//		System.out.println(readContentServlet.readData(BashPath+"about"+File.separator+"about"+".txt"));
//		try {
//			FileInputStream fis = new FileInputStream("/Users/tangyx/Downloads/secret_key_tools_RSA_macosx/rsa_private_key_pkcs8.pem");
//			byte[] buffer = new byte[1024];
//			int temp=-1;
//			StringBuffer sb = new StringBuffer();
//			while((temp = fis.read(buffer))!=-1){
//				sb.append(new String(buffer,0,temp,"UTF-8"));
//			}
//			System.out.println(sb.toString());
//			final String RSA_PRIVATE = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALIWD4fl46Cf4TwTBNn4uXOXm9RssiBaX2OmqubsAPU6diMJNE6/6etl1T8+xptWEj6rxRllcypIPJqQgaMYFDqE6VzTIopG5EfP/lqvE7GhnUkOPKwUqUBemF/JQchVJa+0nSRLEwDE8yxaNHigY9dZvo5tVZ6yLnu6Dory5TZpAgMBAAECgYEAkpILmEsCLOpJo3r3zDqkz9lg/NW1SWF4DvlWwZZON4c1QiLKNWJIsWBvwYz2Q1T00TXok71mqs8nagMzXztrMVtITliNMpDO2UfCVUMqX+Vg4zW8o0wCRovqMU6kRwk3eiTcgHNai5N6t4OeQiK697L5PRj2EKjDyCMhx2zUq7ECQQDWLXcsgeWfJoq6a6ICk11izgxnp/h3VR4jU7Be+2UL7QdbljDlcBAMK5kgObqS+tn7gergSG+3eB39At2DGtqtAkEA1NxrTAuqz5ebplwL0rucwyCLWjPbdHxYRQ0fHY0rASAm2n9sodOaOVX7X5REBoEJQxqMGrK5f/ipAd6Gcc6eLQJAJZ5Qu1GJel2zqjCzgQ5PLcaRKTe4jYU2yO+vH8+6HNEiJLUJm5MGTozt1VouahiDZwiXr5MphCkl40W4kImL9QJAJ2Qi9w+xrHkB9OdTyMuvVuzO3QJ7ujLIov6qo21cZ/jrN8Qc9lWSVMn0mtt1Oz7l1+UxyD8K1QPPQulLOARkgQJBAI18Rcamajh2qHSP/A7vPscepT717eKhLTAzcOrt3OVqGnet9mFaMMHM7r3b4W0TFjIutocF9uL8DuNNmM95jgs=";
//			final String asddddddddd = "MIICdwIBADANBgkqhkiG9w0BAQEFAASCAmEwggJdAgEAAoGBALIWD4fl46Cf4TwTBNn4uXOXm9RssiBaX2OmqubsAPU6diMJNE6/6etl1T8+xptWEj6rxRllcypIPJqQgaMYFDqE6VzTIopG5EfP/lqvE7GhnUkOPKwUqUBemF/JQchVJa+0nSRLEwDE8yxaNHigY9dZvo5tVZ6yLnu6Dory5TZpAgMBAAECgYEAkpILmEsCLOpJo3r3zDqkz9lg/NW1SWF4DvlWwZZON4c1QiLKNWJIsWBvwYz2Q1T00TXok71mqs8nagMzXztrMVtITliNMpDO2UfCVUMqX+Vg4zW8o0wCRovqMU6kRwk3eiTcgHNai5N6t4OeQiK697L5PRj2EKjDyCMhx2zUq7ECQQDWLXcsgeWfJoq6a6ICk11izgxnp/h3VR4jU7Be+2UL7QdbljDlcBAMK5kgObqS+tn7gergSG+3eB39At2DGtqtAkEA1NxrTAuqz5ebplwL0rucwyCLWjPbdHxYRQ0fHY0rASAm2n9sodOaOVX7X5REBoEJQxqMGrK5f/ipAd6Gcc6eLQJAJZ5Qu1GJel2zqjCzgQ5PLcaRKTe4jYU2yO+vH8+6HNEiJLUJm5MGTozt1VouahiDZwiXr5MphCkl40W4kImL9QJAJ2Qi9w+xrHkB9OdTyMuvVuzO3QJ7ujLIov6qo21cZ/jrN8Qc9lWSVMn0mtt1Oz7l1+UxyD8K1QPPQulLOARkgQJBAI18Rcamajh2qHSP/A7vPscepT717eKhLTAzcOrt3OVqGnet9mFaMMHM7r3b4W0TFjIutocF9uL8DuNNmM95jgs=";
//			System.out.println(RSA_PRIVATE.equals(asddddddddd));
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (UnsupportedEncodingException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<String> list = new ArrayList<String>();
		list.add("A");
		list.add("A");
		list.add("B");
		list.add("C");
		list.remove("R");
		System.out.println(list);
	}
}
