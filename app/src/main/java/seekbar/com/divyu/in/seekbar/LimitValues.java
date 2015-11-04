package seekbar.com.divyu.in.seekbar;

/**
 * Created by divyanshu on 04/11/15.
 */
public class LimitValues {
    final int id;
    final int startPoint;
    final int endPoint;

    private LimitValues(final int id, final int startPoint, final int endPoint){
        this.id = id;
        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public static LimitValues createLimitValues(int id, int startPoint, int endPoint) {
        return new LimitValues(id, startPoint, endPoint);
    }

}
