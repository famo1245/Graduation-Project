import React, { useEffect, useState } from 'react';
import { useParams } from 'react-router-dom';
import styles from './CultureDetail.module.css';
import axios from 'axios';

function CultureDetail(props) {
  const { cultureId } = useParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    axios
      .get(`/api/cultures/detail/${cultureId}`)
      .then((response) => {
        setData(response.data);
        setLoading(false);
      })
      .catch((err) => setError(err));
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error...</div>;
  if (!data) return null;

  return (
    <div className={styles.container}>
      <div>
        <h2>{data.codeName} 상세</h2>
        <h1>{data.title}</h1>
        <hr />
      </div>

      <div className={styles.first}>
        <img src={data.main_img} alt="대표이미지" />
        <div className={styles.second}>
          <h1>
            [{data.codeName}] {data.title}
          </h1>
          <h3>날짜: {data.date}</h3>
          <h3>위치: {data.place}</h3>
          <h3>연령: {data.use_trgt}</h3>
          <h3>
            <a href={data.org_link} target="_blank">
              상세 정보 보기
            </a>
          </h3>
        </div>
      </div>
    </div>
  );
}

export default CultureDetail;
