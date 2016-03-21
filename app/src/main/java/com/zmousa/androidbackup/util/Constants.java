package com.zmousa.androidbackup.util;


public class Constants {
    public static String XML_SERIALIZER_FEATURE = "http://xmlpull.org/v1/doc/features.html#indent-output";
    public static String XML_SERIALIZER_OUTPUT = "UTF-8";
    public static String XML_SERIALIZER_ROOT = "root";
    public static String APP_FOLDER_NAME = "com.backup.app";
    public static String FILE_CONTACTS = "Contacts.txt";
    public static String FILE_CALL_LOG = "CallLog.txt";
    public static String FOLDER_GALLERY_IMAGES = "Gallery Images";

    public static String CONTACT = "Contact";
    public static String NAME = "Name";
    public static String NUMBER = "Number";
    public static String EMAIL = "Email";
    public static String CALL_LOG = "CallLog";
    public static String CALL_TYPE = "CallType";
    public static String CALL_DURATION = "CallDuration";
    public static String CALL_DAY_TIME = "CallDayTime";

    public static String OUTGOING = "OUTGOING";
    public static String INCOMING = "INCOMING";
    public static String MISSED = "MISSED";
    public static String SECONDS = "Seconds";


    public static class Messages
    {
        public static String STARTED_CONTACTS_BACKUP = "Started contacts backup";
        public static String ENDED_CONTACTS_BACKUP = "Ended contacts backup";
        public static String FAILED_CONTACTS_BACKUP = "Failed contacts backup";

        public static String STARTED_CALL_LOG_BACKUP = "Started call log backup";
        public static String ENDED_CALL_LOG_BACKUP = "Ended call log backup";
        public static String FAILED_CALL_LOG_BACKUP = "Failed call log backup";

        public static String STARTED_GALLERY_IMAGES_BACKUP = "Started gallery images backup";
        public static String ENDED_GALLERY_IMAGES_BACKUP = "Ended gallery images backup";
        public static String FAILED_GALLERY_IMAGES_BACKUP = "Failed gallery images backup";
    }
}
