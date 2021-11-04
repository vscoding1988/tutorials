import React from 'react';
import './App.css';
import UrlForm from "./components/UrlForm";
import UrlTable from "./components/UrlTable";
import Navbar from "./components/Navbar";

function App() {
  return (
          <div className="App">
            <header className="App-header">
              <h1>Url shortner</h1>
            </header>
            <Navbar/>
            <section className="content-wrapper">
              <UrlTable/>
            </section>
          </div>
  );
}

export default App;
