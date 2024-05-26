import React, { useState, useEffect } from "react";
import './styles/Third.css';
import logoPath from './images/logo.jpg';
import { useNavigate } from "react-router-dom";

function Third() {
    const navigate = useNavigate();

    const [categories, setCategories] = useState([]);
    const [selectedCategory, setSelectedCategory] = useState();
    const [title, setTitle] = useState("");
    const [desc, setDesc] = useState("");
    const [price, setPrice] = useState("");
    const [image64, setImage64] = useState();
    const [titleMsg, setTitleMsg] = useState();
    const [descMsg, setDescMsg] = useState();
    const [priceMsg, setPriceMsg] = useState();

    useEffect(() => {
        fetch('http://localhost:8080/category/findAll')
            .then(res => res.json())
            .then(data => setCategories(data));
    }, []);

    const handleSelectorChange = (event) => {
        setSelectedCategory(event.target.value);
    };

    const handleFileChange = (event) => {
        if (event.target.files[0]) {
            const reader = new FileReader();
            reader.onloadend = () => {
                setImage64(reader.result);
            };
            reader.readAsDataURL(event.target.files[0]);
        }
    };

    const handleSubmitForm = (event) => {
        event.preventDefault();

        let wrong = false;

        if (!title) {
            setTitleMsg("Naslov je obavezan");
            wrong = true;
        } else if (title.length < 3) {
            setTitleMsg("Naslov mora imati bar 3 znaka.");
            wrong = true;
        }

        if (!desc) {
            setDescMsg("Opis je obavezan.");
            wrong = true;
        } else if (desc.length < 10) {
            setDescMsg("Opis mora imati bar 10 znakova.");
            wrong = true;
        }

        if (isNaN(price)) {
            setPriceMsg("Cijena mora biti brojčana.");
            wrong = true;
        } else if (price < 0) {
            setPriceMsg("Cijena mora biti pozitivna.");
            wrong = true;
        }

        if (wrong) return;

        const sendForm = (image) => {
            let user = localStorage.getItem("userType") == 1 ? "admin" : localStorage.getItem("userType") == 2 ? "korisnik1" : "korisnik2";
            let obj = {
                title: title,
                description: desc,
                picture: image.substring(23),
                price: price,
                user: user,
                categoryName: selectedCategory
            };
            fetch('http://localhost:8080/advert/add', {
                method: "POST",
                headers: {
                    "Content-Type": "application/json"
                },
                mode: "cors",
                body: JSON.stringify(obj)
            }).then(res => {
                if (!res.ok) {
                    console.log("Adding advert went wrong.");
                    return;
                } else {
                    navigate("/second");
                }
            });
        };

        if (!image64) {
            fetch(logoPath)
                .then(response => response.blob())
                .then(blob => {
                    const reader = new FileReader();
                    reader.onloadend = () => {
                        sendForm(reader.result);
                    };
                    reader.readAsDataURL(blob);
                });
        } else {
            sendForm(image64);
        }
    };

    const handleTitleChange = (event) => {
        setTitle(event.target.value);
    };

    const handlePriceChange = (event) => {
        setPrice(event.target.value);
    };

    const handleDescChange = (event) => {
        setDesc(event.target.value);
    };

    return (
        <div className="main-form-div">
            <div id="forma">
                <span>Naslov oglasa: </span>
                <div>
                    <input onChange={handleTitleChange} />
                    {titleMsg && <div className="msg">{titleMsg}</div>}
                </div>
                <span>Opis:</span>
                <div>
                    <input onChange={handleDescChange} />
                    {descMsg && <div className="msg">{descMsg}</div>}
                </div>
                <span>Cijena:</span>
                <div>
                    <input onChange={handlePriceChange} />
                    {priceMsg && <div className="msg">{priceMsg}</div>}
                </div>
                <div id="sel-div3">
                    Kategorija:
                    <select id="category-select" value={selectedCategory} onChange={handleSelectorChange}>
                        {categories.map((category, index) => (
                            <option key={index} value={category.name}>
                                {category.name}
                            </option>
                        ))}
                    </select>
                </div>
                <div id="file-div">
                    <input id="file-input" type="file" accept="image/*" onChange={handleFileChange} />
                </div>
                <div id="submit-div">
                    <button onClick={handleSubmitForm}>Potvrdi</button>
                </div>
            </div>
        </div>
    );
}

export default Third;

