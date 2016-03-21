package com.zmousa.androidbackup.util;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.provider.MediaStore;

import com.zmousa.androidbackup.model.CallLog;
import com.zmousa.androidbackup.model.Contact;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class BackupDataHandler {
    public static ArrayList<Contact> readPhoneContacts(Context context) {
        ArrayList<Contact> contactArrayList = new ArrayList<>();
        try {
            ContentResolver cr = context.getContentResolver();
            Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
            HashMap<String,String> contactEmails=new HashMap<>();
            contactEmails=readContactsEmail(context);
            Contact contact;
            while (phones.moveToNext()) {
                String contactId = phones
                        .getString(phones
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone._ID));
                String contactName = phones
                        .getString(phones
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                String contactNumber = phones
                        .getString(phones
                                .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                String contactEmail = contactEmails.get(contactName);
                contact = new Contact(contactNumber, contactName, contactEmail);
                contactArrayList.add(contact);
            }
            phones.close();
            return contactArrayList;
        } catch (Exception e) {
            return contactArrayList;
        }
    }

    private static HashMap<String,String> readContactsEmail(Context context) {
        HashMap<String,String> contactArrayList = new HashMap<>();
        try {
            ContentResolver cr = context.getContentResolver();
            Cursor phones = cr.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, ContactsContract.Contacts.DISPLAY_NAME);
            while (phones.moveToNext()) {
                String contactId = phones
                        .getString(phones
                                .getColumnIndex(ContactsContract.Contacts._ID));
                String contactName = phones
                        .getString(phones
                                .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                Cursor emailCur = context.getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?", new String[]{contactId}, null);
                String contactEmail = "";
                while (emailCur.moveToNext()) {
                    contactEmail = emailCur.getString(emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                }
                emailCur.close();
                contactArrayList.put(contactName, contactEmail);
            }
            phones.close();
            return contactArrayList;
        } catch (Exception e) {
            return contactArrayList;
        }
    }

    public static ArrayList<CallLog> getCallDetails(Context context) {
        ArrayList<CallLog> callLogsArrayList = new ArrayList<>();
        Cursor cursor = context.getContentResolver().query(android.provider.CallLog.Calls.CONTENT_URI,
                null, null, null, android.provider.CallLog.Calls.DATE + " DESC");
        int name = cursor.getColumnIndex(android.provider.CallLog.Calls.CACHED_NAME);
        int number = cursor.getColumnIndex(android.provider.CallLog.Calls.NUMBER);
        int type = cursor.getColumnIndex(android.provider.CallLog.Calls.TYPE);
        int date = cursor.getColumnIndex(android.provider.CallLog.Calls.DATE);
        int duration = cursor.getColumnIndex(android.provider.CallLog.Calls.DURATION);
        CallLog callLog;
        while (cursor.moveToNext()) {
            String nameCall= Utilities.checkNullString(cursor.getString(name));
            String phNumber = Utilities.checkNullString(cursor.getString(number));
            String callType = Utilities.checkNullString(cursor.getString(type));
            String callDate = Utilities.checkNullString(cursor.getString(date));
            Date callDayTime = new Date(Long.valueOf(callDate));
            String callDuration = cursor.getString(duration);
            String dir = "";
            int dircode = Integer.parseInt(callType);
            switch (dircode) {
                case android.provider.CallLog.Calls.OUTGOING_TYPE:
                    dir = Constants.OUTGOING;
                    break;
                case android.provider.CallLog.Calls.INCOMING_TYPE:
                    dir = Constants.INCOMING;
                    break;

                case android.provider.CallLog.Calls.MISSED_TYPE:
                    dir = Constants.MISSED;
                    break;
            }
            SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
            callLog=new CallLog(nameCall, phNumber, dir, callDuration + " " + Constants.SECONDS,sdf.format(callDayTime));
            callLogsArrayList.add(callLog);
        }
        cursor.close();
        return callLogsArrayList;
    }

    public static ArrayList<String> getGalleryImages(Context context) {
        ArrayList<String> galleryImagesArrayList = new ArrayList<>();
        final String[] columns = { MediaStore.Images.Media.DATA, MediaStore.Images.Media._ID, MediaStore.Images.Media.DATE_ADDED };
        final String orderBy = MediaStore.Images.Media.DATE_ADDED;
        ContentResolver cr = context.getContentResolver();

        // External Media
        Cursor imageCursor = cr.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");
        int dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
        while (imageCursor.moveToNext()) {
            galleryImagesArrayList.add(imageCursor.getString(dataColumnIndex));
        }

        // Internal Media
        imageCursor = cr.query(MediaStore.Images.Media.INTERNAL_CONTENT_URI, columns, null, null, orderBy + " DESC");

        dataColumnIndex = imageCursor.getColumnIndex(MediaStore.Images.Media.DATA);
        while (imageCursor.moveToNext()) {
            galleryImagesArrayList.add(imageCursor.getString(dataColumnIndex));
        }
        return galleryImagesArrayList;
    }
}
