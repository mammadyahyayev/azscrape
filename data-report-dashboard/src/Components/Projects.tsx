import Navbar from "./Navbar";
import Project from "./Project";

const Projects = () => {
  return (
    <div className="m-4 flex flex-wrap items-center justify-center">
      <Project
        name="Titanic Clustering"
        description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestias
          veritatis commodi, provident repellat praesentium tempore neque porro"
        // creationTime={new Date()}
      />
      <Project
        name="Titanic Clustering 2"
        description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestias
        veritatis commodi, provident repellat praesentium tempore neque porro"
        // creationTime={new Date("22.04.2022")}
      />

      <Project
        name="Titanic Clustering 3"
        description="Lorem ipsum dolor sit amet consectetur adipisicing elit. Molestias
        veritatis commodi, provident repellat praesentium tempore neque porro"
        // creationTime={new Date("22.04.2022")}
      />
    </div>
  );
};

export default Projects;
