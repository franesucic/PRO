import React, {useState, useEffect} from "react";
import './styles/Third.css'
import logo from './images/logo.png'
import { useLocation, useNavigate,  Link } from "react-router-dom";

function Third() {

    const location = useLocation();
    const navigate = useNavigate()

    const adId = location.state.id
    const adTitle = location.state.title
    const adDesc = location.state.description
    const adPrice = location.state.price
    const adCategoryId = location.state.categoryId
    const adPicture = location.state.picture

    const [categories, setCategories] = useState([])
    const [selectedCategory, setSelectedCategory] = useState();
    const [title, setTitle] = useState()
    const [desc, setDesc] = useState()
    const [price, setPrice] = useState()
    const [image64, setImage64] = useState();
    const [titleMsg, setTitleMsg] = useState()
    const [descMsg, setDescMsg] = useState()
    const [priceMsg, setPriceMsg] = useState()
    const [selectedPicture, setSelectedPicture] = useState(false)


    useEffect(() => {
      if (location.state) {
        setTitle(adTitle)
        setDesc(adDesc)
        setPrice(adPrice)
        if (adPicture) {
          setImage64(adPicture)
        }
    }
        fetch('http://localhost:8080/category/findAll').then(res => res.json()).then(data => {
          setCategories(data)
      })
      },[])

      useEffect(() => {
        for (let i = 0; i < categories.length; ++i) {
          if (categories[i].id === adCategoryId) {
            setSelectedCategory(categories[i].name)
          }
        }
      }, [categories, adCategoryId])

      const handleSelectorChange = (event) => {
        setSelectedCategory(event.target.value);
      };

      const handleFileChange = (event) => {
        setSelectedPicture(true)
        if (event.target.files[0]) {
            const reader = new FileReader();
            reader.onloadend = () => {
              setImage64(reader.result);
            };
            reader.readAsDataURL(event.target.files[0]);
          }
      }

      const handleSubmitForm = (event) => {
        let wrong = false
        if (!title) {
            setTitleMsg("Naslov je obavezan")
            wrong = true
        }
        else if (title.length < 3) {
            setTitleMsg("Naslov mora imati bar 3 znaka.")
            wrong = true
        }
        if (!desc) {
            setDescMsg("Opis je obavezan.")
            wrong = true
        }
        else if (desc.length < 10) {
            setDescMsg("Opis mora imati bar 10 znakova.")
            wrong = true
        }
        if (isNaN(price)) {
            setPriceMsg("Cijena mora biti brojčana.")
            wrong = true
        } else if (price < 0) {
            setPriceMsg("Cijena mora biti pozitivna.")
            wrong = true
        }
        if (wrong) return
        let user = 0
        if (localStorage.getItem("userType") == 1) {
          user = "admin"
        } else if (localStorage.getItem("userType") == 2) {
          user = "korisnik1"
        } else {
          user = "korisnik2"
        }
        let obj = {
          "title": title,
          "description": desc,
          "picture": !selectedPicture ? image64 : image64.substring(23),
          "price": price,
          "user": user,
          "categoryName": selectedCategory
        }
      fetch(`http://localhost:8080/advert/edit?id=${adId}`, {
          method: "PUT",
          headers: {
            "Content-Type": "application/json"
          },
          mode: "cors",
          body: JSON.stringify(obj)
        }).then(res => {
          if (!res.ok) {
            console.log("Updating advert went wrong.")
            return
          } else {
            navigate("/second")
          }
        }).then(data => {
        
      })
      }

      const handleTitleChange = (event) => {
        setTitle(event.target.value)
      }

      const handlePriceChange = (event) => {
        setPrice(event.target.value)
      }

      const handleDescChange = (event) => {
        setDesc(event.target.value)
      }

    return (<>
        <div className="main-form-div">
            <div id="forma">
                <span>Naslov oglasa: </span>
                <div>
                    <input onChange={handleTitleChange} defaultValue={adTitle}></input>
                    {titleMsg ? <div className="msg">{titleMsg}</div> : ""}
                </div>
                <span>Opis:</span>
                <div>
                    <input onChange={handleDescChange} defaultValue={adDesc}></input>
                    {titleMsg ? <div className="msg">{descMsg}</div> : ""}
                </div>
                <span>Cijena:</span>
                <div>
                    <input onChange={handlePriceChange} defaultValue={adPrice}></input>
                    {titleMsg ? <div className="msg">{priceMsg}</div> : ""}
                </div>
                <div id="sel-div3">Kategorija:
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
                <div id="file-div">
                    <input id="file-input" type="file" accept="image/*" onChange={handleFileChange}></input>
                </div>
                <div><img id="ad-img" src={`data:image/jpeg;base64,${adPicture}`} alt="Fetched from backend" /></div>
                <div id="submit-div">
                    <button onClick={handleSubmitForm}>Potvrdi</button>
                </div>
            </div>
        </div>
    </>);
}

export default Third;