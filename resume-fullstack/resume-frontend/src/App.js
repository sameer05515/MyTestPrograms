// import logo from './logo.svg';
// import './App.css';

import { RouterProvider, createBrowserRouter } from "react-router-dom";
import NotFound from "./components/common/NotFound";
import RootLayout from "./components/routes/RootLayout";
import PersonList from "./components/person/PersonList";
import PersonItem from "./components/person/PersonItem";
import ResumeItem from "./components/person/ResumeItem";

const router = createBrowserRouter(
  [
    {
      path: '/',
      element: <RootLayout />,
      children: [
        { path: '/person-list', element: <PersonList /> },
        {
          path: '/person-list/:personId', element: <PersonItem />,
          children: [
            { path: '/person-list/:personId/resume/:resumeId', element: <ResumeItem /> }
          ]
        }
      ]
    },
    { path: '*', element: <NotFound /> }
  ]
);

function App() {
  return (
    <RouterProvider router={router} />
  );
}

export default App;
