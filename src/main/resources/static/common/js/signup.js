const urlParams = new URLSearchParams(window.location.search);
const signupSuccess = urlParams.get('signupSuccess');

if(signupSuccess!=null){
    if (signupSuccess === "false") {
        alert("실패");
    }
}

function basel() {
    let weight = parseFloat(document.getElementById("weight").value);
    let height = parseFloat(document.getElementById("height").value);
    let birthDate = new Date(document.getElementById("birth").value);
    let gender = document.querySelector('input[name="gender"]:checked')?.value;

    if (!weight || !height || !birthDate || !gender) {
        return;
    }

    let today = new Date();
    let age = today.getFullYear() - birthDate.getFullYear();
    let monthDifference = today.getMonth() - birthDate.getMonth();

    if (monthDifference < 0 || (monthDifference === 0 && today.getDate() < birthDate.getDate())) {
        age--;
    }

    let bmr = 0;

    if (gender === 'male') {
        bmr = 88.362 + (13.397 * weight) + (4.799 * height) - (5.677 * age);
    } else if (gender === 'female') {
        bmr = 447.593 + (9.247 * weight) + (3.098 * height) - (4.330 * age);
    }

    document.getElementById("basel_Metabolism").value = bmr.toFixed(0);
}

document.addEventListener('DOMContentLoaded', (event) => {
    document.getElementById("weight").addEventListener("input", basel);
    document.getElementById("height").addEventListener("input", basel);
    document.getElementById("birth").addEventListener("input", basel);
    document.querySelectorAll('input[name="gender"]').forEach((elem) => {
        elem.addEventListener("change", basel);
    });
});