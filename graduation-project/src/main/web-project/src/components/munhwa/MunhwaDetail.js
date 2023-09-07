import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { useLocation, useParams } from "react-router-dom";
import styles from "./MunhwaDetail.module.css";

function MunhwaDetail(props) {
  const { id } = useParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { state } = useLocation();

  useEffect(() => {
    setLoading(true);
    axios
      .get(`/api/cultures/detail/${id}`)
      .then((res) => {
        setData(res.data);
        setLoading(false);
      })
      .catch((err) => setError(err));
  }, []);

  if (loading) return <div>Loading...</div>;
  if (error) return <div>Error...</div>;
  if (!data) return null;

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1 className={styles.title1}>
              뮤지컬/오페라 상세
              <hr style={{ border: 0 }} />
            </h1>
            <h1 className={styles.title2}>
              {data.title} <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <img
              src={data.main_img}
              className={styles.poster}
              alt="문화 포스터 이미지"
            />
            <div className={styles.inner_content}>
              <div className={styles.inner_title}>
                [{data.codeName}] {data.title}
              </div>
              <div className={styles.info}>
                <div>날짜 : {data.date}</div>
              </div>
              <div className={styles.info}>
                <div>위치 : {data.place}</div>
              </div>
              <div className={styles.info}>
                <div>연령 : 전체관람가</div>
              </div>
              <div className={styles.info}>
                <div>평점 : 4.5</div>
              </div>
              <div className={styles.info_active}>
                <div className={styles.link_site}>
                  <a href={data.org_link} target="_blank">
                    보러가기
                  </a>
                </div>
                <div className={styles.zzim}>찜하기</div>
                <div className={styles.culture_friend}>문화친구</div>
                <div className={styles.join}>참여하기</div>
                <div className={styles.lets_together}>구하기</div>
              </div>
            </div>
          </div>
          <div className={styles.lower_content}>
            <div className={styles.review_preview}>
              <div>리뷰게시판</div>
              <div>+</div>
            </div>
            <div className={styles.review_preview_content}>
              <div>[뮤지컬/오페라]리뷰게시판제목</div>
              <div>리뷰게시글 내용내용내용~~~~~~~~~~~~~~~~~~~</div>
            </div>
            <div className={styles.review_preview_content}>
              <div>[뮤지컬/오페라]리뷰게시판제목</div>
              <div>리뷰게시글 내용내용내용~~~~~~~~~~~~~~~~~~~</div>
            </div>
            <div className={styles.review_preview_content}>
              <div>[뮤지컬/오페라]리뷰게시판제목</div>
              <div>리뷰게시글 내용내용내용~~~~~~~~~~~~~~~~~~~</div>
            </div>
            <div className={styles.review_preview_content}>
              <div>[뮤지컬/오페라]리뷰게시판제목</div>
              <div>리뷰게시글 내용내용내용~~~~~~~~~~~~~~~~~~~</div>
            </div>
            <div className={styles.review_preview_content}>
              <div>[뮤지컬/오페라]리뷰게시판제목</div>
              <div>리뷰게시글 내용내용내용~~~~~~~~~~~~~~~~~~~</div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default MunhwaDetail;
