import { Button } from "@mui/material"
import { DataGrid, GridColDef } from "@mui/x-data-grid"

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
    { field: "orderID", headerName: "orderID", width: 200 },
    { field: "lpNumber", width: 200 },
    { field: "janCodes", width: 200 },
    { field: "imageURLs", width: 200, renderCell: (params) => <img height={30} src={params.row.imageURLs[0]} /> },
]

export const WaitingOrdersTable = ({ orders }: Props) => {

    return (
        <DataGrid
            columns={columuns}
            rows={orders}
            autoHeight
            checkboxSelection
            onSelectionModelChange={(ids) => console.log(ids)}
            getRowId={(row) => row.orderID}
            components={{
                Toolbar: () => <Button variant="contained" sx={{ m: 1 }}>complete</Button>
            }}
        />
    )
}