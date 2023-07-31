import React, { useEffect, useState } from 'react';
import axios from 'axios';
import { useParams } from 'react-router-dom';
import './PersonalProfile.css';

const PersonalProfile = ({ role, profileId }) => {
  const { traineeId } = useParams();
  const [profileData, setProfileData] = useState(null);
  const token = localStorage.getItem('token');

  useEffect(() => {
    // Function to fetch data based on role
    const fetchDataBasedOnRole = () => {
      switch (role) {
        case 'Trainee':
          axios
            .get(`http://localhost:8088/trainee/${profileId}`,{
              headers : {
                Authorization: `Bearer ${token}`,
              },
            })
            .then((response) => {
              setProfileData(response.data);
            })
            .catch((error) => {
              console.error(error);
            });
          break;
        case 'Trainer':
          axios
            .get(`http://localhost:8088/trainer/${profileId}`,{
              headers : {
                Authorization: `Bearer ${token}`,
              },
            })
            .then((response) => {
              setProfileData(response.data);
            })
            .catch((error) => {
              console.error(error);
            });
          break;
        case 'Admin':
          axios
            .get(`http://localhost:8088/admin/${profileId}`,{
              headers : {
                Authorization: `Bearer ${token}`,
              },
            })
            .then((response) => {
              setProfileData(response.data);
            })
            .catch((error) => {
              console.error(error);
            });
          break;
        default:
          console.error('Invalid role:', role);
          break;
      }
    };

    fetchDataBasedOnRole();
  }, [role, profileId]);

  return (
    <div>
      {profileData ? (
        <div>
          <div class="container emp-profile">
            <form method="post">
              {/* Profile display code here */}
              <div class="row">
                <div class="col-md-4">
                  <div class="profile-img">
                    <img src="https://static.thenounproject.com/png/5034901-200.png" alt="" />
                    <div class="file btn btn-lg btn-primary">
                     
                      <input type="file" name="file" />
                    </div>
                  </div>
                </div>
                <div class="col-md-6">
                  <div class="profile-head">
                    <h5>{profileData.fullName}</h5>
                    <h6>{profileData.user.role}</h6>

                    <ul class="nav nav-tabs" id="myTab" role="tablist">
                      <li class="nav-item">
                        <a
                          class="nav-link active"
                          id="home-tab"
                          data-toggle="tab"
                          href="#home"
                          role="tab"
                          aria-controls="home"
                          aria-selected="true"
                        >
                          About
                        </a>
                      </li>
                      <li class="nav-item">
                        <a
                          class="nav-link"
                          id="profile-tab"
                          data-toggle="tab"
                          href="#profile"
                          role="tab"
                          aria-controls="profile"
                          aria-selected="false"
                        ></a>
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
                    <p>{profileData.educationalInstitute}</p>
                    
                    {role == 'Trainee' ? (
                      <>
                    <p>Passing Year: {profileData.passingYear}</p>
                    <p>CGPA: {profileData.cgpa}</p>
                    <p>Degree: {profileData.degreeName}</p>
                    
                    </>
                    ):null}
                    
                  </div>
                </div>
                <div class="col-md-8">
                  <div class="tab-content profile-tab" id="myTabContent">
                    <div
                      class="tab-pane fade show active"
                      id="home"
                      role="tabpanel"
                      aria-labelledby="home-tab"
                    >
                      <div class="row">
                        {/* Additional profile information */}
                      </div>
                      <div class="row">
                        <div class="col-md-6">
                          <label>Name</label>
                        </div>
                        <div class="col-md-6">
                          <p>{profileData.fullName}</p>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-md-6">
                          <label>Email</label>
                        </div>
                        <div class="col-md-6">
                          <p>{profileData.email}</p>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-md-6">
                          <label>Phone</label>
                        </div>
                        <div class="col-md-6">
                          <p>{profileData.contactNumber}</p>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-md-6">
                          <label>Profession</label>
                        </div>
                        <div class="col-md-6">
                          <p>{profileData.user.role}</p>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-md-6">
                          <label>Present Address</label>
                        </div>
                        <div class="col-md-6">
                          <p>{profileData.presentAddress}</p>
                        </div>
                      </div>
                      <div class="row">
                        <div class="col-md-6">
                          <label>Role</label>
                        </div>
                        <div class="col-md-6">
                          <p>{profileData.user.role}</p>
                        </div>
                        
                       
                      </div>
                      <div class="row">
                        <div class="col-md-6">
                          <label>Gender</label>
                        </div>
                        <div class="col-md-6">
                          <p>{profileData.gender}</p>
                        </div>
                        
                       
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </form>
          </div>
        </div>
      ) : (
        <p>Loading profile data...</p>
      )}
    </div>
  );
};

export default PersonalProfile;
