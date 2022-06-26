import { Grid } from '@mui/material';
import React from 'react';
import ReactDOM from 'react-dom/client';
import { OutOfStockProductCard } from './component/OutOfStockProductCard';
import './index.css';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(
    <React.StrictMode>
        <Grid container justifyContent="center" sx={{ marginTop: 10 }}>
            <OutOfStockProductCard
                janCode='123456789'
                orderedAmount={10}
                lackedAmount={30}
            />
        </Grid>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
