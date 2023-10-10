import React, { useState, useEffect, useCallback, useMemo } from "react";
import styles from "./Musical.module.css";
import Searchbar from "../searchbar/Searchbar";
import axios from "axios";
import { Link, useLocation, useParams } from "react-router-dom";

//문화 카테고리별 추출
function Musical() {
  let location = useLocation();
  const [inputD, setInputD] = useState(null);
  const [category, setCategory] = useState("");
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { categoryName } = useParams();

  useEffect(() => {
    setLoading(true);
    axios
      .get(`/api/cultures/codename/${categoryName}`)
      .then((res) => {
        setInputD(res.data.category);
        setCategory(res.data.categoryName);
        setLoading(false);
      })
      .catch((err) => setError(err));
  }, [location]);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error...</div>;
  if (!inputD || !category) return null;

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1>
              {category} <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.searchbarcontainer}>
            <Searchbar inputD={inputD} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default Musical;
