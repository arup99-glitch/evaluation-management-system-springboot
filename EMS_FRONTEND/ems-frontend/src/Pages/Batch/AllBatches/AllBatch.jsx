import React, { useEffect, useState } from 'react';
import { Row, Col, Card, Button, Container } from 'react-bootstrap';
import axios from 'axios';
import { HashLink } from 'react-router-hash-link';
import './AllBatch.css'

const AllBatch = ({ traineeId, role }) => {
  const [batches, setBatches] = useState([]);

  useEffect(() => {
    const token = localStorage.getItem('token');
    axios.get('http://localhost:8088/batch/allBatches',{
      headers : {
        Authorization: `Bearer ${token}`,
      },
      })
      .then((response) => {
        setBatches(response.data); // Handle success response
      })
      .catch((error) => {
        console.error(error); // Handle error
      });
  }, []);

  // Helper function to format a timestamp to a human-readable date
  const formatDate = (timestamp) => {
    const date = new Date(timestamp);
    return date.toLocaleDateString(); // Change the format as needed
  };

  const filteredBatches = role === 'Trainee' ? batches.filter((batch) => batch.trainees && batch.trainees.some((trainee) => trainee.id === parseInt(traineeId))) : batches;

  return (
    <>
      <Container>
        <h5 className='allBatchTitle pt-3 pt-4'>Batch Details</h5>
        <Row xs={1} md={3} className="g-4 p-5">
          {filteredBatches.map((batch) => (
            <Col key={batch.id}>
              <Card className='allBatchCard' style={{ width: '19rem' }}>
                {/* Use batch.imageLink for the Card.Img component */}
                <Card.Img variant="top" src="https://plantensive.com/wp-content/uploads/2020/05/corporate-training.jpg" alt="Batch Image" />
                <Card.Body>
                  <Card.Title>{batch.batchName}</Card.Title>
                  <Card.Text className="p-3" style={{ fontFamily: 'Arial', fontSize: '14px', color: 'text-body' }}>
                    <strong>Description:</strong> {batch.description}<br />
                    <strong>Starting Date:</strong> {formatDate(batch.startingDate)}<br />
                    <strong>Ending Date:</strong> {formatDate(batch.endingDate)}<br />
                  </Card.Text>
                  {/* Include the batchId in the link */}
                  <HashLink to={`/batch/${parseInt(batch.id)}`}>
                    <Button variant="success">View Details</Button>
                  </HashLink>
                </Card.Body>
              </Card>
            </Col>
          ))}
        </Row>
      </Container>
    </>
  );
};

export default AllBatch;
