package android.collab.myapplication;

import com.kakao.auth.IApplicationConfig;
import com.kakao.auth.ISessionConfig;
import com.kakao.auth.KakaoAdapter;

public class KakaoSDKAdapter extends KakaoAdapter {

    //로그인 Session을 생성하기 위해 필요한 옵션을 얻기위한 abstract class. 기본 설정은 KakaoAdapter에 정의되어있으며, 설정 변경이 필요한 경우 상속해서 사용할 수 있다.
    @Override
    public ISessionConfig getSessionConfig() {
        return super.getSessionConfig();
    }

    //Application이 가지고있는 정보를 얻기위해 interface 메서드에 MyApp의 싱글턴 인스턴스를 return.
    @Override
    public IApplicationConfig getApplicationConfig() {
        return MyApp::getMyapplApplicationContext;
    }
}

