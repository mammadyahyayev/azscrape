import { BrowserRouter as Router, Route, Switch } from "react-router-dom";
import Home from "./Components/Home";
import Projects from "./Components/Projects";
import "./App.css";
import Navbar from "./Components/Navbar";

//TODO: Ask Question about NavBar: Navbar in only specific pages not in every page
function App() {
  return (
    <div className="app">
      <Router>
        <Navbar />
        <Switch>
          <Route exact path="/" component={Home} />
          <Route exact path="/projects" component={Projects} />
        </Switch>
      </Router>
    </div>
  );
}

export default App;
