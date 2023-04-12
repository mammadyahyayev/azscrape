import {
  FolderIcon,
  DocumentIcon,
  ArrowUpIcon,
} from "@heroicons/react/24/outline";
import FileExplorerRow from "./FileExplorerRow";

const Code = () => {
  return (
    <div className="max-w-screen-lg mx-auto p-6 mt-11 text-white" style={{ border: "2px solid #444c56" }}>
      <div className="flex gap-2 justify-end text-black">
        <button
          className="flex items-center bg-transparent text-white-700 font-semibold py-2 px-4 border-2 border-solid rounded-md text-xs"
          style={{ borderColor: "rgb(104 113 121)" }}
        >
          <p className="mr-2">Name</p>
          <ArrowUpIcon className="w-3" />
        </button>

        <button
          className="flex items-center bg-transparent text-white-700 font-semibold py-2 px-4 border-2 border-solid rounded-md text-xs"
          style={{ borderColor: "rgb(104 113 121)" }}
        >
          <p className="mr-2">Last Modified Time</p>
          <ArrowUpIcon className="w-3" />
        </button>
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
        <FileExplorerRow type="file" name="run.py" lastModifiedTime="now" />
        <FileExplorerRow type="file" name="run.py" lastModifiedTime="now" />
        <FileExplorerRow type="file" name="run.py" lastModifiedTime="now" />
        <FileExplorerRow type="file" name="run.py" lastModifiedTime="now" />
        <FileExplorerRow type="file" name="run.py" lastModifiedTime="now" />
      </div>
    </div>
  );
};

export default Code;
