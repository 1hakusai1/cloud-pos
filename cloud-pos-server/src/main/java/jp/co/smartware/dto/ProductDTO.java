package jp.co.smartware.dto;

import jp.co.smartware.product.Product;

public class ProductDTO {
    public String jancode;
    public String japaneseProductName;
    public String chineseProductName;
    public String imageURL;
    public int inventoryQuantity;

    public static ProductDTO fromProduct(Product product) {
        ProductDTO dto = new ProductDTO();
        dto.jancode = product.getJanCode().getValue();
        if (product.getJapaneseProductName().isPresent()) {
            dto.japaneseProductName = product.getJapaneseProductName().get().getValue();
        }
        if (product.getChineseProductName().isPresent()) {
            dto.chineseProductName = product.getChineseProductName().get().getValue();
        }
        if (product.getImageURL().isPresent()) {
            dto.imageURL = product.getImageURL().get().toString();
        }
        dto.inventoryQuantity = product.getInventoryQuantity();
        return dto;
    }
}
