import React from 'react';
import ReactDOM from 'react-dom/client';
import { HashRouter, Route, Routes } from 'react-router-dom';
import './index.css';
import { Admin } from './page/Admin';
import { Home } from './page/Home';
import { OutOfStock } from './page/OutOfStock';
import { WaitingOrders } from './page/WaitingOrders';
import reportWebVitals from './reportWebVitals';

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(

    <React.StrictMode>
        <HashRouter>
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/waiting' element={<WaitingOrders />} />
                <Route path='/outofstock' element={<OutOfStock />} />
                <Route path='/admin' element={<Admin />} />
            </Routes>
        </HashRouter>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
