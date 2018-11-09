package top.yuyufeng.ws;

import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author yuyufeng
 * @date 2018/11/8.
 */
public class Global {
    public static ChannelGroup group = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
    public static Map<String, TransJson> gameMap = new ConcurrentHashMap();
    public static List<String> colorQueue = new LinkedList<>();

    static {
        colorQueue.add("#000000");
        colorQueue.add("#0000CC");
        colorQueue.add("#003399");
        colorQueue.add("#006600");
        colorQueue.add("#0066CC");
        colorQueue.add("#009933");
        colorQueue.add("#009999");
        colorQueue.add("#0099CC");
        colorQueue.add("#0099FF");
        colorQueue.add("#00CC00");
        colorQueue.add("#00CC99");
        colorQueue.add("#00CCCC");
        colorQueue.add("#00CCFF");
        colorQueue.add("#00FF00");
        colorQueue.add("#00FFFF");
        colorQueue.add("#330000");
        colorQueue.add("#99FF33");
        colorQueue.add("#99FFCC");
        colorQueue.add("#CC00FF");
        colorQueue.add("#CC9933");
        colorQueue.add("#CCCCCC");
        colorQueue.add("#CCCC66");
        colorQueue.add("#CCFF66");
        colorQueue.add("#CCFFCC");
        colorQueue.add("#CCFFFF");
        colorQueue.add("#FF0000");
        colorQueue.add("#FF0066");
        colorQueue.add("#FF0099");
        colorQueue.add("#FF33CC");
        colorQueue.add("#FF9966");
        colorQueue.add("#FFCC33");
    }

    public static AtomicInteger colorIndex = new AtomicInteger(0);

    public synchronized static String getColor() {
        int i = colorIndex.getAndIncrement();
        if (i > colorQueue.size() - 1) {
            colorIndex.set(0);
            i = 0;
        }
        return colorQueue.get(i);
    }
}
