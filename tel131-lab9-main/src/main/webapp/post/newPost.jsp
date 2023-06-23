<%@page import="java.util.ArrayList" %>
<%@ page import="pe.edu.pucp.tel131lab9.bean.Post" %>
<jsp:useBean id="posts" type="java.util.ArrayList<pe.edu.pucp.tel131lab9.bean.Post>" scope="request"/>
<%@page contentType="text/html" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Nuevo Post</title>
    <jsp:include page="../includes/headCss.jsp"></jsp:include>
</head>
<body>
<div class='container'>
    <jsp:include page="../includes/navbar.jsp">
        <jsp:param name="currentPage" value="newPost"/>
    </jsp:include>

    <div class="row mb-4">
        <div class="col"></div>
        <div class="col-md-6">
            <h1 class='mb-3'>Nuevo post</h1>
            <hr>
            <form method="POST" action="PostServlet">
                <div class="mb-3">
                    <label class="form-label" for="titulo">Title</label>
                    <input type="text" class="form-control form-control-sm" id="titulo" name="titulo">
                </div>
                <div class="mb-3">
                    <label class="form-label" for="contenido">Comment</label>
                    <input type="text" class="form-control form-control-sm" id="contenido" name="contenido">
                </div>

                <a href="<%= request.getContextPath()%>/EmployeeServlet" class="btn btn-danger">Cancelar</a>
                <input type="submit" value="Guardar" class="btn btn-primary"/>
            </form>
        </div>
        <div class="col"></div>
    </div>

    <jsp:include page="../includes/footer.jsp"/>
</div>
</body>
</html>
