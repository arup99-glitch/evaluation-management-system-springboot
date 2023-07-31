import React, { useState, useEffect } from 'react';
import { Table, Dropdown } from 'react-bootstrap';
import axios from 'axios';

const ViewSubmission = () => {
  const [batches, setBatches] = useState([]);
  const [selectedBatch, setSelectedBatch] = useState(null);
  const [tasks, setTasks] = useState([]);
  const [selectedTask, setSelectedTask] = useState(null);
  const [submissions, setSubmissions] = useState([]);

  useEffect(() => {
    // Fetch all batches
    axios.get('http://localhost:8088/batch/allBatches',{
      headers : {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setBatches(response.data);
      })
      .catch((error) => {
        console.error('Error fetching batches:', error);
      });
  }, []);

  const handleBatchChange = (batchId) => {
    setSelectedBatch(null);
    setSelectedTask(null);

    // Fetch specific batch details
    axios.get(`http://localhost:8088/batch/${batchId}`,{
      headers : {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setSelectedBatch(response.data);
      })
      .catch((error) => {
        console.error('Error fetching batch details:', error);
      });

    // Fetch tasks for the selected batch
    axios.get(`http://localhost:8088/task/batch/${batchId}`,{
      headers : {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setTasks(response.data);
      })
      .catch((error) => {
        console.error('Error fetching tasks:', error);
      });
  };

  const handleTaskChange = (taskId) => {
    setSelectedTask(taskId);
    // Fetch submissions for the selected task
    axios.get(`http://localhost:8088/tasks/submission/${taskId}`,{
      headers : {
        Authorization: `Bearer ${token}`,
      },
    })
      .then((response) => {
        setSubmissions(response.data);
      })
      .catch((error) => {
        console.error('Error fetching submissions:', error);
      });
  };

  return (
    <div>
      <Dropdown>
        <Dropdown.Toggle variant="primary" id="batch-dropdown">
          {selectedBatch ? `Batch: ${selectedBatch.batchName}` : 'Select Batch'}
        </Dropdown.Toggle>
        <Dropdown.Menu>
          {batches.map((batch) => (
            <Dropdown.Item key={batch.id} onSelect={() => handleBatchChange(batch.id)}>
              {batch.batchName}
            </Dropdown.Item>
          ))}
        </Dropdown.Menu>
      </Dropdown>

      {selectedBatch && (
        <Dropdown>
          <Dropdown.Toggle variant="primary" id="task-dropdown">
            {selectedTask ? `Task: ${selectedTask}` : 'Select Task'}
          </Dropdown.Toggle>
          <Dropdown.Menu>
            {tasks.map((task) => (
              <Dropdown.Item key={task.id} onSelect={() => handleTaskChange(task.id)}>
                {task.taskName}
              </Dropdown.Item>
            ))}
          </Dropdown.Menu>
        </Dropdown>
      )}

      {selectedBatch && selectedTask && (
        <Table striped bordered hover>
          <thead>
            <tr>
              <th>1</th>
              <th>Trainee Name</th>
              <th>File Name</th>
              <th>Date</th>
            </tr>
          </thead>
          <tbody>
            {submissions.map((submission) => (
              <tr key={submission.submissionId}>
                <td>{submission.submissionId}</td>
                <td>{submission.traineeName}</td>
                <td>{submission.fileName}upload file</td>
                <td>{new Date(submission.date).toLocaleDateString()}</td>
              </tr>
            ))}
          </tbody>
        </Table>
      )}
    </div>
  );
};

export default ViewSubmission;