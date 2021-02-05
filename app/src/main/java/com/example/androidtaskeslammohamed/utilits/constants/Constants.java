package com.example.androidtaskeslammohamed.utilits.constants;

public class Constants {

    //region api

    //region url
    public static final String BASE_URL_MAPS = "https://maps.googleapis.com/";
    //endregion


    //region route maps
    public static final String PLACES = "maps/api/place/findplacefromtext/json";
    //endregion

    //region param

    public static final String PARAM_INPUT_TYPE = "textquery";

    //endregion

    //endregion

    //region Permissions
    public static final int PERMISSION_REQUEST_CODE = 9001;
    public static final int GPS_REQUEST_CODE = 9003;

    public final static int PLAY_SERVICES_ERROR_CODE = 9002;
    //endregion


    //region firebase
    public static final String COLLECTION_NAME_SOURCE  ="Source";
    public static final String COLLECTION_NAME_DESTINATION  ="DestinationLocation";

    //endregion

}
