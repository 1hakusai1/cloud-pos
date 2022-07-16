package jp.co.smartware.devel;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.Map;
import java.util.UUID;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.transaction.Transactional;

import org.jboss.logging.Logger;

import io.quarkus.arc.log.LoggerName;
import io.quarkus.arc.properties.IfBuildProperty;
import io.quarkus.runtime.StartupEvent;
import jp.co.smartware.order.LPNumber;
import jp.co.smartware.order.Order;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.order.OrderRepositoryException;
import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

@ApplicationScoped
@IfBuildProperty(name = "register.dummy.data", stringValue = "true")
public class InitialDataRegister {

    @LoggerName("InitialDataRegister")
    Logger logger;

    @Inject
    ProductRepository productRepository;

    @Inject
    OrderRepository orderRepository;

    @Transactional
    void onStart(@Observes StartupEvent event)
            throws MalformedURLException, ProductRepositoryException, OrderRepositoryException {
        logger.info("Start registering inital data...");
        initProductData();
        initOrderData();
        logger.info("...Complete");
    }

    private void initProductData() {
        logger.info("Start registering products data...");
        for (int i = 1; i <= 100; i++) {
            try {
                productRepository.create(
                        new JANCode(String.valueOf(i)),
                        new JapaneseProductName(genRandomString()),
                        new ChineseProductName(genRandomString()),
                        new URL("https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg"),
                        i);
            } catch (Exception e) {
                logger.error(e.getMessage());
            }
        }
        logger.info("...Complete");
    }

    private void initOrderData() throws OrderRepositoryException {
        logger.info("Start registering orders data...");
        for (int i = 1; i <= 100; i++) {
            int orderAmount;
            if (i % 10 == 0) {
                orderAmount = i * 10;
            } else {
                orderAmount = i;
            }
            OrderID orderID = new OrderID(String.valueOf(i));
            LPNumber lpNumber = new LPNumber(String.valueOf(i));
            orderRepository.create(
                    orderID,
                    lpNumber,
                    Map.of(new JANCode(String.valueOf(i)), orderAmount));

            if (i % 5 != 0) {
                Order order = orderRepository.findByID(orderID).get();
                order.complete();
                orderRepository.update(order);
            }
        }
        logger.info("...Complete");
    }

    private String genRandomString() {
        return UUID.randomUUID().toString();
    }

}
