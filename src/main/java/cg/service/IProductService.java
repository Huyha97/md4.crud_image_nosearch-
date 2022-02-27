package cg.service;

import cg.model.Product;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;

public interface IProductService {

    ArrayList<Product> getAllProduct();

    Product saveProduct(Product product);

    void deleteProduct(int id);

    Product getProduct(int id);


    ArrayList<Product> getAllProductByName(String name);
}
