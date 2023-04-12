import { ArrowUpIcon, ArrowDownIcon } from "@heroicons/react/24/outline";
import FileExplorerRow from "./FileExplorerRow";
import Tabs from "./Tabs";
import { useState } from "react";

const Code = () => {
  const [sortByName, setSortByName] = useState(false);
  const [sortByModifiedDate, setSortByModifiedDate] = useState(false);

  return (
    <div>
      <Tabs />
      <div
        className="max-w-screen-lg mx-auto p-6 mt-11 text-white"
        style={{ border: "2px solid #444c56" }}
      >
        <div className="flex gap-2 justify-between items-center text-black">
          <div className="justify-start">
            <h1 className="text-2xl font-semibold hover:underline hover:cursor-pointer">
              Home Price Detection
            </h1>
          </div>

          <div className="flex gap-2 justify-end">
            <button
              className="flex items-center bg-transparent text-white-700 font-semibold py-2 px-4 border-2 border-solid rounded-md text-xs"
              style={{ borderColor: "rgb(104 113 121)" }}
              onClick={() => setSortByName(!sortByName)}
            >
              <p className="mr-2">Name</p>
              {sortByName ? <ArrowUpIcon className="w-3" /> : <ArrowDownIcon className="w-3"/>}
            </button>

            <button
              className="flex items-center bg-transparent text-white-700 font-semibold py-2 px-4 border-2 border-solid rounded-md text-xs"
              style={{ borderColor: "rgb(104 113 121)" }}
              onClick={() => setSortByModifiedDate(!sortByModifiedDate)}
            >
              <p className="mr-2">Last Modified Time</p>
              {sortByModifiedDate ? <ArrowUpIcon className="w-3" /> : <ArrowDownIcon className="w-3"/>}
            </button>
          </div>
        </div>
        <div className="mt-5 text-white">
          <FileExplorerRow
            type="folder"
            name="demo"
            lastModifiedTime="2 hours ago"
          />
          <FileExplorerRow
            type="folder"
            name="learning"
            lastModifiedTime="1 year ago"
          />
          <FileExplorerRow type="file" name="run1.py" lastModifiedTime="now" />
          <FileExplorerRow
            type="file"
            name="run2.py"
            lastModifiedTime="10 seconds ago"
          />
          <FileExplorerRow
            type="file"
            name="run3.py"
            lastModifiedTime="1 year ago"
          />
          <FileExplorerRow
            type="file"
            name="run4.py"
            lastModifiedTime="1 week ago"
          />
          <FileExplorerRow
            type="file"
            name="run5.py"
            lastModifiedTime="3 weeks ago"
          />
        </div>
      </div>
    </div>
  );
};

export default Code;
