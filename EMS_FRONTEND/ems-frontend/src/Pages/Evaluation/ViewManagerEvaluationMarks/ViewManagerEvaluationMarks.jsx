import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Table, Container, Form } from 'react-bootstrap';

const ViewManagerEvaluationMarks = () => {
  const [selectedBatch, setSelectedBatch] = useState(null);
  const [batchData, setBatchData] = useState([]);
  const [marksData, setMarksData] = useState([]);

  useEffect(() => {
    // Make an API call to fetch all batches
    axios.get('http://localhost:8088/batch/allBatches')
      .then((response) => {
        setBatchData(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  const handleBatchChange = (event) => {
    const batchId = event.target.value;
    const selectedBatch = batchData.find((batch) => batch.id === parseInt(batchId));
    setSelectedBatch(selectedBatch);

    // Make an API call to fetch all manager evaluation marks for the selected batch
    axios.get(`http://localhost:8088/managerEvaluation/allMarks/${batchId}`, {
      headers: {
        Authorization: `Bearer ${localStorage.getItem('token')}`,
      },
    })
      .then((response) => {
        setMarksData(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  };

  return (
    <Container>
      <h5>View Manager Evaluation Marks by Batch</h5>
      <Form>
        <Form.Group controlId="batchSelect">
          <Form.Label>Select Batch:</Form.Label>
          <Form.Control as="select" onChange={handleBatchChange} value={selectedBatch?.id || ''}>
            <option value="">Select Batch</option>
            {batchData.map((batch) => (
              <option key={batch.id} value={batch.id}>
                {batch.batchName}
              </option>
            ))}
          </Form.Control>
        </Form.Group>
      </Form>
      {selectedBatch && (
        <div>
          <h5>Manager Evaluation Marks for Batch: {selectedBatch.batchName}</h5>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Trainee Name</th>
                <th>Common Score</th>
                <th>Office Rules Score</th>
                <th>Sincerity Score</th>
                <th>Quality Mindset Score</th>
                <th>Attendance Score</th>
                <th>Communication Skill Score</th>
                <th>English Language Skill Score</th>
                <th>Total Marks</th>
              </tr>
            </thead>
            <tbody>
              {marksData.map((mark) => (
                <tr key={mark.id}>
                  <td>{mark.trainee.fullName}</td>
                  <td>{mark.commonScore.toFixed(2)}</td>
                  <td>{mark.officeRules.toFixed(2)}</td>
                  <td>{mark.sincerity.toFixed(2)}</td>
                  <td>{mark.qualityMindset.toFixed(2)}</td>
                  <td>{mark.attendance.toFixed(2)}</td>
                  <td>{mark.communicationSkill.toFixed(2)}</td>
                  <td>{mark.englishLanguageSkill.toFixed(2)}</td>
                  <td>{mark.totalMarks.toFixed(2)}</td>
                </tr>
              ))}
            </tbody>
          </Table>
        </div>
      )}
    </Container>
  );
};

export default ViewManagerEvaluationMarks;
