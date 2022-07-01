import { Button } from "@mui/material"
import { DataGrid, GridColDef, GridSelectionModel } from "@mui/x-data-grid"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { complteOrders } from "../api-call/completeOrders"

export type orderInfo = {
    orderID: string,
    lpNumber: string,
    janCodes: string[],
    imageURLs: string[],
}

type Props = {
    orders: orderInfo[]
}

const columuns: GridColDef[] = [
    { field: "orderID", headerName: "orderID", width: 200, sortable: false },
    { field: "lpNumber", width: 200, sortable: false },
    { field: "janCodes", width: 200, sortable: false },
    { field: "imageURLs", width: 200, renderCell: (params) => <img height={30} src={params.row.imageURLs[0]} alt={params.row.orderID} />, sortable: false },
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