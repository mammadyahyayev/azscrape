import Navbar from "./Navbar";
import ProjectCard from "./ProjectCard";

const Projects = () => {
  return (
    <div className="m-4 flex flex-wrap items-center justify-center">
      <ProjectCard
        name="Titanic Clustering"
        description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestias
          veritatis commodi, provident repellat praesentium tempore neque porro"
        // creationTime={new Date()}
      />
      <ProjectCard
        name="Titanic Clustering 2"
        description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestias
        veritatis commodi, provident repellat praesentium tempore neque porro"
        // creationTime={new Date("22.04.2022")}
      />

      <ProjectCard
        name="Titanic Clustering 3"
        description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestias
        veritatis commodi, provident repellat praesentium tempore neque porro"
        // creationTime={new Date("22.04.2022")}
      />
    </div>
  );
};

export default Projects;
