import { Button } from "@mui/material";
import { ChangeEvent } from "react";

export const OrderCSVImportButton = () => {
    const handleChange = async (event: ChangeEvent<HTMLInputElement>) => {
        const file = event.target.files![0];
        const content = await file.text();
        console.log(content);
    }
    return (
        <Button variant="contained" component="label">
            UPLOAD
            <input type="file" hidden onChange={handleChange} />
        </Button>
    )
}