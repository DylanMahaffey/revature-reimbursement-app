
/**
 * HTML Elements
 */

let ticketComponent = `
    <table class="table is-fullwidth is-hoverable">
        <thead>
            <tr>
                <th>Requested On</th>
                <th>Ticket Name</th>
                <th>Request Type</th>
                <th>Approved</th>
                <th>Total</th>
            </tr>
        </thead>
        <tbody id="ticketsView">

        </tbody>
    </table>
`;

let allTickets = [];
function writeTypeSelect(type) {
    return `<option value="${type}">${type}</option>`;
}

getTickets();
function getTickets() {
    get('http://localhost:8080/P1/api/tickets', populateTickets)
}
function getTickets(clear) {
    if (clear) {
        document.querySelector("#typeSelect").value = '';
        document.querySelector("#ticketName").value = '';
        document.querySelector("#description").value = '';
        document.querySelector("#expenseName").value = '';
        document.querySelector("#price").value = '';
        document.querySelector("#expenseView").innerHTML = '';
    }
    get('http://localhost:8080/P1/api/tickets', populateTickets)
}

function populateTickets(tickets) {
    allTickets = tickets;
    let html = '';
    for(let t of tickets) {
        html += writeTicket(t);
    }
    document.querySelector("#ticketComponent").innerHTML = ticketComponent;
    let container = document.querySelector("#ticketsView");
    container.innerHTML = '';
    container.innerHTML = html;
}

function writeTicket(t) {
    let approved;
    let total = 0;
    if (t.approvalStatus == null) {
        approved = 'Pending';
    } else if (t.approvalStatus){
        approved = 'Approved';
    } else {
        approved = 'Denied';
    }
    console.log(t);
    for(e of t.expenses) {
        total += e.price;
    }
    return `
        <tr>
            <td>${new Date(t.requestDate).toLocaleString()}</td>
            <td>${t.ticketName}</td>
            <td>${t.reimbursementType}</td>
            <td>${approved}</td>
            <td>$${total.toFixed(2)}</td>
            <td><button class="button" onclick="viewTicket(${t.ticketId})">View</button></td>
        </tr>
    `;
}
function viewTicket(id) {
    t = allTickets.find(ticket => ticket.ticketId == id);
    let approved;
    let total = 0;
    if (t.approvalStatus == null) {
        approved = 'Pending';
    } else if (t.approvalStatus){
        approved = 'Approved';
    } else {
        approved = 'Denied';
    }
    // expenseView.innerHTML = '';
    let expenseHTML = '';
    for(e of t.expenses) {
        total += e.price;
        expenseHTML += writeExpense(e);
    }
    if(!expenseHTML) {
        expenseHTML = "<tr><td>None</td></tr>";
    }

    let modalHTML = `
        <div id="displayTicket" class="box view-ticket-box">
            <h2 class="title is-2">View Ticket</h2>
            <h3 id="viewTicketName" class="title is-3">${t.ticketName}</h3>
            <p><span class="subtitle">Type:</span>  <span id="viewType">${t.reimbursementType}</span></p>
            <p id="viewDescription">${t.description}</p>
            <p><span class="subtitle">Total:</span> <span id="viewTotal">$${total.toFixed(2)}</span></p>
            <p id="viewDate" class="subtitle">${new Date(t.requestDate).toLocaleString()}</p>
            <p id="viewApproved">${approved}</p>
            <div id="expenses">
                <table class="table is-fullwidth">
                    <thead>
                        <tr>
                            <th>Expense</th>
                            <th>Cost</th> 
                        </tr>
                    </thead>
                    <tbody id="viewExpenses">
                        ${expenseHTML}
                    </tbody>
                </table>
            </div>
        </div>
        `;

    document.querySelector("#modalContent").innerHTML = modalHTML;
    document.querySelector("#modalContainer").classList.add("is-active");
    document.querySelector("html").classList.add("is-clipped");
}

function closeModal() {
    document.querySelector("#modalContainer").classList.remove("is-active");
    document.querySelector("html").classList.remove("is-clipped");
}

let expenses = [];
let total = 0;
function addExpense() {
    let expenseName = document.querySelector("#expenseName");
    let expensePrice = document.querySelector("#price");
    let expense = {
        expense_name: expenseName.value,
        price: parseInt(expensePrice.value),
    }
    let expenseView = document.querySelector("#expenseView");
    if(expenses.length == 0) {
        expenseView.innerHTML = '';
    }
    expenseView.innerHTML += writeExpense(expense);
    expenses.push(expense);
    total += expense.price;
    document.querySelector("#ticketTotal").innerHTML = total.toFixed(2);
    expenseName.value = '';
    expensePrice.value = '';
}
function writeExpense(e) {
    return `
        <tr>
            <td>${e.expense_name}</td>
            <td>$${e.price.toFixed(2)}</td>
        </tr>
    `;
}
function submitNewTicket() {
    let type = document.querySelector("#typeSelect");
    let ticketName = document.querySelector("#ticketName");
    let description = document.querySelector("#description");

    let params = {
        reimbursementType: type.value,
        ticketName: ticketName.value,
        description: description.value,
        expenses
    };
    let valid = true;

    let tys = document.getElementsByClassName("type-invalid");
    let typeContainer = document.querySelector("#ticketNameContainer");
    if(params.reimbursementType == "") {
        document.querySelector("#type-message").innerHTML = "Reimbursement Type is Required";
        typeContainer.classList.add('is-danger');
        for(let e of tys) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else {
        typeContainer.classList.remove('is-danger');
        for(let e of tys) {
            e.classList.add('hidden');
        }
    }

    let tns = document.getElementsByClassName("t-name-invalid");
    if(params.ticketName == "") {
        document.querySelector("#t-name-message").innerHTML = "Ticket Name is Required";
        ticketName.classList.add('is-danger');
        for(let e of tns) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else {
        ticketName.classList.remove('is-danger');
        for(let e of tns) {
            e.classList.add('hidden');
        }
    }

    let dns = document.getElementsByClassName("description-invalid");
    if(params.description == "") {
        document.querySelector("#description-message").innerHTML = "Ticket Description is Required";
        description.classList.add('is-danger');
        for(let e of dns) {
            e.classList.remove('hidden');
        }
        valid = false;
    } else {
        description.classList.remove('is-danger');
        for(let e of dns) {
            e.classList.add('hidden');
        }
    }

    if(!valid) {
        console.log('nope!');
        return;
    }
    console.log('passed');
    console.log(params);
    post('http://localhost:8080/P1/api/tickets', params, getTickets);
}


function get(url, callback) {
    fetch(url)
    .then(response => response.json())
    .then(res => {
        callback(res);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
}
function post(url, params, promise) {
    let reqHeader = new Headers();
    reqHeader.append('Content-Type', 'text/json');

    let initObject = {
        method: 'POST', headers: reqHeader, body: JSON.stringify(params)
    };

    fetch(url, initObject)
        .then(res => {
            promise(true);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}