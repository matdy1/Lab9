package pe.edu.pucp.tel131lab9.dao;

import pe.edu.pucp.tel131lab9.bean.Employee;
import pe.edu.pucp.tel131lab9.bean.Post;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PostDao extends DaoBase{

    public ArrayList<Post> listPosts() {

        ArrayList<Post> posts = new ArrayList<>();

        String sql = "SELECT * FROM post left join employees e on e.employee_id = post.employee_id";

        try (Connection conn = this.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Post post = new Post();
                fetchPostData(post, rs);
                posts.add(post);
            }
        } catch (SQLException ex) {
            Logger.getLogger(PostDao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return posts;
    }

    // obtener el bubscador


    public Post getPost(int id) {

        Post post = null;

        String sql = "SELECT * FROM post p left join employees e on p.employee_id = e.employee_id "+
                "where p.post_id = ?";

        try (Connection conn = this.getConnection();
             PreparedStatement statement = conn.prepareStatement(sql)) {

            statement.setInt(1, id);

            try (ResultSet rs = statement.executeQuery()) {

                if (rs.next()) {
                    post = new Post();
                    fetchPostData(post, rs);
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }

        return post;
    }

    // guardar el post
    public void savePost(Post post) {

        String sql = "INSERT INTO post (title, content, employee_id,datetime)\n" +
                "VALUES (?,?,?,current_timestamp());";

        try(Connection connection = this.getConnection();
        PreparedStatement preparedStatement = connection.prepareStatement(sql)){

            preparedStatement.setString(1,post.getTitle());
            preparedStatement.setString(2,post.getContent());
            preparedStatement.setInt(3,post.getEmployeeId());

            preparedStatement.executeUpdate();


        }catch (SQLException e ){
            e.printStackTrace();
        }
    }

    // da valores al post
    private void fetchPostData(Post post, ResultSet rs) throws SQLException {

        post.setPostId(rs.getInt(1));
        post.setTitle(rs.getString(2));
        post.setContent(rs.getString(3));
        post.setEmployeeId(rs.getInt(4));

        Employee employee = new Employee();
        employee.setEmployeeId(rs.getInt("e.employee_id"));
        employee.setFirstName(rs.getString("e.first_name"));
        employee.setLastName(rs.getString("e.last_name"));
        post.setEmployee(employee);
    }

    public ArrayList<Post> buscarPorTitle(String title) {
        ArrayList<Post> lista = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        String sql = "SELECT * FROM post p \n" +
                "inner join employees e on p.employee_id=e.employee_id\n" +
                "where p.title like ? or p.content like ? or e.last_name like ? or e.first_name like ?";
        String url = "jdbc:mysql://localhost:3306/hr";
        try (Connection connection = DriverManager.getConnection(url, "root", "root");
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, "%" + title + "%");
            preparedStatement.setString(2, "%" + title + "%");
            preparedStatement.setString(3, "%" + title + "%");
            preparedStatement.setString(4, "%" + title + "%");

            try (ResultSet resultSet = preparedStatement.executeQuery()) {

                while (resultSet.next()) {
                    Post post = new Post();
                    post.setTitle(resultSet.getString(2));
                    post.setContent(resultSet.getString(3));
                    Employee employee = new Employee();
                    employee.setFirstName(resultSet.getString(7));
                    employee.setLastName(resultSet.getString(4));
                    post.setEmployee(employee);
                    lista.add(post);
                }
            }


        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return lista;
    }



}
