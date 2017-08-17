package appdeveloper.meenakshi.com.Call_Parlour;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by Ashish on 05/30/2017.
 */

public class WeddingandPartyDetails extends Fragment {
    EditText mParty_Makeup;
    EditText mHair_Styling;
    EditText mSaree_Drapping;
    EditText mHands_Indian_Mehndi;
    EditText mLegs_Indian_Mehndi;
    EditText mPalms_Indian_Mehndi;
    EditText mHands_Arabic_Mehndi;
    EditText mLegs_Arabic_Mehndi;
    EditText mPalms_Arabic_Mehndi;
    EditText mPreBridal_Package_Gold;
    EditText mPreBridal_Package_Silver;
    EditText mBridal_Makeup;
    EditText mBridal_Mehndi;
    private SendWeddingpartyDetailsToServer mSendWeddingpartyDataTask = null;

    public static WeddingandPartyDetails newInstance() {
        WeddingandPartyDetails fragment = new WeddingandPartyDetails();
        return fragment;
    }

    public WeddingandPartyDetails() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.activity_weddiingandparty__tab, container, false);
        mParty_Makeup = (EditText) rootView.findViewById(R.id.party_makeup_cost);
        mHair_Styling = (EditText) rootView.findViewById(R.id.hair_styling__cost);
        mSaree_Drapping = (EditText) rootView.findViewById(R.id.saree_drapping_cost);
        mHands_Indian_Mehndi = (EditText) rootView.findViewById(R.id.hands_indian_mehndi_cost);
        mLegs_Indian_Mehndi = (EditText) rootView.findViewById(R.id.legs_indian_mehndi_cost);
        mPalms_Indian_Mehndi = (EditText) rootView.findViewById(R.id.palms_indian_mehndi_cost);
        mHands_Arabic_Mehndi = (EditText) rootView.findViewById(R.id.hands_arabic_mehndi_cost);
        mLegs_Arabic_Mehndi = (EditText) rootView.findViewById(R.id.legs_arabic_mehndi_cost);
        mPalms_Arabic_Mehndi = (EditText) rootView.findViewById(R.id.palms_arabic_mehndi_cost);
        mPreBridal_Package_Gold = (EditText) rootView.findViewById(R.id.pre_bridal_package_gold_cost);
        mPreBridal_Package_Silver = (EditText) rootView.findViewById(R.id.pre_bridal_package_silver_cost);
        mBridal_Makeup = (EditText) rootView.findViewById(R.id.bridal_makeup_cost);
        mBridal_Mehndi = (EditText) rootView.findViewById(R.id.bridal_mehndi_cost);


        return rootView;
    }
    private void attemptLogin() {
        if (mSendWeddingpartyDataTask!= null) {
            return;
        }

        // Reset errors.
        mParty_Makeup.setError(null);
        mHair_Styling.setError(null);
        mSaree_Drapping.setError(null);

        mHands_Indian_Mehndi.setError(null);
        mPalms_Indian_Mehndi.setError(null);
        mLegs_Indian_Mehndi.setError(null);

        mHands_Arabic_Mehndi.setError(null);
        mLegs_Arabic_Mehndi.setError(null);
        mPalms_Arabic_Mehndi.setError(null);

        mPreBridal_Package_Gold.setError(null);
        mPreBridal_Package_Silver.setError(null);
        mBridal_Mehndi.setError(null);
        mBridal_Makeup.setError(null);

        // Store values at the time of the login attempt.
        String party_makeup = mParty_Makeup.getText().toString();
        String hair_styling = mHair_Styling.getText().toString();
        String saree_drapping = mSaree_Drapping.getText().toString();

        String hands_indian_mehndi = mHands_Indian_Mehndi.getText().toString();
        String legs_indian_mehndi= mLegs_Indian_Mehndi.getText().toString();
        String palms_indian_mehndi = mPalms_Indian_Mehndi.getText().toString();

        String hands_arabic_mehndi = mHands_Arabic_Mehndi.getText().toString();
        String palms_arabic_mehndi= mPalms_Arabic_Mehndi.getText().toString();
        String legs_arabic_mehndi = mLegs_Arabic_Mehndi.getText().toString();

        String pre_bridal_package_gold= mPreBridal_Package_Gold.getText().toString();
        String pre_bridal_package_silver = mPreBridal_Package_Silver.getText().toString();
        String bridal_makeup = mBridal_Makeup.getText().toString();
        String bridal_mehndi = mBridal_Mehndi.getText().toString();

        boolean cancel = false;
        View focusView = null;

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            //showProgress(true);
            mSendWeddingpartyDataTask = new SendWeddingpartyDetailsToServer(party_makeup, hair_styling, saree_drapping, hands_indian_mehndi, legs_indian_mehndi,palms_indian_mehndi, hands_indian_mehndi, legs_arabic_mehndi, palms_indian_mehndi, pre_bridal_package_gold, pre_bridal_package_silver,bridal_makeup,bridal_mehndi);
            mSendWeddingpartyDataTask.execute();
        }
    }

    public class SendWeddingpartyDetailsToServer extends AsyncTask<Void, Void, Boolean> {

        private final String mParty_Makeup;
        private final String mHair_Styling;
        private final String mSaree_Drapping;
        private final String mHands_Indian_Mehndi;
        private final String mLegs_Indian_Mehndi;
        private final String mPalms_Indian_Mehndi;
        private final String mHands_Arabic_Mehndi;
        private final String mLegs_Arabic_Mehndi;
        private final String mPalms_Arabic_Mehndi;
        private final String mPre_Bridal_Package_Gold;
        private final String mPre_Bridal_Package_Silver;
        private final String mBridal_Makeup;
        private final String mBridal_Mehndi;

        private ProgressDialog progressDialog;

        SendWeddingpartyDetailsToServer(String party_makeup, String hair_styling, String saree_drapping, String hands_indian_mehndi, String legs_indian_mehndi, String palms_indian_mehndi ,String hands_arabic_mehndi, String legs_arabic_mehndi, String palms_arabic_mehndi, String pre_bridal_package_gold, String pre_bridal_package_silver, String bridal_makeup, String bridal_mehndi) {
            mParty_Makeup = party_makeup;
            mHair_Styling = hair_styling;
            mSaree_Drapping = saree_drapping;
            mHands_Indian_Mehndi = hands_indian_mehndi;
            mLegs_Indian_Mehndi = legs_indian_mehndi;
            mPalms_Indian_Mehndi = palms_indian_mehndi;
            mHands_Arabic_Mehndi = hands_arabic_mehndi;
            mLegs_Arabic_Mehndi = legs_arabic_mehndi;
            mPalms_Arabic_Mehndi = palms_arabic_mehndi;
            mPre_Bridal_Package_Gold = pre_bridal_package_gold;
            mPre_Bridal_Package_Silver = pre_bridal_package_silver;
            mBridal_Makeup = bridal_makeup;
            mBridal_Mehndi = bridal_mehndi;
        }


        protected void onPreExecute() {
            progressDialog = new ProgressDialog(getContext());
            progressDialog.setCancelable(false);
            progressDialog.setTitle("Please Wait...");
            progressDialog.setMessage("Loading");
            //if (User_Registration.this.isFinishing() == false)// To avoid android.view.WindowManager$BadTokenException
            progressDialog.show();

        }

        @Override
        protected Boolean doInBackground(Void... params) {
            // TODO: attempt authentication against a network service.

            try {

                Log.v("WeddingandParty Details", " ***********i am in try 1");
                URL server_url = new URL("http://www.google.co.in/");

                // iCommon.SSLv3_bugfix();
                HttpURLConnection urlc = (HttpURLConnection) server_url.openConnection();
                urlc.setDoOutput(true);
                urlc.setDoInput(true);
                urlc.setRequestMethod("POST");
                urlc.setRequestProperty("Content-Language", "en-US");
                urlc.setRequestProperty("Accept-Encoding", "identity");

                HashMap<String, String> param = new HashMap<>();

                param.put("party makeup", mParty_Makeup);
                param.put("hair styling", mHair_Styling);
                param.put("saree drapping", mSaree_Drapping);
                param.put("hands indian mehndi", mHands_Indian_Mehndi);
                param.put("legs indian mehndi", mLegs_Indian_Mehndi);
                param.put("palms indian mehndi", mPalms_Indian_Mehndi);
                param.put("hands arabic mehndi", mHands_Arabic_Mehndi);
                param.put("legs arabic mehndi", mLegs_Arabic_Mehndi);
                param.put("palms arabic mehndi", mPalms_Arabic_Mehndi);
                param.put("pre bridal package gold", mPre_Bridal_Package_Gold);
                param.put("pre bridal package silver", mPre_Bridal_Package_Silver);
                param.put("bridal makeup", mBridal_Makeup);
                param.put("bridal mehndi", mBridal_Mehndi);

                DataOutputStream os = new DataOutputStream(urlc.getOutputStream());
                os.writeBytes(iCommon.getPostDataString(param));
                Log.v("WeddingandParty Details", iCommon.getPostDataString(param));
                os.flush();
                os.close();
            } catch (ProtocolException e) {
                Log.v("WeddingandParty Details", " i am in catch 1");
                e.printStackTrace();
            } catch (MalformedURLException e) {
                Log.v("WeddingandParty Details", " i am in catch 2");
                e.printStackTrace();
            } catch (IOException e) {
                Log.v("WeddingandParty Details", " i am in catch 3");
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
            mSendWeddingpartyDataTask = null;
            // showProgress(false);

            if (success) {
                //finish();
                Log.v("WeddingandParty Details", "**********success");
            } /*else {
                mPasswordView.setError(getString(R.string.error_incorrect_password));
                mPasswordView.requestFocus();
            }*/
        }

    }

} // This is the end of our MyFragments Class







