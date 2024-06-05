// create a shop
async function shopCreate(event) {

    // prevent default form submission
    event.preventDefault();
    // get the form from the event
    const form = event.target;

    // create a FormData object from the form
    const formData = new FormData(form);
    // fetch to create the shop
    const response = await fetch("/api/shop", {
        method: "POST",
        body: formData
    });

    // parse the JSON from the response
    const returnedJSON = await response.json();
    console.log(returnedJSON);

    // reset the form
    combinedFormReset();
    // fetch all shops
    shopReadAll();
}

// read a shop by id
async function shopReadOne(id) {

    // fetch the shop with the given id
    const response = await fetch("/api/shop" + "/" + id, {
        method: "GET",
    });

    // parse the JSON from the response
    const returnedJSON = await response.json();
    console.log(returnedJSON);

    // populate the form with the returned shop
    const form = document.querySelector(".shopUpdate");
    form.querySelector("input[name=nome]").value = returnedJSON.nome;
    form.querySelector("input[name=indirizzo]").value = returnedJSON.indirizzo;
    form.querySelector("input[name=civico]").value = returnedJSON.civico;
    form.querySelector("input[name=id]").value = returnedJSON.id;

    // populate the form with the returned shop
    const formDelete = document.querySelector(".shopDelete");
    formDelete.querySelector("input[name=id]").value = returnedJSON.id;
}

// remove all child nodes from a parent
function removeAllChildNodes(parent) {
    while (parent.firstChild) {
        parent.removeChild(parent.firstChild);
    }
}

// fetch all shops
async function shopReadAll() {

    // fetch all shops
    const response = await fetch("/api/shop/all", {
        method: "GET",
    });

    // parse the JSON from the response
    const returnedJSON = await response.json();
    console.log(returnedJSON);

    // remove all child nodes from the shopList
    removeAllChildNodes(document.querySelector("#shopList"))
    // populate the shopList with the returned shops
    for (const shop of returnedJSON) {
        // create a list item for the shop
        const li = listItem(shop);
        // add the list item to the shopList
        document.querySelector("#shopList").appendChild(li);
    }
}

// create a list item for a shop
function listItem(shop) {

    // create a list item
    const li = document.createElement("li");

    // set the text of the list item
    li.innerHTML = shop.nome + " " + shop.indirizzo + " " + shop.civico;

    // add an event listener to the list item
    li.addEventListener("click", (event) => shopReadOne(shop.id));

    // return the list item
    return li;
}

// update a shop
async function shopUpdate(event) {

    // prevent default form submission
    event.preventDefault();
    // get the form from the event
    const form = event.target;

    // create a FormData object from the form
    const formData = new FormData(form);
    // get the id from the form
    const id = formData.get("id");
    // fetch to update the shop with the given id
    const response = await fetch("/api/shop" + "/" + id, {
        method: "PUT",
        body: formData
    });

    // parse the JSON from the response
    const returnedJSON = await response.json();
    console.log(returnedJSON);

    // reset the form
    combinedFormReset();

    // fetch all shops
    shopReadAll();
}

// delete a shop
async function shopDelete(event) {

    // prevent default form submission
    event.preventDefault();
    // get the form from the event
    const form = event.target;

    // create a FormData object from the form
    const formData = new FormData(form);
    // get the id from the form
    const id = formData.get("id");
    // fetch to delete the shop with the given id
    const response = await fetch("/api/shop" + "/" + id, {
        method: "DELETE",
    });

    // parse the JSON from the response
    const returnedJSON = await response.json();
    console.log(returnedJSON);

    // reset the form
    combinedFormReset();

    // fetch all shops
    shopReadAll();
}

// reset a form
function formReset(form) {
    // reset the form
    form.reset();
    // reset the hidden id input (only if it's there)
    if (form.querySelector("input[name=id]") != null)
        form.querySelector("input[name=id]").value = "";
}

// reset all forms
function combinedFormReset() {
    // reset all forms
    formReset(document.querySelector(".shopCreate"));
    formReset(document.querySelector(".shopUpdate"));
    formReset(document.querySelector(".shopDelete"));
}

// on page load
function onPageLoad() {

    // fetch all shops
    shopReadAll();

    // add event listeners

    // add event listeners for click
    document.querySelector(".shopReadAll").addEventListener("click", () => shopReadAll());

    // add event listeners for submit
    document.querySelector(".shopCreate").addEventListener("submit", (event) => shopCreate(event));
    document.querySelector(".shopUpdate").addEventListener("submit", (event) => shopUpdate(event));
    document.querySelector(".shopDelete").addEventListener("submit", (event) => shopDelete(event));

}

// on page load
addEventListener("load", onPageLoad);