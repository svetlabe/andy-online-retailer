package no.dnb.reskill.andyonlineretailer.bizlayer;

import no.dnb.reskill.andyonlineretailer.VatBean;
import no.dnb.reskill.andyonlineretailer.datalayer.ProductRepository;
import no.dnb.reskill.andyonlineretailer.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.stream.Collectors;


@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository repository;


    private VatBean vat;


    @Autowired
    public ProductServiceImpl(@Qualifier("productRepositoryMemory") ProductRepository repository, @Qualifier("vatSpec") VatBean vat){
        this.repository = repository;
        this.vat = vat;
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
            theProduct.adjustPriceByPercent(byPercent);
            repository.updateProduct(theProduct);
        }

    }

    @Override
    public double findTheMostExpensiveProduct(){
        return repository.getAllProducts()
                        .stream()
                        .mapToDouble(Product::getPrice)
                        .max()
                        .orElse(0.0);
    }

    @Override
    public double findTheLeastExpensiveProduct(){
        return repository.getAllProducts()
                         .stream()
                         .mapToDouble(Product::getPrice)
                        .min()
                        .orElse(0.0);
    }

    @Override
    public void displayProducts(){
        repository.getAllProducts().stream()
                                   .sorted()
                                   .forEach(p -> System.out.println(p));

    }

    @Override
    public double getPriceWithVat(long productId){
        Product product = repository.getProductById(productId);
        System.out.println("Found product: " + product);
        double price = product.getPrice();
        double vatspec = 0;
        if(product == null){
            System.out.println("Do not have product with such ID");

        } else{

            vatspec = vat.vatSpecification(price);
            System.out.println("I got here.Vat of product: "+ product + " is " + vatspec) ;
        }
        return price + vatspec;
    }


}
