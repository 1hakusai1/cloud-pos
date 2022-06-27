import { sleep } from "../dev/sleep";

export const productArrive = async (janCode: string, num: number) => {
    console.log(`JANCode: ${janCode} arrive ${num}`);
    await sleep(1);
}