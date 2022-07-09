package jp.co.smartware.dto;

import jp.co.smartware.usecase.outofstock.OutOfStockProductInformation;

public class OutOfStockDTO {
    public ProductDTO product;
    public int totalOrderedAmount;
    public int lackedAmount;

    public static OutOfStockDTO fromInformation(OutOfStockProductInformation information) {
        OutOfStockDTO dto = new OutOfStockDTO();
        dto.product = ProductDTO.fromProduct(information.getProduct());
        dto.totalOrderedAmount = information.getTotalOrderedAmount();
        dto.lackedAmount = information.getLackedAmount();
        return dto;
    }
}
