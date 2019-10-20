document.getElementById("go").addEventListener('click',sendQuery);
var airports;
function sendQuery(){
    var location = document.getElementById("location").value;
    var pLimit = document.getElementById("pLimit").value;
    var period = document.getElementById("period").value;
    if(location && pLimit && period){
        var request = {location:location,pLimit:pLimit,period:period};

        var xhttp = new XMLHttpRequest();

        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {

                console.log(this.responseText);

            }
            else{
                console.log("No resposne");
                updateFlight({status:"not found"});
            }
        };
        xhttp.open("POST", "http://localhost:8080/flight", true);
        xhttp.setRequestHeader("Content-Type", "application/json");
        xhttp.send(request);
    }


}



function getSuggestions(){
    var request = {location:location,pLimit:pLimit,period:period};

    var xhttp = new XMLHttpRequest();
    xhttp.onreadystatechange = function() {
        if (this.readyState == 4 && this.status == 200) {

                console.log(this.responseText);
                airports = this.responseText;

        }
        else{
            console.log("No resposne");
        }
    };
    xhttp.open("GET", "https://www.skyscanner.net/g/chiron/api/v1/places/autosuggest/v1.0/AF/GBP/en-EN?query="  , true);
    xhttp.setRequestHeader("api-key", "skyscanner-guts2019");
    xhttp.send(request);
}

function autocomplete(inp) {
    /*the autocomplete function takes two arguments,
    the text field element and an array of possible autocompleted values:*/
    var currentFocus;
    /*execute a function when someone writes in the text field:*/
    inp.addEventListener("input", function(e) {

        var xhttp = new XMLHttpRequest();
        xhttp.onreadystatechange = function() {
            if (this.readyState == 4 && this.status == 200) {

                console.log(this.responseText);
                airports = this.responseText;
                var arr1 = JSON.parse(this.responseText).Places;
                console.log(arr1);
                var arr = [];
                console.log("Arr1 length :" + arr1.length);
                console.log(arr1[0]);
               // for(var j=0;i<arr1.length;i++){
                //    console.log(arr1[j].PlaceName);
                 //   arr.push(arr1[j].PlaceName);
               // }
                arr1.forEach(function (arrayItem) {
                    var x = arrayItem.PlaceName;
                    console.log(x);
                    arr.push(x);

                });
                console.log(arr);



                var a, b, i, val = inp.value;
                console.log(inp);
                /*close any already open lists of autocompleted values*/
                closeAllLists();
                if (!val) { return false;}
                currentFocus = -1;
                /*create a DIV element that will contain the items (values):*/
                a = document.createElement("DIV");
                a.setAttribute("id", this.id + "autocomplete-list");
                a.setAttribute("class", "autocomplete-items");
                /*append the DIV element as a child of the autocomplete container:*/
                inp.parentNode.appendChild(a);
                /*for each item in the array...*/
                for (i = 0; i < arr.length; i++) {
                    /*check if the item starts with the same letters as the text field value:*/
                    if (arr[i].substr(0, val.length).toUpperCase() == val.toUpperCase()) {
                        /*create a DIV element for each matching element:*/
                        b = document.createElement("DIV");
                        /*make the matching letters bold:*/
                        b.innerHTML = "<strong>" + arr[i].substr(0, val.length) + "</strong>";
                        b.innerHTML += arr[i].substr(val.length);
                        /*insert a input field that will hold the current array item's value:*/
                        b.innerHTML += "<input type='hidden' value='" + arr[i] + "'>";
                        /*execute a function when someone clicks on the item value (DIV element):*/
                        b.addEventListener("click", function(e) {
                            /*insert the value for the autocomplete text field:*/
                            inp.value = this.getElementsByTagName("input")[0].value;
                            /*close the list of autocompleted values,
                            (or any other open lists of autocompleted values:*/
                            closeAllLists();
                        });
                        a.appendChild(b);
                    }
                }

            }
            else{
                console.log("No resposne");
            }
        };
        xhttp.open("GET", "https://www.skyscanner.net/g/chiron/api/v1/places/autosuggest/v1.0/AF/GBP/en-EN?query=" + inp.value, true);
        xhttp.setRequestHeader("api-key", "skyscanner-guts2019");
        xhttp.send();

    });
    function addActive(x) {
        /*a function to classify an item as "active":*/
        if (!x) return false;
        /*start by removing the "active" class on all items:*/
        removeActive(x);
        if (currentFocus >= x.length) currentFocus = 0;
        if (currentFocus < 0) currentFocus = (x.length - 1);
        /*add class "autocomplete-active":*/
        x[currentFocus].classList.add("autocomplete-active");
    }
    function removeActive(x) {
        /*a function to remove the "active" class from all autocomplete items:*/
        for (var i = 0; i < x.length; i++) {
            x[i].classList.remove("autocomplete-active");
        }
    }
    function closeAllLists(elmnt) {
        /*close all autocomplete lists in the document,
        except the one passed as an argument:*/
        var x = document.getElementsByClassName("autocomplete-items");
        for (var i = 0; i < x.length; i++) {
            if (elmnt != x[i] && elmnt != inp) {
                x[i].parentNode.removeChild(x[i]);
            }
        }
    }
    /*execute a function when someone clicks in the document:*/
    document.addEventListener("click", function (e) {
        closeAllLists(e.target);
    });
}

autocomplete(document.getElementById("location"));