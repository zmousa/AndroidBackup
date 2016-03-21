package com.zmousa.androidbackup.services;

import android.app.IntentService;
import android.content.Intent;
import android.os.Handler;
import android.util.Xml;

import com.zmousa.androidbackup.model.Contact;
import com.zmousa.androidbackup.util.BackupDataHandler;
import com.zmousa.androidbackup.util.Constants;
import com.zmousa.androidbackup.util.Utilities;

import org.xmlpull.v1.XmlSerializer;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Date;

public class BackupContactsService extends IntentService {
	private Handler mHandler;
	private XmlSerializer serializer;
	private FileOutputStream fileOutputStream;

	public BackupContactsService()
	{
		super("BackupContactsService");
		mHandler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			String filename = Constants.FILE_CONTACTS.replace("$d$", new Date().getTime() + "");
			File file=new File(Utilities.getDataFilePathWithFolder(Constants.APP_FOLDER_NAME, filename));
			if(file.exists())
				file.delete();
			fileOutputStream = new FileOutputStream(file);

			serializer = Xml.newSerializer();
			serializer.setOutput(fileOutputStream, Constants.XML_SERIALIZER_OUTPUT);
			serializer.startDocument(null, Boolean.valueOf(true));
			serializer.setFeature(Constants.XML_SERIALIZER_FEATURE, true);

			serializer.startTag(null, Constants.XML_SERIALIZER_ROOT);

			ArrayList<Contact> contactArrayList = new ArrayList<>();

			contactArrayList = BackupDataHandler.readPhoneContacts(this);

			for (Contact contact:contactArrayList)
			{
				serializer.startTag(null, Constants.CONTACT);

				serializer.startTag(null, Constants.NAME);
				serializer.text(contact.getContactName());
				serializer.endTag(null, Constants.NAME);

				serializer.startTag(null, Constants.NUMBER);
				serializer.text(contact.getContactNumber());
				serializer.endTag(null, Constants.NUMBER);

				serializer.startTag(null, Constants.EMAIL);
				serializer.text(contact.getEmail());
				serializer.endTag(null, Constants.EMAIL);

				serializer.endTag(null, Constants.CONTACT);
			}

			serializer.endDocument();
			serializer.flush();
			mHandler.post(new Utilities.DisplayToastInRunnable(Constants.Messages.ENDED_CONTACTS_BACKUP));
		}
		catch (Exception e)
		{
			mHandler.post(new Utilities.DisplayToastInRunnable(Constants.Messages.FAILED_CONTACTS_BACKUP));
		}
	}
}
