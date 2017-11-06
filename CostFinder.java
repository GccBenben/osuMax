import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.Scanner;

public class CostFinder {
    public CostFinder(String userID)
    {
        spider ppPlus = new spider("baka");
        findCost(ppPlus.Get_Url(getUrl(userID)));
    }

    private String getUrl(String id)
    {
        String target = id.replaceAll(" ", "%20");
        target = target.replaceAll("\\[" , "%5B");
        target = target.replaceAll("]" , "%5D");
        return "https://syrin.me/pp+/u/" + target;
    }

    private void findCost(Document doc)
    {
        //Elements ppPlusInfo = ;
        int aimJump = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-aim]").get(1).text()));
        int aimFlow = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-aim]").get(2).text()));
        int precision = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-aim]").get(3).text()));
        int speed = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-speed]").get(0).text()));
        int stamina = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-speed]").get(1).text()));
        int accuracy = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-acc]").get(0).text()));

        System.out.println("aim jump: " + aimJump);
        System.out.println("aim flow: " + aimFlow);
        System.out.println("precision: " + precision);
        System.out.println("speed: " + speed);
        System.out.println("stamina: " + stamina);
        System.out.println("accuracy: " + accuracy);
        System.out.println("cost is: " + calCost(aimJump, aimFlow, speed, accuracy));
    }

    private double calCost(double aimJump, double aimFlow, double speed, double accurancy)
    {
        return Math.pow(aimJump/3000,0.9) * Math.pow(aimFlow/1500,0.5) + Math.pow(speed/2000,1.25) + accurancy/2700;
    }
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Plz input user name: ");
        String input = input = sc.nextLine();;
        while (!input.equals("quit")) {
            CostFinder cost = new CostFinder(input);
            System.out.println("Plz input user name: ");
            input = sc.nextLine();
        }
    }
}
