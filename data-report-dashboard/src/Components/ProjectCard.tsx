import React from "react";
import { DocumentMagnifyingGlassIcon } from "@heroicons/react/24/outline";

interface ProjectCardProps {
  name: string;
  description: string;
//   creationTime: number;
}

const ProjectCard = (props: ProjectCardProps) => {
  return (
    <div className="w-80 border-2 border-solid border-indigo-500 rounded-md m-3 p-4">
      <div className="flex justify-center m-2">
        <DocumentMagnifyingGlassIcon width={80} />
      </div>

      <div className="flex justify-center flex-col text-center">
        <h1 className="text-2xl">{props.name}</h1>
        <p className="mt-2 mb-2 text-slate-950 text-base">
          {props.description}
        </p>
        {/* <p className="text-gray-400 text-sm">{props.creationTime}</p> */}
      </div>

      <div className="flex justify-center mt-4">
        <button className="explore-btn">Explore</button>
      </div>
    </div>
  );
};

export default ProjectCard;
