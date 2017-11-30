package com.whu.filedownload;

import sun.misc.BASE64Encoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URLEncoder;

/**
 * 这个例子用于演示servlet中的流输入输出操作，便于完成后面的文件下载功能；
 * 对于中文乱码问题要特别关注;
 */
@WebServlet(name = "OutputStreamServletDemo", urlPatterns = {"/fileDownload"})
public class FileDownloadServletDemo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//      获取文件名
        String filename = request.getParameter("filename");
        String fileNameEncoding = null;
        System.out.println(filename);
        String agent = request.getHeader("User-Agent");
        if (agent.contains("MSIE")) {
            // IE浏览器
            fileNameEncoding = URLEncoder.encode(filename, "utf-8");
            fileNameEncoding = fileNameEncoding.replace("+", " ");
        } else if (agent.contains("Firefox")) {
            // 火狐浏览器
            BASE64Encoder base64Encoder = new BASE64Encoder();
            fileNameEncoding = "=?utf-8?B?"
                    + base64Encoder.encode(filename.getBytes("utf-8")) + "?=";
        } else {
            // 其它浏览器
            fileNameEncoding = URLEncoder.encode(filename, "utf-8");
        }

//        这句话让浏览器对待下载文件不要解析，直接以附件形式下载；attachment应该可以看出端倪；
        response.setHeader("Content-Disposition","attachment; filename="+fileNameEncoding);
        /*
        * 另一种设置Content-Type；
        */
        String mimeType = getServletContext().getMimeType(filename);
        response.setHeader("Content-Type", mimeType);
//        response.setHeader("Content-Type", "application/x-msdownload");
        String filePath = getServletContext().getRealPath("/WEB-INF/download/" + filename);
        InputStream is = new FileInputStream(filePath);
        OutputStream out = response.getOutputStream();
        int len;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }
        is.close();
        out.close();

}
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
