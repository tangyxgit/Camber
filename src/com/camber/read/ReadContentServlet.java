package com.camber.read;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class Font
 */
public class ReadContentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	String imgPath = "/Users/xiedong/Documents/tupian/MeiNv/";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		System.out.println("程序来了，快跑");
		String menu = request.getParameter("menu");
		StringBuilder sb = new StringBuilder();
		String path = "";
		JSONObject json = new JSONObject();
		PrintWriter out = response.getWriter();
		if ("about".equals(menu)) {
			path = "/Users/xiedong/Desktop/about.txt";
		} else if ("service".equals(menu)) {
			path = "/Users/xiedong/Desktop/service.txt";
		} else if ("career".equals(menu)) {
			path = "/Users/xiedong/Desktop/career.txt";
		} else if ("contact".equals(menu)) {
			path = "/Users/xiedong/Desktop/contact.txt";
		} else {
			System.out.println("当前传的值：" + menu);
			return;
		}
		sb = readData(path);
		List<String> list = getImgName(imgPath);
		for (String l : list) {
			System.out.println(l);
		}
		try {
			json.put("font", sb);
			json.put("img", list);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		out.write(json.toString());
		out.flush();
		out.close();
		System.out.println(sb.toString());
	}

	/**
	 * 读取内容
	 */
	private StringBuilder readData(String path) {
		BufferedReader br = null;
		StringBuilder sb = new StringBuilder();
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(path)));
			String s = "";
			while ((s = br.readLine()) != null) {
				sb.append(s);
				// sb.append("\n");
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
		return sb;
	}

	/**
	 * 获取图片名字
	 */
	private List<String> getImgName(String path) {
		File file = new File(path);
		List<String> imgList = new ArrayList<String>();
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (File list : listFiles) {
				String listName = list.getName();
				String name = listName.toLowerCase();
				if (name.contains(".jpg") || name.contains(".png") || name.contains(".gif")) {
					imgList.add(name);
				}
			}
		}
		return imgList;
	}
}
