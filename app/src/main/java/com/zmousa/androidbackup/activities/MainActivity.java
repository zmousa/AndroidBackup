package com.zmousa.androidbackup.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;

import com.zmousa.androidbackup.R;
import com.zmousa.androidbackup.services.BackupCallLogService;
import com.zmousa.androidbackup.services.BackupContactsService;
import com.zmousa.androidbackup.services.BackupGalleryImagesService;
import com.zmousa.androidbackup.util.Constants;
import com.zmousa.androidbackup.util.Utilities;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btnBackupContacts_Click(View v)
    {
        Utilities.toast(Constants.Messages.STARTED_CONTACTS_BACKUP);
        Utilities.callService(BackupContactsService.class);
    }

    public void btnBackupCallLog_Click(View v)
    {
        Utilities.toast(Constants.Messages.STARTED_CALL_LOG_BACKUP);
        Utilities.callService(BackupCallLogService.class);
    }

    public void btnBackupGalleryImages_Click(View v)
    {
        Utilities.toast(Constants.Messages.STARTED_GALLERY_IMAGES_BACKUP);
        Utilities.callService(BackupGalleryImagesService.class);
    }
}
