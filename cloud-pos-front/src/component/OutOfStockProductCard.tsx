import { Button, Card, CardMedia, Grid, Typography } from "@mui/material";
import { useEffect, useState } from "react";
import { getProductImage } from "../api-call/getProductImage";
import { ProductArrivalDialog } from "./ProductArrivalDialog";

export type outOfStockProductInfo = {
    janCode: number,
    totalOrderedAmount: number,
    lackedAmount: number,
}

export const OutOfStockProductCard = ({ janCode, totalOrderedAmount: totalOrderedAmount, lackedAmount }: outOfStockProductInfo) => {
    const [url, setUrl] = useState<string>("");
    const [dialogOpen, setDialogOpen] = useState<boolean>(false);

    useEffect(() => {
        getProductImage(janCode).then(imageURL => setUrl(imageURL))
    }, []);

    return (
        <>
            <Card sx={{ width: 600, padding: 1 }}>
                <Grid container spacing={2}>
                    <Grid item xs={3}>
                        <CardMedia
                            component="img"
                            image={url} />
                    </Grid>
                    <Grid item xs={9}>
                        <Grid container>
                            <Typography variant="h5">
                                {janCode}
                            </Typography>
                            <Button onClick={() => setDialogOpen(true)}>ADD</Button>
                        </Grid>
                        <Grid container>
                            <Grid item xs={6}>
                                <Typography variant="h6">
                                    Ordered
                                </Typography>
                                <Typography variant="h3">
                                    {totalOrderedAmount}
                                </Typography>
                            </Grid>
                            <Grid item xs={6} sx={{ color: "red" }}>
                                <Typography variant="h6">
                                    Lacked
                                </Typography>
                                <Typography variant="h3">
                                    {lackedAmount}
                                </Typography>
                            </Grid>
                        </Grid>
                    </Grid>
                </Grid>
            </Card>
            <ProductArrivalDialog janCode={janCode} open={dialogOpen} onClose={() => setDialogOpen(false)} />
        </>
    )
}