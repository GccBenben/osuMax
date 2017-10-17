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
        return songName + "  AR:" + AR + "  CS:" + CS + "  OD:" + OD + "  BPM:" + BPM + "  star:" + star +
                "  HP" + HP + "  Length:" + length + " MOD: " + getMod() + "   PP: " + test;
    }

    public double getBPM()
    {
        String mod = getMod();
        if(mod.contains("none"))
            return BPM;
        else if(mod.contains("DT") || mod.contains("NC"))
            return BPM * 1.5 > 160 ? BPM * 1.5:BPM * 1.5 * 2;
        else
            return 0;
    }
    private String getMod()
    {
        String result ="";
        for(String s : MOD)
            result += s + " ";
        return result;
    }
}
