import React, { useState, useEffect } from "react";
import { FaSearch } from "react-icons/fa";
import styles from "./CultureFriendSearchbar.module.css";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import CultureFriendContent from "./CultureFriendContent";
import ReactPaginate from "react-paginate";

function CultureFriendSearchbar({ inputD, checkId, state }) {
  const [data, setData] = useState("");
  const [pageNumber, setPageNumber] = useState(0);
  // const [searchData, setSearchData] = useState([]);
  // const [pageCount, setPageCount] = useState();
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();

  console.log(state);

  const culturePerPage = 5;
  let pagesVisited = pageNumber * culturePerPage;
  let pageCount = Math.ceil(inputD.length / culturePerPage);

  useEffect(() => {
    if (sessionStorage.getItem("userId")) {
      setIsLogin(true);
    }
  }, []);

  // useEffect(() => {
  //   setPageCount(Math.ceil(inputD.length / culturePerPage));
  // }, []);

  const changePage = ({ selected }) => {
    setPageNumber(selected);
  };

  const handleChange = (e) => {
    setData(e.target.value);
  };

  const handleOnKeyPress = (e) => {
    if (e.key === "Enter") {
      pageCount = 1;
    }
  };

  const displayCultures = inputD
    .filter((val) => {
      if (data == "") {
        return val;
      } else if (val.title.toLowerCase().includes(data.toLowerCase())) {
        return val;
      }
    })
    .slice(pagesVisited, pagesVisited + culturePerPage)
    .map((val, key) => {
      return (
        <CultureFriendContent
          key={key}
          poster={val.main_img}
          title={val.title}
          date={val.date}
          guname={val.guname}
          id={val.id}
          org_link={val.org_link}
          place={val.place}
          codeName={val.codeName}
          player={val.player}
        />
      );
    });

  return (
    <div className={styles.container}>
      <div className={styles.wrapper}>
        <div className={styles.inputWrapper}>
          <div className={styles.searchLogo}>검색 |</div>
          <input
            type="text"
            value={data}
            onChange={handleChange}
            onKeyDown={handleOnKeyPress}
          />
          <FaSearch id={styles.searchIcon} />
        </div>
        <div className={styles.line}></div>
        <div className={styles.row}>
          {displayCultures}
          <ReactPaginate
            previousLabel={"Previous"}
            nextLabel={"Next"}
            pageCount={pageCount}
            onPageChange={changePage}
            containerClassName={styles.paginationBttns}
            previousLinkClassName={styles.previousBttn}
            nextLinkClassName={styles.nextBttn}
            disabledClassName={styles.paginationDisabled}
            activeClassName={styles.paginationActive}
          />
        </div>
      </div>
    </div>
  );
}

export default CultureFriendSearchbar;