package jp.co.smartware.infrastructure.repository.panache;

import java.net.MalformedURLException;
import java.net.URL;

import javax.persistence.Entity;
import javax.persistence.Id;

import jp.co.smartware.product.ChineseProductName;
import jp.co.smartware.product.JANCode;
import jp.co.smartware.product.JapaneseProductName;
import jp.co.smartware.product.Product;

@Entity
public class PanacheProductEntity {

    @Id
    private String janCode;

    private String japaneseProductName;
    private String chineseProductName;
    private String imageURL;
    private int inventoryQuantity;

    public String getJanCode() {
        return janCode;
    }

    public void setJanCode(String janCode) {
        this.janCode = janCode;
    }

    public String getJapaneseProductName() {
        return japaneseProductName;
    }

    public void setJapaneseProductName(String japaneseProductName) {
        this.japaneseProductName = japaneseProductName;
    }

    public String getChineseProductName() {
        return chineseProductName;
    }

    public void setChineseProductName(String chineseProductName) {
        this.chineseProductName = chineseProductName;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public void setInventoryQuantity(int inventoryQuantity) {
        this.inventoryQuantity = inventoryQuantity;
    }

    public Product toProduct() {
        JANCode janCode = new JANCode(this.janCode);
        JapaneseProductName japaneseProductName = null;
        if (this.japaneseProductName != null) {
            japaneseProductName = new JapaneseProductName(this.japaneseProductName);
        }
        ChineseProductName chineseProductName = null;
        if (this.chineseProductName != null) {
            chineseProductName = new ChineseProductName(this.chineseProductName);
        }
        URL url = null;
        if (this.imageURL != null) {
            try {
                url = new URL(this.imageURL);
            } catch (MalformedURLException e) {
                url = null;
            }
        }
        return new Product(janCode, japaneseProductName, chineseProductName, url, inventoryQuantity);
    }

    public void sync(Product product) {
        setInventoryQuantity(product.getInventoryQuantity());
        if (product.getJapaneseProductName().isPresent()) {
            setJapaneseProductName(product.getJapaneseProductName().get().getValue());
        }
        if (product.getChineseProductName().isPresent()) {
            setChineseProductName(product.getChineseProductName().get().getValue());
        }
        if (product.getImageURL().isPresent()) {
            setImageURL(product.getImageURL().get().toString());
        }
    }

}
