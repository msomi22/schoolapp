
<!DOCTYPE html>
<html lang="en">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- The above 3 meta tags *must* come first in the head; any other head content must come *after* these tags -->
    <meta name="description" content="">
    <meta name="author" content="">
    <link rel="icon" href="../img/favicon.ico">

    <title>Fixed Top Navbar Example for Bootstrap</title>
 
    <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap-theme.css">
    <link rel="stylesheet" type="text/css" href="../css/bootstrap/bootstrap-theme.min.css">
       
    <style type="text/css">
    body {
      padding-top: 70px;
    }

    /* Set height of the grid so .sidenav can be 100% (adjust if needed) */
    .row.content {height: auto}
    
    /* Set gray background color and 100% height */
    .sidenav {
      background-color: #f1f1f1;
      height: 100%;
    }
    
    /* Set black background color, white text and some padding */
    footer {
      background-color: #555;
      color: white;
      padding: 15px;
    }
    
    /* On small screens, set height to 'auto' for sidenav and grid */
    @media screen and (max-width: 767px) {
      .sidenav {
        height: auto;
        padding: 15px;
      }
      .row.content {height: auto;}
    }

  </style>


  </head>

  
  <body>
    <nav class="navbar navbar-inverse navbar-fixed-top">
      <div class="container">
        <div class="navbar-header">
          <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#navbar" aria-expanded="false" aria-controls="navbar">
            <span class="sr-only">Toggle navigation</span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
            <span class="icon-bar"></span>
          </button>
          <a class="navbar-brand" href="">FastPro School</a>
        </div>
        <div id="navbar" class="navbar-collapse collapse">
          <ul class="nav navbar-nav">

            <li class="active"><a href="">Students</a></li>
            <li><a href="#finance">Finance</a></li>

            <li class="dropdown">
              <a href="" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Examination <span class="caret"></span></a>
              <ul class="dropdown-menu">
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">Exam Results</li>
                <li><a href="">Form 1</a></li>
                <li><a href="">Form 2</a></li>
                <li><a href="">Form 3</a></li>
                <li><a href="">Form 4</a></li>
                <li role="separator" class="divider"></li>
                <li class="dropdown-header">More..</li>
                <li><a href="">Submit exam</a></li>

              </ul>
            </li>

             <li><a href="#staff">Staff</a></li>
             <li><a href="#controlpanel">Control Panel</a></li>

          </ul>
          <ul class="nav navbar-nav navbar-right">
               <li class="dropdown">
                <a href="" class="dropdown-toggle" data-toggle="dropdown">
                  <button type="button" class="btn btn-default btn-xs">
                 <span class="glyphicon glyphicon-user"></span> User
                </button>
                <b class="caret"></b>
                </a>
                <ul class="dropdown-menu">
                    <li class="divider"></li>
                    <li><a href="profile.html">Profile</a></li> <br>
                    <li><a href="">Help</a></li> <br>
                    <li><a href="../index.html">Logout</a></li>
                </ul>
               </li>
          </ul>
        </div>
      </div>
    </nav> 


<div class="container-fluid">
  <div class="row content">
    <div class="col-sm-3 sidenav">
      <h4>Quick Links</h4>
      <ul class="nav nav-pills nav-stacked">
        <li class="active"><a href="#section1">Home</a></li>
        <li><a href="#section2">Library</a></li>
        <li><a href="#section3">SMSes</a></li>
        <li><a href="#section3">House</a></li>
        <li><a href="#section3">Parents</a></li>
      </ul><br>
      <div class="input-group">
        <input type="text" class="form-control" placeholder="Search Anything...">
        <span class="input-group-btn">
          <button class="btn btn-default" type="button">
            <span class="glyphicon glyphicon-search"></span>
          </button>
        </span>
      </div>
       <hr>
      <div id="myCarousel" class="carousel slide">
          <!-- Carousel indicators -->
            <ol class="carousel-indicators">
              <li data-target="#myCarousel" data-slide-to="0" class="active"></li>
                <li data-target="#myCarousel" data-slide-to="1"></li>
                  <li data-target="#myCarousel" data-slide-to="2"></li>
                  </ol>
                  <!-- Carousel items -->
                  <div class="carousel-inner">
                  <div class="item active">
                  <img src="../img/slide/slide1.jpg" alt="First slide">
                  <div class="carousel-caption">This Caption 1</div>
                  </div>
                  <div class="item">
                  <img src="../img/slide/slide2.jpg" alt="Second slide">
                  <div class="carousel-caption">This Caption 2</div>
                  </div>
                  <div class="item">
                  <img src="../img/slide/slide3.jpg" alt="Third slide">
                  <div class="carousel-caption">This Caption 3</div>
                  </div>
                  </div>
                  <!-- Carousel nav -->
              <a class="carousel-control left" href="#myCarousel"
                  data-slide="prev">&lsaquo;</a>
              <a class="carousel-control right" href="#myCarousel"
                  data-slide="next">&rsaquo;</a>
          </div>
          <hr>
    </div>

    <div class="col-sm-9">
        <div class="breadcrumb">
            <li> <a href="#">New student</a> </li>
            <li> <a href="#">Import from excel</a> </li>
            <li> <a href="#">Promote student</a> </li>
            <li> <a href="#">Erase student</a> </li>
       </div>

      <h4><small>STUDENTS INFO</small></h4>

      <nav class="navbar navbar-default" role="navigation">
        <div class="navbar-header">
          <a class="navbar-brand" href="#">Can i help you? </a>
        </div>
        <div>
          <form class="navbar-form navbar-left" role="search">
            <div class="form-group">
              <input type="text" class="form-control" placeholder="Search">
            </div>
               <div class="btn-group">
                  <button type="button" class="btn btn-primary dropdown-toggle" data-toggle="dropdown">
                         Sort-By <span class="caret"></span>
                  </button>
                  <ul class="dropdown-menu" role="menu">
                    <li><a href="">CLASS</a></li>
                    <li><a href="">ADM-NO</a></li>
                    <li><a href="">FIRST-NAME</a></li>
                    <li><a href="">LAST-NAME</a></li>
                  </ul>
              </div>
          </form>
          
        </div>

     </nav>
      



      



      <div class="table-responsive ">
        <table class="table table-bordered">
        <caption>Form 1 N </caption>
        <thead>
        <tr>
        <th>*</th>
        <th>Adm No</th>
        <th>First Name</th>
        <th>Middle Name</th>
        <th>Last Name</th>
        <th>Class</th>
        </tr>
        </thead>
        <tbody>

        <tr>
        <td>1</td>
        <td>1001</td>
        <td>Peter</td>
        <td>Mwenda</td>
        <td>Njeru</td>
        <td>Form 4 N</td>
        </tr>

        <tr>
        <td>2</td>
        <td>1002</td>
        <td>Obed</td>
        <td>Muthomi</td>
        <td>Mukuru</td>
        <td>Form 4 N</td>
        </tr>

        <tr>
        <td>3</td>
        <td>1003</td>
        <td>Dyanah</td>
        <td>Mwende</td>
        <td>Mwangi</td>
        <td>Form 4 N</td>
        </tr>

        <tr>
        <td>4</td>
        <td>1004</td>
        <td>Naomi</td>
        <td>Kawira</td>
        <td>Marangu</td>
        <td>Form 4 N</td>
        </tr>

        <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

         <tr>
        <td>5</td>
        <td>1005</td>
        <td>Dennis</td>
        <td>Muthomi</td>
        <td>Mutegi</td>
        <td>Form 4 N</td>
        </tr>

        </tbody>
        </table>
      </div>






      <hr>
    </div>
  </div>
</div>

<footer class="container-fluid">   
    <p class="text-center"> &copy; FasTech Solutions. All Rights Reserved. </p>
    <p class="text-center">  <button type="button" class="btn btn-link"> Lisence.</button> </p>
    <p class="text-center">  <button type="button" class="btn btn-link"> Terms & Conditions.</button> </p>
</footer>

    <!-- Bootstrap core JavaScript
    ================================================== -->
    <!-- Placed at the end of the document so the pages load faster -->
  
    <script src="../js/bootstrap/jquery-3.1.1.min.js"></script>
    <script src="../js/bootstrap/bootstrap.min.js"></script>
    <script src="../js/bootstrap/bootstrap-dropdown.js"></script>
    <script src="../js/bootstrap/bootstrap.js"></script>
    <script src="../js/bootstrap/bootstrap-button.js"></script>
    <script src="../js/bootstrap/bootstrap-tab.js"></script>
    <script src="../js/bootstrap/bootstrap-tooltip.js"></script>
    <script src="../js/bootstrap/bootstrap-modal.js"></script>
    <script src="../js/bootstrap/bootstrap-alert.js"></script>
    
    
  
  </body>
</html>
