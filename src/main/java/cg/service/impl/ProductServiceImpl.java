package cg.service.impl;

import cg.model.Product;
import cg.repository.IRepository;
import cg.service.IProductService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

@Service
public class ProductServiceImpl implements IProductService {

    private final IRepository repository;

    public ProductServiceImpl(IRepository repository) {
        this.repository = repository;
    }

    @Override
    public ArrayList<Product> getAllProduct() {
        return (ArrayList<Product>) repository.findAll();
    }

    @Override
    public Product saveProduct(Product product) {
        return repository.save(product);
    }

    @Override
    public void deleteProduct(int id) {
        repository.deleteById(id);
    }

    @Override
    public Product getProduct(int id) {
        if (repository.findById(id).isPresent()) {
            return repository.findById(id).get();
        }
        return null;
    }

    @Override
    public ArrayList<Product> getAllProductByName(String name) {
        return (ArrayList<Product>)  repository.findAllByNameContaining(name);
    }
}
