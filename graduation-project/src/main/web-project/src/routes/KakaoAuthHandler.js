import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { redirect, useSearchParams } from 'react-router-dom';
// import { useDispatch } from "react-redux";
// import { actionCreators as userAction } from "../redux/modules/user";
// import Spinner from "../elements/Spinner";

function KakaoAuthHandler(props) {
  const [error, setError] = useState(null);
  const [isMember, setIsMember] = useState(false);
  const [searchParams, setSearchParams] = useSearchParams();
  const [data, setData] = useState(null);
  const code = searchParams.get('code');
  const params = { code: code };

  useEffect(() => {
    axios
      .get(`/api/auth/kakao/callback`, { params })
      .then((response) => setData(response.data))
      .catch((err) => setError(err));
  });

  if (error) {
    return <div>Error...</div>;
  }

  //   if (isMember) {
  //     redirect('/');
  //     return;
  //   }

  return (
    <div>
      <h1>{data}</h1>
    </div>
  );
}

export default KakaoAuthHandler;
