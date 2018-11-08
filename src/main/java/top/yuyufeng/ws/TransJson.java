package top.yuyufeng.ws;

/**
 * @author yuyufeng
 * @date 2018/11/8.
 */
public class TransJson {
    private String id;
    private String color;
    private int x;
    private int y;
    private int speed;

    public TransJson() {
    }

    public TransJson(String id, String color, int x, int y, int speed) {
        this.id = id;
        this.color = color;
        this.x = x;
        this.y = y;
        this.speed = speed;
    }

    public void editXY(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public String toString() {
        return "TransJson{" +
                "id='" + id + '\'' +
                ", color='" + color + '\'' +
                ", x=" + x +
                ", y=" + y +
                ", speed=" + speed +
                '}';
    }
}
