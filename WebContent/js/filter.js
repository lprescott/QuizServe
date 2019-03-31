/**
 * This function filters a table by its second and third column, given the id of the table (tableName), id of the input (filterName), and
 * input value (filter).
 */

function filterTable(filterName, tableName) {

  //variables
  var input, filter, table, tr, columnTwo, columnThree, i, columnTwoValue, columnThreeValue;
  
  input = document.getElementById(filterName); //the input element
  filter = input.value.toUpperCase(); //the input query
  table = document.getElementById(tableName); //the table element
  tr = table.getElementsByTagName("tr"); //the row elements

  //This for-loop passes the table entries hiding or leaving alone an entry based in its value
  //	in the second OR third column; if the input value is contained therin, the entry is left alone
  for (i = 0; i < tr.length; i++) {
    columnTwo = tr[i].getElementsByTagName("td")[1];
    columnThree = tr[i].getElementsByTagName("td")[2];
    
    //compare both column two and three
    if (columnTwo && columnThree) {
      columnTwoValue = columnTwo.textContent || columnTwo.innerText;
      columnThreeValue = columnThree.textContent || columnThree.innerText;
      
      if ((columnTwoValue.toUpperCase().indexOf(filter) > -1) || (columnThreeValue.toUpperCase().indexOf(filter) > -1)) {
        tr[i].style.display = "";
      } else {
        tr[i].style.display = "none";
      }
    } 
  }
}