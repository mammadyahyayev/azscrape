import { FolderIcon, DocumentIcon } from "@heroicons/react/24/outline";
import React from "react";

interface FileExplorerRowProps {
  type: string;
  name: string;
  lastModifiedTime: string;
}

const FileExplorerRow = (props: FileExplorerRowProps) => {
  return (
    <div
      className="flex bg-slate-800  hover:bg-slate-700"
      style={{ border: "1px solid #444c56" }}
    >
      <div className="w-10 p-2">
        {props.type == "folder" ? <FolderIcon /> : <DocumentIcon />}
      </div>
      <div className="p-2 w-5/6">{props.name}</div>
      <div className="p-2 w-32 text-end" style={{ color: "#768390" }}>
        {props.lastModifiedTime}
      </div>
    </div>
  );
};

export default FileExplorerRow;
