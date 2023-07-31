import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Card, Container, Row, Col, Button, Modal, Table, Form, Alert } from 'react-bootstrap';
import { useParams } from 'react-router-dom';
import './Task.css';

const ViewTasks = ({ fullName, traineeId, role }) => {
  const token = localStorage.getItem('token');
  const { batchId } = useParams();
  const [tasks, setTasks] = useState([]);
  const [showSubmitModal, setShowSubmitModal] = useState(false);
  const [showViewModal, setShowViewModal] = useState(false);
  const [selectedTaskId, setSelectedTaskId] = useState(null);
  const [taskSubmissions, setTaskSubmissions] = useState([]);
  const [selectedTask, setSelectedTask] = useState({ date: '', role: '', file: null });
  const [submitSuccess, setSubmitSuccess] = useState(false);
  const [submitError, setSubmitError] = useState(false);

  useEffect(() => {
    const batchNumber = parseInt(batchId);
    const token = localStorage.getItem('token');
    axios
      .get(`http://localhost:8088/task/batch/${batchNumber}`,{
        headers : {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setTasks(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, [batchId]);

  const handleViewSubmission = (taskId) => {
    setSelectedTaskId(taskId);
    setShowViewModal(true);

    axios
      .get(`http://localhost:8088/tasks/submission/${taskId}`,{
        headers : {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        setTaskSubmissions(response.data);
      })
      .catch((error) => {
        console.error('Error fetching task submissions:', error);
      });
  };

  const handleCloseViewModal = () => {
    setShowViewModal(false);
  };

  const handleTaskSubmission = (taskId) => {
    setSelectedTaskId(taskId);
    setShowSubmitModal(true);
  };

  const handleCloseSubmitModal = () => {
    setShowSubmitModal(false);
    setSelectedTask({ date: '', role: '', file: null }); // Reset the selected task after the modal is closed
    setSubmitSuccess(false); // Reset the success message state
    setSubmitError(false); // Reset the error message state
  };

  const handleTaskSubmit = (e) => {
    e.preventDefault();

    // Create a new FormData object to hold the file and other fields
    const dateObject = new Date(selectedTask.date);
    const formData = new FormData();
    formData.append('traineeId', traineeId);
    formData.append('date', dateObject);
    formData.append('role', selectedTask.role);
    formData.append('taskId', selectedTaskId);
    formData.append('file', selectedTask.file); // Append the selected file

    axios
      .post(`http://localhost:8088/tasks/submit/${parseInt(selectedTaskId)}`, formData,{
        headers : {
          Authorization: `Bearer ${token}`,
        },
      })
      .then((response) => {
        console.log('Task submitted successfully!', response);
        setSubmitSuccess(true); // Set the success message state to true
        setTimeout(() => {
          setShowSubmitModal(false);
          setSelectedTask({ date: '', role: '', file: null }); // Reset the selected task after the modal is closed
          setSubmitSuccess(false); // Reset the success message state after 3 seconds
        }, 3000);
      })
      .catch((error) => {
        console.error('You Already Submitted The Task', error);
        setSubmitError(true); // Set the error message state to true
      });
  };

  const handleFileInputChange = (e) => {
    const file = e.target.files[0];
    setSelectedTask({ ...selectedTask, file });
  };

  const formatDate = (timestamp) => {
    const date = new Date(timestamp);
    return date.toLocaleDateString();
  };

  return (
    <Container className='allTaskBody'>
      <h6>Tasks Under Batch</h6>
      <Row xs={1} md={1} x={1} className='g-3 taskRow'>
        {tasks.map((task) => (
          <Col key={task.id}>
            <Card className='submitTask'>
              <Card.Body >
                <Card.Title>{task.taskName}</Card.Title>
                <Card.Text className='taskBody'>
                  <strong>Task Type:</strong> {task.taskType}<br />
                  <strong>Created On:</strong> {formatDate(task.startingDate)}<br />
                  <strong>Deadline:</strong> {formatDate(task.deadline)}<br />
                </Card.Text>
                <div className='buttonTask'>
                {role === 'Trainee' && (
                  <Button className='taskSubmitButton' variant='success' size='sm' onClick={() => handleTaskSubmission(task.id)}>Submit Task</Button>
                )}
                {role !== 'Trainee' && (
                  <Button variant='primary' size='sm' onClick={() => handleViewSubmission(task.id)}>View Submission</Button>
                )}
                </div>
              </Card.Body>
            </Card>
          </Col>
        ))}
      </Row>

      {/* Modal for submitting the task */}
      <Modal show={showSubmitModal} onHide={handleCloseSubmitModal}>
        <Modal.Header closeButton>
          <Modal.Title>Submit Task</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          {submitError && (
            <Alert variant='danger'>Error submitting task. Please try again later.</Alert>
          )}
          {submitSuccess && (
            <Alert variant='success'>Task submitted successfully!</Alert>
          )}
          <Form onSubmit={handleTaskSubmit}>
            <Form.Group controlId='traineeName'>
              <Form.Label>Trainee Name</Form.Label>
              <Form.Control type='text' value={fullName} disabled />
            </Form.Group>
            <Form.Group controlId='date'>
              <Form.Label>Date</Form.Label>
              <Form.Control type='date' onChange={(e) => setSelectedTask({ ...selectedTask, date: e.target.value })} required />
            </Form.Group>
            <Form.Group controlId='filename'>
              <Form.Label>Choose File</Form.Label>
              {/* Input for selecting a file */}
              <Form.Control type='file' onChange={handleFileInputChange} required />
            </Form.Group>

            <Button type='submit'>Submit Task</Button>
          </Form>
        </Modal.Body>
      </Modal>

      {/* Modal for displaying task submissions */}
      <Modal show={showViewModal} onHide={handleCloseViewModal}>
        <Modal.Header closeButton>
          <Modal.Title>Task Submissions</Modal.Title>
        </Modal.Header>
        <Modal.Body>
          <Table striped bordered hover>
            <thead>
              <tr>
                <th>#</th>
                <th>Trainee Name</th>
                {/* <th>File Name</th> */}
                <th>Date</th>
                <th>Download File</th>
              </tr>
            </thead>
            <tbody>
              {taskSubmissions.map((submission, index) => (
                <tr key={submission.submissionId}>
                  <td>{index + 1}</td>
                  <td>{submission.traineeName}</td>
                  {/* <td>{submission.fileName}</td> */}
                  <td>{formatDate(submission.date)}</td>
                  <td>
                    <a
                      href={`http://localhost:8088/tasks/download/${submission.submissionId}`}
                      download
                    >
                      Download
                    </a>
                  </td>
                </tr>
              ))}
            </tbody>
          </Table>
        </Modal.Body>
        <Modal.Footer>
          <Button variant='secondary' onClick={handleCloseViewModal}>
            Close
          </Button>
        </Modal.Footer>
      </Modal>
    </Container>
  );
};

export default ViewTasks;
