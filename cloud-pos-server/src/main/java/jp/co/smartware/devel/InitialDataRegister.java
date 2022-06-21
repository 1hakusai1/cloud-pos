package jp.co.smartware.devel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import io.quarkus.arc.log.LoggerName;
import io.quarkus.runtime.StartupEvent;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

@ApplicationScoped
public class InitialDataRegister {

    @LoggerName("InitialDataRegister")
    Logger logger;

    @Inject
    ProductRepository productRepository;

    @Inject
    OrderRepository orderRepository;

    void onStart(@Observes StartupEvent event) throws MalformedURLException, ProductRepositoryException {
        logger.info("Start registering inital data...");
        initProductData();
        logger.info("...Complete");
    }

    private void initProductData() throws MalformedURLException, ProductRepositoryException {
        logger.info("Start registering products data...");
        for (int i = 0; i < 100; i++) {
            productRepository.create(
                    new JANCode(String.valueOf(i)),
                    new JapaneseProductName(genRandomString()),
                    new ChineseProductName(genRandomString()),
                    new URL("https://www.pinterest.jp/pin/608760074625600159/"),
                    i);
        }
        logger.info("...Complete");
    }

    private String genRandomString() {
        return UUID.randomUUID().toString();
    }

}
