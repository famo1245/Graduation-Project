import React from "react";
import styles from "./Input_signup.module.css";
import { Link, NavLink, useHistory, useNavigate } from "react-router-dom";
import { useState, useEffect } from "react";
import axios from "axios";

function Input_signup(props) {
  const initialData = localStorage.getItem("userData")
    ? JSON.parse(localStorage.getItem("userData"))
    : [];
  const [userData, setUserData] = useState(initialData);
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
    axios.post("/api/members/new", contents);
    localStorage.removeItem("userData");
    sessionStorage.setItem("userId", userData.id);
    window.location.href = "http://localhost:3000";
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
              회원가입 <hr style={{ border: 0 }} />
            </h1>
          </div>
          <div className={styles.body}>
            <div className={styles.a}>
              <div className={styles.text}>닉네임 |</div>
              <div className={styles.text}>관심지역 |</div>
              <div className={styles.text}>관심문화 |</div>
              <div id={styles.text}>
                <button
                  className={styles.text_link}
                  type="button"
                  form="profileForm"
                  onClick={onClick}
                >
                  회원가입 완료하기
                </button>
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
                    // defaultValue={myInfo.nickName}
                  />
                  <div
                    className={styles.Duplicate_check}
                    onClick={checkDuplicated}
                  >
                    중복확인
                  </div>
                </div>
                <div className={styles.text} id={styles.age}>
                  <input
                    type="hidden"
                    name="age_range"
                    value={userData.age_range}
                  />
                  <input type="hidden" name="email" value={userData.email} />
                  <input type="hidden" name="gender" value={userData.gender} />
                  <input type="hidden" name="id" value={userData.id} />
                </div>
                <div className={styles.text} id={styles.interest_region}>
                  <select name="district">
                    <option value="도봉구">도봉구</option>
                    <option value="노원구">노원구</option>
                    <option value="강북구">강북구</option>
                    <option value="성북구">성북구</option>
                    <option value="중랑구">중랑구</option>
                    <option value="동대문구">동대문구</option>
                    <option value="광진구">광진구</option>
                    <option value="성동구">성동구</option>
                    <option value="중구">중구</option>
                    <option value="용산구">용산구</option>
                    <option value="종로구">종로구</option>
                    <option value="서대문구">서대문구</option>
                    <option value="은평구">은평구</option>
                    <option value="마포구">마포구</option>
                    <option value="강서구">강서구</option>
                    <option value="양천구">양천구</option>
                    <option value="구로구">구로구</option>
                    <option value="영등포구">영등포구</option>
                    <option value="금천구">금천구</option>
                    <option value="동작구">동작구</option>
                    <option value="관악구">관악구</option>
                    <option value="서초구">서초구</option>
                    <option value="강남구">강남구</option>
                    <option value="송파구">송파구</option>
                    <option value="강동구">강동구</option>
                  </select>
                </div>
                <div className={styles.text} id={styles.interest_culture}>
                  <div className={styles.checkbox_container}>
                    <label className={styles.checkbox_wrapper}>
                      <input
                        type="checkbox"
                        name="favoriteCategory"
                        value="뮤지컬/오페라"
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

export default Input_signup;
