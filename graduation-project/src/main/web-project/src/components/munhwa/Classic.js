import React, { useState, useEffect, useCallback, useMemo } from "react";
import styles from "./CultureAndCulture.module.css";
import Searchbar from "../searchbar/Searchbar";
import SearchResultsList from "../searchbar/SearchResultsList";
import axios from "axios";
import { Link } from "react-router-dom";

function Classic() {
  const [inputD, setInputD] = useState([]);

  useEffect(() => {
    axios
      .get(
        `/api/cultures/favorite?user_id=${
          sessionStorage.getItem("userId") === null ? -1 : sessionStorage.getItem("userId")
        }`
      )
      .then((res) => {
        setInputD(res.data.favorite);
      });
  }, []);

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1>
              관심문화 <hr style={{ border: 0 }} />
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

export default Classic;
