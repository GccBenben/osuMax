import java.io.*;

public class ReplayReader {
    private String replay_name;
    private String player;

    public ReplayReader(String add, String name)
    {
        player = name;
        loadHeader(add);
    }

    private void loadHeader(String add)
    {
        try {
            InputStream out = new FileInputStream(new File(add));
            String mod = modFinder(out.read());
            int version = out.read(new byte[4]);

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String modFinder(int mod)
    {
        switch (mod)
        {
            case 0:
                return "STD";
            case 1:
                return "TaiKo";
            case 2:
                return "CTB";
            case 3:
                return "mania";
        }
        return "error!";
    }
}
