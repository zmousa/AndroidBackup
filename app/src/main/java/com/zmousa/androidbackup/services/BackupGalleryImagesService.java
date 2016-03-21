package com.zmousa.androidbackup.services;

import android.app.IntentService;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.os.Handler;

import com.zmousa.androidbackup.util.BackupDataHandler;
import com.zmousa.androidbackup.util.Constants;
import com.zmousa.androidbackup.util.Utilities;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class BackupGalleryImagesService extends IntentService {
	private Handler mHandler;
	private String imageType=".png";
	private int scaleValue=100;
	private int qualityValue=100;

	public BackupGalleryImagesService()
	{
		super("BackupGalleryImagesService");
		mHandler = new Handler();
	}

	@Override
	protected void onHandleIntent(Intent intent) {
		try {
			ArrayList<String> galleryImagesArrayList = BackupDataHandler.getGalleryImages(this);
			for (int i = 0; i < galleryImagesArrayList.size(); i++) {
				if (!galleryImagesArrayList.get(i).contains(Constants.APP_FOLDER_NAME) && !galleryImagesArrayList.get(i).contains(Constants.FOLDER_GALLERY_IMAGES)) {
					String filename = galleryImagesArrayList.get(i).substring(galleryImagesArrayList.get(i).lastIndexOf("/") + 1);
					File image = new File(Utilities.getDataFilePathWithFolder(Constants.APP_FOLDER_NAME, filename));
					if (!image.exists()) {
						try {
							FileOutputStream out = new FileOutputStream(image);
							Bitmap bitmapImageFile = rotateBitmap(BitmapFactory.decodeFile(galleryImagesArrayList.get(i)), getCameraPhotoOrientation(galleryImagesArrayList.get(i)));
							int width = bitmapImageFile.getWidth();
							int height = bitmapImageFile.getHeight();
							float percentage = (scaleValue / 100f);
							width = (int) (width * percentage);
							height = (int) (height * percentage);
							Bitmap bitmapImage = Bitmap.createScaledBitmap(bitmapImageFile, width, height, false);
							bitmapImage.compress(Bitmap.CompressFormat.JPEG, qualityValue, out);
							out.flush();
							out.close();
							bitmapImage.recycle();
							if (bitmapImage.isRecycled())
								bitmapImage = null;
							bitmapImageFile.recycle();
							if (bitmapImageFile.isRecycled())
								bitmapImageFile = null;
						} catch (FileNotFoundException e) {
							mHandler.post(new Utilities.DisplayToastInRunnable("Unable to backup " + filename));
							e.printStackTrace();
						} catch (IOException e) {
							mHandler.post(new Utilities.DisplayToastInRunnable("Unable to backup " + filename));
							e.printStackTrace();
						}
						catch (NullPointerException e)
						{
							mHandler.post(new Utilities.DisplayToastInRunnable("Unable to backup " + filename));
							e.printStackTrace();
						}
					}
				}
			}
			mHandler.post(new Utilities.DisplayToastInRunnable(Constants.Messages.ENDED_GALLERY_IMAGES_BACKUP));
		} catch (Exception e) {
			mHandler.post(new Utilities.DisplayToastInRunnable(Constants.Messages.FAILED_GALLERY_IMAGES_BACKUP));
		}
	}

	private int getCameraPhotoOrientation(String imagePath){
		int rotate = 0;
		try {
			ExifInterface exif = new ExifInterface(imagePath);
			int orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL);

			switch (orientation) {
				case ExifInterface.ORIENTATION_ROTATE_270:
					rotate = 270;
					break;
				case ExifInterface.ORIENTATION_ROTATE_180:
					rotate = 180;
					break;
				case ExifInterface.ORIENTATION_ROTATE_90:
					rotate = 90;
					break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rotate;
	}

	public Bitmap rotateBitmap(Bitmap source, float angle) {
		Matrix matrix = new Matrix();
		matrix.postRotate(angle);
		return Bitmap.createBitmap(source, 0, 0, source.getWidth(), source.getHeight(), matrix, true);
	}
}
