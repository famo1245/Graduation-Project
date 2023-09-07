import Header from "./Header/Header";
import Footer from "./Footer/Footer";
import styles from "./Layout.module.css";
import React from "react";
import { Switch, Redirect, Route } from "react-router-dom";

function Layout(props) {
  return (
    <div className={styles.layout}>
      <Header />
      <main className={styles.main}>{props.children}</main>
      <Footer />
    </div>
  );
}

export default Layout;
