package Service;

import DAO.ProductRepository;
import model.Category;
import model.Product;

import java.sql.SQLException;
import java.util.ArrayList;

public class ProductManager {
    private final ProductRepository productRepository = new ProductRepository();

    public ArrayList<Product> getProducts() {
        return productRepository.getAllProduct();
    }
    public Product getProduct(int id) {
        return productRepository.getProduct(id);
    }

    public boolean createProduct(Product product, int id_category) {
        return productRepository.createProduct(product, id_category);
    }

    public boolean deleteProduct(int id) throws SQLException {
        return productRepository.deleteProduct(id);
    }

    public boolean editProduct(Product product, int id_category) throws SQLException {
        return productRepository.updateProduct(product, id_category);
    }

    public ArrayList<Category> getCategories() {
        return productRepository.getAllCategory();
    }
}
