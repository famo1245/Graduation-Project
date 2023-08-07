import { Link } from "react-router-dom";
import styles from "./Sign_up.module.css";

function Sign_up() {
  return (
    <div className={styles.nature}>
      <div className={styles.container}>
        <h1>회원가입</h1>
        <h3>
          회원가입을 지원하지 않습니다. 로그인 화면으로 가셔서 카카오 간편
          로그인을 이용하시기 바랍니다.
        </h3>
      </div>
    </div>
  );
}

export default Sign_up;
