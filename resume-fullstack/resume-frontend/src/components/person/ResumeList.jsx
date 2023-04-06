import React from 'react'
import { NavLink, Outlet } from 'react-router-dom';

function ResumeList(props) {
    const {resumes, personId}= props;
  return (
    <>
    <ul>
    {
        (resumes && resumes.length>0)? 
                resumes.map(resume=>{
                    return (
                        <li key={resume.id}>
                            <NavLink className="btn btn-xs btn-primary" to={`/person-list/${personId}/resume/${resume.id}`}>{resume.title}</NavLink>
                        </li>
                    );
                })
                : <h2>No resume found</h2>
    }
    </ul> 

    <Outlet/>
    </>   
  )
}

export default ResumeList;