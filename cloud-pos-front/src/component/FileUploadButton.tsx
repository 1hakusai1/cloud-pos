import { Button, CircularProgress } from "@mui/material"
import { ChangeEvent, useState } from "react";
import { sleep } from "../dev/sleep";

type Status = "waiting" | "uploading" | "complete" | "error";

export const FileUploadButton = () => {

    const [status, setStatus] = useState<Status>("waiting");

    const handleChange = async (event: ChangeEvent<HTMLInputElement>) => {
        setStatus("uploading");
        const file = event.target.files![0];
        const content = await file.text();
        console.log(content);
        await sleep(1);
        setStatus("complete");
    }

    const getButtonContent = (status: Status) => {
        if (status === "waiting") {
            return <>UPLOAD</>;
        } else if (status === "uploading") {
            return <CircularProgress />;
        } else if (status === "complete") {
            return <>OK</>;
        } else if (status === "error") {
            return <>ERROR</>;
        }
    }

    const getButtonColor = (status: Status) => {
        if (status === "waiting") {
            return "primary";
        } else if (status === "complete") {
            return "success";
        } else if (status === "error") {
            return "error";
        } else {
            return "primary";
        }
    }

    return (
        <Button
            variant="contained"
            component="label"
            color={getButtonColor(status)}
            disabled={status === "uploading"}>

            {getButtonContent(status)}

            <input type="file" hidden aria-hidden onChange={handleChange} />
        </Button>
    )
}