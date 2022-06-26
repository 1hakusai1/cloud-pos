import { Card, CardMedia, Grid, Typography } from "@mui/material";

type Props = {
    janCode: string,
    imageURL?: string,
    orderedAmount: number,
    lackedAmount: number,
}

export const OutOfStockProductCard = ({ janCode, imageURL, orderedAmount, lackedAmount }: Props) => {
    const url = imageURL ? imageURL : "https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg";
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
                        <Typography variant="h5">
                            {janCode}
                        </Typography>
                        <Grid container>
                            <Grid item xs={6}>
                                <Typography variant="h6">
                                    Orderd
                                </Typography>
                                <Typography variant="h3">
                                    {orderedAmount}
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
        </>
    )
}