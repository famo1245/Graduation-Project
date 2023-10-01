import React, { useEffect } from "react";
import styles from "./ModifyMyPage.module.css";
import { Link, NavLink, useLocation } from "react-router-dom";
import { useState } from "react";
import axios from "axios";

function ModifyMyPage() {
  const location = useLocation();
  const myInfo = location.state?.myinfo;
  const [duplicate, setDuplicate] = useState(false);

  const onClick = () => {
    const form = document.querySelector("#profileForm");
    const formData = new FormData(form);
    const contents = {};
    const favoriteCategory = [];
    formData.forEach((value, key) => {
      if (key === "favoriteCategory") {
        favoriteCategory.push(value);
        return;
      }
      contents[key] = value;
    });
    contents["favoriteCategory"] = favoriteCategory.join();
    contents.userId = myInfo.id;
    axios
      .post("/api/members/info/update", contents)
      .then((window.location.href = "/MyPage"));
  };

  const checkDuplicated = () => {
    const form = document.querySelector("#profileForm");
    const formData = new FormData(form);
    const nickName = formData.get("nickName");
    axios
      .get(`/api/members/check-nickname?nickName=${nickName}`)
      .then((res) => {
        console.log(res.data.isDuplicated);
        if (res.data.isDuplicated) {
          setDuplicate(true);
        } else {
          setDuplicate(false);
        }
      });
  };

  return (
    <div className={styles.container}>
      <div className={styles.container_body}>
        <div className={styles.container_body_inner}>
          <div>
            <h1>
              마이페이지 <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.a}>
              <div className={styles.text}>닉네임 변경하기</div>
              <div className={styles.text}>관심지역 변경하기</div>
              <div className={styles.text}>관심문화 변경하기</div>
              <div id={styles.text}>
                {duplicate ? (
                  <div>수정완료</div>
                ) : (
                  <Link className={styles.text_link} to={`/MyPage`}>
                    <button type="button" form="profileForm" onClick={onClick}>
                      수정완료
                    </button>
                  </Link>
                )}
              </div>
            </div>
            <div className={styles.b}>
              <form name="profile" target="" encType="" id="profileForm">
                <div className={styles.text} id={styles.nickname}>
                  <input
                    className={
                      duplicate ? styles.duplicateName : styles.notDuplicateName
                    }
                    type="text"
                    name="nickName"
                    defaultValue={myInfo.nickName}
                  />
                  <div
                    className={styles.Duplicate_check}
                    onClick={checkDuplicated}
                  >
                    중복확인
                  </div>
                </div>
                <div
                  className={
                    duplicate ? styles.Duplicate_alert : styles.none_alert
                  }
                >
                  중복된 닉네임입니다.
                </div>
                <div className={styles.text} id={styles.interest_region}>
                  <select name="district">
                    <option
                      value="도봉구"
                      selected={myInfo.district === "도봉구" ? true : false}
                    >
                      도봉구
                    </option>
                    <option
                      value="노원구"
                      selected={myInfo.district === "노원구" ? true : false}
                    >
                      노원구
                    </option>
                    <option
                      value="강북구"
                      selected={myInfo.district === "강북구" ? true : false}
                    >
                      강북구
                    </option>
                    <option
                      value="성북구"
                      selected={myInfo.district === "성북구" ? true : false}
                    >
                      성북구
                    </option>
                    <option
                      value="중랑구"
                      selected={myInfo.district === "중랑구" ? true : false}
                    >
                      중랑구
                    </option>
                    <option
                      value="동대문구"
                      selected={myInfo.district === "동대문구" ? true : false}
                    >
                      동대문구
                    </option>
                    <option
                      value="광진구"
                      selected={myInfo.district === "광진구" ? true : false}
                    >
                      광진구
                    </option>
                    <option
                      value="성동구"
                      selected={myInfo.district === "성동구" ? true : false}
                    >
                      성동구
                    </option>
                    <option
                      value="중구"
                      selected={myInfo.district === "중구" ? true : false}
                    >
                      중구
                    </option>
                    <option
                      value="용산구"
                      selected={myInfo.district === "용산구" ? true : false}
                    >
                      용산구
                    </option>
                    <option
                      value="종로구"
                      selected={myInfo.district === "종로구" ? true : false}
                    >
                      종로구
                    </option>
                    <option
                      value="서대문구"
                      selected={myInfo.district === "서대문구" ? true : false}
                    >
                      서대문구
                    </option>
                    <option
                      value="은평구"
                      selected={myInfo.district === "은평구" ? true : false}
                    >
                      은평구
                    </option>
                    <option
                      value="마포구"
                      selected={myInfo.district === "마포구" ? true : false}
                    >
                      마포구
                    </option>
                    <option
                      value="강서구"
                      selected={myInfo.district === "강서구" ? true : false}
                    >
                      강서구
                    </option>
                    <option
                      value="양천구"
                      selected={myInfo.district === "양천구" ? true : false}
                    >
                      양천구
                    </option>
                    <option
                      value="구로구"
                      selected={myInfo.district === "구로구" ? true : false}
                    >
                      구로구
                    </option>
                    <option
                      value="영등포구"
                      selected={myInfo.district === "영등포구" ? true : false}
                    >
                      영등포구
                    </option>
                    <option
                      value="금천구"
                      selected={myInfo.district === "금천구" ? true : false}
                    >
                      금천구
                    </option>
                    <option
                      value="동작구"
                      selected={myInfo.district === "동작구" ? true : false}
                    >
                      동작구
                    </option>
                    <option
                      value="관악구"
                      selected={myInfo.district === "관악구" ? true : false}
                    >
                      관악구
                    </option>
                    <option
                      value="서초구"
                      selected={myInfo.district === "서초구" ? true : false}
                    >
                      서초구
                    </option>
                    <option
                      value="강남구"
                      selected={myInfo.district === "강남구" ? true : false}
                    >
                      강남구
                    </option>
                    <option
                      value="송파구"
                      selected={myInfo.district === "송파구" ? true : false}
                    >
                      송파구
                    </option>
                    <option
                      value="강동구"
                      selected={myInfo.district === "강동구" ? true : false}
                    >
                      강동구
                    </option>
                  </select>
                </div>
                <div className={styles.text} id={styles.interest_culture}>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="뮤지컬/오페라"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "뮤지컬/오페라"
                        )}
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>
                          뮤지컬/오페라
                        </span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="콘서트"
                        className={styles.checkbox_input}
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "콘서트"
                        )}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>콘서트</span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="독주/독창회"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "독주/독창회"
                        )}
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>
                          독주/독창회
                        </span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="문화교양/강좌"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "문화교양/강좌"
                        )}
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>
                          문화교양/강좌
                        </span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="교육/체험"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "교육/체험"
                        )}
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>교육/체험</span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="전시/미술"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "전시/미술"
                        )}
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>전시/미술</span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "클래식"
                        )}
                        value="클래식"
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>클래식</span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "국악"
                        )}
                        value="국악"
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>국악</span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="무용"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "무용"
                        )}
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>무용</span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="연극"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "연극"
                        )}
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>연극</span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="영화"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "영화"
                        )}
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>영화</span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="축제"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "축제"
                        )}
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>축제</span>
                      </span>
                    </label>
                  </div>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="기타"
                        defaultChecked={myInfo.favoriteCategory.includes(
                          "기타"
                        )}
                        className={styles.checkbox_input}
                      />
                      <span className={styles.checkbox_tile}>
                        <span className={styles.checkbox_icon}></span>
                        <span className={styles.checkbox_label}>기타</span>
                      </span>
                    </label>
                  </div>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default ModifyMyPage;
