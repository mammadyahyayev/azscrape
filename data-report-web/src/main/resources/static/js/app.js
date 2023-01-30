// html elements
const hasParentCheckEl = document.querySelector("#has-parent");
const parentSelectionArea = document.querySelector("#parent-selection");
const addElementBtn = document.querySelector("#add-element-btn")
const removeElementBtn = document.querySelector(".remove-element-btn");
const generateReportBtn = document.querySelector("#generate-report-btn")
const dataElementsSection = document.querySelector("#data-elements-section");


const dataElementService = new DataElementService();

hasParentCheckEl.addEventListener("click", (e) => {
    const isChecked = e.target.checked;

    if (isChecked) {
        parentSelectionArea.classList.remove("d-none");
        populateParentElementSelection();
    } else {
        parentSelectionArea.classList.add('d-none');
    }
});

function populateParentElementSelection() {
    const parentSelectEl = parentSelectionArea.querySelector("select");
    clearParentElementSelection();

    const notSetOption = document.createElement("option");
    notSetOption.textContent = "NOT SET";
    parentSelectEl.appendChild(notSetOption);

    const dataElements = dataElementsSection.children;
    if (dataElements.length === 0) {
        return;
    }

    for (const element of dataElements) {
        const parentOption = document.createElement("option");
        parentOption.textContent = element.firstChild.childNodes[0].nodeValue;
        parentOption.id = element.id;

        parentSelectEl.appendChild(parentOption);
    }
}

function clearParentElementSelection() {
    const parentSelectEl = parentSelectionArea.querySelector("select");
    let first = parentSelectEl.firstChild
    while (first) {
        first.remove();
        first = parentSelectEl.firstChild;
    }
}

addElementBtn.addEventListener("click", () => {
    const elementName = document.querySelector("#data-element-name").value;
    const elementSelector = document.querySelector("#data-element-selector").value;

    const dataElement = {
        elementName,
        elementSelector
    };

    const selectedParentOption = parentSelectionArea.querySelector("select").selectedOptions[0];
    if (hasParentCheckEl && selectedParentOption.id) {
        dataElement.parentId = selectedParentOption.id;
    }

    const newDataElement = dataElementService.add(dataElement);
    const htmlElement = dataElementService.constructElementAsHTML(newDataElement);
    dataElementsSection.appendChild(htmlElement);
})

dataElementsSection.addEventListener("click", (e) => {
    if (e.target.textContent === "Remove") {
        const parentElement = e.target.parentElement.parentElement;
        parentElement.remove();
    }
})

generateReportBtn.addEventListener("click", () => {
    const title = document.querySelector("#title").value;
    const description = document.querySelector("#description").value;
    const fileType = document.querySelector("#fileType").selectedOptions[0].value;
    const fileExtension = document.querySelector("#fileExtension").selectedOptions[0].value;
    const url = document.querySelector("#url").value;

    const dataElement = dataElementService.elements().find(e => e.parentId === null);

    const configFile = new ConfigFile(title, description, fileType, fileExtension, url, dataElement);
    const json = configFile.load();
    fetch("/config/send", {
        method: "POST",
        headers: {"Content-Type": "application/json"},
        body: JSON.stringify(json)
    })
        .then(response => response.json())
        .then(data => console.log(data))
        .catch(error => console.error(error));
})
