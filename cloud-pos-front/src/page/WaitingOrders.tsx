import { Box, CircularProgress, Grid } from "@mui/material";
import { useEffect, useState } from "react";
import { getWaitingOrder } from "../api-call/getWaitingOrder";
import { OrderInfo, WaitingOrdersTable } from "../component/WaitingOrdersTable";

export const WaitingOrders = () => {

    const [orders, setOrders] = useState<OrderInfo[]>([]);
    const [dataReady, setDataReady] = useState<boolean>(false);

    useEffect(() => {
        getWaitingOrder().then(res => {
            setOrders(res);
            setDataReady(true);
        })
    }, []);

    return (
        <>
            {
                dataReady ?
                    <Grid container justifyContent="center" mt={3}>
                        <Grid item width={900}>
                            <WaitingOrdersTable orders={orders} />
                        </Grid>
                    </Grid> :
                    <Box sx={{ textAlign: "center", marginTop: 5 }}><CircularProgress /></Box>
            }
        </>
    )
}