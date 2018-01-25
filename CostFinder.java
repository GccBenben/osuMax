import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.Scanner;
import java.io.File;
import java.util.List;

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
        Elements ppPlusInfo = doc.select("h2");
        for(Element test : ppPlusInfo)
        {
            if(test.text().contains("Your player is in another castle!"))
            {
                System.out.println("can not find this player");
                return;
            }
        }
        int aimJump = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-aim]").get(1).text()));
        int aimFlow = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-aim]").get(2).text()));
        int precision = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-aim]").get(3).text()));
        int speed = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-speed]").get(0).text()));
        int stamina = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-speed]").get(1).text()));
        int accuracy = Integer.parseInt(spider.getNumbers(doc.select("tr[class=perform-acc]").get(0).text()));

      /*  System.out.print(aimJump + " ");
        System.out.print(aimFlow + " ");
        System.out.print(precision + " ");
        System.out.print(speed + " ");
        System.out.print(stamina + " ");
        System.out.print(accuracy + " ");
        System.out.println(calCost(aimJump, aimFlow, speed, accuracy,stamina));
*/
        System.out.println("aim jump: " + aimJump + ", ");
        System.out.println("aim flow: " + aimFlow + ", ");
        System.out.println("precision: " + precision + ", ");
        System.out.println("speed: " + speed + ", ");
        System.out.println("stamina: " + stamina + ", ");
        System.out.println("accuracy: " + accuracy + ", ");
        System.out.println("cost is: " + calCost(aimJump, aimFlow, speed, accuracy,stamina));
    }

    private double calCost(double aimJump, double aimFlow, double speed, double accurancy,double stamina)
    {
        return Math.pow(aimJump/3000,0.8) * Math.pow(aimFlow/1500,0.6) + Math.pow(speed/2000,0.8) * Math.pow(stamina/2000,0.5) + accurancy/2250;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Plz input user name: ");
        String input = sc.nextLine();
        while (!input.equals("quit")) {
            if(!input.equals("") && !input.equals("update")) {
                System.out.println("start searching for " + input + ", plz wait.");
                System.out.print( input + " ");
                CostFinder cost = new CostFinder(input);
            }
            else if(input.equals("update")) {
                List<String> dataList = CSVUtils.importCsv(new File("C:/Users/Administrator/Desktop/replay/oclcPlayerList.csv"));
                List<String> playerName = new ArrayList<String>();
                for(int i = 0; i < dataList.size(); i++)
                {
                    System.out.println(dataList.get(i));
                }
            }
            System.out.println("Plz input user name: ");
            input = sc.nextLine();
        }
    }
}
