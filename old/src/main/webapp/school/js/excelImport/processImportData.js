   function checkEntry()
    {
        if ($('#existing_group').val() != "")
        {
            // $('#import_show').modal("hide");
            $('#Multi_groupentry').modal("show");
        } else
        {
            $('#import_groupbody').toggle("slow");
        }
    }
    function checkGroup()
    {
        if ($('#import_groupName').val() != "")
        {
            //   $('#import_show').modal("hide");
            $('#Multi_groupentry').modal("show");
            $('#existing_group').val("");

        } else
        {
            $('#import_groupbody').hide();
        }
    }
    function checkImportGroupName()
    {
        var new_group = document.getElementById("import_groupName").value;
        var existing_groups = "";
        $('.existingGroupsI').each(function ()
        {
            var values = $(this).val();
            existing_groups += values;
        });

        if (existing_groups.includes(new_group) == true)
        {
            document.getElementById("import_btn").setAttribute("type", "button");
            $('#name_conflict').modal("show");
        }
        if (existing_groups.includes(new_group) == false)
        {
            document.getElementById("import_btn").setAttribute("type", "submit");
        }


    }
    $(document).ready(function ()
    {
        $('#my_import').DataTable(
        {
            // "scrollX": true
            paging: false,
            responsive: true
        });
        
         $('#my_Eximport').DataTable(
         {
          //    "scrollX": true
         
          paging: false,
            responsive: true
         });

        $('#multiple_queued').click(function ()
        {
            // window.location = "GroupSmsReload";
            document.getElementById("listHolder").value = "";
            document.getElementById("concts_selected").innerHTML = "Receipts selected <b>:" + 0 + "</b>";
            $('#smsGroup').val("");
            document.getElementById("dliver_m").value = "As soon as Possible";
            var textInit = 160;
            $('#rem').html(textInit + 'Characters remaining');

        })
        $('#disgard_new').click(function ()
        {
            $('#to').val("");
            $('#sms').val("");
            //window.location = "Default.cshtml";
        });

        $('#canceldel').click(function ()
        {

            //clearing the value of the contact id
            $('#contdel_id').val("");
            $('#group_id').val("");
            // alert($('#group_id').val());

        });
        $('#next').click(function ()
        {
            if ($('#next').val() == "")
            {
                $('#import_intro').hide();
                $('#file_import').toggle("slow");
                document.getElementById("back").disabled = false;
                $('#next').hide();
                $('#cancel_Import').show();
                $('#import_btn').hide();
                //  alert($('#final_import').val());
                $('#back').show();
                $('#next2').show();
            } else
            {
                var dataToImport = "";
                $('.import:checked').each(function ()
                {
                    var values = $(this).val();
                    dataToImport += values;
                });
                var type = document.getElementById('import_btn').getAttribute("type");
                document.getElementById("final_import").value = dataToImport;
               // alert(dataToImport)
                var getnums = "";
                var splitted_import = "";
                var further = "";
                var dataToImport = "";
                $('.import:checked').each(function ()
                {
                    var values = $(this).val();
                    dataToImport += values;
                    splitted_import = dataToImport.split("\n");



                });
                // alert(splitted_import.length);
                for (var i = 0; i < splitted_import.length; i++)
                {
                    if (splitted_import[i].length < 1)
                    {
                        break;
                    }
                    further = splitted_import[i].split(",");
                    getnums += further[4];
                }
                //further = splitted_import.split(",");
              // alert(getnums);
                var type = document.getElementById('import_btn').getAttribute("type");
                document.getElementById("final_import").value = dataToImport;
                // alert(splitted_import);
                //impot_split = dataToImport.split(",");

                if (getnums.length % 12 != 0)
                {
                    //alert("Problem with data tgo import");
                    document.getElementById("import_btn").setAttribute("type", "button");
                    $('#cant_import').modal("show");
                } else
                {


                    if ($('#final_import').val() == "")
                    {
                        document.getElementById("import_btn").disabled = true;
                        $('#emptyImport').modal("show");

                    } else
                    {





                        if ($('#final_import').val() != "")
                        {
                            document.getElementById("import_btn").disabled = false;
                        }
                        $('#import_intro').hide();
                        $('#create_group').show();
                        $('#file_import').hide();
                        document.getElementById("back").disabled = false;
                        document.getElementById("next").disabled = true;
                        // $('#next').hide();
                        //  alert($('#final_import').val());
                        $('#cancel_Import').hide();
                        $('#import_btn').show();
                        $('#back').hide();
                        $('#back2').show();
                        document.getElementById("back2").setAttribute('value', 'back2');
                    }
                    // $('#next2').show();
                }
            }

            // document.getElementById("next").disabled = false;
            // $('#back').disabled = false;

        });
        $('#next2').click(function ()
        {
            $('#import_intro').hide();
            var dataToImport = "";
            $('.import:checked').each(function ()
            {
                var values = $(this).val();
                dataToImport += values;
            });
            var type = document.getElementById('import_btn').getAttribute("type");
            document.getElementById("final_import").value = dataToImport;
          //  alert(dataToImport)
            var getnums = "";
            var splitted_import = "";
            var further = "";
            var dataToImport = "";
            $('.import:checked').each(function ()
            {
                var values = $(this).val();
                dataToImport += values;
                splitted_import = dataToImport.split("\n");



            });
            // alert(splitted_import.length);
            for (var i = 0; i < splitted_import.length; i++)
            {
                if (splitted_import[i].length < 1)
                {
                    break;
                }
                further = splitted_import[i].split(",");
                getnums += further[4];
            }
            //further = splitted_import.split(",");
            // alert(getnums);
            var type = document.getElementById('import_btn').getAttribute("type");
            document.getElementById("final_import").value = dataToImport;
            // alert(splitted_import);
            //impot_split = dataToImport.split(",");

            if (getnums.length % 12 != 0)
            {
               // alert("Problem with data tgo import");
                document.getElementById("import_btn").setAttribute("type", "button");
                $('#cant_importNum').modal("show");
            } else
            {


                if ($('#final_import').val() == "")
                {
                    document.getElementById("import_btn").disabled = true;
                    $('#emptyImport').modal("show");
                }
                else
                {


                    if ($('#final_import').val() != "")
                    {
                        document.getElementById("import_btn").disabled = false;
                    }
                    $('#file_import').toggle("slow");
                    // $('#create_group').toggle("slow");
                    $('#create_group').show();
                    // document.getElementById("back").disabled = false;
                    //$('#back').hide();
                    //$('#next').html('<< Back')
                    $('#back').hide();
                    $('#cancel_Import').hide();
                    $('#import_btn').show();
                    $('#back2').show();
                    document.getElementById("next2").disabled = true;
                    // $('#back').disabled = false;
                }
            }
        });

        $('#back').click(function ()
        {
            $('#import_intro').toggle("slow");
            $('#file_import').hide();
            $('#next2').hide();
            $('#next').show();
            if ($('#final_import').val() == "")
            {
                document.getElementById("import_btn").disabled = true;
            }
            document.getElementById("back").disabled = true;
            document.getElementById("next").disabled = false;
            document.getElementById("next").setAttribute('value', '');

        });
        $('#back2').click(function ()
        {
            // $('#import_intro').toggle("slow");
            if ($('#back2').val() == "")
            {
                $('#file_import').toggle("slow");
                $('#create_group').hide();
                document.getElementById("next2").disabled = false;
                // $('')
                document.getElementById("next").setAttribute('value', 'trick');
                $('#next2').hide();
                $('#cancel_Import').show();
                $('#import_btn').hide();
                $('#back2').hide();
                $('#next').show();
                $('#back').show();
            } else
            {
                $('#file_import').toggle("slow");
                $('#create_group').hide();
                //  document.getElementById("next2").disabled = false;
                document.getElementById("next").disabled = false;
                document.getElementById("next2").disabled = false;
                document.getElementById("next").setAttribute('value', '');
                $('#cancel_Import').show();
                $('#import_btn').hide();
                $('#next2').show();
                $('#back2').hide();
                $('#next').hide();
                $('#back').show();
            }


        });
        $('#browse_csv').click(function ()
        {
            // alert("What the ");
            $('#import_intro').hide();
            $('#file_import').toggle("slow");
            document.getElementById("back").disabled = false;
            $('#next').hide();
            $('#next2').show();
            // $('#back').disabled = false;

        });

        $('#ok_colx').click(function ()
        {
            window.location = "Default.cshtml";

        });
        $('#import_btn').click(function ()
        {
            /* if (checker2 == 1) {
            document.getElementById('#import_btn').setAttribute("type", "button");
            $('#import_errorNumber').modal("show");
            }*/
            var getnums = "";
            var splitted_import = "";
            var further = "";
            var dataToImport = "";
            $('.import:checked').each(function ()
            {
                var values = $(this).val();
                dataToImport += values;
                splitted_import = dataToImport.split("\n");



            });
            // alert(splitted_import.length);
            for (var i = 0; i < splitted_import.length; i++)
            {
                if (splitted_import[i].length < 1)
                {
                    break;
                }
                further = splitted_import[i].split(",");
                getnums += further[4];
            }
            //further = splitted_import.split(",");
            // alert(further);
            var type = document.getElementById('import_btn').getAttribute("type");
            document.getElementById("final_import").value = dataToImport;
            // alert(splitted_import);
            //impot_split = dataToImport.split(",");

            if (getnums.length % 12 != 0)
            {
                //alert("Problem with data tgo import");
                document.getElementById("import_btn").setAttribute("type", "button");
                $('#cant_import').modal("show");
            } else
            {
                document.getElementById("import_btn").setAttribute("type", "submit");
            }
            //dataToImport = "";
            // $('#check_import').val("");
            //alert(dataToImport);
            // alert($('#final_import').val());
        });


    });


    function checkEditCsv(id, cid)
    {

        //  id = 'csv' + id;
        cid = 'csv' + cid;
      //  alert(id + cid);
        if (document.getElementById(id).value.length != 12 || document.getElementById(id).value.includes('2547') == false)
        {
            $('#import_editNumber').modal("show");
            document.getElementById(id).focus();
            document.getElementById(cid).disabled = true;
            $('#' + cid).removeAttr('checked');
        } else
        {
            var newnum = document.getElementById(cid).value;

            var splitnum = newnum.split(",");
           // alert(splitnum[4]);
            splitnum[4] = document.getElementById(id).value;
            var joinvalu = "";
            for (var i = 0; i < splitnum.length; i++)
            {
                joinvalu += splitnum[i] + ",";
            }
            joinvalu = joinvalu.substring(0, joinvalu.length - 1);
           // alert(joinvalu);
            document.getElementById(cid).value = joinvalu;
           // alert(document.getElementById(cid).value);
            document.getElementById(cid).disabled = false;
            $('#' + cid).click();
        }
    }
    function deselect(id)
    {
        if (id == "excel_select")
        {
            var check = document.getElementById("excel_select").checked;
            if (check == true)
            {
                $('.Eximport').each(function ()
                {
                    $(this).click();
                });
            } else
            {
                $('.Eximport').each(function ()
                {
                    $(this).removeAttr('checked');
                });
            }
        }

        if (id == "de_select")
        {
            var check2 = document.getElementById("de_select").checked;
            if (check2 == true)
            {
                $('.import').each(function ()
                {
                    $(this).click();
                });
            } else
            {
                $('.import').each(function ()
                {
                    $(this).removeAttr('checked');
                });
            }

        }

    }


    function PreviewText()
    {
        var oFReader = new FileReader();
        oFReader.readAsText(document.getElementById("uploadText").files[0]);
        oFReader.onload = function (oFREvent)
        {
            document.getElementById("uploadTextValue").value = oFREvent.target.result;
            document.getElementById("obj").value = oFREvent.target.result;

            var data2 = $('#obj').val();
            var splitted2 = data2.split("\n");
            var row = "";
            var names = "";
            var proceed = 0;
            var check_col = splitted2[0].split(",");
            for (var i = 0; i < check_col.length; i++)
            {
                if (i == 0)
                {

                    var title = document.createElement("LI");

                    var t_name = document.createTextNode(check_col[0]);
                    title.appendChild(t_name);
                    document.getElementById("col_list").appendChild(title);
                    if (check_col[0] != "Title")
                    {

                        title.setAttribute("class", "error");
                        proceed = 1;
                    }
                }
                if (i == 1)
                {

                    var fname = document.createElement("LI");

                    var f_name = document.createTextNode(check_col[1]);
                    fname.appendChild(f_name);
                    document.getElementById("col_list").appendChild(fname);
                    if (check_col[1] != "First Name")
                    {
                        fname.setAttribute("class", "error");
                        proceed = 1;

                    }
                }
                if (i == 2)
                {

                    var mname = document.createElement("LI");

                    var m_name = document.createTextNode(check_col[2]);
                    mname.appendChild(m_name);
                    document.getElementById("col_list").appendChild(mname);
                    if (check_col[2] != "Middle Name")
                    {
                        mname.setAttribute("class", "error");
                        proceed = 1;

                    }
                }
                if (i == 3)
                {
                    var lname = document.createElement("LI");

                    var l_name = document.createTextNode(check_col[3]);
                    lname.appendChild(l_name);
                    document.getElementById("col_list").appendChild(lname);

                    if (check_col[3] != "Last Name")
                    {
                        lname.setAttribute("class", "error");
                        proceed = 1;

                    }
                }
                if (i == 4)
                {
                    var num = document.createElement("LI");

                    var n_name = document.createTextNode(check_col[4]);
                    num.appendChild(n_name);
                    document.getElementById("col_list").appendChild(num);

                    if (check_col[4] != "Phone Number")
                    {
                        num.setAttribute("class", "error");
                        proceed = 1;

                    }
                }
                if (i == 5)
                {
                    var ename = document.createElement("LI");

                    var e_name = document.createTextNode(check_col[5]);
                    ename.appendChild(e_name);
                    document.getElementById("col_list").appendChild(ename);

                    if (check_col[5] != "Email Address")
                    {
                        ename.setAttribute("class", "error");
                        proceed = 1;

                    }
                }
            }
            if (proceed == 1)
            {
                $('#import_show').modal("hide");
                $('#column_missmatch').modal("show");
            } else
            {

                document.getElementById("check_import").value = "assigned";
                //alert($('#check_import').val());
                $('#check_import').val("assigned");
                var table2 = $('#my_import').DataTable();
                var id = 0;
                var cid = 1000000;
                var nums = "";
                for (var i = 1; i < splitted2.length; i++)
                {
                    //  alert("am pissed off");
                    //  var cells = splitted[i].split(",");
                    // row += '<tr><td>'+cells[0]+'</td> <td>'+cells[1]+'</td> <td></td> <td style="display: none"></td> <td style="display: none"></td> <td style="display: none"></td></tr>'



                    var cells = splitted2[i].split(",");
                    //  alert(cells.length);
                    if (cells.length == 1)
                    {
                        splitted2 = "";
                        break;
                    } else
                    {


                        /* var rowCount = table2.rows.length;
                        var row = table2.insertRow(rowCount);
                        // alert(rowCount);
                        var cell1 = row.insertCell(0);
                        var checkbox = document.createElement("input");
                        checkbox.type = "checkbox";
                        checkbox.name = "gmemberSms";
                        checkbox.id = "gmemberSms";
                        checkbox.setAttribute('checked', true);
                        checkbox.setAttribute('class', 'import');
                        checkbox.value = cells[0] + "," + cells[1] + "," + cells[2] + "," + cells[3] + "," + cells[4] + "," + cells[5] + "," + cells[6] + "," + cells[7] + "\n";
                        cell1.appendChild(checkbox);*/


                        /*var cellx = row.insertCell(1);
                        var title = document.createElement("label");
                        title.setAttribute('class', 'break');
                        title.appendChild(document.createTextNode(cells[0]));*/
                        var title = cells[0];


                        //cellx.appendChild(title);

                        // var cell2 = row.insertCell(2);
                        // var fname = document.createElement("label");

                        //fname.appendChild(document.createTextNode(cells[1]));
                        // if (cells[1] == "")
                        // {
                        //   fname.setAttribute('class', 'error');
                        //  alert("FirstName shouldn't be empty");
                        // }
                        //cell2.appendChild(fname);
                        var fname = cells[1];

                        /* var cell3 = row.insertCell(3);
                        var mname = document.createElement("label");
                        mname.setAttribute('class', 'break');
                        mname.appendChild(document.createTextNode(cells[2]));
                        cell3.appendChild(mname);*/
                        var mname = cells[2];

                        /*var cell4 = row.insertCell(4);
                        var lname = document.createElement("label");
                        lname.setAttribute('class', 'break');
                        lname.appendChild(document.createTextNode(cells[3]));
                        cell4.appendChild(lname);*/
                        var lname = cells[3];

                        var checkbox = '';
                        nums += cells[4];
                        //  var cell5 = row.insertCell(5);
                        var num = '';
                        var checker2 = 0;
                        var init = "2547";
                        var iid = 'csv' + id;
                        var icid = 'csv' + cid;
                        if (cells[4] == "" || cells[4].length < 12 || cells[4].includes(init) == false)
                        {
                            // num.setAttribute('class', 'error');
                            checker2 = 1;
                            num = '<input type="number" maxlength="12"  id=' + iid + ' class="editImportNumCsv" onblur="checkEditCsv(this.id,' + cid + ')" value=' + cells[4] + ' >';
                            checkbox = '<input type="checkbox" class="import" id=' + icid + ' value="' + cells[0] + ',' + cells[1] + ',' + cells[2] + ',' + cells[3] + ',' + cells[4] + ',' + cells[5] + ',' + cells[6] + ',' + cells[7] + '\n" disabled>';

                            // checkbox.setAttribute("checked", false);
                            // checkbox.setAttribute("disabled", "disabled");
                            //document.getElementById("import_btn").setAttribute("type", "button");
                            $('#import_errorNumber').modal("show");
                            id += 1;
                        cid += 1;

                        } else
                        {
                            checkbox = '<input class="import" type="checkbox" value="' + cells[0] + ',' + cells[1] + ',' + cells[2] + ',' + cells[3] + ',' + cells[4] + ',' + cells[5] + ',' + cells[6] + ',' + cells[7] + '\n" checked>';
                            num = cells[4];

                        }

                        

                        //  num.appendChild(document.createTextNode(cells[4]));
                        // cell5.appendChild(num);


                        /* var cell6 = row.insertCell(6);
                        var email = document.createElement("label");
                        email.setAttribute('class', 'break');
                        email.appendChild(document.createTextNode(cells[5]));
                        cell6.appendChild(email);*/
                        var email = cells[5];

                        table2.row.add([
                        checkbox,
                        title,
                        fname,
                        mname,
                        lname,
                        num,
                        email
                        ]).draw(false);


                        /*   var cell7 = row.insertCell(6);
                        var addr = document.createElement("label");
                        addr.setAttribute('class', 'break');
                        addr.appendChild(document.createTextNode(cells[6]));
                        cell7.appendChild(addr);

                        var cell8 = row.insertCell(7);
                        var city = document.createElement("label");
                        city.setAttribute('class', 'break');
                        city.appendChild(document.createTextNode(cells[7]));
                        cell8.appendChild(city);
                        */
                        //names += cells[0];
                    }
                }
                document.getElementById("names_import").value = nums;
            }
            // document.getElementById("import_body").innerHTML = row;
            //document.getElementById("names_import").value = names;
            //alert($('#names_import').val());
            var dataToImport = "";
            if (proceed == 0)
            {

                $('#newImportGroup').show();

            }

        }
    }