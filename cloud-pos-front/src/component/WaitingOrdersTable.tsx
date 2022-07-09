import { Button } from "@mui/material"
import { DataGrid, GridColDef, GridSelectionModel } from "@mui/x-data-grid"
import { type } from "@testing-library/user-event/dist/type"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { complteOrders } from "../api-call/completeOrders"

type OrderedProduct = {
    janCode: string,
    amount: number
}

export type OrderInfo = {
    orderID: string,
    lpNumber: string,
    products: OrderedProduct[]
}

type Props = {
    orders: OrderInfo[]
}

const productColumnValueGetter = (params: any) => {
    const products = params.row.products as OrderedProduct[];
    const janCodeAndAmount = products.map(product => product.janCode + " x " + product.amount);
    return janCodeAndAmount.join(", ");
}

const columuns: GridColDef[] = [
    { field: "orderID", headerName: "orderID", width: 200, sortable: false },
    { field: "lpNumber", width: 200, sortable: false },
    { field: "products", width: 400, sortable: false, valueGetter: productColumnValueGetter }
]

export const WaitingOrdersTable = ({ orders }: Props) => {

    const [selectedIDs, setSelectedIDs] = useState<GridSelectionModel>([]);

    const navigate = useNavigate();

    const submitData = async () => {
        await complteOrders(selectedIDs as string[]);
        navigate(0);
    }

    const SubmitButton = () => {
        return (
            <Button
                variant="contained"
                sx={{ m: 1 }}
                onClick={submitData}
                disabled={selectedIDs.length === 0}>complete</Button>
        )
    }

    return (
        <DataGrid
            columns={columuns}
            rows={orders}
            autoHeight
            checkboxSelection
            onSelectionModelChange={setSelectedIDs}
            getRowId={(row) => row.orderID}
            components={{
                Toolbar: SubmitButton
            }}
            disableColumnMenu
        />
    )
}