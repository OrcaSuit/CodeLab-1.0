package android.collab.myapplication;

import android.app.Application;
import com.kakao.auth.KakaoSDK;

public class MyApp extends Application {
    private static volatile MyApp instance = null;

    //MyApp클래스의 싱글턴 인스턴스
    public static MyApp getMyapplApplicationContext() {
        if (instance == null)
            throw new IllegalStateException("this application does not inherit com.kakao.GlobalApplication");
        return instance;
    }

    //OnCreate() 호출시 Context를 MyApp 인스턴스에 저장. KakaoSDK 초기화.
    public void onCreate() {
        super.onCreate();
        instance = this;
        KakaoSDK.init(new KakaoSDKAdapter());
    }
}
