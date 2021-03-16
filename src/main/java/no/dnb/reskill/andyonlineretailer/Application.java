package no.dnb.reskill.andyonlineretailer;

import no.dnb.reskill.andyonlineretailer.bizlayer.ProductService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        // use interface on the right side, so you can use all the implementations
        ProductService service = ctx.getBean("myService",ProductService.class);
        service.adjustPriceByPercent(1, 10);
        double totalValue = service.calculateTotalValue();
        System.out.println("Total value of the stock: " + totalValue);

        double averagePrice = service.getAveragePrice();
        System.out.println("The average price: " + averagePrice);
        service.displayProducts();
        System.out.println("Full price for product with id 5: " + service.getPriceWithVat(5));

        //ProductRepository repo = ctx.getBean("productRepositoryMemory", ProductRepository.class);

        /*repo.getAllProducts()
                .stream()
                .forEach(p -> System.out.println(p));

         */

    }

}
