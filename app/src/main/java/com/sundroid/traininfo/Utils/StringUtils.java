package com.sundroid.traininfo.Utils;

/**
 * Created by sunil on 19-02-2017.
 */

public class StringUtils {

    public static final String CC="CC";
    public static final String FC="FC";
    public static final String _1A="1A";
    public static final String SL="SL";
    public static final String _2S="2S";
    public static final String _3A="3A";
    public static final String _2A="2A";
    public static final String _3E="3E";



    public static final String SUN="SUN";
    public static final String MON="MON";
    public static final String TUE="TUE";
    public static final String WED="WED";
    public static final String THU="THU";
    public static final String FRI="FRI";
    public static final String SAT="SAT";


    public static String getSeatClassCODE(String seat_class){
        switch (seat_class){
            case "Sleeper Class(SL)":
                return "SL";
            case "First AC(1A)":
                return "1A";
            case "Second AC(2A)":
                return "2A";
            case "Third AC(3A)":
                return "3A";
            case "AC Chair Car(CC)":
                return "CC";
            case "First Class(FC)":
                return "FC";
            case "Second Seating(2S)":
                return "2S";
            case "3 AC Economy(3E)":
                return "3 E - AC 3 Tier Economy";
            default:
                return "SL";
        }
    }


    public static String getQuotaCODE(String Quota){
        switch (Quota){
            case "General Quota(GN)":
                return "GN";
            case "Reservation Against Cancellation(RC/(RAC))":
                return "RC(RAC)";
            case "Ladies Quota(LD)":
                return "\tLD";
            case "Head Quarters/High Official Quota(HO)":
                return "HO";
            case "Defence Quota(DF)":
                return "DF";
            case "Parliament House Quota(PH)":
                return "PH";
            case "Foreign Tourist Quota(FT)":
                return "FT";
            case "Duty Pass Quota(DP)":
                return "DP";
            case "Tatkal Quota(TQ)":
                return "TQ";
            case "Premium Tatkal Quota(PT)":
                return "PT";
            case "Female(above 45 Year)/Senior Citizen/Travelling alone(SS)":
                return "SS";
            case "Physically Handicapped Quota(HP)":
                return "HP";
            case "Railway Employee Staff on Duty for the train(RE)":
                return "RE";
            case "General Quota Road Side(GNRS)":
                return "GNRS";
            case "Out Station(OS)":
                return "OS";
            case "Pooled Quota(PQ)":
                return "PQ";
            case "Road Side(RS)":
                return "RS";
            case "Yuva(YU)":
                return "YU";
            case "Lower Berth(LB)":
                return "LB";
            default:
                return "GN";
        }
    }

}
