import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material"
import { useState } from "react"
import { useNavigate } from "react-router-dom"
import { productArrive } from "../api-call/productArrive"

type Props = {
    janCode: number,
    open: boolean
    onClose: () => void
}

export const ProductArrivalDialog = ({ janCode, open, onClose }: Props) => {
    const [numberRecieved, setNumberRecieved] = useState<number>(0);
    const navigate = useNavigate();
    const handleButtonClick = async () => {
        await productArrive(janCode, numberRecieved);
        navigate(0);
    }

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Arive</DialogTitle>
            <DialogContent>
                <TextField
                    type={"number"}
                    onChange={event => setNumberRecieved(parseInt(event.target.value))}
                    value={numberRecieved}
                    inputProps={{ min: 0 }}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={handleButtonClick}>ADD</Button>
            </DialogActions>
        </Dialog>
    )
}