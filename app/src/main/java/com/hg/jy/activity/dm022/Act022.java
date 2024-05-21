package com.hg.jy.activity.dm022;

import android.annotation.SuppressLint;
import android.app.PendingIntent;
import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.provider.ContactsContract.CommonDataKinds;
import android.provider.ContactsContract.Contacts;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.hg.jy.R;
import com.hg.jy.activity.dm022.entity.Contact;
import com.hg.jy.activity.utils.Constants;
import com.hg.jy.activity.utils.ToastUtil;

import java.util.ArrayList;

@SuppressLint("DefaultLocale")
public class Act022 extends AppCompatActivity implements View.OnClickListener {

    private EditText et_contact_name;
    private EditText et_contact_phone;
    private EditText et_contact_email;
    private static TextView tv_show;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_act022);

        et_contact_name = findViewById(R.id.dm022_et_contact_name);
        et_contact_phone = findViewById(R.id.dm022_et_contact_phone);
        et_contact_email = findViewById(R.id.dm022_et_contact_email);
        tv_show = findViewById(R.id.dm022_showStr);
        findViewById(R.id.dm022_btn_add_contact).setOnClickListener(this);
        findViewById(R.id.dm022_btn_read_contact).setOnClickListener(this);
        findViewById(R.id.dm022_btn_sms_monitor).setOnClickListener(this);
        findViewById(R.id.dm022_btn_sms_manual).setOnClickListener(this);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        case R.id.dm022_btn_add_contact:
            // 创建一个联系人对象
            Contact contact = new Contact();
            contact.name = et_contact_name.getText().toString().trim();
            contact.phone = et_contact_phone.getText().toString().trim();
            contact.email = et_contact_email.getText().toString().trim();

            // 方式一，使用ContentResolver多次写入，每次一个字段
               addContacts(getContentResolver(), contact);
            // 方式二，批处理方式
            //   每一次操作都是一个ContentProviderOperation，构建一个操作集合，然后一次性执行
            //   好处是，要么全部成功，要么全部失败，保证了事务的一致性
            //addFullContacts(getContentResolver(), contact);
            break;
            case R.id.dm022_btn_read_contact:
                readPhoneContacts(getContentResolver());
                break;
            case R.id.dm022_btn_sms_monitor:
                startSmsMonitor();
                break;
            case R.id.dm022_btn_sms_manual:
                sendSmsManual();
                break;
        }
    }


    // 往手机通讯录添加一个联系人信息（包括姓名、电话号码、电子邮箱）
    private void addContacts(ContentResolver resolver, Contact contact) {
        ContentValues values = new ContentValues();
        // 往 raw_contacts 添加联系人记录，并获取添加后的联系人编号
        Uri uri = resolver.insert(ContactsContract.RawContacts.CONTENT_URI, values);
        long rawContactId = ContentUris.parseId(uri);

        ContentValues name = new ContentValues();
        // 关联联系人编号
        name.put(Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // “姓名”的数据类型
        name.put(Contacts.Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE);
        // 联系人的姓名
        name.put(Contacts.Data.DATA2, contact.name);
        // 往提供器添加联系人的姓名记录
        resolver.insert(ContactsContract.Data.CONTENT_URI, name);

        ContentValues phone = new ContentValues();
        // 关联联系人编号
        phone.put(Contacts.Data.RAW_CONTACT_ID, rawContactId);
        // “电话号码”的数据类型
        phone.put(Contacts.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE);
        // 联系人的电话号码
        phone.put(Contacts.Data.DATA1, contact.phone);
        // 联系类型。1表示家庭，2表示工作
        phone.put(Contacts.Data.DATA2, CommonDataKinds.Phone.TYPE_MOBILE);
        // 往提供器添加联系人的姓名记录
        resolver.insert(ContactsContract.Data.CONTENT_URI, phone);


        ContentValues email = new ContentValues();
        // 关联联系人编号
        email.put(Contacts.Data.RAW_CONTACT_ID, rawContactId);
        //  “电子邮箱”的数据类型
        email.put(Contacts.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE);
        // 联系人的电子邮箱
        email.put(Contacts.Data.DATA1, contact.email);
        // 联系类型。1表示家庭，2表示工作
        email.put(Contacts.Data.DATA2, CommonDataKinds.Email.TYPE_WORK);
        // 往提供器添加联系人的姓名记录
        resolver.insert(ContactsContract.Data.CONTENT_URI, email);
    }

    // 往手机通讯录一次性添加一个联系人信息（包括主记录、姓名、电话号码、电子邮箱）
    private void addFullContacts(ContentResolver resolver, Contact contact) {
        // 创建一个插入联系人主记录的内容操作器
        ContentProviderOperation op_main = ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build();

        // 创建一个插入联系人姓名记录的内容操作器
        ContentProviderOperation op_name = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即 raw_contacts 的 id 作为 data 表中的 raw_contact_id
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(Contacts.Data.MIMETYPE, CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(Contacts.Data.DATA2, contact.name)
                .build();

        // 创建一个插入联系人电话号码记录的内容操作器
        ContentProviderOperation op_phone = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即 raw_contacts 的 id 作为 data 表中的 raw_contact_id
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(Contacts.Data.MIMETYPE, CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(Contacts.Data.DATA1, contact.phone)
                .withValue(Contacts.Data.DATA2, CommonDataKinds.Phone.TYPE_MOBILE)
                .build();

        // 创建一个插入联系人电子邮箱记录的内容操作器
        ContentProviderOperation op_email = ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                // 将第0个操作的id，即 raw_contacts 的 id 作为 data 表中的 raw_contact_id
                .withValueBackReference(Contacts.Data.RAW_CONTACT_ID, 0)
                .withValue(Contacts.Data.MIMETYPE, CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(Contacts.Data.DATA1, contact.email)
                .withValue(Contacts.Data.DATA2, CommonDataKinds.Email.TYPE_WORK)
                .build();

        // 声明一个内容操作器的列表，并将上面四个操作器添加到该列表中
        ArrayList<ContentProviderOperation> operations = new ArrayList<>();
        operations.add(op_main);
        operations.add(op_name);
        operations.add(op_phone);
        operations.add(op_email);

        try {
            // 批量提交四个操作
            resolver.applyBatch(ContactsContract.AUTHORITY, operations);
            ToastUtil.show(this, "添加联系人成功！");
        } catch (OperationApplicationException | RemoteException e) {
            e.printStackTrace();
        }
    }

    // 查询通讯录信息
    @SuppressLint("Range")
    private void readPhoneContacts(ContentResolver resolver) {
        // 先查询 raw_contacts 表，在根据 raw_contacts_id 去查询 data 表
        Cursor cursor = resolver.query(ContactsContract.RawContacts.CONTENT_URI,
                new String[]{ContactsContract.RawContacts._ID}, null, null, null, null);
        while (cursor != null && cursor.moveToNext()) {
            int rawContactId = cursor.getInt(0);
            Uri uri = Uri.parse("content://com.android.contacts/contacts/" + rawContactId + "/data");
            Cursor dataCursor = resolver.query(uri, new String[]{Contacts.Data.MIMETYPE,
                     Contacts.Data.DATA1, Contacts.Data.DATA2}, null, null, null);
            Contact contact = new Contact();
            while (dataCursor != null && dataCursor.moveToNext()) {
                String data1 = dataCursor.getString(dataCursor.getColumnIndex(Contacts.Data.DATA1));
                String mimeType = dataCursor.getString(dataCursor.getColumnIndex(Contacts.Data.MIMETYPE));
                switch (mimeType) {
                    //姓名
                    case CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE:
                        contact.name = data1;
                        break;
                    //邮箱
                    case CommonDataKinds.Email.CONTENT_ITEM_TYPE:
                        contact.email = data1;
                        break;
                    //手机
                    case CommonDataKinds.Phone.CONTENT_ITEM_TYPE:
                        contact.phone = data1;
                        break;
                }
            }
            if (dataCursor != null) {
                dataCursor.close();
            }
            // RawContacts 表中出现的 _id，不一定在 Data 表中都会有对应记录
            if (contact.name != null) {
                Log.d(Constants.TAG, contact.toString());
                tv_show.setText(contact.toString());
            }
        }
        if (cursor != null) {
            cursor.close();
        }
    }






// 短信观察者
private SmsGetObserver mObserver;
// 声明一个系统短信提供器的Uri对象
private static final Uri mSmsUri = Uri.parse("content://sms");
private static String mCheckResult;

// 开启短信监听
private void startSmsMonitor() {
    // 给指定Uri注册内容观察器，一旦发生数据变化，就触发观察器的onChange方法
    // notifyForDescendants：
    // false ：表示精确匹配，即只匹配该Uri，true ：表示可以同时匹配其派生的Uri
    // 假设UriMatcher 里注册的Uri共有一下类型：
    //   1.content://AUTHORITIES/table
    //   2.content://AUTHORITIES/table/#
    //   3.content://AUTHORITIES/table/student
    // 假设我们当前需要观察的Uri为content://AUTHORITIES/table:
    // 如果发生数据变化的 Uri 为 3, 当notifyForDescendants为false，
    // 那么该ContentObserver会监听不到，但是当为ture，能捕捉该Uri的数据库变化。
    mObserver = new SmsGetObserver(this);
    getContentResolver().registerContentObserver(mSmsUri, true, mObserver);
    Log.i(Constants.TAG, "短信监听已开始！");
}

// 在页面销毁时触发注销内容观察器
@Override
protected void onDestroy() {
    super.onDestroy();
    getContentResolver().unregisterContentObserver(mObserver);
}

private static class SmsGetObserver extends ContentObserver {
    private final Context mContext;

    public SmsGetObserver(Context context) {
        super(new Handler(Looper.getMainLooper()));
        this.mContext = context;
    }

    @SuppressLint("Range")
    @Override
    public void onChange(boolean selfChange, @Nullable Uri uri) {
        super.onChange(selfChange, uri);
        if (uri == null) {
            return;
        }
        Log.i(Constants.TAG, uri.toString());
        if (uri.toString().contains("content://sms/raw") || uri.toString().equals("content://sms")) {
            return;
        }

        // 通过内容解析器获取符合条件的结果集游标
        Cursor cursor = mContext.getContentResolver().query(uri, new String[] {
                "address", "body", "date" }, null, null, "date DESC");
        if (cursor != null && cursor.moveToNext()) {
            // 短信的发送号码
            String sender = cursor.getString(cursor.getColumnIndex("address"));
            // 短信内容
            String content = cursor.getString(cursor.getColumnIndex("body"));
            Log.d(Constants.TAG, String.format("sender:%s,content:%s", sender, content));
            cursor.close();
        }
    }
}

    // 跳到系统的短信发送页面，由用户手工编辑与发送短信
    private void sendSmsManual() {
        Intent intent = new Intent(Intent.ACTION_SENDTO, Uri.parse("smsto:10086"));
        intent.putExtra("sms_body", "1031");  // 发生1031到10086查询当月话费
        startActivity(intent);
    }

}