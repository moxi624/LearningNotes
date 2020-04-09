import java.time.ZonedDateTime;

/**
 * @author zzyy
 * @version 1.0
 * @create 2020/03/06
 */
public class T2 {
    public static void main(String[] args) {
        // 默认时区
        ZonedDateTime now = ZonedDateTime.now();
        System.out.println(now);
    }
}
