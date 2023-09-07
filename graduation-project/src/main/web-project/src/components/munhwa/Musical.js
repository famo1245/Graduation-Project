import React, { useState, useEffect, useCallback, useMemo } from "react";
import styles from "./Musical.module.css";
import Searchbar from "../searchbar/Searchbar";
import SearchResultsList from "../searchbar/SearchResultsList";
import axios from "axios";
import { Link } from "react-router-dom";

function Musical() {
  const [inputD, setInputD] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    axios
      .get("/api/home")
      .then((res) => {
        setInputD(res.data);
        setLoading(false);
        console.log(res.data);
      })
      .catch((err) => setError(err));
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error...</div>;
  if (!inputD) return null;

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1>
              뮤지컬/오페라 <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.searchbarcontainer}>
            <Searchbar inputD={inputD.recentCultures} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Musical;
