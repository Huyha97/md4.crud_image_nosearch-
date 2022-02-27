package cg.controller;

import cg.model.Product;
import cg.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

@Controller
@RequestMapping("/product")
public class HomeController {

    @Value("${file-upload}")
    private String fileUpload;

    @Autowired
    private IProductService productService;

    @GetMapping
    public ModelAndView showProduct() {
        ModelAndView modelAndView = new ModelAndView("list");
        ArrayList<Product> products = productService.getAllProduct();
        if (products.isEmpty()) {
            modelAndView.addObject("message", "No product!!!");
        }
        modelAndView.addObject("products", products);
        return modelAndView;
    }


    @GetMapping("/delete/{id}")
    public ModelAndView deleteProduct(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("list");
        productService.deleteProduct(id);
        ArrayList<Product> products = productService.getAllProduct();
        modelAndView.addObject("products", products);
        return modelAndView;
    }

    @GetMapping("/view/{id}")
    public ModelAndView showDetail(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("detail");
        Product product = productService.getProduct(id);
//        Product product = productService.getProduct(id);
        if (product != null) {
            modelAndView.addObject("product", product);
        } else {
            modelAndView.addObject("message", " id invalid");
        }
        return modelAndView;
    }


    @GetMapping("/create")
    public ModelAndView createProduct() {
        ModelAndView modelAndView = new ModelAndView("create");
        modelAndView.addObject("product", new Product());
        return modelAndView;
    }

    @PostMapping("/create")
    public ModelAndView create(@ModelAttribute Product product) {
        ModelAndView modelAndView = new ModelAndView("create");
        MultipartFile multipartFile = product.getFile();
        String fileName = multipartFile.getOriginalFilename();
        try {
            FileCopyUtils.copy(product.getFile().getBytes(), new File(fileUpload + fileName));
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        product.setImage(fileName);
        Product productCreate = productService.saveProduct(product);
        if (productCreate != null) {
            modelAndView.addObject("message", "Create successfully!");
        }
        return modelAndView;
    }

    @GetMapping("/edit/{id}")
    public ModelAndView editProduct(@PathVariable("id") int id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        Product product = productService.getProduct(id);
        if (product != null) {
            modelAndView.addObject("product", product);
        } else {
            modelAndView.addObject("message", " id invalid!!!");
        }
        return modelAndView;
    }

    @PostMapping("/{id}")
    public ModelAndView edit(@ModelAttribute Product product, @PathVariable int id) {
        ModelAndView modelAndView = new ModelAndView("edit");
        product.setId(id);
        if (product.getFile().getSize() != 0) {
            MultipartFile multipartFile = product.getFile();
            String fileName = multipartFile.getOriginalFilename();
            try {
                FileCopyUtils.copy(product.getFile().getBytes(), new File(fileUpload + fileName));
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            product.setImage("image/" + fileName);
        }
        else {
            product.setImage(productService.getProduct(product.getId()).getImage());
        }
        Product productEdit = productService.saveProduct(product);
        if (productEdit != null) {
            modelAndView.addObject("message", "Update successfully!");
        }
        return modelAndView;
    }


}
