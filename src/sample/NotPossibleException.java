package sample;

/**
 * Created by Lauris on 08/11/2017.
 */
public class NotPossibleException extends Exception {
    String pb;
    public NotPossibleException(String pb) {
        this.pb=pb;
        System.out.println(pb);
    }
}
