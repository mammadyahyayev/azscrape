class DataElementService {
    dataElementList;

    constructor() {
        this.dataElementList = [];
    }

    add(element) {
        if (!element) {
            console.error(`Given ${element} is falsy.`)
            return null;
        }

        const dataElement = new DataElement(element.elementName, element.elementSelector);
        dataElement.setParent(element.parentId)

        this.dataElementList.push(dataElement);

        return dataElement;
    }

    removeDataElement(dataElement) {
        // find id in the list and remove it.
    }

    elements() {
        this.dataElementList.forEach(element => console.log(element));
    }

    constructElementAsHTML(dataElement) {
        if (dataElement == null) {
            console.error("DataElement is null!");
            return;
        }

        const elementBlock = document.createElement("div");
        elementBlock.className = "w-100 fs-4 mt-3 bg-light border border-2 p-3";

        dataElement.id = Math.ceil(Math.random() * 10000 + 1);
        elementBlock.id = `${dataElement.id}`;

        const idValue = document.createElement("p");
        idValue.style.visibility = "hidden";
        idValue.style.height = '0';
        idValue.style.margin = '0';
        idValue.textContent = `${dataElement.id}`;

        const text = document.createElement("p");
        text.className = "m-0 data-element";
        text.textContent = dataElement.toString();

        const removeBtn = document.createElement("span");
        removeBtn.className = "float-end text-danger remove-element-btn";
        removeBtn.textContent = "Remove";

        text.appendChild(removeBtn);

        elementBlock.appendChild(text);
        elementBlock.appendChild(idValue);

        return elementBlock;
    }
}


class DataElement {
    id;
    parentId;
    children;

    constructor(name, selector) {
        this.name = name;
        this.selector = selector;
        this.parentId = null;
        this.children = [];
    }

    setParent(parentId) {
        if (!parentId) {
            return;
        }
        this.parentId = parentId;
    }

    toString() {
        return "{ " + this.id + " - " + this.name + ": [" + this.selector + "]" + ", parent: " + this.parentId + " }";
    }
}