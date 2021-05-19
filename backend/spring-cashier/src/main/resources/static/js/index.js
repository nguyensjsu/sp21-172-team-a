function openCity(cityName) {
    var i;
    var x = document.getElementsByClassName("city");
    for (i = 0; i < x.length; i++) {
        x[i].style.display = "none";
    }
    document.getElementById(cityName).style.display = "block";
}

function clearSelection() {
    var ele = document.getElementsByName("selection");
    for(var i=0;i<ele.length;i++)
        ele[i].checked = false;
}
