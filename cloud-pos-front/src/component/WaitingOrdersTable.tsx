import { Button } from "@mui/material"
import { DataGrid, GridColDef, GridSelectionModel } from "@mui/x-data-grid"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { complteOrders } from "../api-call/completeOrders"
import { MultipleProductImages } from "./ProductImage"

type OrderedProduct = {
    janCode: number,
    amount: number
}

export type OrderInfo = {
    orderID: number,
    lpNumber: string,
    orderedProducts: OrderedProduct[]
}

type Props = {
    orders: OrderInfo[]
}

const ProductColumn = (params: any) => {
    const products = params.row.orderedProducts as OrderedProduct[];
    const janCodeAndAmount = products.map(product => product.janCode + " x " + product.amount);
    return (
        <ul>
            {janCodeAndAmount.map(str => <li>{str}</li>)}
        </ul>
    )
}

const columuns: GridColDef[] = [
    { field: "orderID", headerName: "orderID", width: 200, sortable: false },
    { field: "lpNumber", width: 200, sortable: false },
    { field: "products", width: 200, sortable: false, renderCell: ProductColumn },
    {
        field: "images", width: 200, sortable: false,
        renderCell: (params) => <MultipleProductImages
            janCodes={(params.row.orderedProducts as OrderedProduct[]).map(product => product.janCode)} />
    }
]

export const WaitingOrdersTable = ({ orders }: Props) => {

    const [selectedIDs, setSelectedIDs] = useState<GridSelectionModel>([]);

    const navigate = useNavigate();

    const submitData = async () => {
        await complteOrders(selectedIDs as number[]);
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
            getRowHeight={() => "auto"}
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