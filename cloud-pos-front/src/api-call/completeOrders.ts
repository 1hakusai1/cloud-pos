export const complteOrders = async (orderIDs: number[]) => {
    try {
        const promises = orderIDs.map(completeOrder);
        await Promise.all(promises);
    } catch (error) {
        console.log(error);
    }
    return { message: "ok" };
}

const completeOrder = async (orderID: number) => {
    await fetch(`/orders/${orderID}`, {
        method: "PUT",
        headers: {
            "Content-Type": "application/json"
        }
    });
}