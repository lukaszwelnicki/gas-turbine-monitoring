import React, { Component } from 'react';
import './App.css';
import GridLW from "./components/gridLW";

class App extends Component {
  render() {
    return (
      <div className="App">
        <header className="App-header">
          <h1 className="App-title">Gas Turbine Monitoring System</h1>
            <GridLW/>
        </header>
      </div>
    );
  }
}

export default App;
