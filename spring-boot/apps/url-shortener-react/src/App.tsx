import React from 'react';
import './styles/style.scss';
import {Header} from "./components/Header/Header";
import {Body} from "./components/Body/Body";
import {Footer} from "./components/Footer/Footer";

function App() {
  return (
          <div className="app">
            <div className="content">
              <Header/>
              <Body/>
            </div>

            <Footer/>
          </div>
  );
}

export default App;
