package allpointech.agency.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import org.json.JSONObject;

public class TNPreference {
    private static String PREF_NAME = "_pref_touchall";

    public static final int REQUEST_TYPE_REFRESH = 0x00001114;

    private static SharedPreferences getPreference(Context context) {
        return context.getSharedPreferences(PREF_NAME, Activity.MODE_APPEND);
    }

    private static Editor getEditor(Context context) {
        return context.getSharedPreferences(PREF_NAME, Activity.MODE_APPEND).edit();
    }

    // userID start
    public static String getMemberID(Context context) {
        return getPreference(context).getString("tnUserID", null);
    }

    public static void setMemberID(Context context, String user_type) {
        getEditor(context).putString("tnUserID", user_type).commit();
    }
    // userID end


    // member_phonenuumber sptart
    public static String getMemphoneNumber(Context context) {
        return getPreference(context).getString("tnMemPhoneNumber", null);
    }

    public static void setMemPhoneNumber(Context context, String mem_phonenumber) {
        getEditor(context).putString("tnMemPhoneNumber", mem_phonenumber).commit();
    }
    // member_phonenuumber end

    // userType start
    public static String getMemberType(Context context) {
        return getPreference(context).getString("tnUserType", null);
    }

    public static void setMemberType(Context context, String user_type) {
        getEditor(context).putString("tnUserType", user_type).commit();
    }
    // userType end

    // member_name sptart
    public static String getMemName(Context context) {
        return getPreference(context).getString("tnMemName", null);
    }

    public static void setMemName(Context context, String mem_name) {
        getEditor(context).putString("tnMemName", mem_name).commit();
    }
    // member_name end

    // member_pw start
    public static String getMemPw(Context context) {
        return getPreference(context).getString("tnMemPw", null);
    }

    public static void setMemPw(Context context, String mem_pw) {
        getEditor(context).putString("tnMemPw", mem_pw).commit();
    }

    // member_pw end

    // member_birthday start
    public static String getMemBirthDay(Context context) {
        return getPreference(context).getString("tnMemBirthDay", null);
    }

    public static void setMemBirthDay(Context context, String mem_birth_day) {
        getEditor(context).putString("tnMemBirthDay", mem_birth_day).commit();
    }
    // member_birthday end

    // member_sex start
    public static String getMemSex(Context context) {
        return getPreference(context).getString("tnMemSex", null);
    }

    public static void setMemSex(Context context, String mem_sex) {
        getEditor(context).putString("tnMemSex", mem_sex).commit();
    }
    // member_sex end


    // member_email start
    public static String getMemEMail(Context context) {
        return getPreference(context).getString("tnMemEMail", null);
    }

    public static void setMemEMail(Context context, String mem_email) {
        getEditor(context).putString("tnMemEMail", mem_email).commit();
    }
    // member_email end

    // member_email start
    public static String getMemAddr(Context context) {
        return getPreference(context).getString("tnMemAddr", null);
    }

    public static void setMemAddr(Context context, String mem_addr) {
        getEditor(context).putString("tnMemAddr", mem_addr).commit();
    }
    // member_email end

    // member_point_type start
    public static String getMemPointType(Context context) {
        return getPreference(context).getString("tnMemPointType", null);
    }

    public static void setMemPointType(Context context, String mem_point_type) {
        getEditor(context).putString("tnMemPointType", mem_point_type).commit();
    }
    // member_point_type end

    // member_bank_code end
    public static void setMemBankCode(Context context, String mem_bank_code) {
        getEditor(context).putString("tnMemBankCode", mem_bank_code).commit();
    }

    public static String getMemBankCode(Context context) {
        return getPreference(context).getString("tnMemBankCode", null);
    }
    // member_bank_code end

    // member_use_date end
    public static void setMemBankAccount(Context context, String mem_bank_account) {
        getEditor(context).putString("tnMemBankAccount", mem_bank_account).commit();
    }

    public static String getMemBankAccount(Context context) {
        return getPreference(context).getString("tnMemBankAccount", null);
    }
    // member_use_date end

    // member_use_date end
    public static void setMemBankHolder(Context context, String mem_bank_account) {
        getEditor(context).putString("tnMemBankHolder", mem_bank_account).commit();
    }

    public static String getMemBankHolder(Context context) {
        return getPreference(context).getString("tnMemBankHolder", null);
    }
    // member_use_date end

    // member_notice_date end
    public static void setNoticeDate(Context context, long date) {
        getEditor(context).putLong("kNoticeDate", date).commit();
    }

    public static Long getNoticeDate(Context context) {
        return getPreference(context).getLong("kNoticeDate", 0);
    }
    // member_notice_date end

    // auto login end
    public static void setAutoLogin(Context context, boolean is) {
        getEditor(context).putBoolean("kAutoLogin", is).commit();
    }

    public static boolean getAutoLogin(Context context) {
        return getPreference(context).getBoolean("kAutoLogin", false);
    }
    // auto login end

    // member_use_date end
    public static void setMemToken(Context context, String token) {
        getEditor(context).putString("tnMemToken", token).commit();
    }

    public static String getMemToken(Context context) {
        return getPreference(context).getString("tnMemToken", null);
    }
    // member_use_date end

    // member_use_date end
    public static void setMemGrade(Context context, String grade) {
        getEditor(context).putString("tnMemGrade", grade).commit();
    }

    public static String getMemGrade(Context context) {
        return getPreference(context).getString("tnMemGrade", null);
    }
    // member_use_date end

    // member_use_date end
    public static void setMemGenderName(Context context, String gender_name) {
        getEditor(context).putString("tnMemGenderName", gender_name).commit();
    }

    public static String getMemGenderName(Context context) {
        return getPreference(context).getString("tnMemGenderName", null);
    }
    // member_use_date end

    // member_use_date end
    public static void setAgreement1(Context context, boolean is) {
        getEditor(context).putBoolean("tnMemAgreement1", is).commit();
    }

    public static boolean getAgreement1(Context context) {
        return getPreference(context).getBoolean("tnMemAgreement1", false);
    }
    // member_use_date end

    // member_use_date end
    public static void setAgreement2(Context context, boolean is) {
        getEditor(context).putBoolean("tnMemAgreement2", is).commit();
    }

    public static boolean getAgreement2(Context context) {
        return getPreference(context).getBoolean("tnMemAgreement2", false);
    }
    // member_use_date end

    // member_use_date end
    public static void setAgreement3(Context context, boolean is) {
        getEditor(context).putBoolean("tnMemAgreement3", is).commit();
    }

    public static boolean getAgreement3(Context context) {
        return getPreference(context).getBoolean("tnMemAgreement3", false);
    }
    // member_use_date end

    public static void setUserInfo(Activity activity, JSONObject object) {
//        String member_code = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbcode);
//        String member_seq = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbseq);
//        String member_phno = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbphno);
//        String member_type = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbtype);
//        String member_name = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbkname);
//        String member_pass = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbpass);
//        String member_birthday = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbbirthdt);
//        String member_sex = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbsex);
//        String member_email1 = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbemail1);
//        String member_email2 = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbemail2);
//        String member_pointtype = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbpointtype);
//        String member_bank_code = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbbankcd);
//        String member_bank_account = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbbankno);
//        String member_bank_account_name = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbbankacname);
//        String member_join_day = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbjoindt);
//        String member_flag1 = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbagr_flag1);
//        String member_flag2 = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbagr_flag2);
//        String member_flag3 = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbagr_flag3);
//        String member_status = JSONParser.getString(object, ResLogin.KEY_RES.MEMBER.mbstatus);
//
//        TNPreference.setMemberID(activity, member_code+member_seq);
//        TNPreference.setMemPhoneNumber(activity, member_phno);
//        TNPreference.setMemberType(activity, member_type);
//        TNPreference.setMemName(activity, member_name);
//        //TNPreference.setMemName(getActivity(), member_name);
//        TNPreference.setMemBirthDay(activity, member_birthday);
//        TNPreference.setMemSex(activity, member_sex);
//        TNPreference.setMemEMail(activity, member_email1 + "@" + member_email2);
//        TNPreference.setMemPointType(activity, member_pointtype);
//        TNPreference.setMemBankCode(activity, member_bank_code);
//        TNPreference.setMemBankAccount(activity, member_bank_account);
//        //TNPreference.setMemBankAccount(activity, member_bank_account);
    }

    public static void clearUserInfo(Activity activity) {
        TNPreference.setMemberID(activity, null);
        TNPreference.setMemPhoneNumber(activity, null);
        TNPreference.setMemberType(activity, null);
        TNPreference.setMemName(activity, null);
        TNPreference.setMemPw(activity, null);
        TNPreference.setMemBirthDay(activity, null);
        TNPreference.setMemSex(activity, null);
        TNPreference.setMemEMail(activity, null);
        TNPreference.setMemPointType(activity, null);
        TNPreference.setMemBankCode(activity, null);
        TNPreference.setMemBankAccount(activity, null);
        //TNPreference.setMemBankAccount(activity, null);
    }
}
