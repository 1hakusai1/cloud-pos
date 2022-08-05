export const productArrive = async (janCode: number, num: number) => {
    const requestBody = { amount: num };
    try {
        const response = await fetch(`/stocks/${janCode}`, {
            method: "PUT",
            body: JSON.stringify(requestBody),
            headers: {
                "Content-Type": "application/json"
            }
        });
        if (response.status !== 200) {
            return { message: "error" };
        }
    } catch (err) {
        console.error(err);
    }
    return { message: "ok" };
}