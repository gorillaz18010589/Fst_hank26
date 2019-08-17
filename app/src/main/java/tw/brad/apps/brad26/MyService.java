package tw.brad.apps.brad26;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;

public class MyService extends Service {
    private  final  IBinder mBinder = new LocalBinder();//有這個物件要負責溝通的橋樑

    //內部類別,定義你想要回傳的重要資料,就是外部類別的Service資料
    public   class  LocalBinder extends Binder{
        MyService getService(){ //呼叫一朝我自己定義的回傳伺服器方法
            return  MyService.this;
        }
    }
    @Override
    public IBinder onBind(Intent intent) {
      return  mBinder; //回傳溝通橋梁物件
    }

    //回傳樂透方法
    public int createLottery(){
        return  (int)(Math.random()*49+1);
    }
}
