import { Box, CircularProgress, Container, Grid } from "@mui/material";
import { useEffect, useState } from "react";
import { getWaitingOrder } from "../api-call/getWaitingOrder";
import { orderInfo, WaitingOrdersTable } from "../component/WaitingOrdersTable";

export const WaitingOrders = () => {

    const [orders, setOrders] = useState<orderInfo[]>([]);

    useEffect(() => {
        getWaitingOrder().then(res => {
            setOrders(res);
        })
    }, []);

    return (
        <>
            {
                orders.length === 0 ?
                    <Box sx={{ textAlign: "center", marginTop: 5 }}><CircularProgress /></Box> :
                    <Grid container justifyContent="center" mt={3}>
                        <Grid item width={900}>
                            <WaitingOrdersTable orders={orders} />
                        </Grid>
                    </Grid>
            }
        </>
    )
}