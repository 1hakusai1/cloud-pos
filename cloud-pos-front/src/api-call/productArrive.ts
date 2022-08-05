export const productArrive = async (janCode: number, num: number) => {
    const requestBody = { janCode: janCode, amount: num };
    try {
        const response = await fetch("/products/arrival", {
            method: "POST",
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