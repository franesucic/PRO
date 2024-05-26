import React, {useState, useEffect} from "react";
import './styles/First.css'
import logo from './images/logo.png'
import { Link } from "react-router-dom";

function First() {

    const [categories, setCategories] = useState([])
    const [addsInCategory, setAddsInCategory] = useState([])
    const [selectedCategory, setSelectedCategory] = useState("Majice");
    const [menu, setMenu] = useState(true);

    useEffect(() => {
      fetch('http://localhost:8080/category/findAll').then(res => res.json()).then(data => {
        setCategories(data)
        setSelectedCategory("Majice")
    })
    },[])

    useEffect(() => {
      fetch(`http://localhost:8080/advert/findByCategory?categoryName=${selectedCategory}`).then(response => {
        if (!response.ok) {
          throw new Error("Adverts per category response was not ok.")
        }
        return response.json(); 
      }).then(data => {
         setAddsInCategory(data)
      }).catch(error => {
        console.error("There was a problem with the adverts in category fetch:", error);
      });
    },[selectedCategory]);

    const handleSelectorChange = (event) => {
        setSelectedCategory(event.target.value);
      };

    const handleUser0Click = (event) => {
      fetch('http://localhost:8080/login?username=admin').then(response => {
        if (!response.ok) {
          throw new Error("Login response was not ok.")
        }
        return response; 
      }).then(data => {
        localStorage.setItem('userType', "1"); 
      }).catch(error => {
        console.error("There was a problem with the login fetch:", error);
      });
        setMenu(false)
    }

    const handleUser1Click = (event) => {
      fetch('http://localhost:8080/login?username=korisnik1').then(response => {
        if (!response.ok) {
          throw new Error("Login response was not ok.")
        }
        return response;
      }).then(data => {
        localStorage.setItem('userType', "2"); 
      }).catch(error => {
        console.error("There was a problem with the login fetch:", error);
      });
        setMenu(false)
    }

    const handleUser2Click = (event) => {
      fetch('http://localhost:8080/login?username=korisnik2').then(response => {
        if (!response.ok) {
          throw new Error("Login response was not ok.")
        }
        return response; 
      }).then(data => {
        localStorage.setItem('userType', "3"); 
      }).catch(error => {
        console.error("There was a problem with the login fetch.", error);
      });
        setMenu(false)
    }

    const handleLogout = (event) => {
      localStorage.removeItem("userType")
      window.location.reload()
    }

    return (
        <>
          {menu && !localStorage.getItem("userType") ? (
            <div className="menu-div">
                <div>
                    <button onClick={handleUser0Click}>Korisnik 0</button>
                </div>
                <div>
                    <button onClick={handleUser1Click}>Korisnik 1</button>
                </div>
                <div>
                    <button onClick={handleUser2Click}>Korisnik 2</button>
                </div>
            </div>
          ) : (
            <>
              <div id="navbar">
                <div>
                  <Link className="link" to="/">KATEGORIJE</Link>
                </div>
                <div>
                  <img alt="logo" src={logo}></img>
                </div>
                <div>
                  <Link className="link" to="/second">OGLASI</Link>
                </div>
                <div><button onClick={handleLogout}>Odjava</button></div>
              </div>
              <div className="main-container">
                <div className="master-div">
                  <div className="input-div">
                    <div>
                      <b>Odaberi kategoriju:</b>
                      <select
                        id="category-select"
                        value={selectedCategory}
                        onChange={handleSelectorChange}
                      >
                        {categories.map((category, index) => (
                          <option key={index} value={category["name"]}>
                            {category["name"]}
                          </option>
                        ))}
                      </select>
                    </div>
                    
                  </div>
                </div>
                <div id="separator"></div>
                <div className="details-div">
                  <div>OGLASI IZ KATEGORIJE</div>
                  <br></br>
                  <div>
                    { addsInCategory.map((elem, index) => (
                        <div key={index} value={elem} className="add-div">
                            <div>
                              <div><b>{elem.title}</b></div>
                              <div>{elem.description}</div>
                              <div>{elem.price} €</div>
                            </div>
                            <div><img id="ad-img" src={`data:image/jpeg;base64,${elem.picture}`} alt="Fetched from backend" /></div>
                        </div>
                    )) }
                  </div>
                </div>
              </div>
            </>
          )}
        </>
      );
}

export default First;