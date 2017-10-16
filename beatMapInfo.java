import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class beatMapInfo {
    protected double AR;
    protected double CS;
    protected double HP;
    protected double OD;
    protected double star;
    protected double BPM;
    protected double length;
    protected String songName;
    protected String url;

    public beatMapInfo()
    {

    }

    public beatMapInfo(String name,String link, double ar, double cs, double hp, double bpm, double len)
    {
            songName = name;
            url = link;
            AR = ar;
            CS = cs;
            HP = hp;
            BPM = bpm;
    }


    public beatMapInfo(String name, String bid)
    {
        songName = name;
        url = "https://osu.ppy.sh" + bid;
    }

    public void setInfo(double[] info, String bpm, String drain)
    {
        CS = info[0] / 14;
        AR = info[1] / 14;
        HP = info[2] / 14;
        star = info[3] / 14;
        OD = info[4] / 14;
        BPM = Double.parseDouble(bpm);
        String reg = "[^:()0-9]";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(drain);
        String[] drainTime = m.replaceAll("").split("\\(|:|\\)");
        length = Integer.parseInt(drainTime[2]) * 60 + Integer.parseInt(drainTime[3]);
    }

    public double circleSize()
    {
        return CS;
    }
    public double aprochRate()
    {
        return AR;
    }
    public double hp()
    {
        return HP;
    }
    public double diffcult()
    {
        return star;
    }
    public double speed()
    {
        return BPM;
    }
    public double od()
    {
        return OD;
    }
    public double drainTime()
    {
        return length;
    }

    public String link()
    {
        return url;
    }

    public String name() { return songName;}

    public void Out()
    {
        System.out.println(songName + "  AR:" + AR + "  CS:" + CS + "  OD:" + OD + "  BPM:" + BPM + "  star:" + star +
        "  HP" + HP + "  Length:" + length);
    }
}
