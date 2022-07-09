import { OrderInfo } from "../component/WaitingOrdersTable";
import { sleep } from "../dev/sleep";

export const getWaitingOrder = async () => {
    const orders: OrderInfo[] = [
        { orderID: "100001", lpNumber: "200001", products: [{ janCode: "300001", amount: 1 }] },
        { orderID: "100002", lpNumber: "200002", products: [{ janCode: "300002", amount: 2 }] },
        { orderID: "100003", lpNumber: "200003", products: [{ janCode: "300003", amount: 3 }] },
        { orderID: "100004", lpNumber: "200004", products: [{ janCode: "300004", amount: 4 }, { janCode: "300005", amount: 5 }] },
    ];
    await sleep(1);
    return orders;
}
