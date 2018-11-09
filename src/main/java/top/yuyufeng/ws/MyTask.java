package top.yuyufeng.ws;

import com.alibaba.fastjson.JSONObject;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

import java.util.concurrent.TimeUnit;

/**
 * @author yuyufeng
 * @date 2018/11/9.
 */
public class MyTask extends Thread{
    @Override
    public void run() {
        while(true){
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            TextWebSocketFrame tws = new TextWebSocketFrame(JSONObject.toJSONString(Global.gameMap));
            Global.group.writeAndFlush(tws);
        }
    }
}
