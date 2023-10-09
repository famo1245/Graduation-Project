import React from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { useLocation, useParams, Link, useNavigate } from "react-router-dom";
import styles from "./ReviewDetail.module.css";
import ReviewDetailComment from "./ReviewDetailComment";

function ReviewDetail() {
  const { id } = useParams();
  const [userName, setUserName] = useState("");
  const [comment, setComment] = useState("");
  const [feedComments, setFeedComments] = useState([]); // 댓글리스트
  const [isValid, setIsValid] = useState(false); //댓글 게시 가능여부, 유효성 검사
  const [isLogin, setIsLogin] = useState(false);
  const [like, setLike] = useState(false);
  const location = useLocation();
  const reviewInfo = location.state;
  const navigate = useNavigate();
  const [star, setStar] = useState(0);
  const [state, setState] = useState({
    value: "",
    update: null,
    list: [],
  });

  const handleClick = (i) => (e) => {
    setState({
      ...state,
      value: e.target.innerHTML,
      update: i,
    });
  };

  const handleChange = (e) => {
    const { value } = { ...e.target };
    setState({
      ...state,
      value,
    });
  };

  const updateList = (list) => {
    setState({
      list: list,
    });
  };

  const updateKeyDown = (k) => (e) => {
    if (e.key !== "Enter") return;

    const newList = [...state.list];
    newList[k].content = state.value;

    const data = { content: state.value };
    axios.post(`/api/review/reviewComment/${newList[k].id}/edit`, data);
    setState({
      ...state,
      update: null,
    });

    updateList(newList);
  };

  const deleteList = (k) => {
    let comment;
    const newList = state.list.filter((v, i) => {
      if (i !== k) {
        return v;
      } else {
        comment = v;
      }
    });

    axios.get(`/api/review/reviewComment/${comment.id}/delete`);
    updateList(newList);
  };

  let renderList = state.list.map((v, k) => {
    return (
      <div key={k} className={styles.renderList_container}>
        <div className={styles.comment_row}>
          <div className={styles.comment_id}>{v.nickname}</div>
          <div className={styles.comment_date}>
            <small>{new Date(v.createdDate).toLocaleDateString()}</small>
          </div>
        </div>
        <div className={styles.comment_content}>
          <span>
            {state.update === k ? (
              sessionStorage.getItem("userId") == v.userId ? (
                <div className={styles.update_div}>
                  <input
                    type="text"
                    value={state.value}
                    onChange={handleChange}
                    className={styles.comment_update_input}
                    onKeyDown={updateKeyDown(k)}
                  />
                  <button
                    className={styles.comment_delete_btn}
                    onClick={() => {
                      deleteList(k);
                    }}
                  >
                    삭제
                  </button>
                </div>
              ) : (
                <div>
                  <span className={styles.comment_content_inner} onClick={handleClick(k)}>
                    {v.content}
                  </span>
                </div>
              )
            ) : (
              <div>
                <span className={styles.comment_content_inner} onClick={handleClick(k)}>
                  {v.content}
                </span>
              </div>
            )}
          </span>
        </div>
      </div>
    );
  });

  const addList = (content) => {
    const body = { content: content };
    axios
      .post(`/api/review/reviewDetail/${reviewInfo.id}?userId=${sessionStorage.getItem("userId")}`, body)
      .then((res) => {
        setState({
          list: [...state.list, res.data.comment],
        });
      });
  };

  const starCount = () => {
    let arr = [];
    for (let i = 0; i < star; i++) {
      arr.push(<img className={styles.star_fill} src={`/img/star.png`} />);
    }
    return arr;
  };

  const onClickCultureDetail = () => {
    navigate(`/munhwaRow/${reviewInfo.culture_id}`, {
      replace: false,
      state: reviewInfo,
    });
  };

  useEffect(() => {
    setState({
      list: [...reviewInfo.reviewComments],
    });
    setStar(parseInt(reviewInfo.reviewGrade));
    if (reviewInfo.jimMember.includes(parseInt(sessionStorage.getItem("userId")))) {
      setLike(true);
    }
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  const toggleLike = async () => {
    // const res = await axios.post(
    // ); /* [POST] 사용자가 좋아요를 누름 -> DB 갱신 */
    axios.get(`/api/review/jim/${reviewInfo.id}?userId=${sessionStorage.getItem("userId")}`);
    setLike(!like);
  };

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1 className={styles.title1}>
              리뷰게시판
              <hr style={{ border: 0 }} />
            </h1>
            <h1 className={styles.title2}>
              {reviewInfo.reviewTitle} <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <img src={reviewInfo.main_img} className={styles.poster} alt="문화 포스터 이미지" />
            <div className={styles.inner_content}>
              <div className={styles.info}>
                <div>작성자 : {reviewInfo.nickname}</div>
              </div>
              <div className={styles.info}>
                <div>작성 날짜 : {new Date(reviewInfo.reviewDateTime).toLocaleDateString()}</div>
              </div>
              <div className={styles.info}>
                <div className={styles.star_ratings_fill}>
                  <div>추천도</div> {starCount()}
                </div>
              </div>
              <div className={styles.info_active}>
                {isLogin ? (
                  <div className={styles.zzim1}>
                    <img
                      className={styles.zzim}
                      src={like ? `/img/heart (2).png` : `/img/heart.png`}
                      alt="찜하기"
                      onClick={toggleLike}
                    />
                    <span>좋아요</span>
                  </div>
                ) : (
                  <div></div>
                )}
                <div className={styles.link_site} onClick={onClickCultureDetail}>
                  문화상세
                </div>
              </div>
            </div>
          </div>
          <div className={styles.lower_content}>
            <div className={styles.lower_content_text}>{reviewInfo.reviewContents}</div>
            <div className={styles.lower_content_img}>
              {reviewInfo.image1Url === null ? "" : <img src={`${reviewInfo.image1Url}`}></img>}
              {reviewInfo.image2Url === null ? "" : <img src={`${reviewInfo.image2Url}`}></img>}
            </div>
          </div>
          <div className={styles.comment_container}>
            <div className={styles.comment_title}>댓글</div>
            {isLogin ? <ReviewDetailComment addList={addList} /> : <></>}
            {renderList}
          </div>
        </div>
      </div>
    </div>
  );
}

export default ReviewDetail;
