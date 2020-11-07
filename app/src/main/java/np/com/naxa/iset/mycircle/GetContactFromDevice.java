package np.com.naxa.iset.mycircle;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.util.Log;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import np.com.naxa.iset.utils.DialogFactory;
import np.com.naxa.iset.utils.TextUtils;

public class GetContactFromDevice {
    private static final String TAG  = "GetContactFromDevice";

    public ArrayList<MyCircleContactListData> getContacts(Context context) {
        ArrayList<MyCircleContactListData> list = new ArrayList<>();
        ArrayList<String> contactNoList = new ArrayList<>();
        ContentResolver contentResolver = context.getContentResolver();
        Cursor cursor = contentResolver.query(ContactsContract.Contacts.CONTENT_URI, null, null, null, null);
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                String id = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                if (cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)) > 0) {
                    Cursor cursorInfo = contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = ?", new String[]{id}, null);
                    InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream(context.getContentResolver(),
                            ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id)));

                    Uri person = ContentUris.withAppendedId(ContactsContract.Contacts.CONTENT_URI, new Long(id));
                    Uri pURI = Uri.withAppendedPath(person, ContactsContract.Contacts.Photo.CONTENT_DIRECTORY);

                    Bitmap photo = null;
                    if (inputStream != null) {
                        photo = BitmapFactory.decodeStream(inputStream);
                    }
                    while (cursorInfo.moveToNext()) {

                        MyCircleContactListData info = new MyCircleContactListData("", "", "", "", false);
//                        info.id = id;
                        info.name = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                        info.mobileNumber = TextUtils.validatePhoneNumber(cursorInfo.getString(cursorInfo.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER)));

                        info.imgUrl= pURI.toString();
                        list.add(info);
                        contactNoList.add(info.mobileNumber);

                        Log.d("GetContactFromDevice", "getContacts: " + info.name);
                        Log.d("GetContactFromDevice", "getContacts: " + info.mobileNumber);
                    }

                    cursorInfo.close();
                }
            }
            cursor.close();
        }
        return list;
    }

}
