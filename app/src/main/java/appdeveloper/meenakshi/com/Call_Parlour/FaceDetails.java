package appdeveloper.meenakshi.com.Call_Parlour;

import android.app.ProgressDialog;
import android.app.TabActivity;
import android.os.AsyncTask;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TabHost;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Ashish on 04/20/2017.
 */

public class FaceDetails extends Fragment {
    EditText mFruit_Facial, mLotus_Facial, mVLCC_Facial, mShehnaz_Facial,mO3_Facial;
    EditText mFruit_Cleanup, mSkin_Whitening_CLeanup, mLotus_Cleanup;
    EditText mEyebrow_Threading, mUpperlip_Threading,mForehead_Threading,mFull_Face_Threading;
    EditText mFace_Neck_Bleach;
    private SendFaceDetailstoServer mSendDataTask = null;

    public static FaceDetails newInstance() {
        FaceDetails fragment = new FaceDetails();
        return fragment;
    }

    public FaceDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

         View rootView = inflater.inflate(R.layout.activity_face__tab, container, false);
        //final TabLayout tabLayout = (TabLayout)rootView. findViewById(R.id.tabs);
        mFruit_Facial = (EditText) rootView.findViewById(R.id.fruit_facial_cost);
        mLotus_Facial = (EditText) rootView.findViewById(R.id.lotus_cost);
        mVLCC_Facial  = (EditText) rootView.findViewById(R.id.vlcc_cost);
        mShehnaz_Facial = (EditText) rootView.findViewById(R.id.shehnaz_cost);
        mO3_Facial = (EditText) rootView.findViewById(R.id.o3_cost);

        mFruit_Cleanup = (EditText) rootView.findViewById(R.id.fruit_cleanup_cost);
        mSkin_Whitening_CLeanup = (EditText) rootView.findViewById(R.id.skin_whitening_cleanup_cost);
        mLotus_Cleanup = (EditText) rootView.findViewById(R.id.lotus_cleanup_cost);

        mEyebrow_Threading = (EditText) rootView.findViewById(R.id.eyebrow_cost);
        mUpperlip_Threading = (EditText) rootView.findViewById(R.id.upperlip_cost);
        mForehead_Threading = (EditText) rootView.findViewById(R.id.forehead_cost);
        mFull_Face_Threading = (EditText) rootView.findViewById(R.id.fullface_cost);

        mFace_Neck_Bleach = (EditText) rootView.findViewById(R.id.face_neck_cost);

        Button button = (Button) rootView.findViewById(R.id.save);
        button.setOnClickListener(new View.OnClickListener()
        {
            public void onClick(View view)
            {

               /* TabActivity tabs = (TabActivity)rootView. getParent();
                tabs.getTabHost().setCurrentTab(4);*/
               // tabHost.setCurrentTab(2);

               // TabLayout tabLayout = (TabLayout)rootView. findViewById(R.id.tabs);
                TabLayout tabhost = (TabLayout) getActivity().findViewById(R.id.tabs);
                TabLayout.Tab tab = tabhost.getTabAt(3);
                tab.select();
            }
        });
        return rootView;
    }

    public void attemptLogin(View view) {
        if (mSendDataTask!= null) {
            return;
        }

        Log.v("face details", "button is clicked *******");
        // Reset errors.
        mFruit_Facial.setError(null);
        mLotus_Facial.setError(null);
        mVLCC_Facial.setError(null);
        mShehnaz_Facial.setError(null);
        mO3_Facial.setError(null);

        mFruit_Cleanup.setError(null);
        mSkin_Whitening_CLeanup.setError(null);
        mLotus_Cleanup.setError(null);

        mEyebrow_Threading.setError(null);
        mUpperlip_Threading.setError(null);
        mForehead_Threading.setError(null);
        mFull_Face_Threading.setError(null);

        mFace_Neck_Bleach.setError(null);

        // Store values at the time of the login attempt.
        String fruit_facial = mFruit_Facial.getText().toString();
        String lotus_facial = mLotus_Facial.getText().toString();
        String vlcc_facial = mVLCC_Facial.getText().toString();
        String shehnaz_facial = mShehnaz_Facial.getText().toString();
        String o3_facial = mO3_Facial.getText().toString();

        String fruit_cleanup = mFruit_Cleanup.getText().toString();
        String skin_whitening_cleanup = mSkin_Whitening_CLeanup.getText().toString();
        String lotus_cleanup = mLotus_Cleanup.getText().toString();

        String eyebrow_threading = mEyebrow_Threading.getText().toString();
        String upperlip_threading = mUpperlip_Threading.getText().toString();
        String forehead_threading = mForehead_Threading.getText().toString();
        String full_face_threading = mFull_Face_Threading.getText().toString();

        String face_neck_bleach = mFace_Neck_Bleach.getText().toString();

        boolean cancel = false;
        View focusView = null;


        /*if (TextUtils.isEmpty(fruit_facial)) {
            mFruit_Facial.setError(getString(R.string.error_field_required));
            focusView = mFruit_Facial;
            cancel = true;
        }
        if (TextUtils.isEmpty(lotus_facial)) {
            mLotus_Facial.setError(getString(R.string.error_field_required));
            focusView = mLotus_Facial;
            cancel = true;
        }
        if (TextUtils.isEmpty(vlcc_facial)) {
            mVLCC_Facial.setError(getString(R.string.error_field_required));
            focusView = mVLCC_Facial;
            cancel = true;
        }
        if (TextUtils.isEmpty(shehnaz_facial)) {
            mShehnaz_Facial.setError(getString(R.string.error_field_required));
            focusView = mShehnaz_Facial;
            cancel = true;
        }
        if (TextUtils.isEmpty(o3_facial)) {
            mO3_Facial.setError(getString(R.string.error_field_required));
            focusView = mO3_Facial;
            cancel = true;
        }
        // Check for a valid password, if the user entered one.
       /* if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }*/

      /*  // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }*/

       /* if (TextUtils.isEmpty(fruit_cleanup)) {
            mFruit_Cleanup.setError(getString(R.string.error_field_required));
            focusView = mFruit_Cleanup;
            cancel = true;
        }

        if (TextUtils.isEmpty(skin_whitening_cleanup)) {
            mSkin_Whitening_CLeanup.setError(getString(R.string.error_field_required));
            focusView = mSkin_Whitening_CLeanup;
            cancel = true;
        }

        if (TextUtils.isEmpty(lotus_cleanup)) {
            mLotus_Cleanup.setError(getString(R.string.error_field_required));
            focusView = mLotus_Cleanup;
            cancel = true;
        }

        if (TextUtils.isEmpty(eyebrow_threading)) {
            mEyebrow_Threading.setError(getString(R.string.error_field_required));
            focusView = mEyebrow_Threading;
            cancel = true;
        }

        if (TextUtils.isEmpty(upperlip_threading)) {
            mUpperlip_Threading.setError(getString(R.string.error_field_required));
            focusView = mUpperlip_Threading;
            cancel = true;
        }


        if (TextUtils.isEmpty(forehead_threading)) {
            mForehead_Threading.setError(getString(R.string.error_field_required));
            focusView = mForehead_Threading;
            cancel = true;
        }

        if (TextUtils.isEmpty(full_face_threading)) {
            mFull_Face_Threading.setError(getString(R.string.error_field_required));
            focusView = mFull_Face_Threading;
            cancel = true;
        }*/

      /*  if (!TextUtils.isEmpty(face_neck_bleach)) {
            mFace_Neck_Bleach.setError(getString(R.string.error_field_required));
            focusView = mFace_Neck_Bleach;
            cancel = true;
        }*/

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mSendDataTask = new SendFaceDetailstoServer(fruit_facial, lotus_facial,vlcc_facial,shehnaz_facial,o3_facial,fruit_cleanup,skin_whitening_cleanup, lotus_cleanup,eyebrow_threading,full_face_threading,upperlip_threading,forehead_threading, face_neck_bleach );
            mSendDataTask.execute();
        }
    }

    public class SendFaceDetailstoServer extends AsyncTask<Void, Void, Boolean> {

        private final String mFruit_Facial;
        private final String mLotus_Facial;
        private final String mVlcc_Facial;
        private final String mShehnaz_Facial;
        private final String mO3_Facial;
        private final String mFruit_Cleanup;
        private final String mSkin_Whitening_Cleanup;
        private final String mLotus_Cleanup;
        private final String mEyebrow_Threading;
        private final String mForehead_Threading;
        private final String mUpperlip_Threading;
        private final String mFull_Face_Threading;
        private final String mFaceandNeck_Bleach;

        private ProgressDialog progressDialog;

        SendFaceDetailstoServer(String fruit_facial, String lotus_facial, String vlcc_facial, String shehnaz_facial, String o3_facial, String fruit_cleanup, String skin_whitening_cleanup, String lotus_cleanup, String eyebrow_threading, String full_face_threading, String upperlip_threading, String forehead_threading, String face_neck_bleach) {
            mFruit_Facial = fruit_facial;
            mLotus_Facial = lotus_facial;
            mVlcc_Facial = vlcc_facial;
            mShehnaz_Facial = shehnaz_facial;
            mO3_Facial = o3_facial;
            mFruit_Cleanup = fruit_cleanup;
            mSkin_Whitening_Cleanup = skin_whitening_cleanup;
            mLotus_Cleanup = lotus_cleanup;
            mEyebrow_Threading = eyebrow_threading;
            mForehead_Threading = forehead_threading;
            mUpperlip_Threading = upperlip_threading;
            mFull_Face_Threading = full_face_threading;
            mFaceandNeck_Bleach = face_neck_bleach;
        }


        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(false);
           // progressDialog.setTitle("Please Wait...");
            progressDialog.setMessage("Saving...");
            //if (User_Registration.this.isFinishing() == false)// To avoid android.view.WindowManager$BadTokenException
            progressDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {

                Log.v("Face Details", " ***********i am in try 1");
                URL server_url = new URL("http://www.google.co.in/");

                // iCommon.SSLv3_bugfix();
                HttpURLConnection urlc = (HttpURLConnection) server_url.openConnection();
                urlc.setDoOutput(true);
                urlc.setDoInput(true);
                urlc.setRequestMethod("POST");
                urlc.setRequestProperty("Content-Language", "en-US");
                urlc.setRequestProperty("Accept-Encoding", "identity");

                HashMap<String, String> param = new HashMap<>();

                // param.put("id", iCommon.get_UserID(context));
                //  param.put("did", iCommon.get_AndroidID(context));
                //param.put("name", mName);
                param.put("fruit facial", mFruit_Facial);
                param.put("lotus facial", mLotus_Facial);
                param.put("vlcc facial", mVlcc_Facial);
                param.put("shehnaz facial", mShehnaz_Facial);
                param.put("fruit cleanup", mFruit_Cleanup);
                param.put("skin whitening cleanup", mSkin_Whitening_Cleanup);
                param.put("lotus Cleanup", mLotus_Cleanup);
                param.put("eyebrow threading", mEyebrow_Threading);
                param.put("full face threading ", mFull_Face_Threading);
                param.put("upperlip threading", mUpperlip_Threading);
                param.put("forehead threading", mForehead_Threading);
                param.put("face and neck bleach", mFaceandNeck_Bleach);


//                    param.put("location", mLocation);
                // param.put("contact_number", mContact);
                DataOutputStream os = new DataOutputStream(urlc.getOutputStream());
                os.writeBytes(iCommon.getPostDataString(param));
                Log.v("face details", iCommon.getPostDataString(param));
                os.flush();
                os.close();
            } catch (ProtocolException e) {
                Log.v("face details", " i am in catch 1");
                e.printStackTrace();
            } catch (MalformedURLException e) {
                Log.v("face details", " i am in catch 2");
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("face details", " i am in catch 3");
                e.printStackTrace();
            }

         /*   try {
                // Simulate network access.
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                return false;
            }

            for (String credential : DUMMY_CREDENTIALS) {
                String[] pieces = credential.split(":");
                if (pieces[0].equals(mEmail)) {
                    // Account exists, return true if the password matches.
                    return pieces[1].equals(mPassword);
                }
            }*/

            //  register the new account in app storage here.

           /* iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_NAME,);
            iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_EMAIL,mEmail);
            iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_PASSWORD,mPassword);
            iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_AGE,mAge);
            iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_LOCATION,mLocation);
            iStorage.savePropertyWithValue(getBaseContext(),iStorage.FILE_CONTACT,mContact);*/

            return true;
        }

        @Override
        protected void onPostExecute(final Boolean success) {
            mSendDataTask = null;
            // showProgress(false);

            if (success) {
               // finish();
                Log.v("face details", "**********success");
                getActivity().finish();
            } /*else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
        }

    }

} // This is the end of our MyFragments Class



