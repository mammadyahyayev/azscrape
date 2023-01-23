class DataElement {
    constructor(name, selector) {
        this.name = name;
        this.selector = selector;
    }

    constructElement() {
        const elementBlock = document.createElement("div");
        elementBlock.className = "w-100 fs-4 mt-3 bg-light border border-2 p-3";

        const text = document.createElement("p");
        text.className = "m-0";
        text.textContent = this.toString();

        const removeBtn = document.createElement("span");
        removeBtn.className = "float-end text-danger text-decoration-underline";
        removeBtn.textContent = "Remove";

        text.appendChild(removeBtn);

        elementBlock.appendChild(text);

        return elementBlock;
    }

    toString() {
        return "{ " + this.name + ": [" + this.selector + "] }";
    }
}