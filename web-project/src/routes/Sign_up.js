import { Link } from "react-router-dom";
import styles from "./Login.module.css";
import React from "react";
import axios from "axios";
import logo from "../imgs/kakao_login_medium_narrow.png";

function Sign_up() {
  const REST_API_KEY = "92fbf81b06d378a41d55ee603e5b6bd0";
  const REDIRECT_URI = "http://localhost:3000/auth/kakao/callback";
  // oauth 요청 url
  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  const handleLogin = () => {
    window.location.href = KAKAO_AUTH_URL;
  };

  // const code = new URL(window.location.href).searchParams.get("code")

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
            <div className={styles.a}></div>
            <div className={styles.line}></div>
            <div className={styles.b}>
              <div className={styles.body_text}>
                다른 아이디로
                <br />
                간편하게
                <br />
                회원가입하기
              </div>
              <div className={styles.login_group}>
                <img src={logo} onClick={handleLogin} />
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}

export default Sign_up;
