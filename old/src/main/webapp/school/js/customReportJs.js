$("#exam").change(function () {
      if($("#exam option:selected").length > 3 ) {
          alert('At least one exam and not more than three exams are allowed');
          
          $("#exam").focus();
          $("#exam").val("");
      }
  });