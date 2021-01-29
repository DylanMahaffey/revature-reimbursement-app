/**
 * Initiate Data
 */

let allTickets = [];
let myTickets = [];

let myTicketMethod = myTicketsByDate;
let employeeTicketMethod = employeeTicketsByDate;

getMyTickets()
function getMyTickets(res = true) {
    get('http://localhost:8080/P1/api/tickets', myTicketMethod);
}
getEmployeeTickets(res = true);
function getEmployeeTickets() {
    get('http://localhost:8080/P1/api/manager-tickets', employeeTicketMethod);
}

/**
 * 
 * General functions
 * 
*/
function getTotal(ticket) {
    let total = 0;
    for (let e of ticket.expenses) {
        total += e.price;
    }
    return total;
}

/**
 * 
 * Ticket Interactions
 * 
 */

 changeTab(employeeTab);
function changeTab(id) {
    switch(id) {
        case "myTab":
            document.querySelector("#myTab").classList.add('is-active');
            document.querySelector("#employeeTab").classList.remove('is-active');
            document.querySelector("#myTable").classList.remove('hidden');
            document.querySelector("#employeeTable").classList.add('hidden');
            break;
        case "employeeTab":
            document.querySelector("#myTab").classList.remove('is-active');
            document.querySelector("#employeeTab").classList.add('is-active');
            document.querySelector("#myTable").classList.add('hidden');
            document.querySelector("#employeeTable").classList.remove('hidden');
            break;
    }
}

function viewTicket(id) {
    let t = allTickets.find(ticket => ticket.ticketId == id);

    let approved;
    let total = 0;
    if (t.approvalStatus == null) {
        approved = 'Pending';
    } else if (t.approvalStatus){
        approved = 'Approved';
    } else {
        approved = 'Denied';
    }

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
            <p><span class="subtitle">Requested by:</span>  <span id="viewType">${t.employee.firstName + " " + t.employee.lastName}</span></p>
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
            
            <div id="approvalButtons" class="buttons">
                <button class="button is-primary" onclick="approveTicket(${id})">Approve</button>
                <button class="button is-danger" onclick="denyTicket(${id})">Deny</button>
            </div>
        </div>
    `;

    document.querySelector("#modalContent").innerHTML = modalHTML;
    document.querySelector("#modalContainer").classList.add("is-active");
    document.querySelector("html").classList.add("is-clipped");
}

function viewMyTicket(id) {
    t = myTickets.find(ticket => ticket.ticketId == id);
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
    `

    document.querySelector("#modalContent").innerHTML = modalHTML;
    document.querySelector("#modalContainer").classList.add("is-active");
    document.querySelector("html").classList.add("is-clipped");
}

function closeModal() {
    document.querySelector("#modalContainer").classList.remove("is-active");
    document.querySelector("html").classList.remove("is-clipped");
}

function writeExpense(e) {
    return `
        <tr>
            <td>${e.expense_name}</td>
            <td>$${e.price.toFixed(2)}</td>
        </tr>
    `
}

function approveTicket(id) {
    let ticket = allTickets.find(t => t.ticketId == id);
    ticket.approvalStatus = true;
    put('http://localhost:8080/P1/api/manager-tickets',ticket,employeeTicketMethod);
}
function denyTicket(id) {
    let ticket = allTickets.find(t => t.ticketId == id);
    ticket.approvalStatus = false;
    put('http://localhost:8080/P1/api/manager-tickets',ticket,employeeTicketMethod);
}

/**
 * 
 * Request Ticket
 * 
 */

 let expenses = [];
let total = 0;
function addExpense() {
    let expenseName = document.querySelector("#expenseName");
    let expensePrice = document.querySelector("#price");
    let expense = {
        expense_name: expenseName.value,
        price: parseFloat(expensePrice.value).toFixed(2),
    }
    let expenseView = document.querySelector("#expenseView");
    
    if(expenses.length == 0) {
        expenseView.innerHTML = '';
    }
    expenseView.innerHTML += writeExpense(expense);
    expenses.push(expense);
    total += expense.price;
    document.querySelector("#ticketTotal").innerHTML = total;
    expenseName.value = '';
    expensePrice.value = '';
}
function writeExpense(e) {
    return `
        <tr>
            <td>${e.expense_name}</td>
            <td>$${e.price}</td>
        </tr>
    `
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

    post('http://localhost:8080/P1/api/tickets', params, getMyTickets);
    clear();
}

function clear() {
    document.querySelector("#typeSelect").value = '';
    document.querySelector("#ticketName").value = '';
    document.querySelector("#description").value = '';
    document.querySelector("#expenseName").value = '';
    document.querySelector("#price").value = '';
    document.querySelector("#expenseView").innerHTML = '';
}

/**
 * 
 * Sort Tickets Functions
 * 
 */

 function sort(sortBy, e) {
     console.log(e);
    switch(sortBy) {
        case "approval":
            if(e) {
                employeeTicketMethod = employeeTicketsByApproval;
                employeeTicketMethod(allTickets);
            } else {
                myTicketMethod = myTicketsByApproval;
                myTicketMethod(myTickets);
            }
            break;
        case "date":
            if(e) {
                employeeTicketMethod = employeeTicketsByDate;
                employeeTicketMethod(allTickets);
            } else {
                myTicketMethod = myTicketsByDate;
                myTicketMethod(myTickets);
            }
            break;
        case "price":
            if(e) {
                employeeTicketMethod = employeeTicketsByPrice;
                employeeTicketMethod(allTickets);
            } else {
                myTicketMethod = myTicketsByApproval;
                myTicketMethod(myTickets);
            }
            break;
    }
 }

function employeeTicketsByApproval(tickets) {
    if( !Array.isArray(tickets) ) {
        tickets = allTickets;
    }
    tickets = tickets.sort((a, b) => (a.approvalStatus > b.approvalStatus) ? 1 : -1);
    populateEmployeeTickets(tickets);
    closeModal();
}
function myTicketsByApproval(tickets) {
    if( !Array.isArray(tickets) ) {
        tickets = myTickets;
    }
    console.log('Get my tickets');
    tickets = tickets.sort((a, b) => (a.approvalStatus > b.approvalStatus) ? 1 : -1);
    populateTickets(tickets);
    closeModal();
}
function employeeTicketsByDate(tickets) {
    if( !Array.isArray(tickets) ) {
        tickets = allTickets;
    }
    tickets = tickets.sort((a, b) => (a.requestDate < b.requestDate) ? 1 : -1);
    populateEmployeeTickets(tickets);
    closeModal();
}
function myTicketsByDate(tickets) {
    if( !Array.isArray(tickets) ) {
        tickets = myTickets;
    }
    tickets = tickets.sort((a, b) => (a.requestDate < b.requestDate) ? 1 : -1);
    populateTickets(tickets);
    closeModal();
}
function employeeTicketsByPrice(tickets) {
    if( !Array.isArray(tickets) ) {
        tickets = allTickets;
    }
    tickets = tickets.sort((a, b) => (getTotal(a) < getTotal(b)) ? 1 : -1);
    populateEmployeeTickets(tickets);
    closeModal();
}
function myTicketsByPrice(tickets) {
    if( !Array.isArray(tickets) ) {
        tickets = myTickets;
    }
    tickets = tickets.sort((a, b) => (getTotal(a) < getTotal(b)) ? 1 : -1);
    populateTickets(tickets);
    closeModal();
}

/**
 * 
 * Writing tickets to the DOM
 * 
 */


function populateTickets(tickets) {
    myTickets = tickets;
    let container = document.querySelector("#myTicketsView");
    container.innerHTML = '';
    let html = '';
    for(let t of tickets) {
        html += writeTicket(t);
    }
    container.innerHTML = html;
}

function populateEmployeeTickets(tickets) {
    allTickets = tickets;
    let container = document.querySelector("#ticketsView");
    container.innerHTML = '';
    let html = '';
    for(let t of tickets) {
        html += writeEmployeeTicket(t);
    }
    container.innerHTML = html;
}
function writeEmployeeTicket(t) {
    return writeTicket(t, `<td>${t.employee.firstName} ${t.employee.lastName}</td>`);
}
function writeTicket(t, col = '') {
    let approved;
    let total = 0;
    if (t.approvalStatus == null) {
        approved = 'Pending';
    } else if (t.approvalStatus){
        approved = 'Approved';
    } else {
        approved = 'Denied';
    }
    for(e of t.expenses) {
        total += e.price;
    }

    let viewButton = `<td><button class="button" onclick="viewTicket(${t.ticketId})">View</button></td>`;
    if (col == '') {
        viewButton = `<td><button class="button" onclick="viewMyTicket(${t.ticketId})">View</button></td>`;
    }

    return `
        <tr>
            <td>${new Date(t.requestDate).toLocaleString()}</td>
            ${col}
            <td>${t.ticketName}</td>
            <td>${t.reimbursementType}</td>
            <td>${approved}</td>
            <td>$${total.toFixed(2)}</td>
            ${viewButton}
        </tr>
    `;
}

/**
 * 
 * AJAX methods
 * 
 */

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
function put(url, params, promise) {
    let reqHeader = new Headers();
    reqHeader.append('Content-Type', 'text/json');

    let initObject = {
        method: 'PUT', headers: reqHeader, body: JSON.stringify(params)
    };

    fetch(url, initObject)
        .then(res => {
            promise(true);
        })
        .catch((error) => {
            console.error('Error:', error);
        });
}