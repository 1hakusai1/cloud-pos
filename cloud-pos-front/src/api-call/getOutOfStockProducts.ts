import { outOfStockProductInfo } from "../component/OutOfStockProductCard";
import { sleep } from "../dev/sleep";

export const getOutOfStockProducts = async () => {
    const answer: outOfStockProductInfo[] = [
        { janCode: "100001", lackedAmount: 10, orderedAmount: 50 },
        { janCode: "100002", lackedAmount: 20, orderedAmount: 50 },
        { janCode: "100003", lackedAmount: 30, orderedAmount: 50 },
        { janCode: "100004", lackedAmount: 40, orderedAmount: 100 },
    ];
    await sleep(1);
    return answer;
}