import { sleep } from "../dev/sleep"

export const complteOrders = async (janCodes: string[]) => {
    console.log(janCodes);
    await sleep(1);
    console.log("complete");
    return { message: "ok" };
}