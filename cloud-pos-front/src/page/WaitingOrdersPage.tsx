import { Box, CircularProgress } from "@mui/material";
import { useEffect, useState } from "react"
import { getWaitingOrder } from "../api-call/getWaitingOrder";
import { orderInfo, WaitingOrders } from "../component/WaitingOrders"

export const WaitingOrdersPage = () => {

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
                    <WaitingOrders orders={orders} />
            }
        </>
    )
}