let modalContainer = document.querySelector("#modalContainer");
let modalContent = document.querySelector("#modalContent");
function openModal(html) {
    modalContent.innerHTML = html;
    modalContainer.classList.add("is-active");
    document.querySelector("html").classList.add("is-clipped");
}
function closeModal() {
    modalContainer.classList.remove("is-active");
    document.querySelector("html").classList.remove("is-clipped");
}

let url = 'http://localhost:8080/P1/api/reimbursement-types';
fetch(url)
    .then(response => response.json())
    .then(res => {
        populateTypes(res);
    })
    .catch((error) => {
        console.error('Error:', error);
    });
let types = [];
function populateTypes(res) {
    types = res
}
function writeTypeSelect(type) {
    return `<option value="${type}">${type}</option>`;
}

function addTicket() {
    let options = '';
    for(let type of types) {
        options += writeTypeSelect(type);
    }
    let ticketForm = `
    <div class="box">
        <h3 class="title is-3">New Ticket</h3>
        <form id="ticketForm">
            
            <div class="field">
                <div class="control">
                    <div id="ticketNameContainer" class="select is-info">
                        <select id="typeSelect" required>
                            <option value="" disabled selected>Select a Type</option>
                            ${options}
                        </select>
                    </div>
                </div>
                <p id="type-message" class="help is-danger type-invalid hidden">Reimbursement Type is Required.</p>
            </div> 


            <div class="field">
                <label class="label">Ticket Name</label>
                <div class="control has-icons-left has-icons-right">
                    <input id="ticketName" class="input" type="text" placeholder="Ticket Name" name="ticketName" required>
                    <span class="icon is-small is-left">
                        <i class="fas fa-user"></i>
                    </span>
                    <span class="icon is-small is-right t-name-invalid hidden">
                        <i class="fas fa-exclamation-triangle"></i>
                    </span>
                </div>
                <p id="t-name-message" class="help is-danger t-name-invalid hidden">Ticket name is Required.</p>
            </div>

            <div class="field">
                <label class="label">Description</label>
                <div class="control has-icons-left has-icons-right">
                    <textarea id="description" class="textarea is-hovered" placeholder="Ticket Description" required></textarea>
                    <span class="icon is-small is-left">
                        <i class="fas fa-user"></i>
                    </span>
                    <span class="icon is-small is-right description-invalid hidden">
                        <i class="fas fa-exclamation-triangle"></i>
                    </span>
                </div>
                <p id="description-message" class="help is-danger description-invalid hidden">Description is Required.</p>
            </div>
            
            <table class="table is-fullwidth">
                <thead>
                    <tr>
                        <th>Expense</th>
                        <th>Cost</th>
                    </tr>
                </thead>
                <tbody id="expenseView">
                    <tr><td>None</td></tr>
                </tbody>
            </table>

            <label class="label">Total - $<span id="ticketTotal">0</span></label>
            <div class="addExpense">
                <input id="expenseName" class="input" type="text" placeholder="Expense" name="expenseName" required>
                $<input id="price" class="input" type="text" placeholder="Cost" name="price" onkeypress="numbersOnly(event)" required>
                <button type="button" class="button" onclick="addExpense()">Add</button>
            </div>


            <div class="buttons">
                <button type="button" class="button is-info" onclick="submitNewTicket()">Submit Ticket</button>
                <button type="button" class="button is-danger" onclick="closeModal()">Cancel</button>
            </div>
        </form>
    </div>
`;
    openModal(ticketForm);
}

function numbersOnly(evt) {
    var charCode = (evt.which) ? evt.which : evt.keyCode;
      if (charCode == 46) {
        //Check if the text already contains the . character
        if (evt.target.value.indexOf('.') === -1) {
          return true;
        } else {
            evt.preventDefault();
            
          return false;
        }
      } else {
        if (charCode > 31 &&
          (charCode < 48 || charCode > 57)){
            evt.preventDefault();
            return false;

          }
      }
      return true;
}

