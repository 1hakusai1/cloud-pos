import { useEffect, useState } from "react"
import { getProductImage } from "../api-call/getProductImage";

type ImageProps = {
    janCode: string
}

export const ProductImage = ({ janCode }: ImageProps) => {
    const [imageURL, setImageURL] = useState<string>("");
    useEffect(() => {
        getProductImage(janCode).then(url => setImageURL(url));
    }, [])
    return (
        <>
            {imageURL &&
                <img height={30} width={30} src={imageURL} />
            }
        </>
    )
}

type MultipleImagesProp = {
    janCodes: string[]
}

export const MultipleProductImages = ({ janCodes }: MultipleImagesProp) => {
    return (
        <>
            {janCodes.map(janCode => <ProductImage janCode={janCode} />)}
        </>
    )
}