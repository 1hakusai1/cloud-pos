import { Typography } from "@mui/material"
import { Box } from "@mui/system"
import { Link } from "react-router-dom"
import { FileUploadButton } from "../component/FileUploadButton"

export const Home = () => {
    return (
        <>
            <Typography variant="h1" textAlign={"center"} sx={{ marginTop: 2 }}>Hello</Typography>
            <Link to={"/waiting"}>
                <Typography variant="h6" textAlign={"center"}>wating orders</Typography>
            </Link>
            <Link to={"/outofstock"}>
                <Typography variant="h6" textAlign={"center"}>out of stock</Typography>
            </Link>
            <Box textAlign="center" mt={2}>
                <FileUploadButton uploadURL="/orders" />
            </Box>
            <Link to={"/admin"}>
                <Typography variant="h6" textAlign={"center"}>admin page</Typography>
            </Link>
        </>
    )
}