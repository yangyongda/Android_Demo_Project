package com.fjsd.yyd.systemcontentprovider;

import android.content.ContentResolver;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    private Button read;
    private ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        read = (Button)findViewById(R.id.read);

        resolver = getContentResolver();

        read.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor cursor = resolver.query(ContactsContract.Contacts.CONTENT_URI, null ,null,null,null);
                if(cursor.moveToFirst()){ //移动到首行
                    do{
                        int idIndex = cursor.getColumnIndex(ContactsContract.Contacts._ID);  //获取ID所在的列索引
                        int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME); //获取联系人名字所在列的索引
                        String contactId = cursor.getString(idIndex);
                        String displayName = cursor.getString(displayNameIndex);
                        Log.v("YANG", "联系人名字："+displayName);

                        int phoneCount = cursor.getInt(cursor.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));
                        if(phoneCount > 0){
                            Cursor phoneCursor = resolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                                    null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "=" + contactId,null ,null,null);

                            while(phoneCursor.moveToNext()){
                                    String phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                                    Log.v("YANG","      电话号码："+phoneNumber );
                                }
                            }
                    } while(cursor.moveToNext());
                }
            }
        });

    }
}
