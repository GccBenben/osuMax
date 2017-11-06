public class CostFinder {
    public CostFinder(String userID)
    {
        spider ppPlus = new spider("baka");
        ppPlus.Get_Url(getUrl(userID));
    }

    private String getUrl(String id)
    {
        String target = toString().replaceAll(" ", "%20");
        return "https://syrin.me/pp+/u/" + target;
    }
}
