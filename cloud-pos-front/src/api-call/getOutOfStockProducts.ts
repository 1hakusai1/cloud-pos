import { outOfStockProductInfo } from "../component/OutOfStockProductCard";

export const getOutOfStockProducts = async () => {
    const response = await fetch("/stocks/outofstock");
    if (response.status !== 200) {
        return [];
    }
    const json = await response.json() as outOfStockProductInfo[];
    return json;
}