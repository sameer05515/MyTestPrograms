import React from 'react'
import { Outlet } from 'react-router-dom'
import Navbar from '../header/Navbar'

function RootLayout() {
    return (
        <div className="container">
            <div>
                <Navbar/>
            </div>

            <Outlet />


        </div>
    )
}

export default RootLayout