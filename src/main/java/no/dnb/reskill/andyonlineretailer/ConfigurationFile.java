package no.dnb.reskill.andyonlineretailer;


import no.dnb.reskill.andyonlineretailer.bizlayer.ProductService;
import no.dnb.reskill.andyonlineretailer.bizlayer.ProductServiceImpl;
import no.dnb.reskill.andyonlineretailer.datalayer.ProductRepository;
import no.dnb.reskill.andyonlineretailer.datalayer.ProductRepositoryMemory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigurationFile {

    @Bean (name = "vatSpec")
    public VatBean myVatBean(){
        VatBean vatBean = new VatBean();
        return vatBean;
    }



    @Bean(name = "myService")
    public ProductService myProductService() {
        return new ProductServiceImpl(myProductRepository(), myVatBean());
    }

    @Bean(name = "memoryRepository")
    public ProductRepository myProductRepository() {
        return new ProductRepositoryMemory();
    }


}
