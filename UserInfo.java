import java.beans.beancontext.BeanContext;
import java.io.IOException;

public class UserInfo {
    private String userId;
    private String pageLink;
    private String infoUrl;
    private String bpUrl_1;
    private String bpUrl_2;
    private int performance;
    private long rankedScore;
    private long totalScore;
    private long TTH;
    private double ACC;
    private int PC;
    private int PT;
    private int lvl;
    private int Ranks;
    private int localRanks;
    private int maxCombo;
    private int kudosu;
    private int replays;
    private int UID;
    private bpInfo[] bp = new bpInfo[100];
    int[] bpm_classify = new int[8];
    int[] ar_classify  = new int[5];
    int[] mod_classify = new int[9];

    public void outInfo()
    {
        String info = userId + "  Rank: " + Ranks + "  LocalRank: " + localRanks + "  " + performance + "pp" +
                "  RankedScore: " + rankedScore + "  ACC: " + ACC + "  PC:" + PC + "  PT: " + PT + "  TotalScore:"
                + totalScore + "  LV:" + lvl + "  TTH: " + TTH; // + "     " +pageLink + "   " + infoUrl + "   " + bpUrl_1 + "   " + bpUrl_2;
        System.out.println(info);
    }

    public UserInfo(String ID, int UID_value)
    {
        userId = ID;
        UID = UID_value;
        setLink();
    }

    public void addBP(bpInfo newBP, int count)
    {
        bp[count] = newBP;
        //System.out.println(newBP.out());
    }

    public void setID(String ID)
    {
        userId = ID;
    }

    public void setPP(int value)
    {
        performance = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setRS(long value)
    {
        rankedScore = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setTS(long value)
    {
        totalScore = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setACC(double value)
    {
        ACC = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setPC(int value)
    {
        PC = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setPT(int value)
    {
        PT = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setTTH(long value)
    {
        TTH = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setRank(int value)
    {
        Ranks = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setLRank(int value)
    {
        localRanks = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setLv(int value)
    {
        lvl = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setMaxCombo(int value)
    {
        maxCombo = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setKudosu(int value)
    {
        kudosu = value;
        //System.out.println("value is " + value + "!!!");
    }

    public void setReplays(int value)
    {
        replays = value;
        //System.out.println("value is " + value + "!!!");
    }

    public String getInfoUrl()
    {
        return infoUrl;
    }

    public String getBpUrl_1(){ return bpUrl_1;}
    public String getBpUrl_2(){ return bpUrl_2;}

    public String out_put_info()
    {
        String result = userId + "," + rankedScore + "," + performance + "," + ACC + "," + PC + "," + PT + "," +
                totalScore + "," + TTH + "," + maxCombo + "," + replays + "," + mod_classify[0] + "," +
                mod_classify[1] + "," + mod_classify[2] + "," + mod_classify[3] + "," + mod_classify[4] + "," +
                mod_classify[5] + "," + mod_classify[6] + ","+ mod_classify[7] + "," + mod_classify[8]+ "," + ar_classify[0] + "," +
                ar_classify[1] + "," + ar_classify[2] + "," + ar_classify[3] + "," + ar_classify[4] + "," +
                bpm_classify[0] + "," +bpm_classify[1] + "," + bpm_classify[2] + "," + bpm_classify[3] + "," +
                bpm_classify[4] + "," + bpm_classify[5] + "," + bpm_classify[6] + "," + bpm_classify[7] + ",";
        return result;
    }

    public void anayliseBP()
    {
        //System.out.println(bp.length);
        for(bpInfo theBP : bp)
        {
            //System.out.println(theBP.name());
            bpm_anaylise(theBP, bpm_classify);
            ar_anaylise(theBP, ar_classify);
            mod_anaylise(theBP, mod_classify);
            //System.out.print("bpm is " + theBP.getBPM());
        }
        System.out.println("180-: " + bpm_classify[0] +"  200-: " + bpm_classify[1] +"  220-: " + bpm_classify[2] +"  240-: " + bpm_classify[3]
                +"  260-: " + bpm_classify[4] +"  280-: " + bpm_classify[5] +"  300-: " + bpm_classify[6] +"  300+: " + bpm_classify[7]);
        System.out.println("9.2-: " + ar_classify[0] +"  9.6-: " + ar_classify[1] +"  10-: " + ar_classify[2] +"  10.4-: " + ar_classify[3]
                +"  10.4+: " + ar_classify[4]);
        System.out.println("none: " + mod_classify[0] +"  HD: " + mod_classify[1] +"  HR: " + mod_classify[2] +"  HDHR: " + mod_classify[3]
                +"  DT/NC: " + mod_classify[4] +"  HDDT/HDNC: " + mod_classify[5] +"  HDHRDT/NC: " + mod_classify[6]);
    }

    private void mod_anaylise(bpInfo bp, int[] classify)
    {
        String mod = bp.getMod();
        if(mod.contains("EZ"))
            classify[7]++;
        else if((mod.contains("DT") || mod.contains("NC")) && mod.contains("HR"))
            classify[6]++;
        else if((mod.contains("DT") || mod.contains("NC")) && mod.contains("HD"))
            classify[5]++;
        else if(mod.contains("DT") || mod.contains("NC"))
            classify[4]++;
        else if(mod.contains("HR") && mod.contains("HD"))
            classify[3]++;
        else if(mod.contains("HR"))
            classify[2]++;
        else if(mod.contains("HD"))
            classify[1]++;
        else if(mod.contains("SO") || mod.contains("NF"))
            classify[8]++;
        else
            classify[0]++;
    }
    private void ar_anaylise(bpInfo bp, int[] classify)
    {
        double ar = bp.getAR();
        if(ar < 9.2)
            classify[0]++;
        else if(ar < 9.6)
            classify[1]++;
        else if(ar < 10)
            classify[2]++;
        else if(ar < 10.4)
            classify[3]++;
        else
            classify[4]++;
    }
    private void bpm_anaylise(bpInfo bp, int[] classify)
    {
        double bpm = bp.getBPM();
        //System.out.println("bpm is " + bpm);
        if(bpm > 240)
        {
            if(bpm> 280)
            {
                if(bpm > 300)
                    classify[7]++;
                    //System.out.println("   300+");
                else
                    classify[6]++;
                //System.out.println("   280-300");
            }
            else
            {
                if(bpm > 260)
                    classify[5]++;
                    //System.out.println("   280-280");
                else
                    classify[4]++;
                //System.out.println("  240-260");
            }
        }
        else
        {
            if(bpm > 200)
            {
                if(bpm > 220)
                    classify[3]++;
                    //System.out.println("  220-240");
                else
                    classify[2]++;
                //System.out.println("  200-220");
            }
            else
            {
                if(bpm > 180)
                    classify[1]++;
                    //System.out.println("  180-200");
                else
                    classify[0]++;
                //System.out.println("  180-");

            }
        }
    }
    private void setLink()
    {
        pageLink = "https://osu.ppy.sh/u/" + UID;
        setGenerelInfoUrl();
    }

    private void setGenerelInfoUrl()
    {
        infoUrl = "https://osu.ppy.sh/pages/include/profile-general.php?u=" + UID + "&m=0";
        bpUrl_1 = "https://osu.ppy.sh/pages/include/profile-leader.php?u=" + UID + "&m=0";
        bpUrl_2 = "https://osu.ppy.sh/pages/include/profile-leader.php?u=" + UID + "&m=0&pp=1";
    }
}
