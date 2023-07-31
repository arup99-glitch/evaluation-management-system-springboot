import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Table, Form, Button, Alert } from 'react-bootstrap';
import { useParams } from 'react-router-dom';

const TaskEvaluation = () => {
  const { batchId } = useParams();
  const [batches, setBatches] = useState([]);
  const [selectedBatch, setSelectedBatch] = useState(null);
  const [trainees, setTrainees] = useState([]);
  const [tasks, setTasks] = useState([]);
  const [taskEvaluations, setTaskEvaluations] = useState([]);
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);
  const [traineesLoaded, setTraineesLoaded] = useState(false);

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

      axios
        .get(`http://localhost:8088/task/batch/${batchId}`)
        .then((response) => {
          setTasks(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [batchId]);

  const handleTaskChange = (traineeId, field, value) => {
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
    if (!taskEvaluation) {
      return;
    }

    const taskType = taskEvaluation.taskType;
    const requirementUnderstanding = parseFloat(taskEvaluation.requirementUnderstanding);
    const expectedOutput = parseFloat(taskEvaluation.expectedOutput);
    const codeQuality = parseFloat(taskEvaluation.codeQuality);
    const demonstration = parseFloat(taskEvaluation.demonstration);
    const codeUnderstanding = parseFloat(taskEvaluation.codeUnderstanding);

    let isValid = true;
    let errorMessage = '';

    if (!taskType) {
      errorMessage += 'Please select a task type.\n';
      isValid = false;
    }

    if (taskType === 'Daily Task' && (requirementUnderstanding > 2 || expectedOutput > 2)) {
      errorMessage += 'For Daily Task, requirement understanding and expected output cannot be more than 2.\n';
      isValid = false;
    } else if (taskType === 'Mini Project' && (requirementUnderstanding > 10 || expectedOutput > 10)) {
      errorMessage += 'For Mini Project, requirement understanding and expected output cannot be more than 10.\n';
      isValid = false;
    } else if (taskType === 'Mid Project' && (requirementUnderstanding > 10 || expectedOutput > 10)) {
      errorMessage += 'For Mid Project, requirement understanding and expected output cannot be more than 10.\n';
      isValid = false;
    } else if (taskType === 'Final Project' && (requirementUnderstanding > 20 || expectedOutput > 20)) {
      errorMessage += 'For Final Project, requirement understanding and expected output cannot be more than 20.\n';
      isValid = false;
    }

    if (!isValidNumber(requirementUnderstanding) || requirementUnderstanding < 0 || requirementUnderstanding > 100) {
      errorMessage += 'Please enter a valid requirement understanding score (0-100).\n';
      isValid = false;
    }

    if (!isValidNumber(expectedOutput) || expectedOutput < 0 || expectedOutput > 100) {
      errorMessage += 'Please enter a valid expected output score (0-100).\n';
      isValid = false;
    }

    if (!isValidNumber(codeQuality) || codeQuality < 0 || codeQuality > 100) {
      errorMessage += 'Please enter a valid code quality score (0-100).\n';
      isValid = false;
    }

    if (!isValidNumber(demonstration) || demonstration < 0 || demonstration > 100) {
      errorMessage += 'Please enter a valid demonstration score (0-100).\n';
      isValid = false;
    }

    if (!isValidNumber(codeUnderstanding) || codeUnderstanding < 0 || codeUnderstanding > 100) {
      errorMessage += 'Please enter a valid code understanding score (0-100).\n';
      isValid = false;
    }

    if (!isValid) {
      alert(errorMessage); // Display the error message to the user
      return;
    }
    const token = localStorage.getItem('token');
    axios
      .post('http://localhost:8088/taskEvaluation/marks/upload', taskEvaluation,{
        headers : {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setShowSuccessMessage(true);
        setTaskEvaluations((prevEvaluations) =>
          prevEvaluations.filter((te) => te.traineeId !== traineeId)
        );
      })
      .catch((error) => {
        console.error(error);
        setShowSuccessMessage(false);
      });
  };

  // Function to validate decimal or fractional numbers
  const isValidNumber = (value) => {
    const numberPattern = /^\d+(\.\d+)?$/; // Allows digits and an optional decimal point
    return numberPattern.test(value);
  };

  return (
    <Container>
      <h4>Select Batch:</h4>
      <Form.Select
        value={batchId}
        onChange={(e) => {
          const selectedBatchId = e.target.value;
          window.location.href = `/taskEvaluation/${selectedBatchId}`;
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
      {showSuccessMessage && <Alert variant="success">Tasks submitted successfully!</Alert>}
      {traineesLoaded ? (
        <Form>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>Trainee Name</th>
                <th>Task Name</th>
                <th>Task Type</th>
                <th>Requirement Understanding</th>
                <th>Expected Output</th>
                <th>Code Quality</th>
                <th>Demonstration</th>
                <th>Code Understanding</th>
                <th>Submit</th>
              </tr>
            </thead>
            <tbody>
              {trainees.map((trainee) => (
                <tr key={trainee.id}>
                  <td>{trainee.fullName}</td>
                  <td>
                    <Form.Select
                      value={
                        taskEvaluations.find((te) => te.traineeId === trainee.id)?.taskId || ''
                      }
                      onChange={(e) =>
                        handleTaskChange(
                          trainee.id,
                          'taskId',
                          e.target.value
                        )
                      }
                    >
                      <option value="">Select Task</option>
                      {tasks.map((task) => (
                        <option key={task.id} value={task.id}>
                          {task.taskName}
                        </option>
                      ))}
                    </Form.Select>
                  </td>
                  <td>
                    <Form.Select
                      value={
                        taskEvaluations.find((te) => te.traineeId === trainee.id)?.taskType || ''
                      }
                      onChange={(e) =>
                        handleTaskChange(trainee.id, 'taskType', e.target.value)
                      }
                    >
                      <option value="">Select Task Type</option>
                      <option value="Daily Task">Daily Task</option>
                      <option value="Mini Project">Mini Project</option>
                      <option value="Mid Project">Mid Project</option>
                      <option value="Final Project">Final Project</option>
                    </Form.Select>
                  </td>
                  <td>
                    <Form.Control
                      type="text" // Use text input instead of number
                      value={
                        taskEvaluations.find((te) => te.traineeId === trainee.id)?.requirementUnderstanding || ''
                      }
                      onChange={(e) => {
                        const value = e.target.value;
                        if (isValidNumber(value)) {
                          handleTaskChange(
                            trainee.id,
                            'requirementUnderstanding',
                            value
                          );
                        }
                      }}
                    />
                  </td>
                  <td>
                    <Form.Control
                      type="text" // Use text input instead of number
                      value={
                        taskEvaluations.find((te) => te.traineeId === trainee.id)?.expectedOutput || ''
                      }
                      onChange={(e) => {
                        const value = e.target.value;
                        if (isValidNumber(value)) {
                          handleTaskChange(
                            trainee.id,
                            'expectedOutput',
                            value
                          );
                        }
                      }}
                    />
                  </td>
                  <td>
                    <Form.Control
                      type="text" // Use text input instead of number
                      value={
                        taskEvaluations.find((te) => te.traineeId === trainee.id)?.codeQuality || ''
                      }
                      onChange={(e) => {
                        const value = e.target.value;
                        if (isValidNumber(value)) {
                          handleTaskChange(
                            trainee.id,
                            'codeQuality',
                            value
                          );
                        }
                      }}
                    />
                  </td>
                  <td>
                    <Form.Control
                      type="text" // Use text input instead of number
                      value={
                        taskEvaluations.find((te) => te.traineeId === trainee.id)?.demonstration || ''
                      }
                      onChange={(e) => {
                        const value = e.target.value;
                        if (isValidNumber(value)) {
                          handleTaskChange(
                            trainee.id,
                            'demonstration',
                            value
                          );
                        }
                      }}
                    />
                  </td>
                  <td>
                    <Form.Control
                      type="text" // Use text input instead of number
                      value={
                        taskEvaluations.find((te) => te.traineeId === trainee.id)?.codeUnderstanding || ''
                      }
                      onChange={(e) => {
                        const value = e.target.value;
                        if (isValidNumber(value)) {
                          handleTaskChange(
                            trainee.id,
                            'codeUnderstanding',
                            value
                          );
                        }
                      }}
                    />
                  </td>
                  <td>
                    <Button
                      variant="primary"
                      onClick={() => handleTaskSubmit(trainee.id)}
                      disabled={
                        !taskEvaluations.find((te) => te.traineeId === trainee.id)?.taskId ||
                        !taskEvaluations.find((te) => te.traineeId === trainee.id)?.taskType ||
                        !isValidNumber(taskEvaluations.find((te) => te.traineeId === trainee.id)?.requirementUnderstanding) ||
                        !isValidNumber(taskEvaluations.find((te) => te.traineeId === trainee.id)?.expectedOutput) ||
                        !isValidNumber(taskEvaluations.find((te) => te.traineeId === trainee.id)?.codeQuality) ||
                        !isValidNumber(taskEvaluations.find((te) => te.traineeId === trainee.id)?.demonstration) ||
                        !isValidNumber(taskEvaluations.find((te) => te.traineeId === trainee.id)?.codeUnderstanding)
                      }
                    >
                      Submit
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

export default TaskEvaluation;
