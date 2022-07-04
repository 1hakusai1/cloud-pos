package jp.co.smartware.endpoint;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.net.MalformedURLException;
import java.net.URL;

import javax.inject.Inject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusTest;
import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.ProductRepository;
import jp.co.smartware.product.ProductRepositoryException;

@QuarkusTest
public class ProductResourceTest {

    @Inject
    ProductRepository repository;

    @BeforeEach
    public void setup() throws ProductRepositoryException {
        repository.clear();
    }

    @Test
    public void IDを指定して商品情報を取得できる() throws ProductRepositoryException, MalformedURLException {

        repository.create(new JANCode("1"), new JapaneseProductName("aa"), new ChineseProductName("aa"),
                new URL("http://example.com"), 1);

        given()
                .when().get("/product/{id}", 1)
                .then().statusCode(200)
                .body("jancode", is("1"));
    }

}
