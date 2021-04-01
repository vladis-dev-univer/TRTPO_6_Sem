package by.bsuir.project.controller;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

@WebServlet(name = "UserInfoImageServlet")
public class UserInfoImageServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        File img = new File(request.getParameter("path"));
        try (InputStream inputStream = new FileInputStream(img);
             OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("image/jpeg");
            outputStream.write(inputStream.readAllBytes());
            outputStream.flush();
        }
    }

}
