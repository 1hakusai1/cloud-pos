import { Typography } from "@mui/material"
import { Link } from "react-router-dom"

export const Home = () => {
    return (
        <>
            <Typography variant="h1">Hello</Typography>
            <Link to={"/waiting"}>wating orders</Link>
        </>
    )
}