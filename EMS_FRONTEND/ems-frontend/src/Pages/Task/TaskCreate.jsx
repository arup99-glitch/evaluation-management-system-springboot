import React, { useState, useEffect } from 'react';
import { Form, Button, Container, Row, Col, Alert } from 'react-bootstrap';
import axios from 'axios';
import './Task.css';

const TaskCreate = () => {
  const token = localStorage.getItem('token');
    const [taskName, setTaskName] = useState('');
    const [startingDate, setStartingDate] = useState('');
    const [deadline, setDeadline] = useState('');
    const [taskType, setTaskType] = useState('');
    const [batchOptions, setBatchOptions] = useState([]);
    const [selectedBatch, setSelectedBatch] = useState('');
    const [message, setMessage] = useState('');
  
    useEffect(() => {
      // Fetch all batches from the backend API
      axios
        .get('http://localhost:8088/batch/allBatches',{
          headers : {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          setBatchOptions(response.data);
        })
        .catch((error) => {
          console.error('Error fetching batches:', error);
        });
    }, []);
  
    const handleSubmit = (event) => {
        event.preventDefault();
    
        // Prepare the data to be sent to the API
        const formData = {
          taskName,
          startingDate,
          deadline,
          taskType,
          batchId: selectedBatch,
        };
    
        // Send a POST request to create the task
        axios
          .post('http://localhost:8088/task/create', formData,{
            headers : {
              Authorization: `Bearer ${token}`,
            },
          })
          .then((response) => {
            // Handle the success response
            setMessage('Task created successfully!');
    
            // Clear the form fields after successful task creation
            setTaskName('');
            setStartingDate('');
            setDeadline('');
            setTaskType('');
            setSelectedBatch('');
    
            // Clear the message after 3 seconds
            setTimeout(() => {
              setMessage('');
            }, 3000);
          })
          .catch((error) => {
            // Handle the error response
            setMessage('Error creating the task.');
    
            // Clear the message after 3 seconds
            setTimeout(() => {
              setMessage('');
            }, 3000);
          });
      };
    return (
        <div className='taskCreate'>
             {message && <Alert variant={message.includes('successfully') ? 'success' : 'danger'}>{message}</Alert>}
            <Container className='taskCreateForm'>
      <Row>
        <Col md={{ span: 6, offset: 3 }}>
          <h5 className='createTaskTitle'>Create Task</h5>
          {/* {message && <Alert variant={message.includes('successfully') ? 'success' : 'danger'}>{message}</Alert>} */}
          <Form className='taskFormBody'onSubmit={handleSubmit}>
            <Form.Group controlId="taskName">
              <Form.Label>Task Name</Form.Label>
              <Form.Control type="text" value={taskName} onChange={(e) => setTaskName(e.target.value)} required />
            </Form.Group>
            <Form.Group controlId="startingDate">
              <Form.Label>Created</Form.Label>
              <Form.Control type="date" value={startingDate} onChange={(e) => setStartingDate(e.target.value)} required />
            </Form.Group>
            <Form.Group controlId="deadline">
              <Form.Label>Deadline</Form.Label>
              <Form.Control type="date" value={deadline} onChange={(e) => setDeadline(e.target.value)} required />
            </Form.Group>
            <Form.Group controlId="taskType">
              <Form.Label>Task Type</Form.Label>
              <Form.Control as="select" value={taskType} onChange={(e) => setTaskType(e.target.value)} required>
                <option value="">Select Task Type</option>
                <option value="Daily Task">Daily Task</option>
                <option value="Mini Project">Mini Project</option>
                <option value="Mid Project">Mid Project</option>
                <option value="Final Project">Final Project</option>
              </Form.Control>
            </Form.Group>
            <Form.Group controlId="selectedBatch">
              <Form.Label>Batch:</Form.Label>
              <Form.Control as="select" value={selectedBatch} onChange={(e) => setSelectedBatch(e.target.value)} required>
                <option value="">Select Batch</option>
                {batchOptions.map((batch) => (
                  <option key={batch.id} value={batch.id}>
                    {batch.batchName}
                  </option>
                ))}
              </Form.Control>
            </Form.Group>
            <Button className='taskCreateButton' variant="success" type="submit">
              Submit
            </Button>
          </Form>
        </Col>
      </Row>
    </Container>
    
        </div>
    );
};

export default TaskCreate;