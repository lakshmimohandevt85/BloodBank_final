package com.sdsu.cs646.lakshmi.bloodbank.util;

import com.sdsu.cs646.lakshmi.bloodbank.constants.Constant;

/**
 * Created by lakshmimohandev on 5/9/16.
 */
public class StringUtils
{
    /**
     *
     * @param blood_group
     * @return
     */

    public static String convertStringToSymbol(String blood_group)
    {

        String convertedValue = "";

        if (Constant.STR_A_POSITIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.SYMBOL_A_POSITIVE;
        }
        else if (Constant.STR_A_NEGATIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.SYMBOL_A_NEGATIVE;
        }
        else if (Constant.STR_B_POSITIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.SYMBOL_B_POSITIVE;
        }
        else if (Constant.STR_B_NEGATIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.SYMBOL_B_NEGATIVE;
        }
        else if (Constant.STR_AB_POSITIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.SYMBOL_AB_POSITIVE;
        }
        else if (Constant.STR_AB_NEGATIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.SYMBOL_AB_NEGATIVE;
        }
        else if (Constant.STR_O_POSITIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.SYMBOL_O_POSITIVE;
        }
        else if (Constant.STR_O_NEGATIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue =  Constant.SYMBOL_O_NEGATIVE;
        }

        return convertedValue;
    }

    /**
     *
     * @param blood_group
     * @return
     */
    public static String convertSymbolToString(String blood_group){

        String convertedValue = "";

        if (Constant.SYMBOL_A_POSITIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.STR_A_POSITIVE;
        }
        else if (Constant.SYMBOL_A_NEGATIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.STR_A_NEGATIVE;
        }
        else if (Constant.SYMBOL_B_POSITIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.STR_B_POSITIVE;
        }
        else if (Constant.SYMBOL_B_NEGATIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.STR_B_NEGATIVE;
        }
        else if (Constant.SYMBOL_AB_POSITIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.STR_AB_POSITIVE;
        }
        else if (Constant.SYMBOL_AB_NEGATIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.STR_AB_NEGATIVE;
        }
        else if (Constant.SYMBOL_O_POSITIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue = Constant.STR_O_POSITIVE;
        }
        else if (Constant.SYMBOL_O_NEGATIVE.equalsIgnoreCase(blood_group))
        {
            convertedValue =  Constant.STR_O_NEGATIVE;
        }

        return convertedValue;
    }

    /**
     * This method will validate the phone number and format the phone number;
     * @param phone
     * @return
     */
    public static String valiatePhoneNumber(String phone){

        boolean isNumeric = false;
        String formatedPhoneNumber =  null;

        // check whether phone number has dot
        if (phone.contains(".")){
            return formatedPhoneNumber;
        }
        // check whether phone number length
        if (phone.length() != 10){
            return formatedPhoneNumber;
        }

        // check whether phone number is numeric
        try
        {
            double d = Double.parseDouble(phone);
            isNumeric = true;
        }
        catch(NumberFormatException nfe)
        {
            isNumeric =  false;
            return formatedPhoneNumber;
        }

        if (isNumeric){
                // format the phone number.
                formatedPhoneNumber = "(" + phone.substring( 0,3 ) + ") " + phone.substring( 3,6 ) + "-" + phone.substring( 6,10 );

        }

        return formatedPhoneNumber;
    }
}





