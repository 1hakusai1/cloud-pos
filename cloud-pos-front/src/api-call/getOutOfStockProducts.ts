import { outOfStockProductInfo } from "../component/OutOfStockProductCard";
import { sleep } from "../dev/sleep";

export const getOutOfStockProducts = async () => {
    const response = await fetch("/outofstock");
    if (response.status !== 200) {
        return [];
    }
    const json = await response.json() as any[];
    return json.map(elem => convert(elem)) as outOfStockProductInfo[];
}

const convert = (obj: any) => {
    return {
        janCode: obj.product.jancode,
        imageURL: obj.product.imageURL,
        orderedAmount: obj.totalOrderedAmount,
        lackedAmount: obj.lackedAmount
    }
}