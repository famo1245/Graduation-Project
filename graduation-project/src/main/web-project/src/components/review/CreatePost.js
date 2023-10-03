import React, { useRef } from "react";
import { useState, useEffect } from "react";
import axios from "axios";
import { useLocation, useParams, Link, useNavigate, redirect } from "react-router-dom";
import styles from "./CreatePost.module.css";

// async function productFetch(id){
//     const response = await axios.get(`http://localhost:8080/products/${id}`);
//     return response.data
// }

function CreatePost() {
  const { id } = useParams();
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const { state } = useLocation();
  const [like1, setLike1] = useState(true);
  const [like2, setLike2] = useState(true);
  const [like3, setLike3] = useState(true);
  const [like4, setLike4] = useState(true);
  const [like5, setLike5] = useState(true);
  const [likeCount, setLikeCount] = useState(0);
  const navigate = useNavigate();
  const [imageURL1, setImageURL1] = useState(null);
  const [imageURL2, setImageURL2] = useState(null);

  const onClick1 = async () => {
    setLike1(!like1);
    if (like1 === true) {
      setLikeCount(likeCount + 1);
    } else {
      setLikeCount(likeCount - 1);
    }
  };
  const onClick2 = async () => {
    setLike2(!like2);
    if (like2 === true) {
      setLikeCount(likeCount + 1);
    } else {
      setLikeCount(likeCount - 1);
    }
  };
  const onClick3 = async () => {
    setLike3(!like3);
    if (like3 === true) {
      setLikeCount(likeCount + 1);
    } else {
      setLikeCount(likeCount - 1);
    }
  };
  const onClick4 = async () => {
    setLike4(!like4);
    if (like4 === true) {
      setLikeCount(likeCount + 1);
    } else {
      setLikeCount(likeCount - 1);
    }
  };
  const onClick5 = async () => {
    setLike5(!like5);
    if (like5 === true) {
      setLikeCount(likeCount + 1);
    } else {
      setLikeCount(likeCount - 1);
    }
  };

  const onChange1 = async (e) => {
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    return new Promise((resolve) => {
      reader.onload = () => {
        setImageURL1(reader.result || null);
        resolve();
      };
    });
  };
  const onChange2 = async (e) => {
    const file = e.target.files[0];
    const reader = new FileReader();
    reader.readAsDataURL(file);
    return new Promise((resolve) => {
      reader.onload = () => {
        setImageURL2(reader.result || null);
        resolve();
      };
    });
  };

  //   const imageInput1 = useRef();
  //   const imageInput2 = useRef();

  //   const onClickImageUpload1 = () => {
  //     imageInput1.current.click();
  //   };

  //   const onClickImageUpload2 = () => {
  //     imageInput2.current.click();
  //   };

  const onGoReviewBoard = (e) => {
    // const form = document.querySelector("#");
    // console.log(form);
    // const formData = new FormData(form);
    // const contents = {};
    // formData.forEach((value, key) => {
    //   contents[key] = value;
    // });
    // axios.post("", contents);
    e.preventDefault();
    const form = document.querySelector("#reviewForm");
    const formData = new FormData(form);
    const contents = {};
    formData.forEach((value, key) => {
      contents[key] = value;
    });
    const reviewContents = document.querySelector("#reviewContents");
    contents.userId = sessionStorage.getItem("userId");
    contents.reviewContents = reviewContents.value;
    axios.post(`api/review/reviewWrite`, contents);
    navigate(`/reviewBoard`, {
      replace: true,
    });
    // window.location.href = "http://localhost:3000/reviewBoard";
    // <redirect to="/reviewBoard" />;
  };

  console.log(imageURL1);
  console.log(imageURL2);

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
              게시글작성
              <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <form id="reviewForm" method="post">
              <div>
                문화제목 : <input type="hidden" name="cultureTitle" value={state.title} />[{state.codeName}]
                {state.title}
              </div>
              <div>
                제목 : <input type="text" name="reviewTitle" />
              </div>
              <div>
                <span>추천도 :</span>
                <img src={like1 ? "img/star (1).png" : "img/star.png"} onClick={onClick1} />
                <img src={like2 ? "img/star (1).png" : "img/star.png"} onClick={onClick2} />
                <img src={like3 ? "img/star (1).png" : "img/star.png"} onClick={onClick3} />
                <img src={like4 ? "img/star (1).png" : "img/star.png"} onClick={onClick4} />
                <img src={like5 ? "img/star (1).png" : "img/star.png"} onClick={onClick5} />
                <input className={styles.likeCount} type="text" name="reviewGrade" value={likeCount} />
              </div>
            </form>
          </div>
          <div className={styles.body_content}>
            <form method="post">
              <textarea
                name="reviewContents"
                id="reviewContents"
                cols="30"
                rows="30"
                placeholder="내용을 입력하세요."
              ></textarea>
            </form>
          </div>
          <div className={styles.lower_content}>
            <form name="" id="" action="" method="post">
              <div>사진파일 업로드</div>
              <div className={styles.lower_content_image}>
                <div>
                  {/* <button type="text" onClick={onClickImageUpload1}>
                    이미지 선택
                  </button> */}
                  <input
                    // ref={imageInput1}
                    type="file"
                    name=""
                    id=""
                    onChange={onChange1}
                    //   value={imageURL1}
                    encType="multipart/form-data"
                    multiple="file"
                    accept="image/*"
                  />
                </div>
                <div className={styles.image1}>
                  {/* <button type="text" onClick={onClickImageUpload2}>
                    이미지 선택
                  </button> */}
                  <input
                    // ref={imageInput2}
                    type="file"
                    name=""
                    id=""
                    onChange={onChange2}
                    //   value={imageURL2}
                    encType="multipart/form-data"
                    multiple="file"
                    accept="image/*"
                  />
                </div>
              </div>
              <div className={styles.lower_content_inner}>
                <div>
                  <img src={imageURL1} alt="" />
                </div>
                <div>
                  <img src={imageURL2} alt="" />
                </div>
              </div>
            </form>
          </div>
          <div className={styles.post_button}>
            <button className={styles.text_link} type="button" onClick={onGoReviewBoard}>
              작성완료
            </button>
          </div>
        </div>
      </div>
    </div>
  );
}

export default CreatePost;
