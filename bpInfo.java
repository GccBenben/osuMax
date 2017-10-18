public class bpInfo extends beatMapInfo{

    private double PP_value;
    private String[] MOD;
    private String test;

    public bpInfo()
    {
        //System.out.println("BAKA error!");
    }

    public bpInfo(beatMapInfo map, String mod)
    {
        this.AR    = map.AR;
        this.CS    = map.CS;
        this.HP    = map.HP;
        this.BPM   = map.BPM;
        this.star  = map.star;
        this.OD    = map.OD;
        this.length= map.length;
        this.songName = map.songName;
        this.addMod(mod);
    }

    public void addMod(String mod)
    {
        //this.MOD[0] = mod;
        //System.out.println("In BP info, mod is " + mod);
        this.MOD = mod.split(",");
    }

    public void addPP(String pp_value)
    {
        test = pp_value;
    }

    public String out()
    {
        return songName + "  AR:" + AR + "  real AR:" + getAR() + "  CS:" + CS + "  OD:" + OD +
                "  BPM:" + BPM + "  real BPM:" + getBPM() + "  star:" + star +
                "  HP" + HP + "  Length:" + length + " MOD: " + getMod() + "   PP: " + test;
    }

    public double getBPM()
    {
        String mod = getMod();
        //System.out.println("mod is " + mod);
        if(mod.contains("DT") || mod.contains("NC"))
            return BPM * 1.5 > 160 ? BPM * 1.5:BPM * 1.5 * 2;
        else if(mod.contains("HT"))
            return BPM * 0.75;
        else
            return BPM;
    }

    public double getAR()
    {
        String mod = getMod();
        //System.out.println("mod is " + mod);
        double ar = AR;
        if(mod.contains("HR"))
            ar = ar * 1.4 > 10 ? 10 : ar * 1.4;

        double ms = ms_cal(ar);
        //System.out.println("ms is " + ms);
        if(mod.contains("DT") || mod.contains("NC"))
            ms = ms * 2 / 3;
        if(mod.contains("HT"))
            ms = ms *  4 / 3;

        //System.out.println("ms is " + ms);
        return ar_cal(ms);
    }

    public String getMod()
    {
        String result ="";
        for(String s : MOD)
            result += s + " ";
        return result;
    }

    private double ms_cal(double ar)
    {
        return ar >= 5 ?  1200 - (ar - 5) * 150 : 1800 - ar * 120;
    }

    private double ar_cal(double ms)
    {
        return ms >= 1200 ? (1800 - ms) / 120 : (1200 - ms) / 150 + 5;
    }


}
