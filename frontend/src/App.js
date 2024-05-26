import './styles/App.css';
import {BrowserRouter as Router, Routes, Route} from 'react-router-dom'
import First from './Home';
import Second from './Adverts';
import Third from './EditAd';
import Fourth from './NewAd';

function App() {
  return (
    <Router>
      <Routes>
        <Route path="/" element={<First/>}/>
        <Route path="/second" element={<Second/>}/>
        <Route path='/third' element={<Third/>}/> 
        <Route path='/fourth' element={<Fourth/>}/> 
      </Routes>
    </Router>
  );
}

export default App;
