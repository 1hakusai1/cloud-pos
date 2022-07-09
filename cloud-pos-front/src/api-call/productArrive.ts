export const productArrive = async (janCode: string, num: number) => {
    const requestBody = { janCode: janCode, amount: num };
    try {
        const response = await fetch("/products/arrival", {
            method: "POST",
            body: JSON.stringify(requestBody),
            headers: {
                "Content-Type": "application/json"
            }
        });
    } catch (err) {
        console.error(err);
    }
    return { message: "ok" };
}