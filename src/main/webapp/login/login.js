const loginForm = `
    <form id="loginForm" action="/P1/view/login" method="POST">
        <div class="field">
            <label class="label">Email</label>
            <div class="control has-icons-left has-icons-right">
                <input id="email" class="input" type="email" placeholder="Email input" name="email" required>
                <span class="icon is-small is-left">
                    <i class="fas fa-envelope"></i>
                </span>
                <span class="icon is-small is-right email-invalid hidden">
                    <i class="fas fa-exclamation-triangle"></i>
                </span>
            </div>
            <p id="email-message" class="help is-danger email-invalid hidden">This email is invalid</p>
        </div>
        <div class="field">
            <label class="label">Password</label>
            <div class="control has-icons-left has-icons-right">
                <input id="password" class="input" type="password" placeholder="Password" name="password" required>
                <span class="icon is-small is-left">
                    <i class="fas fa-lock"></i>
                </span>
                <span class="icon is-small is-right password-invalid hidden">
                    <i class="fas fa-exclamation-triangle"></i>
                </span>
            </div>
            <p class="help is-danger password-invalid hidden">Password is required.</p>
        </div>

        <div class="buttons">
            <button type="button" onclick="onLogin()" class="button is-link">Log In</button>
            
        </div>
    </form>
`;

const registerForm = `
    <form id="registerForm" action="/P1/view/register" method="POST">
        <div class="field">
            <label class="label">First Name</label>
            <div class="control has-icons-left has-icons-right">
                <input id="firstName" class="input" type="text" placeholder="First Name" name="firstName" required>
                <span class="icon is-small is-left">
                    <i class="fas fa-user"></i>
                </span>
                <span class="icon is-small is-right first-invalid hidden">
                    <i class="fas fa-exclamation-triangle"></i>
                </span>
            </div>
            <p class="help is-danger first-invalid hidden">First name is Required.</p>
        </div>
        <div class="field">
            <label class="label">Last Name</label>
            <div class="control has-icons-left has-icons-right">
                <input id="lastName" class="input" type="text" placeholder="Last Name" name="lastName" required>
                <span class="icon is-small is-left">
                    <i class="fas fa-user"></i>
                </span>
                <span class="icon is-small is-right last-invalid hidden">
                    <i class="fas fa-exclamation-triangle"></i>
                </span>
            </div>
            <p class="help is-danger last-invalid hidden">Last name is Required.</p>
        </div>
        <div class="field">
            <label class="label">Email</label>
            <div class="control has-icons-left has-icons-right">
                <input id="email" class="input" type="email" placeholder="Email input" name="email" required>
                <span class="icon is-small is-left">
                    <i class="fas fa-envelope"></i>
                </span>
                <span class="icon is-small is-right email-invalid hidden">
                    <i class="fas fa-exclamation-triangle"></i>
                </span>
            </div>
            <p id="email-message" class="help is-danger email-invalid hidden">This email is invalid</p>
        </div>
        <div class="field">
            <label class="label">Password</label>
            <div class="control has-icons-left has-icons-right">
                <input id="password" class="input" type="password" placeholder="Password" name="password" required>
                <span class="icon is-small is-left">
                    <i class="fas fa-lock"></i>
                </span>
                <span class="icon is-small is-right password-invalid hidden">
                    <i class="fas fa-exclamation-triangle"></i>
                </span>
            </div>
            <p id="password-message" class="help is-danger password-invalid hidden">Password is required</p>
        </div>
        <div class="field">
            <label class="label">Confirm Password</label>
            <div class="control has-icons-left has-icons-right">
                <input id="confPassword" class="input" type="password" placeholder="Confirm Password" required>
                <span class="icon is-small is-left">
                    <i class="fas fa-lock"></i>
                </span>
            </div>
        </div>

        <div class="buttons">
            <button type="button" onclick="onRegister()" class="button is-link">Register</button>
        </div>
    </form>
`;

const formContainer = document.querySelector("#formContainer");
formChange('login');

let reqHeader = new Headers();
reqHeader.append('Content-Type', 'text/json');

function inputChange(e) {
    if(e.key == " ") {
        console.log("space");
        e.preventDefault();
    }
}


function onLogin() {
    let params = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value
    };
    let valid = true;
    // email validation
    let els = document.getElementsByClassName("email-invalid");
    if(params.email == "") {
        document.querySelector("#email-message").innerHTML = "Email is Required";
        document.querySelector("#email").classList.add('is-danger');
        for(let e of els) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else if(!/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(params.email)) {
        document.querySelector("#email-message").innerHTML = "This email is invalid";
        document.querySelector("#email").classList.add('is-danger');
        for(let e of els) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else {
        document.querySelector("#email").classList.remove('is-danger');
        for(let e of els) {
            e.classList.add('hidden');
        }
    }

    // password validation
    let pls = document.getElementsByClassName("password-invalid");
    if(params.password == "") {
        document.querySelector("#password").classList.add('is-danger');
        for(let e of pls) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else {
        document.querySelector("#password").classList.remove('is-danger');
        for(let e of pls) {
            e.classList.add('hidden');
        }
    }

    if(!valid){
        return;
    }

    
    let initObject = {
        method: 'POST', headers: reqHeader, body: JSON.stringify(params)
    };
    
    fetch('http://localhost:8080/P1/api/login', initObject)
    .then(response => response.json())
    .then(res => {
        if(!res.data.auth) {
            
            alert("Username or Password was incorrect");
        } else {
            document.querySelector("#loginForm").submit();
        }
    })
    .catch((error) => {
        console.error('Error:', error);
    });
    
}

function onRegister(e) {
    let params = {
        email: document.getElementById('email').value,
        password: document.getElementById('password').value,
        firstName: document.getElementById('firstName').value,
        lastName: document.getElementById('lastName').value,
    };
    let valid = true;
    let fls =  document.getElementsByClassName("first-invalid");
    if (params.firstName == '')  {
        document.querySelector("#firstName").classList.add('is-danger');
        for(let e of fls) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else {
        document.querySelector("#firstName").classList.remove('is-danger');
        for(let e of fls) {
            e.classList.add('hidden');
        }
    }
    
    let lls =  document.getElementsByClassName("last-invalid");
    if (params.firstName == '')  {
        document.querySelector("#lastName").classList.add('is-danger');
        for(let e of lls) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else {
        document.querySelector("#lastName").classList.remove('is-danger');
        for(let e of lls) {
            e.classList.add('hidden');
        }
    }

    let els = document.getElementsByClassName("email-invalid");
    if(params.email == "") {
        document.querySelector("#email-message").innerHTML = "Email is Required";
        document.querySelector("#email").classList.add('is-danger');
        for(let e of els) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else if(!/^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@[a-zA-Z0-9-]+(?:\.[a-zA-Z0-9-]+)*$/.test(params.email)) {
        document.querySelector("#email-message").innerHTML = "This email is invalid";
        document.querySelector("#email").classList.add('is-danger');
        for(let e of els) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else {
        document.querySelector("#email").classList.remove('is-danger');
        for(let e of els) {
            e.classList.add('hidden');
        }
    }

    let pls = document.getElementsByClassName("password-invalid");
    if(params.password == "") {
        document.querySelector("#password").classList.add('is-danger');
        for(let e of pls) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else {
        document.querySelector("#password").classList.remove('is-danger');
        for(let e of pls) {
            e.classList.add('hidden');
        }
        if(params.password != document.getElementById("confPassword").value) {
            console.log('no match');
            document.querySelector("#password-message").innerHTML = "Passwords must match";
            document.querySelector("#password").classList.add('is-danger');
            for(let e of pls) {
                e.classList.remove('hidden');
            }
            valid = false;
        } else {
            console.log('match');
            document.querySelector("#password").classList.remove('is-danger');
            document.querySelector("#password-message").innerHTML = "Password is Required";
            for(let e of pls) {
                e.classList.add('hidden');
            }
        }
    }

    if(!valid) {
        console.log('nope!');
        return;
    }

    
    let initObject = {
        method: 'POST', headers: reqHeader, body: JSON.stringify(params)
    };

    fetch('http://localhost:8080/P1/api/register', initObject)
    .then(response => response.json())
    .then(res => {
        if(res) {
            document.querySelector("#email").classList.add('is-danger');
            document.querySelector("#email-message").innerHTML = "This email is taken.";
            
            for(let e of els) {
                e.classList.remove('hidden');
            }
            valid = false;
        } else {
            document.querySelector("#email").classList.remove('is-danger');
            for(let e of els) {
                e.classList.add('hidden');
            }
            document.querySelector("#registerForm").submit();
        }
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}

function formChange(form) {
    switch(form) {
        case "login":
            formContainer.innerHTML = loginForm;
            break;
        case "register":
            formContainer.innerHTML = registerForm;
            break;
    }
}
