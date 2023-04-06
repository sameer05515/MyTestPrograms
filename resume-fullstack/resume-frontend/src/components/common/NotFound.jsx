import React from 'react'
import { useLocation } from 'react-router-dom'

function NotFound() {
    const location= useLocation();
  return (
    <>
        <h1>Not Found:- {location.pathname}</h1>
    </>
  )
}

export default NotFound