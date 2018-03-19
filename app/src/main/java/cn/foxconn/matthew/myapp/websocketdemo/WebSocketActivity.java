package cn.foxconn.matthew.myapp.websocketdemo;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.drafts.Draft_10;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

import butterknife.BindView;
import butterknife.ButterKnife;
import cn.foxconn.matthew.myapp.R;

/**
 * @author:Matthew
 * @date:2018/3/13
 * @email:guocheng0816@163.com
 */
public class WebSocketActivity extends AppCompatActivity {

    private static final String TAG = "WebSocketActivity";

    @BindView(R.id.et_message)
    EditText et_message;
    @BindView(R.id.bt_send)
    Button bt_send;
    @BindView(R.id.tv_log)
    TextView tv_log;

    private WebSocketClient mClient;
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            tv_log.setText(tv_log.getText() + "\n" + msg.obj);
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    mClient=new WebSocketClient(new URI("ws://192.168.210.2:2018/"),new Draft_10()) {
                        @Override
                        public void onOpen(ServerHandshake handshakedata) {
                            Log.e(TAG, "onOpen: " +handshakedata.getHttpStatus());
                        }

                        @Override
                        public void onMessage(String message) {
                            Log.e(TAG, "onMessage: " );
                            handler.obtainMessage(0,message).sendToTarget();
                        }

                        @Override
                        public void onClose(int code, String reason, boolean remote) {
                            Log.e(TAG, "onClose: " );
                            if(mClient!=null){
                                mClient.close();
                                finish();
                            }
                        }

                        @Override
                        public void onError(Exception ex) {
                            Log.e(TAG, "onError: " );
                        }
                    };
                    mClient.connect();
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }
            }
        }).start();

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(et_message.getText().toString())){
                    if(mClient!=null) {
                        Log.e(TAG, "onClick: "+mClient.getReadyState() );
                        if(mClient.getReadyState()== WebSocket.READYSTATE.OPEN) {
                            mClient.send(et_message.getText().toString());
                        }
                    }
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(mClient!=null){
            mClient.close();
        }
    }
}
