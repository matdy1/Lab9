package pe.edu.pucp.tel131lab9.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pe.edu.pucp.tel131lab9.bean.Post;
import pe.edu.pucp.tel131lab9.dao.CommentDao;
import pe.edu.pucp.tel131lab9.dao.PostDao;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Date;

@WebServlet(name = "PostServlet", urlPatterns = {"/PostServlet"})
public class PostServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";
        PostDao postDao = new PostDao();
        if (action.equals("comment")) {
            String textoBuscar = req.getParameter("textoBuscar");
            req.setAttribute("posts", postDao.buscarPorTitle(textoBuscar));
            req.getRequestDispatcher("home.jsp").forward(req, resp);
        }


    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher view;
        String action = req.getParameter("action") != null ? req.getParameter("action") : "";
        HttpSession session = req.getSession();

        if (action.equals("new")) {

            Post post =new Post();
            post.setTitle(req.getParameter("titulo"));
            post.setContent(req.getParameter("contenido"));
            post.setEmployeeId(Integer.getInteger(session.getId()));
            Date date = new Date();
            Timestamp ts=new Timestamp(date.getTime());
            post.setDate(ts);

            PostDao postDao =new PostDao();
            postDao.savePost(post);
            resp.sendRedirect("EmployeeServlet?msg=Empleado creado exitosamente");
=======

>>>>>>> 14b1469bfbdfb9c484fb2d1cb533aaa5c8f13fe3

        }
        else if (action.equals("view")) {

            String id = req.getParameter("id") != null ? req.getParameter("id") : "";
            System.out.println(id);
            PostDao postDao = new PostDao();
            CommentDao commentDao = new CommentDao();
            req.setAttribute("post", postDao.getPost(Integer.parseInt(id)));
            req.setAttribute("comments", commentDao.listCommentsByPostId(Integer.parseInt(id)));
            view = req.getRequestDispatcher("post/viewPost.jsp");
            view.forward(req, resp);

        }

    }
}
