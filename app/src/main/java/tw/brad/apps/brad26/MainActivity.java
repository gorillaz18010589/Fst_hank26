package tw.brad.apps.brad26;
//這個service跟著activity榜定在玩
//介紹流程架構你怎麼去控
//實際案例用在藍芽
//1.創service

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private  MyService myService;
    private  boolean isBind; //是否連線上
    private TextView tv;
    private ServiceConnection mConnection = new ServiceConnection() {
        //當某個物件被連上,連線成功時
        @Override
        public void onServiceConnected(ComponentName componentName, IBinder iBinder) {//當這個被連上,iBinder會被呼叫,靠這個連線溝通
            MyService.LocalBinder binder = (MyService.LocalBinder)iBinder;
            myService = binder.getService();//透過這個binder,取得伺服器物件
            isBind = true; //連上線時
        }

        //當某個物件備不要連上時
        @Override
        public void onServiceDisconnected(ComponentName componentName) {
            isBind = false; //沒有連上線時
        }
    };


    //當開始實,連接到myservice頁面榜定近來
    @Override
    protected void onStart() {
        super.onStart();
        Intent intent = new Intent(this,MyService.class);
        bindService(intent,mConnection, Context.BIND_AUTO_CREATE);//與service企圖連線,當連線成功時到mConnection處理
    }
    //當停止時,解除榜定
    @Override
    protected void onStop() {
        super.onStop();
        if(isBind){ //當結束時如果還綁定,做以下是情
            unbindService(mConnection);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tv = findViewById(R.id.tv);
    }

    //按下按鈕產生伺服器寫的樂頭方法,這些都是Service去做的不是ACtivity
    public void test1(View view) {
        if(isBind && myService != null){ //如果有榜定而且伺服器不是空的
            int lottery = myService.createLottery(); //抓到伺服器的樂透方法
            tv.setText("Lottery:" +lottery); //顯示出來
        }
    }
}
