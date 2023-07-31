import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { Container, Row, Col, Card } from 'react-bootstrap';
import './ViewTrainer.css';

const ViewTrainer = () => {
  const [trainers, setTrainers] = useState([]);

  useEffect(() => {
    // Make an API call to fetch the trainers data
    axios
      .get('http://localhost:8088/viewTrainers')
      .then((response) => {
        setTrainers(response.data);
      })
      .catch((error) => {
        console.error(error);
      });
  }, []);
  const formatDate = (timestamp) => {
    const date = new Date(timestamp);
    return date.toLocaleDateString(); // Change the format as needed
  };

  return (
    <div class="page-content page-container" id="page-content">
      <div class="padding">
        <Container>
          <h5>All Trainers</h5>
          <Row>
            {trainers.map((trainer) => (
              <Col key={trainer.id} sm={6} md={12}>
                <div class="card user-card-full">
                  <div class="row m-l-0 m-r-0">
                    <div class="col-sm-4 bg-c-lite-green user-profile">
                      <div class="card-block text-center text-white">
                        <div class="m-b-10">
                          <img
                            src="https://static.thenounproject.com/png/5034901-200.png"
                            class="img-radius"
                            alt="User-Profile-Image"
                          />
                        </div>
                        <h6 className="name">{trainer.fullName}</h6>
                        <p className="name">{trainer.designation}</p>
                        <i class=" mdi mdi-square-edit-outline feather icon-edit m-t-10 f-16"></i>
                      </div>
                    </div>
                    <div class="col-sm-8">
                      <div class="card-block">
                        <h6 class="m-b-20 p-b-5 b-b-default f-w-600">Information</h6>
                        <div class="row">
                          <div class="col-sm-6">
                            <p class="m-b-10 f-w-600">Email</p>
                            <h6 class="text-muted f-w-400">{trainer.email}</h6>
                          </div>
                          <div class="col-sm-6">
                            <p class="m-b-10 f-w-600">Phone</p>
                            <h6 class="text-muted f-w-400">{trainer.contactNumber}</h6>
                          </div>
                        </div>
                       
                        <div class="row">
                          <div class="col-sm-6">
                            <p class="m-b-10 f-w-600">Expertises</p>
                            <h6 class="text-muted f-w-400">{trainer.expertises}</h6>
                          </div>
                          <div class="col-sm-6">
                            <p class="m-b-10 f-w-600">Role</p>
                            <h6 class="text-muted f-w-400">{trainer.user.role}</h6>
                          </div>
                        </div>

                        <div class="row">
                          <div class="col-sm-6">
                          <p class="m-b-10 f-w-600">Year Of Experience</p>
                            <h6 class="text-muted f-w-400">{trainer.yearsOfExperience}</h6>
                          </div>
                          <div class="col-sm-6">
                            <p class="m-b-10 f-w-600">Joining Date</p>
                            <h6 class="text-muted f-w-400">{formatDate(trainer.joiningDate)}</h6>
                          </div>
                        </div>
                      
                      </div>
                    </div>
                  </div>
                </div>
              </Col>
            ))}
          </Row>
        </Container>
      </div>
    </div>
  );
};

export default ViewTrainer;
