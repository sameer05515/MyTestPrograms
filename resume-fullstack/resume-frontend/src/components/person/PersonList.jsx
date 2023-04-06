import React, { useEffect, useState } from 'react'

import ResumeService from '../../shared/services/ResumeService';
import { NavLink } from 'react-router-dom';

function PersonList() {
    const [persons, setPersons] = useState([]);
    const retrievePersons = () => {
        ResumeService.getAll()
            .then((response) => {
                const persons = response.data;
                setPersons(persons);
                console.log(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    };

    useEffect(retrievePersons, []);
    const refreshList = () => {
        retrievePersons();
    };
    return (
        <>
            <h1>Person List</h1>
            <ul className="list-group">
                {
                    persons.map((person, index) => {
                        return (
                            <li className="list-group-item"
                                aria-current="true"
                                key={index}>
                                <span>
                                {person.name}
                                </span>
                                <NavLink className="btn btn-xs btn-primary" to={`/person-list/${person.id}`}>Details</NavLink>
                                {/* <br /> Index {index} */}
                            </li>
                        );
                    })
                }
            </ul>
            <button className="btn btn-xs btn-primary" onClick={refreshList}>
                Refresh List
            </button>
        </>

    )
}

export default PersonList