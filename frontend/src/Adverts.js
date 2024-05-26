import React, {useEffect, useState} from "react";
import { useNavigate, Link } from "react-router-dom";
import logo from './images/logo.png'
import './styles/Second.css'

function Second() {

  let navigate = useNavigate();

    const [allAds, setAllAds] = useState([])
    const [copyOfAds, setCopyOfAds] = useState([])
    const [searchBarText, setSearchBarText] = useState("")

    useEffect(() => {
      fetch('http://localhost:8080/advert/findAll').then(res => res.json()).then(data => {
        setAllAds(data)
        setCopyOfAds(data)
      })
    },[])

    const handleDelete = (advertId) => {
      const confirmation = window.confirm("Jeste li sigurni da želite obrisati oglas?")
      if (confirmation) {
        fetch(`http://localhost:8080/advert/delete?id=${advertId}`, {
        method: "DELETE"
      }).then(res => {if (res.ok) {
        window.location.reload()
      } else console.log("Deleting went wrong.")})
      }
    }

    const handleEdit = (event) => {
      navigate("/third")
    }

    const handleSearchChange = (event) => {
      setSearchBarText(event.target.value)
      if (event.target.value === "") {
        setAllAds(copyOfAds)
      }
    }

    const searchAds = (event) => {
      const filtered = allAds.filter(item => (
        item.title.toLowerCase().includes(searchBarText.toLowerCase())
      ))
      console.log(filtered)
      setAllAds(filtered)
    }

    return (<>
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
        </div>
        <div className="all-ads-title">
            Svi oglasi:
        </div>
        <div className="search-div">
          <input id="searcher" placeholder="Pretraži proizvod..." onChange={handleSearchChange}></input><button onClick={searchAds}>Pretraži</button>
          <Link to='/fourth'><button id="new-ad">Novi oglas</button></Link>
        </div>
        <div className="all-ads">
            { allAds.map((elem, index) => (
                <div key={index} value={elem} className="add-div2">
                  <div id="left-div">
                    <div>
                      <div><b>{elem.title}</b></div>
                      <div>{elem.description}</div>
                      <div>Cijena: {elem.price} €</div>
                    </div>
                    <div><img id="ad-img" src={`data:image/jpeg;base64,${elem.picture}`} alt="Fetched from backend" /></div>
                  </div>
                  {localStorage.getItem("userType") == elem.userId ? <div className="buttons-div"><button id="del" onClick={() => handleDelete(elem.id)}>Obriši</button><Link to='/third' state={elem}><button id="edit" onClick={handleEdit}>Uredi</button></Link></div> : ""}
                </div>
            )) }
        </div>
    </>);
}

export default Second