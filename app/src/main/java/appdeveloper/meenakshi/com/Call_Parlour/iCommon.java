package appdeveloper.meenakshi.com.Call_Parlour;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.JsonReader;
import android.util.Log;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocketFactory;

/**
 * Created by Ashish on 06/03/2017.
 */

public class iCommon {
    final static String TAG = "iCommon";

    //private static double mLongitude, mLatitude = 0.0;
    private static Thread mAlertThread, mLogThread;

    private static boolean isProviderUpdated = false, isSSL_fixapplied = false;

    public static void version() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            Log.v(TAG, ">LOLLIPOP");
        else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
            Log.v(TAG, ">JELLY_BEAN");
    }

    public static String signup(Context context) {

        if (iCommon.checkNetconnectivity(context)) {
            try {
                URL url = new URL(Constants.URL_INSTALL);
                iCommon.SSLv3_bugfix();
                HttpsURLConnection urlc = (HttpsURLConnection) url.openConnection();
                urlc.setDoOutput(true);
                urlc.setDoInput(true);
                urlc.setRequestMethod("POST");
                urlc.setRequestProperty("Content-Language", "en-US");
                urlc.setRequestProperty("Accept-Encoding", "identity");

                HashMap<String, String> param = new HashMap<>();
                param.put("id", get_UserID(context));

                DataOutputStream os = new DataOutputStream(urlc.getOutputStream());

                //Log.e(TAG, param.toString()); //debug
                os.writeBytes(getPostDataString(param));
                os.flush();
                os.close();

                //json parsing
                InputStream response = new BufferedInputStream(urlc.getInputStream());
                JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
                reader.setLenient(true);
                reader.beginObject();

                String PIN = "";
                while (reader.hasNext()) {
                    String name = reader.nextName();

                    if (name.equals("PIN")) {
                        PIN = reader.nextString();
                        Log.v("iCommon", "json PIN is " + PIN);
                    }
                }

                if (PIN != null && !PIN.equals(""))
                    iStorage.savePropertyWithValue(context, iStorage.FILE_PIN, PIN);

                return PIN;
            } catch (Exception e) {
                e.printStackTrace();
                iCommon.sendDebugLogToCloud("SignUp-" + TAG + "-" + e.toString(), context);
            }
        }

        return null;
    }

    public static String getPostDataString(HashMap<String, String> param2) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;
        for (Map.Entry<String, String> entry : param2.entrySet()) {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(entry.getKey(), "UTF-8"));
            result.append("=");
            result.append(URLEncoder.encode(entry.getValue(), "UTF-8"));
        }
            Log.v(TAG, " data is " + result.toString());
        return result.toString();
    }

    public static void sendDebugLogToCloud(String tag, Context context) {
        String response_final;
        try {
            Process process = Runtime.getRuntime().exec("logcat -d");
            BufferedReader response = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder str = new StringBuilder();
            while ((line = response.readLine()) != null) {
                str.append(line).append("\n");
            }
            response.close();

            response_final = str.toString();
            //Log.v("log", "Response is " + response_final);
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

        mLogThread = new AlertParent(context, Constants.URL_DEBUG_LOG, tag, "log", response_final);
        mLogThread.start();
    }


    private static class AlertParent extends Thread {
        String url;
        String tag;
        String param_value;
        Context context;
        String post_param;

        public AlertParent(Context context, String url, String tag, String post_param, String param_value) {
            this.context = context;
            this.url = url;
            this.tag = tag;
            this.param_value = param_value;
            this.post_param = post_param;
        }

        public void run() {
            if (!iCommon.checkNetconnectivity(context))
                return;

            try {
                URL u = new URL(url);
                iCommon.SSLv3_bugfix();
                HttpsURLConnection con = (HttpsURLConnection) u.openConnection();
                con.setDoOutput(true);
                con.setDoInput(true);
                con.setRequestMethod("POST");
                con.setRequestProperty("Content-Language", "en-US");
                con.setRequestProperty("Accept-Encoding", "identity");
                HashMap<String, String> param = new HashMap<>();
                param.put("id", get_UserID(context));
                param.put("tag", tag);
                param.put(post_param, param_value);

                DataOutputStream os = new DataOutputStream(con.getOutputStream());
                os.writeBytes(getPostDataString(param));

                os.flush();
                os.close();
                con.getInputStream();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void SSLv3_bugfix() {
        if (isProviderUpdated) // TODO not sure if TLSv1 fallback is required with updated Provider
            return;
        if (isSSL_fixapplied) // singleton
            return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) // TODO may need more tweaking
            return;

        // Apply minimum TLS and subtract SSLv3 fallback - to Lollipop and below ONLY

        /* https://developer.android.com/reference/javax/net/ssl/SSLEngine.html
        Client socket:
        Protocol	Supported (API Levels)	Enabled by default (API Levels)
        SSLv3	        1+	                    1–22
        TLSv1	        1+	                    1+
        TLSv1.1	        20+	                    20+
        TLSv1.2	        20+	                    20+
        */

        SSLContext sslcontext;
        SSLSocketFactory NoSSLv3Factory;
        try {
            sslcontext = SSLContext.getInstance("TLSv1"); // API 14 and below 20
            sslcontext.init(null, null, null);
            NoSSLv3Factory = new NoSSLv3SocketFactory(sslcontext.getSocketFactory());

            // Subtract SSLv3 forcefully for Https class
            HttpsURLConnection.setDefaultSSLSocketFactory(NoSSLv3Factory);
            isSSL_fixapplied = true; // fix works appl-wide, so needed only one time
        } catch (Exception e) {
            Log.e(TAG, "SSLv3-" + e.toString());
        }
    }

    public static String get_UserID(Context context) {
        String tmp = iStorage.readPropertyWithValue(context, iStorage.FILE_ID);
        if (tmp == null || tmp.equals(""))
            tmp = "";
        return tmp;
    }

    public static String get_AndroidID(Context context) {
        // http://android-developers.blogspot.in/2011/03/identifying-app-installations.html
        return Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
    }

    public static boolean checkNetconnectivity(Context context) {
        ConnectivityManager connMgr = (ConnectivityManager)
                context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();
        return networkInfo != null && networkInfo.isConnected();
    }

    //using Async task as it is handling conditions more effectively then thread
    public static class NetworkOperation extends AsyncTask<Void, Void, Boolean> {
        Context context;
        String url;
        private String TAG = "iCommon-NetworkOp";

        public NetworkOperation(Context context, String url) {
            this.context = context;
            this.url = url;
        }

        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            //Log.v(TAG, "Event " + url);

            if (iCommon.checkNetconnectivity(context)) {
                try {
                    URL server_url = new URL(this.url);
                    iCommon.SSLv3_bugfix();
                    HttpsURLConnection urlc = (HttpsURLConnection) server_url.openConnection();
                    urlc.setDoOutput(true);
                    urlc.setDoInput(true);
                    urlc.setRequestMethod("POST");
                    urlc.setRequestProperty("Content-Language", "en-US");
                    urlc.setRequestProperty("Accept-Encoding", "identity");

                    HashMap<String, String> param = new HashMap<>();

                    // ID, Android ID is common in all cases
                    param.put("id", get_UserID(context));

                    switch (url) {
                        case Constants.URL_ON_APP_UPGRADE:
                            String Country_ISO = "";
                            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
                            if (tm.getSimState() != TelephonyManager.SIM_STATE_ABSENT) {
                                Country_ISO = tm.getNetworkCountryIso();
                                Log.v(TAG, "ISO " + Country_ISO);
                                if (Country_ISO != null && !Country_ISO.equals(""))
                                    iStorage.savePropertyWithValue(context, iStorage.FILE_COUNTRY_ISO, Country_ISO);
                                else
                                    Country_ISO = "";
                            }

                            param.put("did", get_AndroidID(context)); // user migration
                            param.put("app_versioncode", String.valueOf(BuildConfig.VERSION_CODE));
                            param.put("API", String.valueOf(Build.VERSION.SDK_INT));
                            param.put("os_version", Build.VERSION.RELEASE);
                            param.put("PhoneModel", android.os.Build.MODEL);
                            param.put("locale", Locale.getDefault().toString());
                            param.put("country", Country_ISO);
                            break;
                        case Constants.URL_UPDATE_LOCATION:
                            param.put("latitude", String.valueOf((int) iStorage.read_lat_lon_from_storage(context, iStorage.FILE_LATITUDE)));
                            param.put("longitude", String.valueOf((int) iStorage.read_lat_lon_from_storage(context, iStorage.FILE_LONGITUDE)));
                            break;
                        case Constants.URL_ON_PIN_CHANGE:
                            param.put("PIN", iStorage.readPropertyWithValue(context, iStorage.FILE_PIN));
                            break;
                        case Constants.URL_ON_SMS_ALERT:
                            String tmp = iStorage.readProperty(context, iStorage.FILE_SMS_ALERT) ? "1" : "0";
                            param.put("sms_alert", tmp);
                            break;
                        default:
                            break; // IMP - don't return, else empty param cases like byte_info will miss out
                    }

                    DataOutputStream os = new DataOutputStream(urlc.getOutputStream());
                    os.writeBytes(iCommon.getPostDataString(param));
                    os.flush();
                    os.close();

                    // Command response processing
                    InputStream response = new BufferedInputStream(urlc.getInputStream());
                    JsonReader reader = new JsonReader(new InputStreamReader(response, "UTF-8"));
                    reader.beginObject();
                    reader.setLenient(true);

                    switch (url) {
                        case Constants.URL_ON_APP_UPGRADE: // migration code is identical
                            serverResponse_ID(reader, context);
                            break;
                        case Constants.URL_ON_PIN_CHANGE:
                            serverResponse_PIN(reader, context);
                            break;
                        // two ways for FMEA and quickness if sync
                        case Constants.URL_BYTE_INFO: // 1. 1MB

                        default:
                            break;
                    }
                    reader.close();
                    response.close();
                    return true;
                } catch (Exception e) {
                    Log.v(TAG, "URL " + url);
                    e.printStackTrace();
                    iCommon.sendDebugLogToCloud(TAG + "-" + e.toString(), context);
                    return false;
                }
            }
            return false;
        }

        protected void onPostExecute(final Boolean success) {
        }
    }
    public static void serverResponse_ID(JsonReader reader, Context context) {
        String serverID = null;
        try {
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("id")) {
                    serverID = reader.nextString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendDebugLogToCloud("ID-" + TAG + "-" + e.toString(), context);
        }

        Log.v("iCommon", "server ID " + serverID);
        if (serverID != null && !serverID.equals(""))
            iStorage.savePropertyWithValue(context, iStorage.FILE_ID, serverID);
    }

    private static void serverResponse_PIN(JsonReader reader, Context context) {
        String serverPIN = null;
        try {
            while (reader.hasNext()) {
                String name = reader.nextName();
                if (name.equals("pin")) {
                    serverPIN = reader.nextString();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            sendDebugLogToCloud("pinUpdate-" + TAG + "-" + e.toString(), context);
        }

        Log.v("iCommon", "server PIN " + serverPIN);
        if (serverPIN != null && !serverPIN.equals(""))
            iStorage.savePropertyWithValue(context, iStorage.FILE_PIN, serverPIN);
    }

}
