import { Typography } from "@mui/material"
import { Link } from "react-router-dom"

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
        </>
    )
}