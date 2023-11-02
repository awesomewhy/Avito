
function submitSignupForm() {
    // Get data from the signup form
    const username = document.getElementById('user').value;
    const password = document.getElementById('pass').value;
    const confirmPassword = document.getElementById('confirmPassword').value;
    const email = document.getElementById('email').value;

    const userData = {
        username: username,
        password: password,
        confirmPassword: confirmPassword,
        email: email
    };

    sendUserDataToServer('/registration', userData).then(r => quit());
}

function submitLoginForm() {
    const username = document.getElementById('loginuser').value;
    const password = document.getElementById('loginpass').value;

    const loginData = {
        username: username,
        password: password
    };
    const response = sendUserDataToServer('/auth', loginData);
    response
        .then(() => {
            // Successful login, redirect to '/secured'
            window.location.href = '/secured';
        })
        .catch(error => {
            if (error.response && (error.response.status === 401 || error.response.status === 403)) {
                // Unauthorized or Forbidden error, prompt user to login
                alert('Please login to access the secured page.');
            } else {
                // Other error, handle accordingly
                console.error('Error:', error);
            }
        });
}

function sendUserDataToServer(url, data) {
    return new Promise((resolve, reject) => {
        // Make the AJAX request to the server
        // Example using fetch API
        fetch(url, {
            method: 'POST',
            body: JSON.stringify(data),
            headers: {
                'Content-Type': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    resolve(response.json());
                } else {
                    reject(response);
                }
            })
            .catch(error => {
                reject(error);
            });
    });
}
