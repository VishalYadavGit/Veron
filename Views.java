import java.io.*;


public class Views {
    public static void RenderHome(PrintWriter writer) {
        System.out.println("Rendering Home Page");
        Utils.RenderHTML("templates/index.html", writer);
    }

    public static void RenderAbout(PrintWriter writer) {
        System.out.println("Rendering About Page");
        Utils.RenderHTML("templates/about.html", writer);
    }
}