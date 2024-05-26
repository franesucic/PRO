package INFSUS.project.PRO.services;

public class RefactorString {

    public static String refactor(String str) {
        return str.substring(0, 1).toUpperCase()
                + str.substring(1).toLowerCase();
    }

}
