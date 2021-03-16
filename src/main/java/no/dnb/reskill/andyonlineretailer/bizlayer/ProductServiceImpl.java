package no.dnb.reskill.andyonlineretailer.bizlayer;

import no.dnb.reskill.andyonlineretailer.models.Product;
import no.dnb.reskill.andyonlineretailer.datalayer.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository repository;
    @Autowired
    public ProductServiceImpl(@Qualifier("productRepositoryMemory") ProductRepository repository){
        this.repository = repository;
    }


    @Override
    public double calculateTotalValue() {
        return repository.getAllProducts()
                         .stream()
                         .mapToDouble(p -> p.getPrice() * p.getInStock())
                         .sum();

    }

    @Override
    public Collection<Product> getLowStockProducts(long threashhold) {
        return repository.getAllProducts()
                         .stream()
                         .filter(p -> p.getInStock() < threashhold)
                         .collect(Collectors.toList());

    }

    @Override
    public double getAveragePrice() {
        return repository.getAllProducts()
                         .stream()
                         .mapToDouble(p -> p.getPrice() * p.getInStock())
                         .average()
                         .orElse(0.0);
    }

    @Override
    public void adjustPriceByPercent(long id, double byPercent) {
        Product theProduct = repository.getProductById(id);
        if(theProduct == null){
            return;
        } else{
            theProduct.adjustPriceByPersent(byPercent);
            repository.updateProduct(theProduct);
        }

    }
}
