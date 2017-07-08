<jsp:include page="header.jsp" />


        <!-- page content -->
        <div class="right_col" role="main">
          <div class="">
            <div class="page-title">
              <div class="title_left">
                <h3>Student's Profile</h3>
              </div>
            </div>
            
            <div class="clearfix"></div>

            <div class="row">
              <div class="col-md-12 col-sm-12 col-xs-12">
                <div class="x_panel">
                  <div class="x_content">
                  
                    <div class="col-md-3 col-sm-3 col-xs-12 profile_left">
                      <div class="profile_img">
                        <div id="crop-avatar">
                          <!-- Current avatar -->
                          <img class="img-responsive avatar-view" src="images/peter.jpg" alt="Avatar" title="Change the avatar">
                        </div>
                      </div>
                      <h3>Peter Mwenda</h3>

                      <ul class="list-unstyled user_data">
                        <li>
                          <i class="fa fa-map-marker user-profile-icon"></i> Tharaka Nithi 
                        </li>
                        
                        <li>
                          <i class="fa fa-briefcase user-profile-icon"></i> FORM 4 N
                        </li>

                        <li class="m-top-xs">
                          <i class="fa fa-external-link user-profile-icon"></i>
                           Active
                        </li>
                      </ul>

                      <a class="btn btn-success"><i class="fa fa-edit m-right-xs"></i>Edit Profile</a>
                      <br />

                      <!-- start skills -->
                      <h4>More</h4>
                      <ul class="list-unstyled user_data">
                        <li>
                          <p>Ranking </p>
                          <div class="progress progress_sm">
                            <div class="progress-bar bg-green" role="progressbar" data-transitiongoal="50"></div>
                          </div>
                        </li>                        
                      </ul>
                      <!-- end of skills -->
                    </div>
                    
                    
                    <div class="col-md-9 col-sm-9 col-xs-12">
                     
                      <div class="" role="tabpanel" data-example-id="togglable-tabs">
                        <ul id="myTab" class="nav nav-tabs bar_tabs" role="tablist">
                          <li role="presentation" class="active">
                            <a href="#tab_content1" id="home-tab" role="tab" data-toggle="tab" aria-expanded="true">General Info</a>
                          </li>
                          <li role="presentation" class="">
                            <a href="#tab_content2" role="tab" id="profile-tab" data-toggle="tab" aria-expanded="false">Subjects And Classroom</a>
                          </li>
                          <li role="presentation" class="">
                            <a href="#tab_content3" role="tab" id="profile-tab2" data-toggle="tab" aria-expanded="false">Primary</a>
                          </li>
                        </ul>
                        <div id="myTabContent" class="tab-content">
                          <div role="tabpanel" class="tab-pane fade active in" id="tab_content1" aria-labelledby="home-tab">

                            <!-- start recent activity -->
                            <div class="messages">
                              
                              General Info here

                            </div>
                            <!-- end recent activity -->

                          </div>
                          <div role="tabpanel" class="tab-pane fade" id="tab_content2" aria-labelledby="profile-tab">

                            <!-- start user projects -->
                             <p> 
                             Subjects and classroom 
                             </p>
                            <!-- end user projects -->

                          </div>
                          <div role="tabpanel" class="tab-pane fade" id="tab_content3" aria-labelledby="profile-tab">
                            <p>
                              Primary school info
                            </p>
                          </div>
                        </div>
                      </div>
                    </div>
                    
                    
                    
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
        <!-- /page content -->

        <!-- footer -->
<jsp:include page="footer.jsp" />
        