import React from "react";

const ScrollableTemplateForm = () => {
  return (
    <div className="w-full flex justify-center mt-14 items-center flex-col gap-8">
      <div>
        <h1 className="text-2xl">Scrollable Template Form</h1>
      </div>

      <div className="w-96">
        <label className="block text-sm font-medium leading-6 text-gray-900">
          Page URL
        </label>
        <div className="relative mt-2 rounded-md shadow-sm">
          <input
            type="text"
            name="price"
            id="price"
            className="block w-full rounded-md border-0 py-1.5 pl-7 pr-20 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            placeholder="Enter page url"
          />
        </div>
      </div>

      <div className="w-96">
        <label className="block text-sm font-medium leading-6 text-gray-900">
          Delay Between Scrolls
        </label>
        <div className="relative mt-2 rounded-md shadow-sm">
          <input
            type="text"
            name="price"
            id="price"
            className="block w-full rounded-md border-0 py-1.5 pl-7 pr-20 text-gray-900 ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
            placeholder="Enter delay in milliseconds"
          />
        </div>
      </div>

      <div className="w-96">
        <button className="border border-teal-400 float-right border-solid px-4 py-2 rounded-lg hover:bg-teal-600 hover:text-white">
          Submit
        </button>
      </div>
    </div>
  );
};

export default ScrollableTemplateForm;
