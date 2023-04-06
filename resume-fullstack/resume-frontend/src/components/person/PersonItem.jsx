import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import ResumeService from '../../shared/services/ResumeService';
import ResumeList from './ResumeList';

function PersonItem() {
    let { personId } = useParams();
    const [person, setPerson] = useState(null);

    const retrievePerson = () => {
        ResumeService.get(personId)
            .then((response) => {
                const person = response.data;
                setPerson(person);
                console.log(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    };

    useEffect(retrievePerson, [personId]);
  return (
    <>
    {
        person? 
        (<h1>
            Person : {person.name}
            <br/>
            Email : {person.email}
            <br/>
            Resumes : <br/>
            <ResumeList resumes={person.resumes} personId={personId}/>            
            </h1>)
        : (<h1>Person not found</h1>)
    }
    </>
    
  )
}

export default PersonItem