import React, { useState, useEffect } from 'react';
import './BatchCreate.css';
import { Form, Button, Toast } from 'react-bootstrap';
import axios from 'axios';

const BatchCreate = () => {
  const [batchData, setBatchData] = useState({
    batchName: '',
    description: '',
    startingDate: '',
    endingDate: '',
  });
  const [showSuccessMessage, setShowSuccessMessage] = useState(false);

  const handleChange = (event) => {
    const { name, value } = event.target;
    setBatchData((prevState) => ({
      ...prevState,
      [name]: value,
    }));
  };

  const handleSubmit = (event) => {
    const token = localStorage.getItem('token');
    event.preventDefault();
    axios.post('http://localhost:8088/batch/create', batchData,{
      headers : {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setShowSuccessMessage(true); // Show success message
      })
      .catch((error) => {
        console.error(error); // Handle error
      });
  };

  useEffect(() => {
    if (showSuccessMessage) {
      const timer = setTimeout(() => {
        setShowSuccessMessage(false);
      }, 3000); // Auto hide after 3 seconds

      return () => {
        clearTimeout(timer);
      };
    }
  }, [showSuccessMessage]);

  return (
    <div>
      <Toast
        show={showSuccessMessage}
        onClose={() => setShowSuccessMessage(false)}
        delay={3000} // Auto hide after 3 seconds
        autohide
        style={{ position: 'absolute', top: 20, right: 20 }} // Customize position as needed
      >
        <Toast.Header>
          <strong className="mr-auto">Success</strong>
        </Toast.Header>
        <Toast.Body>
          Batch Created Successfully!
        </Toast.Body>
      </Toast>
      <div className='batchCreateForm'>
        <Form className="batchCreate" onSubmit={handleSubmit}>
        <h6 className="titleName mb-4  font-weight-bold ">Batch Create</h6>
       <Form.Group controlId="batchName">
          <Form.Label>Batch Name</Form.Label>
          <Form.Control
            type="text"
            name="batchName"
            value={batchData.batchName}
            onChange={handleChange}
            required
          />
        </Form.Group>
  
        <Form.Group controlId="description">
          <Form.Label>Description</Form.Label>
          <Form.Control
            type="text"
            name="description"
            value={batchData.description}
            onChange={handleChange}
            required
          />
        </Form.Group>
  
        <Form.Group controlId="startingDate">
          <Form.Label>Starting Date</Form.Label>
          <Form.Control
            type="date"
            name="startingDate"
            value={batchData.startingDate}
            onChange={handleChange}
            required
          />
        </Form.Group>
  
        <Form.Group controlId="endingDate">
          <Form.Label>Ending Date</Form.Label>
          <Form.Control
            type="date"
            name="endingDate"
            value={batchData.endingDate}
            onChange={handleChange}
            required
          />
        </Form.Group>
          
          <Button className="createBatchButton" variant="success" type="submit">
            Create Batch
          </Button>
        </Form>
      </div>
    </div>
  );
};

export default BatchCreate;