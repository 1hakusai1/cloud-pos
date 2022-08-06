import { Box, Typography } from "@mui/material"
import { FileUploadButton } from "../component/FileUploadButton"

export const Admin = () => {
    return (
        <>
            <Typography variant="h1" textAlign="center">Admin Page</Typography>
            <Box textAlign="center" sx={{ m: 1 }}>
                <FileUploadButton uploadURL="/stocks" buttonText="Import Stock" />
            </Box>
            <Box textAlign="center">
                <FileUploadButton uploadURL="/products" buttonText="Update Product Info" />
            </Box>
        </>
    )
}