class DataElement {
    id;
    parentId;

    constructor(name, selector) {
        this.name = name;
        this.selector = selector;
        this.parentId = null;
    }

    constructElementAsHTML() {
        const elementBlock = document.createElement("div");
        elementBlock.className = "w-100 fs-4 mt-3 bg-light border border-2 p-3";

        this.id = Math.ceil(Math.random() * 10000 + 1);
        elementBlock.id = `${this.id}`;

        const idValue = document.createElement("p");
        idValue.style.visibility = "hidden";
        idValue.style.height = '0';
        idValue.style.margin = '0';
        idValue.textContent = this.id;

        const text = document.createElement("p");
        text.className = "m-0";
        text.textContent = this.toString();

        const removeBtn = document.createElement("span");
        removeBtn.className = "float-end text-danger remove-element-btn";
        removeBtn.textContent = "Remove";

        text.appendChild(removeBtn);

        elementBlock.appendChild(text);
        elementBlock.appendChild(idValue);

        return elementBlock;
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