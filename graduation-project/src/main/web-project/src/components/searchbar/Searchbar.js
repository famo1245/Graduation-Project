import React, { useState, useEffect } from "react";
import { FaSearch } from "react-icons/fa";
import styles from "./Searchbar.module.css";
import axios from "axios";
import { Link, useNavigate } from "react-router-dom";
import MunhwaRow from "./MunhwaRow";
import ReactPaginate from "react-paginate";

function Searchbar({ inputD }) {
  const [data, setData] = useState("");
  const [pageNumber, setPageNumber] = useState(0);
  // const [searchData, setSearchData] = useState([]);
  // const [pageCount, setPageCount] = useState();

  const culturePerPage = 18;
  let pagesVisited = pageNumber * culturePerPage;

  // useEffect(() => {
  //   setPageCount(Math.ceil(inputD.length / culturePerPage));
  // }, []);
  let pageCount = Math.ceil(inputD.length / culturePerPage);

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
        <MunhwaRow
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
          <input type="text" value={data} onChange={handleChange} onKeyDown={handleOnKeyPress} />
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

export default Searchbar;

{
  /* <tr key={key} className={styles.json_data}>
<td></td>
<td>
  
</td>
<td></td>
<td>{val.singer}</td>
</tr> */
}

// let i = 0;
// let dataArray = [];

// while (i < pageCount) {
//   if (i === pageCount - 1) {
//     for (let j = i * culturePerPage; j < cnt; j++) {
//       dataItems.push(dataItem[j]);
//     }
//   } else {
//     for (let j = i * culturePerPage; j < (i + 1) * culturePerPage; j++) {
//       dataItems.push(dataItem[j]);
//     }
//   }
//   dataArray.push(dataItems);
//   i++;
// }

// try {
//   let cnt = 0;
//   let dataItem = [];

//   for (let i = 0; i < inputD.length; i++) {
//     if (inputD[i].title.includes(data.toLowerCase())) {
//       dataItem.push(inputD[i]);
//       cnt = cnt + 1;
//     }
//   }
//   if (cnt === 0 || data === "") {
//     return setSearchData(inputD);
//   } else {
//     setPageCount(Math.ceil(dataItem.length / culturePerPage));
//     return setSearchData(dataItem);
//   }
// } catch (error) {
//   console.log(error);
// }
