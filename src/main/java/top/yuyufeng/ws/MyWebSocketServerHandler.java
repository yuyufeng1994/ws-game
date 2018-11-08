package top.yuyufeng.ws;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.*;

import java.util.Date;

/**
 * @author yuyufeng
 * @date 2018/11/8.
 */
public class MyWebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * channel 通道 action 活跃的 当客户端主动链接服务端的链接后，这个通道就是活跃的了。也就是客户端与服务端建立了通信通道并且可以传输数据
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        // 添加
        Global.group.add(ctx.channel());
        Global.gameMap.put(ctx.channel().id().toString(), new TransJson(ctx.channel().id().toString(), "red", 0, 0, 5));
        System.out.println("客户端与服务端连接开启：" + ctx.channel().remoteAddress().toString());
    }

    /**
     * channel 通道 Inactive 不活跃的 当客户端主动断开服务端的链接后，这个通道就是不活跃的。也就是说客户端与服务端关闭了通信通道并且不可以传输数据
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        // 移除
        Global.group.remove(ctx.channel());
        Global.gameMap.remove(ctx.channel().id().toString());
        System.out.println("客户端与服务端连接关闭：" + ctx.channel().remoteAddress().toString());
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object o) throws Exception {
        /**
         * writeAndFlush接收的参数类型是Object类型，但是一般我们都是要传入管道中传输数据的类型，比如我们当前的demo
         * 传输的就是TextWebSocketFrame类型的数据
         */
        if (o instanceof WebSocketFrame) {
            handlerWebSocketFrame(ctx, (WebSocketFrame) o);
        }
    }

    /**
     * channel 通道 Read 读取 Complete 完成 在通道读取完成后会在这个方法里通知，对应可以做刷新操作 ctx.flush()
     */
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    /**
     * exception 异常 Caught 抓住 抓住异常，当发生异常的时候，可以做一些相应的处理，比如打印日志、关闭链接
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private void handlerWebSocketFrame(ChannelHandlerContext ctx, WebSocketFrame frame) {
        // 判断是否关闭链路的指令
        if (frame instanceof CloseWebSocketFrame) {
            return;
        }
        // 判断是否ping消息
        if (frame instanceof PingWebSocketFrame) {
            ctx.channel().write(new PongWebSocketFrame(frame.content().retain()));
            return;
        }
        // 本例程仅支持文本消息，不支持二进制消息
        if (!(frame instanceof TextWebSocketFrame)) {
            System.out.println("本例程仅支持文本消息，不支持二进制消息");
            throw new UnsupportedOperationException(
                    String.format("%s frame types not supported", frame.getClass().getName()));
        }
        // 返回应答消息
        String request = ((TextWebSocketFrame) frame).text();
        TransJson reqJson = JSONObject.parseObject(request, TransJson.class);
        reqJson.setId(ctx.channel().id().toString());
        System.out.println(reqJson);
        Global.gameMap.get(reqJson.getId()).editXY(reqJson.getX(), reqJson.getY());
        TextWebSocketFrame tws = new TextWebSocketFrame(JSONObject.toJSONString(Global.gameMap));
        // 群发
        Global.group.writeAndFlush(tws);
        // 返回【谁发的发给谁】
        // ctx.channel().writeAndFlush(tws);
    }


}
