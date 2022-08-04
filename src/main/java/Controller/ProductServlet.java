package Controller;

import model.Category;
import model.Product;
import Service.ProductManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;

@WebServlet(name = "ProductServlet", value = "/product")
public class ProductServlet extends HttpServlet {
    ProductManager productManager = new ProductManager();

        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action(request, response);
        }

        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            action(request, response);
        }

        private void action(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String action = request.getParameter("action");
            if (action == null) {
                action = "";
            }
            try {
                switch (action) {
                    case "createGet":
                        createGet(request, response);
                        break;
                    case "createPost":
                        createPost(request, response);
                        break;
                    case "editGet":
                        editGet(request, response);
                        break;
                    case "editPost":
                        editPost(request, response);
                        break;
                    case "delete":
                        delete(request, response);
                        break;
                    case "findByNameByName":
                        findByName(request, response);
                        break;
                    default:
                        displayAllProduct(request, response);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        private void findByName(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String search = request.getParameter("search");

        }

        private void delete(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
            int id_product = Integer.parseInt(request.getParameter("id"));
            productManager.deleteProduct(id_product);
            displayAllProduct(request, response);
        }

        private void editPost(HttpServletRequest request, HttpServletResponse response) throws SQLException, ServletException, IOException {
            int id_product = Integer.parseInt(request.getParameter("id"));
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String color = request.getParameter("color");
            String description = request.getParameter("description");
            int id_category = Integer.parseInt(request.getParameter("category"));
            Product product = new Product(id_product, name, price, quantity, color, description);
            boolean check = productManager.editProduct(product, id_category);
            request.setAttribute("check", check);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit_Product.jsp");
            requestDispatcher.forward(request, response);
        }

        private void editGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            int id_product = Integer.parseInt(request.getParameter("id"));
            Product productEdit = productManager.getProduct(id_product);
            ArrayList<Category> categories = productManager.getCategories();
            int id_category = -1;
            for (Category category : categories) {
                if (category.getName_category().equals(productEdit.getCategory())) {
                    id_category = category.getId_category();
                }
            }
            request.setAttribute("id_category", id_category);
            request.setAttribute("productEdit", productEdit);
            request.setAttribute("categories", categories);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("edit_Product.jsp");
            requestDispatcher.forward(request, response);
        }

        private void createPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            String name = request.getParameter("name");
            double price = Double.parseDouble(request.getParameter("price"));
            int quantity = Integer.parseInt(request.getParameter("quantity"));
            String color = request.getParameter("color");
            String description = request.getParameter("description");
            int id_category = Integer.parseInt(request.getParameter("category"));
            Product product = new Product(name, price, quantity, color, description);
            boolean check = productManager.createProduct(product, id_category);
            request.setAttribute("check", check);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("create_Product.jsp");
            requestDispatcher.forward(request, response);
        }

        private void createGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ArrayList<Category> categories = productManager.getCategories();
            request.setAttribute("categories", categories);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("create_Product.jsp");
            requestDispatcher.forward(request, response);
        }

        private void displayAllProduct(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            ArrayList<Product> products = productManager.getProducts();
            request.setAttribute("products", products);
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("index.jsp");
            requestDispatcher.forward(request, response);
        }
}
