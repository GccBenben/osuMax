import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.util.List;
import java.util.HashMap;
import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.io.IOException;

public class spider {
    private String targetUrl;
    private static String rankingPageBase = "https://osu.ppy.sh/p/pp/?m=0&s=3&o=1&f=0&page=";
    private List<String> rankingUrls;
    private HashMap<Integer, UserInfo> userList;
    private HashMap<String, beatMapInfo> beatMapDB;

    public spider(String url)
    {
        targetUrl = url;
        createUserDatebase();
        createBeatMapDatebase();
        enumRankingPageUrl();
        getUsersLink();

        //Document contents = Get_Url(targetUrl);
        //getGenerelInfo(contents, user);
    }
    public Document Get_Url(String url)
    {
        try
        {
            Document doc = Jsoup.connect(url)
                    .ignoreContentType(true)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    //.post()
                    .get();

            return doc;
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    private void createUserDatebase()
    {
        userList = new HashMap<Integer,UserInfo>();
    }

    private void createBeatMapDatebase(){ beatMapDB = new HashMap<String,beatMapInfo>();}

    private void getUsersLink()
    {
        for(int i = 0; i < 1; i++)
        {
            Document rankingPage = Get_Url(rankingUrls.get(i));
            getRankingInfo(rankingPage,i);
            //System.out.println(rankingPage.outerHtml());
        }
    }

    private void enumRankingPageUrl()
    {
        rankingUrls = new LinkedList<String>();

        for(int i = 1; i < 201; i ++)
        {
            rankingUrls.add(rankingPageBase + i);
            //System.out.println(rankingUrls.get(i-1));
        }
    }

    private void getRankingInfo(Document doc, int page)
    {
        UserInfo user;
        Elements a = doc.select("a[href~=/u/[1-9]\\d*]");
        //Element user_ID = doc.getElementsByAttributeValue("href","/u/124493").first();
        //String test = user_ID.outerHtml();
        int rank = page * 50 + 1;
        for(Element as : a)
        {
            String linkUrl = as.attr("href");
            String userId = as.text();
            String reg = "[^0-9]";
            Pattern p = Pattern.compile(reg);
            Matcher m = p.matcher(linkUrl);
            int uid = Integer.parseInt(m.replaceAll("").trim());
            user = new UserInfo(userId, uid);
            getGenerelInfo(user);
            getBpInfo(user);
            user.setRank(rank);
            userList.put(rank,user);
            user.outInfo();
            rank++;
            return;
        }
    }

    private String getNumbers(String input)
    {
        String reg = "[^.0-9]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(input);
        return m.replaceAll("").trim();
    }

    private void getBpInfo(UserInfo user)
    {
        String userBpUrl_1 = user.getBpUrl_1();
        String userBpUrl_2 = user.getBpUrl_2();
        int count = 0;

        try {
            Document doc = Jsoup.connect(userBpUrl_1)
                    .ignoreContentType(true)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    //.post()
                    .get();

            Elements bpInfo = doc.select("b");
            for(Element bp : bpInfo)
            {
                //System.out.println(bp.outerHtml());
                String text = bp.text();
                if(text.equals("Show me more!"))
                    break;

                if(text.contains("[")) {
                    //System.out.println(text);
                    String bid = bp.select("a[href~=/b]").attr("href");
                    System.out.println(bid);
                    String bpName = bp.select("a[href~=/b]").text();
                    System.out.println("name is : " + bpName);
                    String mod = "";
                    if(text.contains("HR"))
                    {
                        mod += "HR,";
                    }
                    if(text.contains("DT"))
                    {
                        mod += "DT,";
                    }
                    if(text.contains("HD"))
                    {
                        mod += "HD,";
                    }
                    if(text.contains("FL"))
                    {
                        mod += "FL,";
                    }
                    if(text.contains("EZ"))
                    {
                        mod += "EZ,";
                    }
                    if(text.contains("NC"))
                    {
                        mod += "NC,";
                    }
                    if(text.contains("HT"))
                    {
                        mod += "HT,";
                    }
                    if(mod == "")
                        mod = "none";
                    System.out.println("mod is:" + mod);

                    if(!beatMapDB.containsKey(bid))
                    {
                        beatMapInfo map = new beatMapInfo(bpName, bid);
                        getSongInfo(map);
                        map.Out();
                        user.addBP(map,count, mod);
                        beatMapDB.put(bid,map);
                    }
                    else
                    {
                        user.addBP(beatMapDB.get(bid), count, mod);
                        System.out.println("database have this map!");
                        beatMapDB.get(bid).Out();
                    }
                    count++;
                }
                else if(text.contains("pp")) {
                    System.out.println("this is pp value: " + text);
                }
            }
/*
            Elements bpInfo = doc.select("a[href~=/b]");
            for(Element bp : bpInfo)
            {
                String bpName = bp.text();
                String bid = bp.attr("href");
                //System.out.println(bpName + "   " + bpLink);
                if(!beatMapDB.containsKey(bid))
                {
                    beatMapInfo map = new beatMapInfo(bpName, bid);
                    getSongInfo(map);
                    map.Out();
                    user.addBP(map,count);
                    beatMapDB.put(bid,map);
                }
                else
                {
                    user.addBP(beatMapDB.get(bid), count);
                    System.out.println("database have this map!");
                    beatMapDB.get(bid).Out();
                }
                count++;
            }*/
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }

        try {
            Document doc = Jsoup.connect(userBpUrl_2)
                    .ignoreContentType(true)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    //.post()
                    .get();

            Elements bpInfo = doc.select("b");
            for (Element bp : bpInfo) {
                //System.out.println(bp.outerHtml());
                String text = bp.text();
                if (text.equals("Show me more!"))
                    break;

                if (text.contains("[")) {
                    //System.out.println(text);
                    String bid = bp.select("a[href~=/b]").attr("href");
                    System.out.println(bid);
                    String bpName = bp.select("a[href~=/b]").text();
                    System.out.println("name is : " + bpName);
                    String mod = "";
                    if (text.contains("HR")) {
                        mod += "HR,";
                    }
                    if (text.contains("DT")) {
                        mod += "DT,";
                    }
                    if (text.contains("HD")) {
                        mod += "HD,";
                    }
                    if (text.contains("FL")) {
                        mod += "FL,";
                    }
                    if (text.contains("EZ")) {
                        mod += "EZ,";
                    }
                    if (text.contains("NC")) {
                        mod += "NC,";
                    }
                    if (text.contains("HT")) {
                        mod += "HT,";
                    }
                    if (mod == "")
                        mod = "none";
                    System.out.println("mod is:" + mod);

                    if (!beatMapDB.containsKey(bid)) {
                        beatMapInfo map = new beatMapInfo(bpName, bid);
                        getSongInfo(map);
                        map.Out();
                        user.addBP(map, count, mod);
                        beatMapDB.put(bid, map);
                    } else {
                        user.addBP(beatMapDB.get(bid), count, mod);
                        System.out.println("database have this map!");
                        beatMapDB.get(bid).Out();
                    }
                    count++;
                } else if (text.contains("pp")) {
                    System.out.println("this is pp value: " + text);
                }
            }
        }
        /*try {
            Document doc = Jsoup.connect(userBpUrl_2)
                    .ignoreContentType(true)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    //.post()
                    .get();

            Elements bpInfo = doc.select("a[href~=/b]");
            for(Element bp : bpInfo)
            {
                String bpName = bp.text();
                String bid = bp.attr("href");
                //System.out.println(bpName + "   " + bpLink);
                if(!beatMapDB.containsKey(bid))
                {
                    beatMapInfo map = new beatMapInfo(bpName, bid);
                    getSongInfo(map);
                    map.Out();
                    user.addBP(map,count);
                    beatMapDB.put(bid,map);
                }
                else
                    user.addBP(beatMapDB.get(bid),count);
                count++;
            }
        }*/
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }

    private void getSongInfo(beatMapInfo map)
    {
        try {
            Document doc = Jsoup.connect(map.link())
                    .ignoreContentType(true)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    //.post()
                    .get();

            Elements bpInfo = doc.select("div.active");
            //Element bpInfo = doc.select("table[id]").first();
            //Elements trs = bpInfo.select("tr");
            double[] info = new double[5];
            int count = 0;
            for(Element tr : bpInfo)
            {
                String text = tr.attr("style");
                info[count] = Double.parseDouble(getNumbers(text));
                //System.out.println(info[count]);
                count++;
            }
            String bpm;
            String drain;
            Elements info2 = doc.select("td[class=colour]");
            drain = info2.get(8).text();
            bpm = info2.get(11).text();
            map.setInfo(info, bpm, drain);

        }
        catch(IOException e)
        {
            e.printStackTrace();
            System.out.println(map.name());
        }
    }

    private void getGenerelInfo(UserInfo user)
    {
        String userGenerelUrl = user.getInfoUrl();
        try
        {
            Document doc = Jsoup.connect(userGenerelUrl)
                    .ignoreContentType(true)
                    .data("query", "Java")
                    .userAgent("Mozilla")
                    .cookie("auth", "token")
                    .timeout(3000)
                    //.post()
                    .get();

            Elements generel_Info = doc.select("div.profileStatLine");
            for (Element info : generel_Info)
            {
                String linkText = info.text();
                //System.out.println(linkText);
                String regGen = "[^#.0-9]";
                Pattern p = Pattern.compile(regGen);
                Matcher m = p.matcher(linkText);
                String infor = m.replaceAll("").trim();
                if(linkText.contains("Performance:"))
                {
                    //System.out.println(infor.split("#")[0]);
                    user.setPP(Integer.parseInt(infor.split("#")[0]));
                    user.setRank(Integer.parseInt(infor.split("#")[1]));
                    user.setLRank(Integer.parseInt(infor.split("#")[2]));
                }
                else if(linkText.contains("Ranked Score"))
                {
                    //System.out.println(infor);
                    user.setRS(Long.parseLong(infor));
                }
                else if(linkText.contains("Accuracy"))
                {
                    //System.out.println(infor);
                    user.setACC(Double.parseDouble(infor));
                }
                else if(linkText.contains("Count"))
                {
                    //System.out.println(infor);
                    user.setPC(Integer.parseInt(infor));
                }
                else if(linkText.contains("Time"))
                {
                    //System.out.println(infor);
                    user.setPT(Integer.parseInt(infor));
                }
                else if(linkText.contains("Total Score"))
                {
                    //System.out.println(infor);
                    user.setTS(Long.parseLong(infor));
                }
                else if(linkText.contains("Level"))
                {
                    //System.out.println(infor);
                    user.setLv(Integer.parseInt(infor));
                }
                else if(linkText.contains("Hits"))
                {
                    //System.out.println(infor);
                    user.setTTH(Long.parseLong(infor));
                }
                else if(linkText.contains("Combo"))
                {
                    //System.out.println(infor);
                    user.setMaxCombo(Integer.parseInt(infor));
                }
                else if(linkText.contains("Kudosu"))
                {
                    //System.out.println(infor);
                    user.setKudosu(Integer.parseInt(infor));
                }
                else if(linkText.contains("Replays"))
                {
                    //System.out.println(infor);
                    user.setReplays(Integer.parseInt(infor));
                }
            }

        }
        catch(IOException e)
        {
            e.printStackTrace();
        }


    }

    public static void main(String[] args)
    {
        String url = "https://osu.ppy.sh/p/pp/?m=0&s=3&o=1&f=0&page=2033345";
        spider spider = new spider(url);

/*
        List<String> dataList=new ArrayList<String>();
        dataList.add("1,张三,男");
        dataList.add("2,李四,男");
        dataList.add("3,小红,女");
        boolean isSuccess=CSVUtils.exportCsv(new File("E:/python/ljq.csv"), dataList);*/
    }
}