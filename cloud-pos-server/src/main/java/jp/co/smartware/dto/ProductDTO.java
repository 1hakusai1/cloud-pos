package jp.co.smartware.dto;

import io.quarkus.qute.TemplateData;
import jp.co.smartware.product.Product;

@TemplateData
public class ProductDTO {
    public String jancode;
    public String japaneseProductName;
    public String chineseProductName;
    public String imageURL;
    public int inventoryQuantity;

    public static ProductDTO fromProduct(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.jancode = product.getJanCode().getValue();
        dto.japaneseProductName = product.getJapaneseProductName().getValue();
        dto.chineseProductName = product.getChineseProductName().getValue();
        dto.imageURL = product.getImageURL().toString();
        dto.inventoryQuantity = product.getInventoryQuantity();
        return dto;
    }
}
