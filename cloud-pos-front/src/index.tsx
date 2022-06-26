import React from 'react';
import ReactDOM from 'react-dom/client';
import { HashRouter, Route, Routes } from 'react-router-dom';
import { Home } from './page/Home';
import { WaitingOrders } from './component/WaitingOrders';
import './index.css';
import reportWebVitals from './reportWebVitals';

const orders = [
    { id: "100001", lpNumber: "200001", janCodes: ["300001"], imageURLs: ["https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg"] },
    { id: "100002", lpNumber: "200002", janCodes: ["300002"], imageURLs: ["https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg"] },
    { id: "100003", lpNumber: "200003", janCodes: ["300003"], imageURLs: ["https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg"] },
    { id: "100004", lpNumber: "200004", janCodes: ["300004"], imageURLs: ["https://kokai.jp/wp/wp-content/uploads/2015/09/Google_favicon_2015.jpg"] },
]

const root = ReactDOM.createRoot(
    document.getElementById('root') as HTMLElement
);
root.render(

    <React.StrictMode>
        <HashRouter>
            <Routes>
                <Route path='/' element={<Home />} />
                <Route path='/waiting' element={<WaitingOrders orders={orders} />} />
            </Routes>
        </HashRouter>
    </React.StrictMode>
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals();
