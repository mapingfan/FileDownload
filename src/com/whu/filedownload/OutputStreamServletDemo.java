package com.whu.filedownload;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 这个例子用于演示servlet中的流输入输出操作，便于完成后面的文件下载功能；
 */
@WebServlet(name = "OutputStreamServletDemo", urlPatterns = {"/output"})
public class OutputStreamServletDemo extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//        把文件a.jpg写到浏览器；
        String filePath = this.getServletContext().getRealPath("/WEB-INF/download/a.jpg");
//        读取文件
        InputStream is = new FileInputStream(filePath);
        OutputStream out = response.getOutputStream();
        int len ;
        byte[] buffer = new byte[1024];
        while ((len = is.read(buffer)) > 0) {
            out.write(buffer, 0, len);
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
