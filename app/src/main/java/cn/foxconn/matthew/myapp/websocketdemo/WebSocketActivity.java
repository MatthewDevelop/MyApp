package cn.foxconn.matthew.myapp.websocketdemo;

import android.os.Handler;
import android.os.Message;
import android.support.test.espresso.core.internal.deps.guava.util.concurrent.ThreadFactoryBuilder;
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

import java.lang.ref.WeakReference;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

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
    EditText etMessage;
    @BindView(R.id.bt_send)
    Button btSend;
    @BindView(R.id.tv_log)
    TextView tvLog;

    private WebSocketClient mClient;
    private MyHandler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_socket);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        handler=new MyHandler(this);
        ThreadFactory webSocketFactory=new ThreadFactoryBuilder().setNameFormat("webSocket-thread").build();
        ExecutorService fixedThreadPool = new ThreadPoolExecutor(1, 1, 0, TimeUnit.MILLISECONDS, new LinkedBlockingDeque<Runnable>(), webSocketFactory);
        Runnable runnable=new Runnable() {
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
        };
        fixedThreadPool.execute(runnable);

        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!TextUtils.isEmpty(etMessage.getText().toString())){
                    if(mClient!=null) {
                        Log.e(TAG, "onClick: "+mClient.getReadyState() );
                        if(mClient.getReadyState()== WebSocket.READYSTATE.OPEN) {
                            mClient.send(etMessage.getText().toString());
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

    private static class MyHandler extends Handler {
        WeakReference<WebSocketActivity> mReference;

        public MyHandler(WebSocketActivity webSocketActivity){
            mReference= new WeakReference<>(webSocketActivity);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if(mReference.get()==null){
                return;
            }
            mReference.get().tvLog.setText(mReference.get().tvLog.getText() + "\n" + msg.obj);
        }
    }
}
