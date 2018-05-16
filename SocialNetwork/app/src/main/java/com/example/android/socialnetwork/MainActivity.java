package com.example.android.socialnetwork;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Xml;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.socialnetwork.packagefordb.UserHelper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;

import com.example.android.socialnetwork.packagefordb.usercontract.userEntry;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;
import org.xmlpull.v1.XmlSerializer;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

public class MainActivity extends AppCompatActivity {




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView sign_up=(TextView)findViewById(R.id.sign_up); // Navigate to choose username , passwords
        TextView admin = (TextView)findViewById(R.id.admin);  // Navigate to show all users
        TextView log_in=(TextView)findViewById(R.id.log_in);  // Navigate to Enter id/username
    }

    public static ArrayList<user> users = new ArrayList<>();


    public void ne(View view) {
        Intent n = new Intent(this , signup.class);
        startActivity(n);
    }
    public void parseXml (View v)
    {

        XmlSerializer serializer = Xml.newSerializer();
        StringWriter writer = new StringWriter();


            UserHelper userHelper = new UserHelper(getBaseContext());
            SQLiteDatabase sql = userHelper.getReadableDatabase();
            String [] projection = {userEntry.COULMN_UserName , userEntry.COULMN_number_friends , userEntry.COULMN_friends , userEntry._ID,userEntry.COULMN_password} ;
            Cursor c = sql.query(userEntry.TABLE_NAME , projection,null,null,null,null,null);
            c.moveToFirst();
            int no_of_all_users = c.getCount() ;
       // Toast.makeText(this, String.valueOf(no_of_all_users),Toast.LENGTH_LONG).show();
        try {
            serializer.setOutput(writer);
            serializer.startDocument("UTF-8", true);
            serializer.startTag("", userEntry.TABLE_NAME);
            for(int i=0;i<no_of_all_users;i++) {
                serializer.startTag("","ID");
                serializer.text(String.valueOf(c.getInt(c.getColumnIndex(userEntry._ID))));
                serializer.endTag("","ID");
                serializer.startTag("","username");
                serializer.text(String.valueOf(c.getInt(c.getColumnIndex(userEntry.COULMN_UserName))));
                serializer.endTag("","username");
                serializer.startTag("","Number of Friends");
                serializer.text(String.valueOf(c.getInt(c.getColumnIndex(userEntry.COULMN_number_friends))));
                serializer.endTag("","Number of Friends");
                serializer.startTag("","password");
                serializer.text(String.valueOf(c.getInt(c.getColumnIndex(userEntry.COULMN_password))));
                serializer.endTag("","password");
                c.moveToNext();

            }
            //Toast.makeText(this, "The End",Toast.LENGTH_LONG).show();

            serializer.endTag("",userEntry.TABLE_NAME);
            serializer.endDocument();
            String result= writer.toString();
            fileStream fs= new fileStream();

            fs.writeToFile(this,"database.xml",result);
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public void readXml(View v)
    {/*
        Document doc=null;
        DocumentBuilderFactory dbf=DocumentBuilderFactory.newInstance();
        DocumentBuilder db;
        */
        XmlPullParserFactory Factory;
        FileInputStream fis=null;

        try
            {/*
                db=dbf.newDocumentBuilder();
                FileInputStream fis=openFileInput("database.xml");
                doc=db.parse(fis);
                NodeList table=doc.getElementsByTagName(userEntry.TABLE_NAME);
                String result="";
                for(int i=0;i<table.getLength();i++)
                {
                    Node users=table.item(i);
                    NodeList userinfo=users.getChildNodes();
                    for(int j=0;j<userinfo.getLength();j++)
                    {
                        Node info=userinfo.item(j);
                            result +="<" info.getNodeName()+">"+info.getTextContent()+"</"+info.getNodeName()+">\n";
                    }
                }
*/
                StringBuilder sb= new StringBuilder();
                Factory=XmlPullParserFactory.newInstance();
                Factory.setNamespaceAware(true);
                XmlPullParser xpp= Factory.newPullParser();
                fis=openFileInput("database.xml");
                xpp.setInput(fis,null);
                int eventType=xpp.getEventType();
                while(eventType!=XmlPullParser.END_DOCUMENT)
                {
                    if(eventType==XmlPullParser.START_DOCUMENT)
                    sb.append("[START]");
                    else if(eventType==XmlPullParser.START_TAG)
                        sb.append("\n<"+xpp.getName()+">");
                    else if(eventType==XmlPullParser.END_TAG)
                        sb.append("</"+xpp.getName()+">");
                    else if(eventType==XmlPullParser.TEXT)
                        sb.append(xpp.getText());

                    eventType=xpp.next();


                }
                fileStream fs= new fileStream();
                fs.set_string(sb.toString());

            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
            catch(XmlPullParserException e)
            {
                e.printStackTrace();
            }
            finally {
            if(fis!=null){
                try {
                    fis.close();
                    }
                    catch (IOException e){
                    e.printStackTrace();
                }
            }
        }

        Intent n  = new Intent(this,Parsing_Xml_File.class);
        startActivity(n);

    }
    public void gotoLogin(View view){
        Intent n  = new Intent(this,Login.class);
        startActivity(n);
    }
    public void gotoAdmin(View view)
    {
        Intent n = new Intent(this,admin.class);
        startActivity(n);
    }


}
