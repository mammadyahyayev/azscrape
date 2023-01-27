// html elements
const hasParentCheck = document.querySelector("#has-parent");
const parentSelectionArea = document.querySelector("#parent-selection");
const addElementBtn = document.querySelector("#add-element-btn")
const removeElementBtn = document.querySelector(".remove-element-btn");
const generateReportBtn = document.querySelector("#generate-report-btn")
const htmlElementsSection = document.querySelector("#html-element-section");


let dataElementList = [];

hasParentCheck.addEventListener("click", (e) => {
    const isChecked = e.target.checked;

    if (isChecked) {
        parentSelectionArea.classList.remove("d-none");
        populateParentElementSelection();
    } else {
        parentSelectionArea.classList.add('d-none');
    }
});

function populateParentElementSelection() {
    const parentSelect = parentSelectionArea.lastElementChild;
    clearParentElementSelection();

    const notSetOption = document.createElement("option");
    notSetOption.textContent = "NOT SET";
    parentSelect.appendChild(notSetOption);

    const htmlElements = htmlElementsSection.children;
    if (htmlElements.length === 0) {
        return;
    }

    for (const element of htmlElements) {
        const parentOption = document.createElement("option");
        parentOption.textContent = element.firstChild.childNodes[0].nodeValue;
        parentOption.id = element.id;

        parentSelect.appendChild(parentOption);
    }
}

function clearParentElementSelection() {
    const childrenSelect = parentSelectionArea.lastElementChild;
    let first = childrenSelect.firstChild
    while (first) {
        first.remove();
        first = childrenSelect.firstChild;
    }
}

addElementBtn.addEventListener("click", () => {
    const elementName = document.querySelector("#data-element-name").value;
    const elementSelector = document.querySelector("#data-element-selector").value;

    const dataElement = new DataElement(elementName, elementSelector);

    const selectedParentOption = parentSelectionArea.querySelector("select").selectedOptions[0];
    if (hasParentCheck && selectedParentOption.id) {
        dataElement.setParent(selectedParentOption.id);
    }

    dataElementList.push(dataElement);

    const htmlElement = dataElement.constructElementAsHTML();
    htmlElementsSection.appendChild(htmlElement);
})

htmlElementsSection.addEventListener("click", (e) => {
    if (e.target.textContent === "Remove") {
        const parentElement = e.target.parentElement.parentElement;
        parentElement.remove();
    }
})

generateReportBtn.addEventListener("click", (e) => {
    console.log(e);
})
