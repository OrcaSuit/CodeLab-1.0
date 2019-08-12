package android.collab.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.kakao.auth.ApiResponseCallback;
import com.kakao.auth.AuthService;
import com.kakao.auth.Session;
import com.kakao.auth.network.response.AccessTokenInfoResponse;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.LoginButton;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.callback.UnLinkResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.helper.log.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    LoginButton kakao;
    Button token;
    Button update;
    Button save;
    Button unlink;
    TextView mTextView;
    EditText mEditText;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mTextView = (TextView) findViewById(R.id.textview);
        mEditText = (EditText) findViewById(R.id.edittext);

        // 카카오 톡으로 앱 연결하기
        kakao = (LoginButton) findViewById(R.id.com_kakao_login);
        kakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Session.getCurrentSession().checkAndImplicitOpen();
            }
        });

        //토큰 가져오기
        token = (Button) findViewById(R.id.token_btn);
        token.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AuthService.getInstance().requestAccessTokenInfo(new ApiResponseCallback<AccessTokenInfoResponse>() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                    }
                    @Override
                    public void onNotSignedUp() {
                    }
                    @Override
                    public void onSuccess(AccessTokenInfoResponse result) {
                        mTextView.setText("" + result.getUserId());
                    }
                });
            }
        });

        // 카카오 톡 회원정보 가져오기.
        update = (Button) findViewById(R.id.update_btn);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!(Session.getCurrentSession().checkAndImplicitOpen())) {
                    mTextView.setText("연결해제된 아이디입니다.");
                }
                List<String> keys = new ArrayList<>();
                keys.add("properties.nickname");
                UserManagement.getInstance().me(keys, new MeV2ResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                    }

                    @Override
                    public void onSuccess(MeV2Response result) {
                        Logger.d("user id : " + result.getNickname());
                        mTextView.setText(result.getNickname());
                    }
                });
            }
        });

        //사용자 정보 수정하기
        save = (Button) findViewById(R.id.save_btn);
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Map<String, String> properties = new HashMap<String, String>();

                properties.put("nickname",mEditText.getText().toString());

                UserManagement.getInstance().requestUpdateProfile(new ApiResponseCallback<Long>() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {
                    }

                    @Override
                    public void onNotSignedUp() {
                    }
                    @Override
                    public void onSuccess(Long result) {

                    }
                },properties);

            }
        });

        // 이 앱에서 카카오톡 연결해제하기.
        unlink = (Button) findViewById(R.id.unlink_btn);
        unlink.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserManagement.getInstance().requestUnlink(new UnLinkResponseCallback() {
                    @Override
                    public void onSessionClosed(ErrorResult errorResult) {

                    }

                    @Override
                    public void onNotSignedUp() {

                    }

                    @Override
                    public void onSuccess(Long result) {
                        mTextView.setText("앱연결해제");
                    }
                });

            }
        });


    }
}
