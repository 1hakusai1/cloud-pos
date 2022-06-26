import { DataGrid, GridColDef } from "@mui/x-data-grid"

type orderInfo = {
    id: string,
    lpNumber: string,
    janCodes: string[],
    imageURLs: string[],
}

type Props = {
    orders: orderInfo[]
}

const columuns: GridColDef[] = [
    { field: "id", width: 200 },
    { field: "lpNumber", width: 200 },
    { field: "janCodes", width: 200 },
    { field: "imageURLs", width: 200, renderCell: (params) => <img height={30} src={params.row.imageURLs[0]} /> },
]

export const WaitingOrders = ({ orders }: Props) => {

    return (
        <DataGrid
            columns={columuns}
            rows={orders}
            autoHeight
            checkboxSelection
            onSelectionModelChange={(ids) => console.log(ids)}
        />
    )
}