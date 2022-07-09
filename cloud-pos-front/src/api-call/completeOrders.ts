export const complteOrders = async (orderIDs: string[]) => {
    try {
        const requestBody = { orderID: orderIDs };
        const response = await fetch("/orders/complete", {
            method: "POST",
            body: JSON.stringify(requestBody),
            headers: {
                "Content-Type": "application/json"
            }
        });
    } catch (error) {
        console.log(error);
    }
    return { message: "ok" };
}