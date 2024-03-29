import { useState, useEffect } from "react";
import styles from "./CultureRequest.module.css";
import axios from "axios";
import { Link, useLocation, useParams } from "react-router-dom";
import CultureRequestSearch from "./CultureRequestSearch";

function CultureRequest() {
  let location = useLocation();
  const [inputD, setInputD] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { id } = useParams();
  const [checkId, setCheckId] = useState(false);

  useEffect(() => {
    if (id > 0) {
      setCheckId(true);
    }
  });

  useEffect(() => {
    setLoading(true);
    axios
      .get(`/api/culture-request/list`)
      .then((response) => {
        setInputD(response.data.requests);
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
              문화요청
              <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.searchbarcontainer}>
            <CultureRequestSearch inputD={inputD} checkId={checkId} state={location.state} />
          </div>
        </div>
      </div>
    </div>
  );
}

export default CultureRequest;
