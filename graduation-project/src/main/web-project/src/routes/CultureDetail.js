import React, { useEffect, useState } from "react";
import { useParams } from "react-router-dom";
import styles from "./Home.module.css";
import axios from "axios";


function CultureDetail(props) {
  const { cultureId } = useParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);

  useEffect(() => {
    setLoading(true);
    axios.get(`/api/cultures/detail/${cultureId}`)
      .then (response => {
        setData(response.data);
        setLoading(false);
      })
      .catch (err => setError(err));
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error...</div>;
  if (!data) return null;

  return (
    <div className={styles.home}>
      <div>
      <table class="table">
            <tr>
                <td>
                    <img src={data.main_img} alt="대표이미지"/>
                </td>
            </tr>
            <tr>
                <td>{data.title}</td>
            </tr>
            <tr>
                <td>{data.codeName}</td>
            </tr>
            <tr>
                <td>{data.date}</td>
            </tr>
            <tr>
                <td>{data.guname}</td>
            </tr>
            <tr>
                <td><a href={data.org_link} target="_blank">상세 정보 보기</a></td>
            </tr>
        </table>
      </div>
    </div>
  );
}

export default CultureDetail;