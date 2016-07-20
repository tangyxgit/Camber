package com.camber.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;

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
	private final static String BashPath = "/Users/tangyx/Documents/eclipseJavaWeb/Work/Camber/WebContent/img/";
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		String menu = request.getParameter("menu");
		String title = request.getParameter("title");
		String callback = request.getParameter("callback");
		String path;
		String imgPath;
		if(title==null){
			path = BashPath+menu+File.separator+menu+".txt";
			imgPath = BashPath+menu;
		}else{
			path = BashPath+menu+File.separator+title+File.separator+title+".txt";
			imgPath = BashPath+menu+File.separator+title;
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
		ReadContentServlet readContentServlet = new ReadContentServlet();
		System.out.println(readContentServlet.readData(BashPath+"about"+File.separator+"about"+".txt"));
	}
}
