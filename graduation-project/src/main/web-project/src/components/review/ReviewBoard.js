import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { useLocation, useParams, Link, useNavigate } from "react-router-dom";
import styles from "./ReviewBoard.module.css";
import ReviewSearch from "./ReviewSearch";

function ReviewBoard(props) {
  const { id } = useParams();
  const { state } = useLocation();
  const [inputD, setInputD] = useState();
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    axios
      .get(`api/review/`)
      .then((response) => {
        setInputD(response.data);
        setLoading(false);
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
              리뷰게시판 <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.searchbarcontainer}>
            <ReviewSearch inputD={inputD.reviews} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReviewBoard;
