package com.futurmap.contactsqlite.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.room.TypeConverter;

import java.io.ByteArrayOutputStream;

public class Converters {
    @TypeConverter
    public Bitmap toBitmap(byte[] outputStream) {
        return BitmapFactory.decodeByteArray(outputStream, 0, outputStream.length);
    }

    @TypeConverter
    public byte[] fromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream);
        byte[] b = outputStream.toByteArray();
        return b;
    }
}
