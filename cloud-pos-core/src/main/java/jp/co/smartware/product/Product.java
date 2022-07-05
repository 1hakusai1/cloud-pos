package jp.co.smartware.product;

import java.net.URL;
import java.util.Optional;

public class Product {

    private final JANCode janCode;
    private JapaneseProductName japaneseProductName;
    private ChineseProductName chineseProductName;
    private Optional<URL> imageURL;
    private int inventoryQuantity;

    Product(
            final JANCode janCode,
            final JapaneseProductName japaneseProductName,
            final ChineseProductName chineseProductName,
            final URL imageURL,
            final int inventoryQuantity) {
        if (janCode == null) {
            throw new NullPointerException("JANCode is null.");
        }
        if (japaneseProductName == null && chineseProductName == null) {
            throw new NullPointerException("Either JapaneseProductName or ChineseProductName is required");
        }
        if (inventoryQuantity < 0) {
            throw new IllegalArgumentException("Inventory quantity must be more than 0.");
        }
        this.janCode = janCode;
        this.japaneseProductName = japaneseProductName;
        this.chineseProductName = chineseProductName;
        this.imageURL = Optional.ofNullable(imageURL);
        this.inventoryQuantity = inventoryQuantity;
    }

    public int getInventoryQuantity() {
        return inventoryQuantity;
    }

    public JANCode getJanCode() {
        return janCode;
    }

    public JapaneseProductName getJapaneseProductName() {
        return japaneseProductName;
    }

    public ChineseProductName getChineseProductName() {
        return chineseProductName;
    }

    public Optional<URL> getImageURL() {
        return imageURL;
    }

    public void ship(int shipmentNum) throws InventoryNotEnoughException {
        if (shipmentNum < 0) {
            throw new IllegalArgumentException("ShipmentNum is less than 0");
        }
        if (shipmentNum > inventoryQuantity) {
            throw new InventoryNotEnoughException("Currnet inventory quantity is " + inventoryQuantity
                    + ". Can't ship " + shipmentNum);
        }
        inventoryQuantity = inventoryQuantity - shipmentNum;
    }

    public void arrive(int amount) {
        inventoryQuantity = inventoryQuantity + amount;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((janCode == null) ? 0 : janCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Product other = (Product) obj;
        if (!janCode.equals(other.janCode)) {
            return false;
        }
        return true;
    }

}
