import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Home from "./Components/Home";
import Projects from "./Components/Projects";
import "./App.css";
import Navbar from "./Components/Navbar";
import Project from "./Components/Project";
import Code from "./Components/Code";

//TODO: Ask Question about NavBar: Navbar in only specific pages not in every page
function App() {
  return (
    <div className="app">
      <Router>
        <Navbar />
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/projects" component={Projects} />
          <Route exact path="/projects/:id/code" component={Code} />
          <Route exact path="/projects/:id" component={Project} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
