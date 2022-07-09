import { CircularProgress, Grid } from "@mui/material";
import { Box } from "@mui/system";
import { useEffect, useState } from "react";
import { getOutOfStockProducts } from "../api-call/getOutOfStockProducts";
import { OutOfStockProductCard, outOfStockProductInfo } from "../component/OutOfStockProductCard";

export const OutOfStock = () => {
    const [produsts, setProducts] = useState<outOfStockProductInfo[]>([]);
    const [dataReady, setDataReady] = useState<boolean>(false);

    useEffect(() => {
        const fetchData = async () => {
            const outOfStockProdusts = await getOutOfStockProducts();
            setProducts(outOfStockProdusts);
        }
        fetchData().then(() => setDataReady(true));
    }, [])

    return (
        <>
            {dataReady ?
                <>
                    {
                        produsts.map((product) =>
                            <Box sx={{ margin: 3 }} key={product.janCode}>
                                <Grid container justifyContent={"center"}>
                                    <OutOfStockProductCard
                                        janCode={product.janCode}
                                        lackedAmount={product.lackedAmount}
                                        orderedAmount={product.orderedAmount}
                                        imageURL={product.imageURL}
                                    />
                                </Grid>
                            </Box>
                        )
                    }
                </> :
                <Grid container justifyContent={"center"} sx={{ marginTop: 5 }}>
                    <CircularProgress />
                </Grid>
            }
        </>
    )
}