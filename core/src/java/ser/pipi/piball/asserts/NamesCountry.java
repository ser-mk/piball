package ser.pipi.piball.asserts;

/**
 * Created by ser on 16.03.18.
 */

public class NamesCountry {
    final static int NUM_COUNTRY = FlagList.NUM_COUNTRY;

    final static  String[] name = {"Russia", "Egypt", "Uruguay", "Saudi Arabia",
            "Portugal" , "Spain", "Iran", "Morocco",
            "Denmark" , "France", "Peru", "Australia",
            "Argentina" , "Croatia", "Iceland", "Nigeria",
            "Brazil" , "Switzerland", "Costa-Rica", "Serbia",
            "Mexico" , "Sweden", "Germany", "Korea",
            "Belgium" , "England", "Tunisia", "Panama",
            "Poland" , "Colombia", "Senegal", "Japan",};

    final static String[] nameShort = {"Rus", "Egypt", "Urug", "S.A.",
            "Port" , "Spain", "Iran", "Moroc",
            "Den" , "Fran", "Peru", "Austr",
            "Arg" , "Croa", "Ice", "Nig",
            "Braz" , "Swit", "C.R.", "Serb",
            "Mex" , "Swed", "Germ", "Korea",
            "Belg" , "Eng", "Tun", "Pan",
            "Pol" , "Colom", "Sen", "Japan",};

    static final String ERROR = "undefined";

    public static String getNameCountry(int number){
        if(number < 0 || number >= name.length){
            return ERROR;
        }
        return name[number];
    }

    public static String getNameShortCountry(int number){
        if(number < 0 || number >= name.length){
            return ERROR;
        }
        return nameShort[number];
    }

}
