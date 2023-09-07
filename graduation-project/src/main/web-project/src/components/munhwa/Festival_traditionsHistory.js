import React, { useState, useEffect, useCallback, useMemo } from "react";
import styles from "./CultureAndCulture.module.css";
import Searchbar from "../searchbar/Searchbar";
import SearchResultsList from "../searchbar/SearchResultsList";
import axios from "axios";
import { Link } from "react-router-dom";

function Festival_traditionsHistory() {
  const [inputD, setInputD] = useState([]);

  useEffect(() => {
    axios.get("http://localhost:3000/music.json").then((res) => {
      setInputD(res.data);
      console.log(res.data);
    });
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1>
              축제-전통/역사 <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.searchbarcontainer}>
            <Searchbar inputD={inputD} />
          </div>
        </div>
        {/* <div>
          <SearchResultsList inputD={inputD} data={dataa} />
        </div> */}
      </div>
    </div>
  );
}

export default Festival_traditionsHistory;
