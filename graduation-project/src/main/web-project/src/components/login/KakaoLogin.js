import KakaoLogin from "react-kakao-login";

const SocialKakao = () => {
    const kakaoClientId = '	41bfbd2400bb4a4c19889bdcbe2af211';
    const kakaoOnSuccess = async (data) => {
        console.log(data);
        const idToken = data.response.access_token;
    };

    const kakaoOnFailure = (err) => {
        console.error(err);
    };

    return (
        <>
          <KakaoLogin
            token={kakaoClientId}
            onSuccess={kakaoOnSuccess}
            onFail={kakaoOnFailure}
            />
        </>
    )
}

export default SocialKakao;