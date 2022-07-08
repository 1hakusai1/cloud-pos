import { Button, CircularProgress } from "@mui/material";
import { ChangeEvent, useState } from "react";

type Status = "waiting" | "uploading" | "complete" | "error";

type Props = {
    uploadURL: string,
    buttonText?: string
};

export const FileUploadButton = ({ uploadURL, buttonText }: Props) => {

    const [status, setStatus] = useState<Status>("waiting");

    const handleChange = async (event: ChangeEvent<HTMLInputElement>) => {
        setStatus("uploading");
        try {
            const file = event.target.files![0];
            const formData = new FormData();
            formData.append("file", file);
            const response = await fetch(uploadURL, {
                method: "POST",
                body: formData
            });
            if (response.status !== 200) {
                setStatus("error");
                return;
            }
            setStatus("complete");
        } catch (e) {
            console.error(e);
            setStatus("error");
        }
    }

    const getButtonContent = (status: Status) => {
        if (status === "waiting") {
            return buttonText ? <>{buttonText}</> : <>UPLOAD</>
        } else if (status === "uploading") {
            return <CircularProgress size={30} />;
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