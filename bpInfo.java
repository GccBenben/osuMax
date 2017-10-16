public class bpInfo extends beatMapInfo{

    private double PP_value;
    private String[] MOD;

    public bpInfo(beatMapInfo map)
    {
        this.AR    = map.aprochRate();
        this.CS    = map.circleSize();
        this.HP    = map.hp();
        this.BPM   = map.speed();
        this.star  = map.diffcult();
        this.OD    = map.od();
        this.length= map.drainTime();
    }

    public void addMod(String mod)
    {
        //this.MOD[0] = mod;
        System.out.println("In BP info, mod is " + mod);
        Out();
    }
}
