export const getProductImage = async (janCode: number) => {
    const response = await fetch(`/products/${janCode}`);
    if (response.status !== 200) {
        throw "Failed to fetch";
    }
    const json = await response.json();
    if (json.imageURL) {
        return json.imageURL as string;
    } else {
        return "https://i.pinimg.com/736x/01/7c/44/017c44c97a38c1c4999681e28c39271d.jpg";
    }
}