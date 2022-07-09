import { OrderInfo } from "../component/WaitingOrdersTable";

export const getWaitingOrder = async () => {
    const response = await fetch("/orders/waiting");
    if (response.status !== 200) {
        return [];
    };
    const body = await response.json() as OrderInfo[];
    return body;
}
