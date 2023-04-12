import Tabs from "./Tabs";

const Project = () => {
  return (
    <div>
      <Tabs />
      <div className="px-4 md:px-8">
        <div className="max-w-screen-xl mx-auto px-2 py-6">
          <h1 className="text-3xl">Project Info</h1>
          <div className="mt-6">
            <div className="flex text-xl mt-2">
              <p className="mr-4 text-violet-500">Project Name:</p>
              <p>Titanic Clustering</p>
            </div>

            <div className="flex text-xl mt-2">
              <p className="mr-4 text-violet-500">Project Owner:</p>
              <p>Mammad Yahyayev</p>
            </div>

            <div className="flex text-xl mt-2">
              <p className="mr-4 text-violet-500">Creation Time:</p>
              <p>07.04.2023 13:34</p>
            </div>

            <div className="flex flex-col text-xl mt-2">
              <p className="mr-4 text-violet-500">Project Description:</p>
              <p>
                Lorem ipsum, dolor sit amet consectetur adipisicing elit. Itaque
                aliquid consectetur adipisci quae, dolore aliquam perferendis
                vel asperiores, amet nisi tempore fugit voluptatem praesentium
                odio. Ab, ipsa provident. Molestias, esse.
              </p>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};

export default Project;
