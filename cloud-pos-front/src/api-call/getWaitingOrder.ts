import { OrderInfo } from "../component/WaitingOrdersTable";
import { sleep } from "../dev/sleep";

export const getWaitingOrder = async () => {
    const orders: OrderInfo[] = [
        { orderID: "100001", lpNumber: "200001", orderedProducts: [{ "300001": 1 }] },
        { orderID: "100002", lpNumber: "200002", orderedProducts: [{ "300002": 2 }] },
        { orderID: "100003", lpNumber: "200003", orderedProducts: [{ "300003": 3 }] },
        { orderID: "100004", lpNumber: "200004", orderedProducts: [{ "300004": 4 }, { "300005": 5 }] },
    ];
    await sleep(1);
    return orders;
}
