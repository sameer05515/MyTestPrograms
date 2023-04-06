import React, { useEffect, useState } from 'react'
import { useParams } from 'react-router-dom';
import ResumeService from '../../shared/services/ResumeService';

function ResumeItem() {
    let { personId, resumeId } = useParams();
    const [resume, setResume] = useState(null);

    const retrieveResume = () => {
        ResumeService.getResume(personId, resumeId)
            .then((response) => {
                const resume = response.data;
                setResume(resume);
                console.log(response.data);
            })
            .catch((e) => {
                console.log(e);
            });
    };

    useEffect(retrieveResume, [personId, resumeId]);
  return (
    <div>
        {JSON.stringify(resume)}
    </div>
  )
}

export default ResumeItem