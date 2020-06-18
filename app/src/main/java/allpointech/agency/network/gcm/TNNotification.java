package allpointech.agency.network.gcm;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.widget.RemoteViews;

import com.tuna.utils.SLog;

import allpointech.agency.AppInfo;
import allpointech.agency.R;
import allpointech.agency.intro.IntroActivity;


public class TNNotification {

    private static Notification.Builder mBuilderGeneral;
    private static NotificationCompat.Builder mBuilderCompatBig;
    private static Notification.Builder mBuilderOngoing;

    public class NOTI_INTENT_IDS {
        public static final int GENERAL_ID = 7090;
        public static final int GENERAL_BIG_ID = 7091;
        public static final int ONGOING_ID = 7099;
    }
    public class NOTI_IDS {
        public static final int GENERAL_ID = 8090;
        public static final int GENERAL_BIG_ID = 8091;
        public static final int ONGOING_ID = 8099;
    }

    // 노티 삭제
    public static void clearNotification(Context context) {
        clearNotification(context, NOTI_IDS.GENERAL_ID);
    }
    public static void clearBigPictureNotification(Context context) {
        clearNotification(context, NOTI_IDS.GENERAL_BIG_ID);
    }
    public static void clearOngoingNotification(Context context) {
        clearNotification(context, NOTI_IDS.ONGOING_ID);
    }
    private static void clearNotification(Context context, int id) {
        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.cancel(id);

        if (mBuilderOngoing != null) {
            mBuilderOngoing = null;
        }
    }

    /**
     * 일반 알림
     */
    public static void makeNotification(Context context, String title, String message, Class<?> activityClass, Bundle bundle) {
        // bundle 기반 메세지
        if (title == null) {
            if (bundle != null) {
                title = context.getResources().getString(R.string.app_name) + " " + bundle.getString("title", "");
            } else {
                // 깜박하고 데이터 안 넣었을 경우 default
                title = context.getResources().getString(R.string.app_name);
            }
        }
        if (message == null) {
            if (bundle != null) {
                message = bundle.getString("body", "새로운 메세지가 왔습니다.");
            } else {
                // 깜박하고 데이터 안 넣었을 경우 default
                message = "새로운 메세지가 왔습니다.";
            }
        }

        // 기본 이동
        if (activityClass == null) {
            activityClass = IntroActivity.class;
        }

        mBuilderGeneral = new Notification.Builder(context);
        mBuilderGeneral.setContentIntent(getMoveIntent(context, activityClass, bundle, NOTI_INTENT_IDS.GENERAL_ID));
        mBuilderGeneral.setAutoCancel(true);

        // 상단 알림바 문구
        mBuilderGeneral.setTicker(message);
        // 아이콘
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0 이상
            mBuilderGeneral.setColor(context.getResources().getColor(R.color.primary));
            //mBuilderGeneral.setSmallIcon(R.drawable.ic_stat_ic_noti);
        } else {
            mBuilderGeneral.setSmallIcon(R.mipmap.ic_launcher);
        }
        // 시간 표시
        mBuilderGeneral.setWhen(System.currentTimeMillis());
        // 알림 메세지
        mBuilderGeneral.setContentTitle(title);
        mBuilderGeneral.setContentText(message);
        // 누적 카운트
        mBuilderGeneral.setNumber(AppInfo.getPendingNotificationsCount());

        // 알림 형태
        mBuilderGeneral.setDefaults(getNotiMode(context));

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTI_IDS.GENERAL_ID, mBuilderGeneral.build());
    }
    public static void setIntent(Context context, Class<?> activityClass, Bundle bundle) {
        if (mBuilderGeneral != null) {
            mBuilderGeneral.setContentIntent(getMoveIntent(context, activityClass, bundle, NOTI_INTENT_IDS.GENERAL_ID));
        }
    }

    /**
     * 큰사진 알림
     */
    public static void makeNotificationWithBigPicture(Context context, String title, String message, Bitmap banner, Class<?> activityClass, Bundle bundle) {
        // bundle 기반 메세지
        if (title == null) {
            if (bundle != null) {
                title = context.getResources().getString(R.string.app_name) + " " + bundle.getString("title", "");
            } else {
                // 깜박하고 데이터 안 넣었을 경우 default
                title = context.getResources().getString(R.string.app_name);
            }
        }
        if (message == null) {
            if (bundle != null) {
                message = bundle.getString("body", "새로운 메세지가 왔습니다.");
            } else {
                // 깜박하고 데이터 안 넣었을 경우 default
                message = "새로운 메세지가 왔습니다.";
            }
        }

        // 기본 이동
        if (activityClass == null) {
            activityClass = IntroActivity.class;
        }

        mBuilderCompatBig = new NotificationCompat.Builder(context);
        mBuilderCompatBig.setContentIntent(getMoveIntent(context, activityClass, bundle, NOTI_INTENT_IDS.GENERAL_BIG_ID));
        mBuilderCompatBig.setAutoCancel(true);

        // 상단 알림바 문구
        mBuilderCompatBig.setTicker(message);
        // 아이콘
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0 이상
            mBuilderCompatBig.setColor(context.getResources().getColor(R.color.primary));
            //mBuilderCompatBig.setSmallIcon(R.drawable.ic_stat_ic_noti);
        } else {
            mBuilderCompatBig.setSmallIcon(R.mipmap.ic_launcher);
        }
        // 시간 표시
        mBuilderCompatBig.setWhen(System.currentTimeMillis());
        // 알림 메세지
        mBuilderCompatBig.setContentTitle(title);
        mBuilderCompatBig.setContentText(message);

        // 큰 이미지 스타일
        SLog.LogD("" + banner);
        if (banner != null) {
            NotificationCompat.BigPictureStyle bigImagestyle = new NotificationCompat.BigPictureStyle();
            bigImagestyle.setBigContentTitle(title);
            bigImagestyle.bigPicture(banner);
            bigImagestyle.setSummaryText(message);
            mBuilderCompatBig.setStyle(bigImagestyle);
        } else {
            NotificationCompat.BigTextStyle bigTextStyle = new NotificationCompat.BigTextStyle();
            bigTextStyle.setBigContentTitle(title);
            bigTextStyle.bigText(message);
//            bigTextStyle.setSummaryText(message);
            mBuilderCompatBig.setStyle(bigTextStyle);
        }

//        NotificationCompat.InboxStyle bigInboxStyle = new NotificationCompat.InboxStyle();
//        bigInboxStyle.setBigContentTitle(title);
//        bigInboxStyle.addLine("Line 1");
//        bigInboxStyle.addLine("Line 2");
//        bigInboxStyle.addLine("Line 3");
//        mBuilderCompatBig.setStyle(bigInboxStyle);

        // 알림 형태
//        mBuilderCompatBig.setDefaults(getNotiMode(context));

        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        nm.notify(NOTI_IDS.GENERAL_BIG_ID, mBuilderCompatBig.build());
    }
    public static void setBigIntent(Context context, Class<?> activityClass, Bundle bundle) {
        if (mBuilderCompatBig != null) {
            mBuilderCompatBig.setContentIntent(getMoveIntent(context, activityClass, bundle, NOTI_INTENT_IDS.GENERAL_BIG_ID));
        }
    }

    /**
     * 온고잉(진행중) 노티피케이션
     */
    // 온고잉 등록
    private static RemoteViews contentView;
    public static void makeOngoingNotification(Context context, String title, String message, Bitmap bitmap, Class<?> activityClass, Bundle bundle) {
        // bundle 기반 메세지
        if (title == null) {
            if (bundle != null) {
                title = context.getResources().getString(R.string.app_name) + " " + bundle.getString("title", "");
            } else {
                // 깜박하고 데이터 안 넣었을 경우 default
                title = context.getResources().getString(R.string.app_name);
            }
        }
        if (message == null) {
            if (bundle != null) {
                message = bundle.getString("body", "새로운 메세지가 왔습니다.");
            } else {
                // 깜박하고 데이터 안 넣었을 경우 default
                message = "새로운 메세지가 왔습니다.";
            }
        }

        if (mBuilderOngoing == null) {
            mBuilderOngoing = new Notification.Builder(context);

            mBuilderOngoing.setContentIntent(getMoveIntent(context, activityClass, bundle, NOTI_INTENT_IDS.ONGOING_ID));
            mBuilderOngoing.setAutoCancel(true);
            mBuilderOngoing.setOngoing(true);

            // 상단 알림바 문구
            mBuilderOngoing.setTicker(title);
            // 아이콘
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) { // 5.0 이상
                mBuilderOngoing.setColor(context.getResources().getColor(R.color.primary));
                mBuilderOngoing.setSmallIcon(R.mipmap.ic_launcher);
            } else {
                mBuilderOngoing.setSmallIcon(R.mipmap.ic_launcher);
            }
            // 시간 표시
            mBuilderOngoing.setWhen(System.currentTimeMillis());
            // 알림 메세지
            mBuilderOngoing.setContentTitle(title);

//            if (bitmap != null) {
//                // 알림 커스텀 뷰
//                contentView = new RemoteViews(context.getPackageName(), R.layout.noti_view);
//                contentView.setImageViewBitmap(R.id.image, bitmap);
//                contentView.setTextViewText(R.id.title, title);
//                contentView.setTextViewText(R.id.content, message);
//                mBuilderOngoing.setContent(contentView);
//            } else {
//                mBuilderOngoing.setContentText(message);
//            }

            // 알림 형태
//            mBuilderOngoing.setDefaults(getNotiMode(context));

            NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            nm.notify(NOTI_IDS.ONGOING_ID, mBuilderOngoing.build());
        } else {
//            setData(context, title, message);
        }
    }

    // 온고잉 내용 변경
//    public static boolean setData(Context context, String title, String message) {
//        if (mBuilderOngoing == null) {
//            return false;
//        }
//
//        if (contentView != null) {
//            contentView.setTextViewText(R.id.title, title);
//            contentView.setTextViewText(R.id.content, message);
//            mBuilderOngoing.setContent(contentView);
//        } else {
//            mBuilderOngoing.setContentTitle(title);
//            mBuilderOngoing.setContentText(message);
//        }
//
//        // OnGoing 은 수시로 동작하기에 동작 해제
//        mBuilderOngoing.setDefaults(TNConstants.NotiMode.TYPE_NONE);
//
//        NotificationManager nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
//        nm.notify(NOTI_IDS.ONGOING_ID, mBuilderOngoing.build());
//
//        return true;
//    }
    public static void setOngoinIntent(Context context, Class<?> activityClass, Bundle bundle) {
        if (mBuilderOngoing != null) {
            mBuilderOngoing.setContentIntent(getMoveIntent(context, activityClass, bundle, NOTI_INTENT_IDS.ONGOING_ID));
        }
    }

    // 이동할 화면 정의
    private static PendingIntent getMoveIntent(Context context, Class<?> activityClass, Bundle bundle, int id) {
        if (activityClass == null) {
            return null;
        }

        Intent data_intent = new Intent(context, activityClass);
        if (bundle != null) {
            data_intent.putExtras(bundle);
        }
        data_intent.addFlags(
                Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TOP
                | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, id, data_intent, PendingIntent.FLAG_UPDATE_CURRENT);
        return pendingIntent;
    }

    // 알림 모드
    private static int getNotiMode(Context context) {
//        int noti_mode = TNPreference.getNotiMode(context);
//        switch (noti_mode) {
//            case TNConstants.NotiMode.TYPE_NONE:
//                return Notification.DEFAULT_LIGHTS;
//            case TNConstants.NotiMode.TYPE_SOUND:
//                return Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND;
//            case TNConstants.NotiMode.TYPE_VIBE:
//                return Notification.DEFAULT_LIGHTS | Notification.DEFAULT_VIBRATE;
//            case TNConstants.NotiMode.TYPE_SOUND_VIBE:
//            default:
//                return Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
//        }
        return Notification.DEFAULT_LIGHTS | Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE;
    }

}


/**
 * 노티피케이션 커스텀 뷰
 * 글라이드 이용한 라운드 서클 이미지 적용
 * /
 /*
 private Bitmap bitmap;
 private void test() {
 new AsyncTask<Void, Void, Void>() {
@Override
protected Void doInBackground(Void... params) {
Looper.prepare();
try {
bitmap = Glide.
with(MainActivity.this).
load("http://www.kccosd.org/files/testing_image.jpg")
.asBitmap()
.transform(new CircleTransformation(getApplicationContext()))
.into(-1, -1)
.get();
} catch (final ExecutionException e) {
} catch (final InterruptedException e) {
}
return null;
}

@Override
protected void onPostExecute(Void dummy) {
if (null != bitmap) {
// The full bitmap should be available here
CustomNotification.makeNotification(MainActivity.this, "제목", "내용", MainActivity.class, null);
CustomNotification.makeNotificationWithBigPicture(MainActivity.this, "제목", "내용", bitmap, MainActivity.class, null);

CustomNotification.makeOngoingNotification(MainActivity.this, "제목", "내용", bitmap, MainActivity.class, null);
CustomNotification.setData(MainActivity.this, "제목1", "내용1");
}
}
}.execute();
 }
 */