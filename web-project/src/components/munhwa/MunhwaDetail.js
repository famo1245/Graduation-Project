import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { useLocation, useParams, Link, useNavigate } from "react-router-dom";
import styles from "./MunhwaDetail.module.css";
import { HiPlus } from "react-icons/hi";
import dummy from "../db/data.json";

function MunhwaDetail(props) {
  const { id } = useParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { state } = useLocation();
  const [like, setLike] = useState(false);
  const [clickCultureFriend, setClickCultureFriend] = useState(false);
  const [visible, setVisible] = useState(styles.join_none);
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();

  const onClickReviewCreatePost = () => {
    navigate(`/CreatePost`, {
      replace: false,
      state: data,
    });
  };

  const onClickReviewBoard = () => {
    navigate(`/ReviewBoard`, {
      replace: false,
      state: data,
    });
  };

  const onClickCultureFriendPost = () => {
    navigate(`/CultureFriendPost`, {
      replace: false,
      state: data,
    });
  };

  const onClickCultureFriend = () => {
    navigate(`/CultureFriend/`, {
      replace: false,
      state: data,
    });
  };

  const toggleLike = async () => {
    // const res = await axios.post(
    //   ""
    // ); /* [POST] 사용자가 좋아요를 누름 -> DB 갱신 */
    axios.get(`/api/cultures/add/like/${data.id}?userId=${sessionStorage.getItem("userId")}`);
    setLike(!like);
  };

  const toggleCultureFriend = async () => {
    setClickCultureFriend(!clickCultureFriend);
    if (clickCultureFriend === true) {
      setVisible(styles.join_none);
    } else {
      setVisible(styles.join);
    }
  };

  useEffect(() => {
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  useEffect(() => {
    setLoading(true);
    axios
      .get(`/api/cultures/detail/${id}`)
      .then((res) => {
        setData(res.data);
        if (sessionStorage.getItem("userId")) {
          axios.get(`/api/cultures/like/${res.data.id}?userId=${sessionStorage.getItem("userId")}`).then((res) => {
            setLike(res.data.liked);
            setLoading(false);
          });
        } else {
          setLoading(false);
        }
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
              {data.codeName}
              <hr style={{ border: 0 }} />
            </h1>
            <h1 className={styles.title2}>
              {data.title} <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <img src={data.main_img} className={styles.poster} alt="문화 포스터 이미지" />
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
                <div>연령 : {data.use_trgt === null ? "누구나" : data.use_trgt}</div>
              </div>
              <div className={styles.info}>
                <div>가격 : {data.isFree}</div>
              </div>
              <div className={styles.info_active}>
                <div className={styles.link_site}>
                  <a href={data.org_link} target="_blank">
                    보러가기
                  </a>
                </div>
                {isLogin ? (
                  <div className={styles.createPost} onClick={onClickReviewCreatePost}>
                    <img className={styles.zzim} src={`/img/copy-writing.png`} alt="리뷰작성" />
                    <span>리뷰작성하기</span>
                  </div>
                ) : (
                  <div></div>
                )}
                {isLogin ? (
                  <div className={styles.zzim1}>
                    <img
                      className={styles.zzim}
                      src={like ? `/img/heart (2).png` : `/img/heart2v2.png`}
                      alt="찜하기"
                      onClick={toggleLike}
                    />
                    <span>찜하기</span>
                  </div>
                ) : (
                  <div></div>
                )}
                {isLogin ? (
                  <div className={styles.culture_friend}>
                    {" "}
                    <img
                      className={styles.zzim}
                      src={clickCultureFriend ? `/img/check.png` : `/img/add.svg`}
                      alt="문화친구"
                      onClick={toggleCultureFriend}
                    />
                    <span>문화친구</span>
                  </div>
                ) : (
                  <div></div>
                )}
                <div className={visible}>
                  {" "}
                  <img className={styles.zzim} src={`/img/login.png`} alt="참여하기" onClick={onClickCultureFriend} />
                  <span>참여하기</span>
                </div>
                <div className={visible}>
                  {" "}
                  <img
                    className={styles.zzim}
                    src={`/img/hand-cursor.png`}
                    alt="구하기"
                    onClick={onClickCultureFriendPost}
                  />
                  <span>구하기</span>
                </div>
              </div>
            </div>
          </div>
          <div className={styles.lower_content}>
            <div className={styles.review_preview}>
              <div onClick={onClickReviewBoard}>리뷰게시판</div>
              <div onClick={onClickReviewBoard}>
                <HiPlus />
              </div>
            </div>
            {data.reviews.map((review) => {
              return (
                <div className={styles.box_review_preview_content}>
                  <div
                    className={styles.review_preview_title}
                    onClick={() => {
                      navigate(`/ReviewDetail/${review.id}`, {
                        replace: false,
                        state: review,
                      });
                    }}
                  >
                    {review.reviewTitle}
                  </div>
                  <div className={styles.review_preview_content}>{review.reviewContents}</div>
                </div>
              );
            })}
          </div>
        </div>
      </div>
    </div>
  );
}

export default MunhwaDetail;
