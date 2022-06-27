import { Button, Dialog, DialogActions, DialogContent, DialogTitle, TextField } from "@mui/material"
import { useState } from "react"
import { productArrive } from "../api-call/productArrive"

type Props = {
    janCode: string,
    open: boolean
    onClose: () => void
}

export const ProductArrivalDialog = ({ janCode, open, onClose }: Props) => {
    const [numberRecieved, setNumberRecieved] = useState<number>(0);
    const handleButtonClick = async () => {
        await productArrive(janCode, numberRecieved);
        onClose();
    }

    return (
        <Dialog open={open} onClose={onClose}>
            <DialogTitle>Arive</DialogTitle>
            <DialogContent>
                <TextField
                    type={"number"}
                    onChange={event => setNumberRecieved(parseInt(event.target.value))}
                    value={numberRecieved}
                />
            </DialogContent>
            <DialogActions>
                <Button onClick={handleButtonClick}>ADD</Button>
            </DialogActions>
        </Dialog>
    )
}