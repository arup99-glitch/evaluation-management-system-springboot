import React, { useState, useEffect } from 'react';
import { Container, Form, Button } from 'react-bootstrap';
import axios from 'axios';
import './FinalScore.css';

const FinalScore = () => {
  const [batchData, setBatchData] = useState([]);
  const [selectedBatchId, setSelectedBatchId] = useState('');
  const [dailyTask, setDailyTask] = useState('');
  const [miniProject, setMiniProject] = useState('');
  const [midProject, setMidProject] = useState('');
  const [finalProject, setFinalProject] = useState('');
  const [managerEvaluation, setManagerEvaluation] = useState('');
  const [ceoEvaluation, setCeoEvaluation] = useState('');
  const [submitSuccess, setSubmitSuccess] = useState(false);

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
    setSelectedBatchId(event.target.value);
  };

  const handleSubmit = () => {
    const formData = {
      dailyTask,
      miniProject,
      midProject,
      finalProject,
      managerEvaluation,
      ceoEvaluation,
    };

    // Make an API call to submit the form data with the selected batch ID
    axios.post(`http://localhost:8088/final/create/${selectedBatchId}`, formData)
      .then((response) => {
        setSubmitSuccess(true);
        // Reset form fields after successful submission
        setDailyTask('');
        setMiniProject('');
        setMidProject('');
        setFinalProject('');
        setManagerEvaluation('');
        setCeoEvaluation('');
      })
      .catch((error) => {
        console.error(error);
        setSubmitSuccess(false);
      });
  };

  return (
    <Container>
      <h5>Final Evaluation Form</h5>
      <Form className='finalScoreForm'>
        <Form.Group controlId="batchSelect">
          <Form.Label>Select Batch:</Form.Label>
          <Form.Control as="select" onChange={handleBatchChange} value={selectedBatchId}>
            <option value="">Select Batch</option>
            {batchData.map((batch) => (
              <option key={batch.id} value={batch.id}>
                {batch.batchName}
              </option>
            ))}
          </Form.Control>
        </Form.Group>
        <Form.Group controlId="dailyTask">
          <Form.Label>Daily Task:</Form.Label>
          <Form.Control
            type="text"
            value={dailyTask}
            onChange={(e) => setDailyTask(e.target.value)}
          />
        </Form.Group>
        <Form.Group controlId="miniProject">
          <Form.Label>Mini Project:</Form.Label>
          <Form.Control
            type="text"
            value={miniProject}
            onChange={(e) => setMiniProject(e.target.value)}
          />
        </Form.Group>
        <Form.Group controlId="midProject">
          <Form.Label>Mid Project:</Form.Label>
          <Form.Control
            type="text"
            value={midProject}
            onChange={(e) => setMidProject(e.target.value)}
          />
        </Form.Group>
        <Form.Group controlId="finalProject">
          <Form.Label>Final Project:</Form.Label>
          <Form.Control
            type="text"
            value={finalProject}
            onChange={(e) => setFinalProject(e.target.value)}
          />
        </Form.Group>
        <Form.Group controlId="managerEvaluation">
          <Form.Label>Manager Evaluation:</Form.Label>
          <Form.Control
            type="text"
            value={managerEvaluation}
            onChange={(e) => setManagerEvaluation(e.target.value)}
          />
        </Form.Group>
        <Form.Group controlId="ceoEvaluation">
          <Form.Label>CEO Evaluation:</Form.Label>
          <Form.Control
            type="text"
            value={ceoEvaluation}
            onChange={(e) => setCeoEvaluation(e.target.value)}
          />
        </Form.Group>
        <Button variant="primary" onClick={handleSubmit}>
          Submit
        </Button>
        {submitSuccess && <p>Form submitted successfully!</p>}
      </Form>
    </Container>
  );
};

export default FinalScore;
