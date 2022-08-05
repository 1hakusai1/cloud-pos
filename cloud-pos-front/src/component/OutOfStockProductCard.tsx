import { Button, Card, CardMedia, Grid, Typography } from "@mui/material";
import { useState } from "react";
import { ProductArrivalDialog } from "./ProductArrivalDialog";

export type outOfStockProductInfo = {
    janCode: number,
    totalOrderdAmount: number,
    lackedAmount: number,
}

export const OutOfStockProductCard = ({ janCode, totalOrderdAmount, lackedAmount }: outOfStockProductInfo) => {
    const url = "https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg";
    const [dialogOpen, setDialogOpen] = useState<boolean>(false);
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
                                    Orderd
                                </Typography>
                                <Typography variant="h3">
                                    {totalOrderdAmount}
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