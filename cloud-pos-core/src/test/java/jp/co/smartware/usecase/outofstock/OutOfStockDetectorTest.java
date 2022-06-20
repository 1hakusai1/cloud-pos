package jp.co.smartware.usecase.outofstock;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URL;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import jp.co.smartware.infrastructure.repository.InMemoryOrderRepository;
import jp.co.smartware.infrastructure.repository.InMemoryProductRepository;
import jp.co.smartware.order.LPNumber;
import jp.co.smartware.order.OrderID;
import jp.co.smartware.order.OrderRepository;
import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.ProductRepository;

public class OutOfStockDetectorTest {

    private ProductRepository productRepository;
    private OrderRepository orderRepository;

    private JANCode janCode1;
    private JANCode janCode2;
    private JapaneseProductName japaneseProductName;
    private ChineseProductName chineseProductName;
    private URL imageURL;
    private OrderID orderID1;
    private OrderID orderID2;
    private LPNumber lpNumber;

    @BeforeEach
    public void setup() throws Exception {
        productRepository = new InMemoryProductRepository();
        orderRepository = new InMemoryOrderRepository();

        janCode1 = new JANCode("100001");
        janCode2 = new JANCode("100002");
        japaneseProductName = new JapaneseProductName("dummy");
        chineseProductName = new ChineseProductName("dummy");
        imageURL = new URL("https://www.example.com/image.png");
        orderID1 = new OrderID("200001");
        orderID2 = new OrderID("200002");
        lpNumber = new LPNumber("300001");

        productRepository.create(janCode1, japaneseProductName, chineseProductName, imageURL, 10);
        productRepository.create(janCode2, japaneseProductName, chineseProductName, imageURL, 10);

        orderRepository.create(orderID1, lpNumber, Map.of(janCode1, 5, janCode2, 5));
        orderRepository.create(orderID2, lpNumber, Map.of(janCode1, 7));
    }

    @Test
    public void 在庫不足の商品一覧を取得できる() throws Exception {
        OutOfStockDetector outOfStockDetector = new OutOfStockDetector(orderRepository, productRepository);
        List<OutOfStockProductInformation> info = outOfStockDetector.listOutOfStockProducts();

        assertEquals(1, info.size());
        assertEquals(janCode1, info.get(0).getProduct().getJanCode());
        assertEquals(12, info.get(0).getTotalOrderedAmount());
        assertEquals(2, info.get(0).getLackedAmount());
    }

}
