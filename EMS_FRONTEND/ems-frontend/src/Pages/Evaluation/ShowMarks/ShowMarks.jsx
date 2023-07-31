import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Dropdown, Table } from 'react-bootstrap';
import "./ShowMarks.css";

const ShowMarks = () => {
  const [batches, setBatches] = useState([]);
  const [selectedBatch, setSelectedBatch] = useState('');
  const [marksData, setMarksData] = useState([]);

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
    if (selectedBatch) {
      const token = localStorage.getItem('token');
      axios
        .get('http://localhost:8088/taskEvaluation/allMarks',{
          headers : {
            Authorization: `Bearer ${token}`,
          },
        })
        .then((response) => {
          const filteredMarks = response.data.filter((item) => item.task.batchId === selectedBatch);
          setMarksData(filteredMarks);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [selectedBatch]);

  const handleBatchSelect = (batchId) => {
    setSelectedBatch(batchId);
  };

  return (
    <Container>
      <h5>Task Marks by Batch</h5>
      <Dropdown>
        <Dropdown.Toggle variant="primary" id="dropdown-basic">
          Select Batch
        </Dropdown.Toggle>
        <div className='marksButton'>
        <Dropdown.Menu >
          {batches.map((batch) => (
            <Dropdown.Item key={batch.id} onClick={() => handleBatchSelect(batch.id)}>
              {batch.batchName}
            </Dropdown.Item>
            
          ))}
        </Dropdown.Menu>
        </div>
      </Dropdown>

      {selectedBatch && (
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>Trainee Name</th>
              <th>Task Type</th>
              <th>Requirement Understanding</th>
              <th>Expected Output</th>
              <th>Code Quality</th>
              <th>Demonstration</th>
              <th>Code Understanding</th>
              <th>Total Marks</th>
            </tr>
          </thead>
          <tbody>
            {marksData.map((mark) => (
              <tr key={mark.trainee.id}>
                <td>{mark.trainee.fullName}</td>
                <td>{mark.task.taskType}</td>
                <td>{mark.requirementUnderstanding.toFixed(2)}</td>
                <td>{mark.expectedOutput.toFixed(2)}</td>
                <td>{mark.codeQuality.toFixed(2)}</td>
                <td>{mark.demonstration.toFixed(2)}</td>
                <td>{mark.codeUnderstanding.toFixed(2)}</td>
                <td>{mark.totalMarks.toFixed(2)}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </Container>
  );
};

export default ShowMarks;