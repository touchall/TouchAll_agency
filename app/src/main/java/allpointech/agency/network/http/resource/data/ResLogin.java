package allpointech.agency.network.http.resource.data;


import allpointech.agency.network.http.HttpInfo;
import allpointech.agency.network.http.MultiPartData;
import allpointech.agency.network.http.resource.ResBaseHttp;

public class ResLogin extends ResBaseHttp {
    /**
     * @Desc : 보내는키
     */
    public class KEY_REQ {
        public static final String mobile = "mobile";
        public static final String pw = "pw";
        public static final String type = "type";
        public static final String fcm = "fcm";
        public static final String device = "device";
    }

    /**
     * @Desc : 받는키
     */
    public class KEY_RES {

        public static final String message = "message"; // '회원코드 mb',

//        public class MEMBER {
//            public static final String mbcode = "mbcode"; // '회원코드 mb',
//            public static final String mbseq = "mbseq"; // '회원가입 SEQ 발생 Seq는 100000000 부터 시작함.',
//            public static final String mbphnodc = "mbphnodc"; // '휴대폰번호 중복상태',
//            public static final String mbphno = "mbphno"; // '휴대폰번호',
//            public static final String mbtype = "mbtype"; // '회원구분 00:어드민, 10:일반회원, 20:가맹점, 30:가맹점 종업원',
//            public static final String mbkname = "mbkname"; // '가입자성명',
//            public static final String mbpass = "mbpass"; // '회원 비밀번호',
//            public static final String mbbirthdt = "mbbirthdt"; // '가입자생년월일',
//            public static final String mbsex = "mbsex"; // '가입자성별',
//            public static final String mbemail1 = "mbemail1"; // '가입자이메일주소1',
//            public static final String mbemail2 = "mbemail2"; // '가입자이메일주소2',
//            public static final String mbpointtype = "mbpointtype"; // '포인트 적립유형 0:현금이체, 1:전자현금',
//            public static final String mbbankcd = "mbbankcd"; // '현금이체 계좌이체 은행코드',
//            public static final String mbbankno = "mbbankno";// '은행 계좌번호',
//            public static final String mbbankacname = "mbbankacname"; // '예금주성명',
//            public static final String mbjoindt = "mbjoindt"; // '회원가입일자',
//            public static final String mbagr_flag1 = "mbagr_flag1"; // '약관동의 Flag',
//            public static final String mbagr_flag2 = "mbagr_flag2"; // '개인정보 수집동의 Flag',
//            public static final String mbagr_flag3 = "mbagr_flag3"; // '위치정보 제공동의 Flag',
//            public static final String mbstatus = "mbstatus"; // '회원상태 ACTIVE:사용중, STANDBY:휴면, SECESSION:탈퇴',
//        }
    }

    // request
    public void setParameter(String... params) {
        setMultipartData(MultiPartData.FORM, KEY_REQ.mobile, params[0]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.pw, params[1]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.type, params[2]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.fcm, params[3]);
        setMultipartData(MultiPartData.FORM, KEY_REQ.device, params[4]);
    }

    @Override
    public String makeType() throws Exception {
        return HttpInfo.JSON;
        //return super.makeType();
    }
}
