import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Table, Form, Button, Alert } from 'react-bootstrap';
import { useParams } from 'react-router-dom';

const CEOEvaluation = () => {
  const { batchId } = useParams();
  const [batches, setBatches] = useState([]);
  const [selectedBatch, setSelectedBatch] = useState(null);
  const [trainees, setTrainees] = useState([]);
  const [taskEvaluations, setTaskEvaluations] = useState([]);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [traineesLoaded, setTraineesLoaded] = useState(false);
  const [submittedTraineeIds, setSubmittedTraineeIds] = useState([]);

  useEffect(() => {
    axios
      .get('http://localhost:8088/batch/allBatches')
      .then((response) => {
        setBatches(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);

  useEffect(() => {
    if (batchId) {
      axios
        .get(`http://localhost:8088/batch/${batchId}`)
        .then((response) => {
          setSelectedBatch(response.data);

          if (response.data) {
            setTrainees(response.data.trainees || []);
            setTraineesLoaded(true);
          }
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [batchId]);

  const handleTaskChange = (traineeId, field, value) => {
    // Check if the value is greater than 30, if so, set it to 30
    if (value > 30) {
      value = 30;
    }

    setTaskEvaluations((prevEvaluations) => {
      const existingEvaluation = prevEvaluations.find((te) => te.traineeId === traineeId);

      if (existingEvaluation) {
        return prevEvaluations.map((te) =>
          te.traineeId === traineeId ? { ...te, [field]: value } : te
        );
      } else {
        return [
          ...prevEvaluations,
          {
            traineeId,
            [field]: value,
          },
        ];
      }
    });
  };

  const handleTaskSubmit = (traineeId) => {
    const taskEvaluation = taskEvaluations.find((te) => te.traineeId === traineeId);
    if (
      !isValidNumber(taskEvaluation.aptitudeTest) ||
      !isValidNumber(taskEvaluation.hrInterview)
    ) {
      return;
    }
    const token = localStorage.getItem('token');

    axios
      .post('http://localhost:8088/CEOEvaluation/upload', taskEvaluation,{
        headers : {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setShowSuccessMessage(true);
        setTaskEvaluations((prevEvaluations) =>
          prevEvaluations.filter((te) => te.traineeId !== traineeId)
        );

        // Show "Submitted" for the button of the submitted trainee
        setSubmittedTraineeIds((prevIds) => [...prevIds, traineeId]);

        // Hide success message after 3 seconds
        setTimeout(() => {
          setShowSuccessMessage(false);
        }, 3000);
      })
      .catch((error) => {
        console.error(error);
        setShowSuccessMessage(false);
      });
  };

  // Function to validate decimal or fractional numbers between 0 and 30
  const isValidNumber = (value) => {
    const numberPattern = /^([0-9]+(\.[0-9]*)?|\.[0-9]+)$/;
    return numberPattern.test(value) && parseFloat(value) >= 0 && parseFloat(value) <= 30;
  };

  return (
    <Container>
      <h4>Select Batch:</h4>
      <Form.Select
        value={batchId}
        onChange={(e) => {
          const selectedBatchId = e.target.value;
          window.location.href = `/CEOEvaluation/${selectedBatchId}`;
        }}
      >
        <option value="">Select Batch</option>
        {batches.map((batch) => (
          <option key={batch.id} value={batch.id}>
            {batch.batchName}
          </option>
        ))}
      </Form.Select>
      <h4>Batch: {selectedBatch?.batchName}</h4>
      {showSuccessMessage && (
        <Alert variant="success">Tasks submitted successfully!</Alert>
      )}
      {traineesLoaded ? (
        <Form>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Trainee Name</th>
                <th>Aptitude Test</th>
                <th>HR Interview</th>
                <th>Submit</th>
              </tr>
            </thead>
            <tbody>
              {trainees.map((trainee) => (
                <tr key={trainee.id}>
                  <td>{trainee.fullName}</td>
                  <td>
                    <Form.Control
                      type="text"
                      value={
                        taskEvaluations.find((te) => te.traineeId === trainee.id)?.aptitudeTest || ''
                      }
                      onChange={(e) => {
                        const value = e.target.value;
                        if (isValidNumber(value)) {
                          handleTaskChange(trainee.id, 'aptitudeTest', value);
                        }
                      }}
                    />
                  </td>
                  <td>
                    <Form.Control
                      type="text"
                      value={
                        taskEvaluations.find((te) => te.traineeId === trainee.id)?.hrInterview || ''
                      }
                      onChange={(e) => {
                        const value = e.target.value;
                        if (isValidNumber(value)) {
                          handleTaskChange(trainee.id, 'hrInterview', value);
                        }
                      }}
                    />
                  </td>
                  <td>
                    <Button
                      variant="primary"
                      onClick={() => handleTaskSubmit(trainee.id)}
                      disabled={
                        !isValidNumber(taskEvaluations.find((te) => te.traineeId === trainee.id)?.aptitudeTest) ||
                        !isValidNumber(taskEvaluations.find((te) => te.traineeId === trainee.id)?.hrInterview) ||
                        submittedTraineeIds.includes(trainee.id) // Disable button if the trainee already submitted
                      }
                    >
                      {submittedTraineeIds.includes(trainee.id) ? 'Submitted' : 'Submit'}
                    </Button>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Form>
      ) : (
        <p>Loading trainees...</p>
      )}
    </Container>
  );
};

export default CEOEvaluation;