package ser.pipi.piball.asserts;

/**
 * Created by ser on 16.03.18.
 */

public class NamesCountry {
    final static int NUM_COUNTRY = FlagList.NUM_COUNTRY;

    final static  String[] name = {
            "Poland" , "Colombia", "Senegal", "Japan",
            "Belgium" , "England", "Tunisia", "Panama",
            "Germany", "Mexico" , "Sweden",  "Korea",
            "Brazil" , "Switzerland", "Costa-Rica", "Serbia",
            "Argentina" , "Croatia", "Iceland", "Nigeria",
            "France","Denmark" ,  "Peru", "Australia",
            "Portugal" , "Spain", "Iran", "Morocco",
            "Russia", "Uruguay", "Egypt", "Saudi\nArabia",};

    final static String[] nameShort = {
            "Pol" , "Col", "Sen", "Japan",
            "Belg" , "Eng", "Tun", "Pan",
            "Germ", "Mex" , "Swed",  "Korea",
            "Brazil" , "Switz", "C.-R.", "Serb",
            "Arg" , "Croat", "Ice", "Nig",
            "Fran","Den" ,  "Peru", "Aust",
            "Port" , "Spain", "Iran", "Mor",
            "Rus", "Uru", "Egy", "S.A.",
    };

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
