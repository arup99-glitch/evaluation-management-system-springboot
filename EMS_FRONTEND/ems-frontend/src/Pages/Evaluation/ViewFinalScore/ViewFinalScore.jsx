import React, { useState, useEffect } from 'react';
import { Container, Dropdown, Table } from 'react-bootstrap';
import axios from 'axios';

const ViewFinalScore = () => {
  const [selectedBatchId, setSelectedBatchId] = useState(null);
  const [batchDetails, setBatchDetails] = useState([]);
  const [traineeMarks, setTraineeMarks] = useState([]);
  const token = localStorage.getItem('token');

  useEffect(() => {
    // Fetch batch details from the API
    axios.get('http://localhost:8088/batch/allBatches',{
      headers : {
        Authorization: `Bearer ${token}`,
      },
    })
      .then(response => {
        setBatchDetails(response.data);
      })
      .catch(error => {
        console.error('Error fetching batch details:', error);
      });
  }, []);

  const handleBatchSelect = async (batchId) => {
    setSelectedBatchId(batchId);
    

    try {
      // Fetch trainee marks from the API
      const marksResponse = await axios.get(`http://localhost:8088/final/marks/${batchId}`,{
        headers : {
          Authorization: `Bearer ${token}`,
        },
      });
      setTraineeMarks(marksResponse.data);
      
    } catch (error) {
      console.error('Error fetching trainee marks:', error);
    }
  };

  return (
    <Container>
      <h4>Batch Marks</h4>
      <Dropdown>
        <Dropdown.Toggle variant="primary" id="batch-dropdown">
          Select a Batch
        </Dropdown.Toggle>
        <Dropdown.Menu>
          {batchDetails.map((batch) => (
            <Dropdown.Item key={batch.id} onClick={() => handleBatchSelect(batch.id)}>
              {batch.batchName}
            </Dropdown.Item>
          ))}
        </Dropdown.Menu>
      </Dropdown>
      {selectedBatchId && batchDetails.length > 0 && traineeMarks.length > 0 && (
        <div>
          <h5>Batch Details</h5>
          <p>Batch Name: {batchDetails.find(batch => batch.id === selectedBatchId).batchName}</p>
          {/* Add other batch details here */}
          <h5>Trainee Marks</h5>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Trainee ID</th>
                <th>Trainee Name</th>
                <th>Daily Marks</th>
                <th>Mini Project</th>
                <th>Mid Project</th>
                <th>Final Project</th>
                <th>Manager Evaluation</th>
                <th>CEO/HR Interview</th>
                <th>Total Marks</th>
              </tr>
            </thead>
            <tbody>
              {traineeMarks.map((trainee) => (
                <tr key={trainee.id}>
                  <td>{trainee.id}</td>
                  <td>{trainee.traineeEntity.fullName}</td>
                  
                  <td>{trainee.dailyMarks.toFixed(2) || '-'}</td>
                  <td>{trainee.miniProject.toFixed(2) || '-'}</td>
                  <td>{trainee.midProject.toFixed(2) || '-'}</td>
                  <td>{trainee.finalProject.toFixed(2) || '-'}</td>
                  <td>{trainee.managerEvaluation.toFixed(2) || '-'}</td>
                  <td>{trainee.ceoHrInterview.toFixed(2) || '-'}</td>
                  <td>{trainee.totalMarks.toFixed(2) || '-'}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      )}
    </Container>
  );
};

export default ViewFinalScore;
