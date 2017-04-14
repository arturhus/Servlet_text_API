package ua.lviv.art.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ua.lviv.art.Reader;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by artur on 2.04.2017.
 */
public class MainServlet extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String limit = (request.getParameter("limit") != null) ? request.getParameter("limit") : "";
        String query = (request.getParameter("q") != null) ? request.getParameter("q") : "";
        String length = (request.getParameter("length") != null) ? request.getParameter("length") : "";

        List<String> list = new Reader().read(query, length, limit);


        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonText = gson.toJson("text:") + gson.toJson(list);

        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(jsonText);
    }
}