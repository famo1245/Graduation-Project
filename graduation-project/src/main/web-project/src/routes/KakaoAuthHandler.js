import axios from 'axios';
import React, { useEffect, useState } from 'react';
import { Navigate, useSearchParams } from 'react-router-dom';
// import { useDispatch } from "react-redux";
// import { actionCreators as userAction } from "../redux/modules/user";
// import Spinner from "../elements/Spinner";

function KakaoAuthHandler() {
  const [error, setError] = useState(null);
  const [isMember, setIsMember] = useState(false);
  const [searchParams, setSearchParams] = useSearchParams();
  const [loading, setLoading] = useState(false);
  const [data, setData] = useState(null);
  const code = searchParams.get('code');
  const params = {
    code: code,
  };

  useEffect(() => {
    setLoading(true);
    axios
      .get('/api/auth/kakao/callback', { params })
      .then((response) => {
        setData(response.data);
        setIsMember(response.data.isMember);
        setLoading(false);
      })
      .catch((err) => setError(err));
  }, []);

  if (loading) return <div>Loading...</div>;
  if (!data) return null;

  if (isMember) {
    sessionStorage.setItem('userId', data.id);
    return <Navigate to="/" />;
  }

  return <Navigate to="/members/join" />;
  // return (
  //   <div>
  //     {/* <h1>is Member: {isMember}</h1> */}
  //     {/* <h1>{data[0].id}</h1>s */}
  //     <h2>{data.id}</h2>
  //     {/* <h2>{data.age_range}</h2> */}
  //   </div>
  // );
}

export default KakaoAuthHandler;
