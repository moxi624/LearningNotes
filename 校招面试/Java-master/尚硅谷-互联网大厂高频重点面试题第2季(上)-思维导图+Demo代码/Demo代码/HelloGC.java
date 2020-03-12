public class HelloGC {
    public static int oneAddone(int x,int y){
        return x+y;
    }
    public static void main(String[] args) throws InterruptedException {
        int res = oneAddone(1,1);
        System.out.println(res);
    }
}
