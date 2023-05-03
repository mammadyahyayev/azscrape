import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Home from "./Components/Home";
import Projects from "./Components/Projects";
import "./App.css";
import Navbar from "./Components/Navbar";
import Project from "./Components/Project";
import Code from "./Components/Code";
import ScrapeTemplates from "./Components/ScrapeTemplates";
import ScrollableTemplateForm from "./Components/ScrollableTemplateForm";

//TODO: Ask Question about NavBar: Navbar in only specific pages not in every page
function App() {
  return (
    <div className="app">
      <Router>
        <Navbar />
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/scrape-templates" component={ScrapeTemplates} />
          <Route exact path="/scrollable-template-form" component={ScrollableTemplateForm} />
          <Route exact path="/projects" component={Projects} />
          <Route exact path="/projects/:projectName/code" component={Code} />
          <Route exact path="/projects/:projectName" component={Project} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
