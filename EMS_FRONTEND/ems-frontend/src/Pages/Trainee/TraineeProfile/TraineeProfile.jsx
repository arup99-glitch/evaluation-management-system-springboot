import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './TraineeProfile.css';

const TraineeProfile = () => {
    const token = localStorage.getItem('token');

    const {traineeId} = useParams();
    // const traineeId=1;
    
    
  const [trainee, setTrainee] = useState(null);

  useEffect(() => {
    console.log(traineeId);
    if (traineeId) {
       
      axios
        .get(`http://localhost:8088/trainee/${traineeId}`,{
            headers : {
              Authorization: `Bearer ${token}`,
            },
          })
        .then((response) => {
          setTrainee(response.data);
        })
        .catch((error) => {
          console.error(error);
        });
    }
  }, [traineeId]);
 

  return (
    <div>
        
      {trainee ? (
        <div>
            <div>
            <div class="container emp-profile">
            <form method="post">
                <div class="row">
                    <div class="col-md-4">
                        <div class="profile-img">
                            <img src="https://static.thenounproject.com/png/5034901-200.png" alt=""/>
                            <div class="file btn btn-lg btn-primary">
                                
                                <input type="file" name="file"/>
                            </div>
                        </div>
                    </div>
                    <div class="col-md-6">
                        <div class="profile-head">
                                    <h5>
                                    {trainee.fullName}
                                    </h5>
                                    <h6>
                                        {trainee.user.role}
                                    </h6>
                                 
                                  <ul class="nav nav-tabs" id="myTab" role="tablist">
                                <li class="nav-item">
                                    <a class="nav-link active" id="home-tab" data-toggle="tab" href="#home" role="tab" aria-controls="home" aria-selected="true">About</a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" id="profile-tab" data-toggle="tab" href="#profile" role="tab" aria-controls="profile" aria-selected="false"></a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    {/* <div class="col-md-2">
                        <input type="submit" class="profile-edit-btn" name="btnAddMore" value="Edit Profile"/>
                    </div> */}
                </div>
                <div class="row">
                    <div class="col-md-4">
                        <div class="profile-work">
                          
                            <p>{trainee.educationalInstitute}</p>
                            <p>Passing Year :{trainee.passingYear}</p>
                            <p> CGPA :{trainee.cgpa}</p>
                            <p>Degree :{trainee.degreeName}</p>
                            <p>Gender :{trainee.gender}</p>
                        </div>
                    </div>
                    <div class="col-md-8">
                        <div class="tab-content profile-tab" id="myTabContent">
                            <div class="tab-pane fade show active" id="home" role="tabpanel" aria-labelledby="home-tab">
                                        <div class="row">
                                            
                                            
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label>Name</label>
                                            </div>
                                            <div class="col-md-6">
                                                <p>{trainee.fullName}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label>Email</label>
                                            </div>
                                            <div class="col-md-6">
                                                <p>{trainee.email}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label>Phone</label>
                                            </div>
                                            <div class="col-md-6">
                                                <p>{trainee.contactNumber}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label>Profession</label>
                                            </div>
                                            <div class="col-md-6">
                                                <p>{trainee.user.role}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label>Present Address</label>
                                            </div>
                                            <div class="col-md-6">
                                                <p>{trainee.presentAddress}</p>
                                            </div>
                                        </div>
                                        <div class="row">
                                            <div class="col-md-6">
                                                <label>Role</label>
                                            </div>
                                            <div class="col-md-6">
                                                <p>{trainee.user.role}</p>
                                            </div>
                                        </div>
                            </div>
                          
                        </div>
                    </div>
                </div>
            </form>           
        </div>
            </div>
        </div>
      ) : (
        <p>Loading trainee profile...</p>
      )}
    </div>
  );
};

export default TraineeProfile;