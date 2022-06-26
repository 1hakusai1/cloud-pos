import { orderInfo } from "../component/WaitingOrders"
import { sleep } from "../dev/sleep";

export const getWaitingOrder = async () => {
    const orders: orderInfo[] = [
        { orderID: "100001", lpNumber: "200001", janCodes: ["300001"], imageURLs: ["https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg"] },
        { orderID: "100002", lpNumber: "200002", janCodes: ["300002"], imageURLs: ["https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg"] },
        { orderID: "100003", lpNumber: "200003", janCodes: ["300003"], imageURLs: ["https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg"] },
        { orderID: "100004", lpNumber: "200004", janCodes: ["300004"], imageURLs: ["https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg"] },
    ];
    await sleep(1);
    return orders;
}
