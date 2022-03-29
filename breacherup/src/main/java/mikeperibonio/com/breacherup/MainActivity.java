package mikeperibonio.com.breacherup;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.app.Activity;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;

import java.text.NumberFormat;

public class MainActivity extends Activity {

    EditText c4;
    Spinner c4units;

    Spinner boostnum;

    Spinner detcordtype;
    EditText detcordinput;
    Spinner detcordunits;

    Spinner detatype;
    EditText detawidth;
    Spinner widthunit;
    EditText detalength;
    Spinner lenghtunit;

    Spinner flsctype;
    EditText flscinput;
    Spinner flscunits;

    TextView result1;

    Spinner boostertimes;
    Spinner dettimes;
    Spinner flsctimes;

    Spinner caps;

    String detcorddefault = "1.66";
    String c4default = "1.37";
    String boosterdefault = "1.19";
    String prima1000default = "1.19";
    String prima2000default = "1.27";
    String rdxdefault = "1.6";
    String h6default = "1.38";
    String petndefault = "1.27";
    String capdefault = "13.5";

    Double detcordre;
    Double c4re;
    Double capweight;
    Double boosterre;
    Double prima1000re;
    Double prima2000re;
    Double rdxre;
    Double h6re;
    Double petnre;

    String capmakeval;


    final Context context = this;
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    String detpref = "detpref";
    String detapref = "detapref";
    String flscpref = "flscpref";
    String c4pref = "c4pref";
    String ranbefore = "ranbefore";
    String ranbefore2 = "ranbefore2";
    boolean ranBefore;
    boolean ranBefore2;

    boolean feet;
    boolean roundup;
    Button settings;

    TextView msdinternal;
    TextView msdinternalunits;
    TextView msdexternal;
    TextView msdexternalunits;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_explosives_calculator);

        sharedpreferences = getSharedPreferences("Preferences", Context.MODE_PRIVATE);
        editor = sharedpreferences.edit();
        getprefs();

        if (!ranBefore) {
            // custom dialog
            final Dialog dialog = new Dialog(context);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setContentView(R.layout.custom);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setCancelable(false);
            Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
            // if button is clicked, close the custom dialog
            dialogButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    SharedPreferences.Editor editor = sharedpreferences.edit();
                    editor.putBoolean(ranbefore, true);
                    editor.commit();
                    dialog.dismiss();
                    if (!ranBefore2) {
                        // custom dialog
                        final Dialog dialog = new Dialog(context);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.custom2);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setCancelable(false);
                        Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                        // if button is clicked, close the custom dialog
                        dialogButton.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                SharedPreferences.Editor editor = sharedpreferences.edit();
                                editor.putBoolean(ranbefore2, true);
                                editor.commit();
                                dialog.dismiss();
                            }
                        });

                        dialog.show();
                    }
                }
            });

            dialog.show();
        }

        result1 = (TextView) findViewById(R.id.result1);
        msdinternal = (TextView) findViewById(R.id.msdinternal);
        msdinternalunits = (TextView) findViewById(R.id.msdinternalunits);
        msdexternal = (TextView) findViewById(R.id.msdexternal);
        msdexternalunits = (TextView) findViewById(R.id.msdexternalunits);


        c4 = (EditText) findViewById(R.id.c4input);
        c4.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        c4.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (c4.getText().length() > 0 && c4.getText().toString().matches(".*\\d+.*"))
                    calculatetool();
                if (c4.getText().length() == 0) {
                    c4.setHint("0");
                    calculatetool();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        c4units = (Spinner) findViewById(R.id.c4units);
        String[] c4array = {"grains", "grams", "lbs", "kg", "blocks"};
        ArrayAdapter c4adapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, c4array);
        c4units.setAdapter(c4adapter);
        c4units.setSelection(sharedpreferences.getInt(c4pref, 1));
        c4units.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                editor.putInt(c4pref, c4units.getSelectedItemPosition());
                editor.commit();
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        detcordtype = (Spinner) findViewById(R.id.detcordtype);
        String[] detarray = {"Lightweight (25 gr/ft)-M977", "40 gr/ft", "41 gr/ft", "Pilofilm (42 gr/ft)-M456", "Wirebound (42 gr/ft)-M457", "43 gr/ft", "44 gr/ft", "45 gr/ft", "46 gr/ft", "47 gr/ft", "48 gr/ft", "49 gr/ft", "50 gr/ft", "51 gr/ft", "52 gr/ft", "53 gr/ft", "54 gr/ft", "55 gr/ft", "56 gr/ft", "57 gr/ft", "58 gr/ft", "59 gr/ft", "60 gr/ft", "Heavyload (100 gr/ft)-MN34", "100 gr/ft-MU42", "Heavyload (200 gr/ft)-MN34", "200 gr/ft-MU41", "400 gr/ft-MU40", "666 gr/ft-MU43"};
        ArrayAdapter detadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, detarray);
        detcordtype.setAdapter(detadapter);
        detcordtype.setSelection(sharedpreferences.getInt(detpref, 3));
        detcordtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                editor.putInt(detpref, detcordtype.getSelectedItemPosition());
                editor.commit();
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        detcordinput = (EditText) findViewById(R.id.detcordinput);
        detcordinput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        detcordinput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (detcordinput.getText().length() > 0 && detcordinput.getText().toString().matches(".*\\d.*")) {
                    dettimes.setEnabled(true);
                    calculatetool();
                }
                if (detcordinput.getText().length() == 0) {
                    dettimes.setEnabled(false);
                    detcordinput.setHint("0");
                    calculatetool();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        detcordunits = (Spinner) findViewById(R.id.detcordunits);
        String[] detunitarray = {"in", "ft", "cm", "m"};
        ArrayAdapter detunitadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, detunitarray);
        detcordunits.setAdapter(detunitadapter);
        detcordunits.setSelection(0);
        detcordunits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        detatype = (Spinner) findViewById(R.id.detatype);
        String[] detaarray = {"C1 Primasheet 1000 - M994", "C1.5 Primasheet 1000 - M993", "C2 Primasheet 1000 - M980", "C3 Primasheet 1000 - M981", "C4 Primasheet 1000 - M982", "C5 Primasheet 1000 - M983", "C6 Primasheet 1000 - M984", "C7 Primasheet 1000 - M985", "C8 Primasheet 1000 - M986", "C1 Primasheet 2000", "C2 Primasheet 2000", "C3 Primasheet 2000", "C4 Primasheet 2000", "C5 Primasheet 2000", "C6 Primasheet 2000"};
        ArrayAdapter detaadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, detaarray);
        detatype.setAdapter(detaadapter);
        detatype.setSelection(sharedpreferences.getInt(detapref, 2));
        detatype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                editor.putInt(detapref, detatype.getSelectedItemPosition());
                editor.commit();
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        detawidth = (EditText) findViewById(R.id.widthinput);
        detawidth.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        detawidth.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (detawidth.getText().length() > 0 && detawidth.getText().toString().matches(".*\\d.*"))
                    calculatetool();
                if (detawidth.getText().length() == 0) {
                    detawidth.setHint("0");
                    calculatetool();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        widthunit = (Spinner) findViewById(R.id.widthunit);
        String[] widtharray = {"in", "ft", "cm", "m"};
        ArrayAdapter widthadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, widtharray);
        widthunit.setAdapter(widthadapter);
        widthunit.setSelection(0);
        widthunit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        detalength = (EditText) findViewById(R.id.lengthinput);
        detalength.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        detalength.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (detalength.getText().length() > 0 && detalength.getText().toString().matches(".*\\d.*")) {
                    calculatetool();
                }
                if (detalength.getText().length() == 0) {
                    detalength.setHint("0");
                    calculatetool();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        lenghtunit = (Spinner) findViewById(R.id.lengthunits);
        String[] lengtharray = {"in", "ft", "cm", "m"};
        ArrayAdapter lengthadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, lengtharray);
        lenghtunit.setAdapter(lengthadapter);
        lenghtunit.setSelection(0);
        lenghtunit.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        flsctype = (Spinner) findViewById(R.id.flsctype);
        String[] flscarray = {"5400 GPF-MM54/MK149 (PETN)", "2400 GPF-MM53/MK145 (PETN)", "1200 GPF-MM52/MK144 (PETN)", "900 GPF ECT (PETN)", "600 GPF-ML19 (H-6)", "600 GPF-MM40 (RDX)", "600 GPF-MM48 (H-6)", "600 GPF-MM51/MK143 (PETN)", "500 GPF-ML18 (H-6)", "450 GPF ECT (PETN)", "400 GPF-ML17 (H-6)", "400 GPF-MM39 (RDX)", "400 GPF-MM47 (H-6)", "300 GPF-ML16 (H-6)", "300 GPF-MK142 (PETN)", "225 GPF-ML15 (H-6)", "225 GPF-MM38 (RDX)", "225 GPF-MM46 (H-6)", "200 GPF-MM37 (RDX)", "150 GPF-MM36 (RDX)", "125 GPF-ML14 (H-6)", "125 GPF-MM45 (H-6)", "100 GPF-MM35 (RDX)", "75 GPF-ML13 (H-6)", "75 GPF-MM34 (RDX)", "75 GPF-MM44 (H-6)", "60 GPF-ML12 (H-6)", "60 GPF-ML33 (RDX)", "60 GPF-ML43 (H-6)", "40 GPF-ML11 (H-6)", "40 GPF-MM32 (RDX)", "40 GPF-MM42 (H-6)", "30 GPF-ML10 (H-6)", "30 GPF-MM41 (H-6)", "25 GPF-MM31 (RDX)", "20 GPF-ML09 (H-6)"};
        ArrayAdapter flscadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, flscarray);
        flsctype.setAdapter(flscadapter);
        flsctype.setSelection(sharedpreferences.getInt(flscpref, 7));
        flsctype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                editor.putInt(flscpref, flsctype.getSelectedItemPosition());
                editor.commit();
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        flscinput = (EditText) findViewById(R.id.flscinput);
        flscinput.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
        flscinput.addTextChangedListener(new TextWatcher() {
            public void afterTextChanged(Editable s) {
                if (flscinput.getText().length() > 0 && flscinput.getText().toString().matches(".*\\d.*")) {
                    flsctimes.setEnabled(true);
                    calculatetool();
                }
                if (flscinput.getText().length() == 0) {
                    flsctimes.setEnabled(false);
                    flscinput.setHint("0");
                    calculatetool();
                }
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }
        });

        flscunits = (Spinner) findViewById(R.id.flscunits);
        String[] flscunitarray = {"in", "ft", "cm", "m"};
        ArrayAdapter flscunitadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, flscunitarray);
        flscunits.setAdapter(flscunitadapter);
        flscunits.setSelection(0);
        flscunits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        boostnum = (Spinner) findViewById(R.id.boostnum);
        String[] boostarray = {"0", "¼", "⅓", "½", "¾", "1"};
        ArrayAdapter boostadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, boostarray);
        boostnum.setAdapter(boostadapter);
        boostnum.setSelection(0);
        boostnum.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                if (boostnum.getSelectedItemPosition() == 0)
                    boostertimes.setEnabled(false);
                if (boostnum.getSelectedItemPosition() > 0)
                    boostertimes.setEnabled(true);
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        dettimes = (Spinner) findViewById(R.id.dettimes);
        String[] dettimesarray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter dettimesadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, dettimesarray);
        dettimes.setAdapter(dettimesadapter);
        dettimes.setSelection(0);
        dettimes.setEnabled(false);
        dettimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        flsctimes = (Spinner) findViewById(R.id.flsctimes);
        String[] flsctimesarray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter flsctimesadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, flsctimesarray);
        flsctimes.setAdapter(flsctimesadapter);
        flsctimes.setSelection(0);
        flsctimes.setEnabled(false);
        flsctimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        boostertimes = (Spinner) findViewById(R.id.boostertimes);
        String[] boostertimesarray = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
        ArrayAdapter boostertimesadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, boostertimesarray);
        boostertimes.setAdapter(boostertimesadapter);
        boostertimes.setSelection(0);
        boostertimes.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        caps = (Spinner) findViewById(R.id.caps);
        String[] capsarray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "10",};
        ArrayAdapter capsadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, capsarray);
        caps.setAdapter(capsadapter);
        caps.setSelection(0);
        caps.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                calculatetool();
            }

            @Override
            public void onNothingSelected(AdapterView<?> arg0) {

            }
        });

        settings = (Button) findViewById(R.id.setting);
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.custom3);
                Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonsave);
                Button dialogButton2 = (Button) dialog.findViewById(R.id.dialogButtonclose);
                Button dialogButton3 = (Button) dialog.findViewById(R.id.dialogButtondefault);

                EditText dialogcapweight = (EditText) dialog.findViewById(R.id.capweight);
                dialogcapweight.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                dialogcapweight.setText(capweight.toString());

                Spinner capmake = (Spinner) dialog.findViewById(R.id.capmake);
                String[] capmakearray = {"PETN", "RDX"};
                ArrayAdapter capmakeadapter = new ArrayAdapter(getApplicationContext(), R.layout.spinner, capmakearray);
                capmake.setAdapter(capmakeadapter);
                capmake.setSelection(Integer.parseInt(capmakeval));

                EditText dialogc4re = (EditText) dialog.findViewById(R.id.c4re);
                dialogc4re.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                dialogc4re.setText(c4re.toString());

                EditText dialogboosterre = (EditText) dialog.findViewById(R.id.boosterre);
                dialogboosterre.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                dialogboosterre.setText(boosterre.toString());

                EditText dialogprima1000re = (EditText) dialog.findViewById(R.id.primasheet1000re);
                dialogprima1000re.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                dialogprima1000re.setText(prima1000re.toString());

                EditText dialogprima2000re = (EditText) dialog.findViewById(R.id.primasheet2000re);
                dialogprima2000re.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                dialogprima2000re.setText(prima2000re.toString());

                EditText dialogpetnre = (EditText) dialog.findViewById(R.id.petnre);
                dialogpetnre.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                dialogpetnre.setText(petnre.toString());

                EditText dialogrdxre = (EditText) dialog.findViewById(R.id.rdxre);
                dialogrdxre.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                dialogrdxre.setText(rdxre.toString());

                EditText dialogh6re = (EditText) dialog.findViewById(R.id.h6re);
                dialogh6re.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                dialogh6re.setText(h6re.toString());

                EditText dialogdetcordre = (EditText) dialog.findViewById(R.id.detcordre);
                dialogdetcordre.setFilters(new InputFilter[]{new InputFilter.LengthFilter(6)});
                dialogdetcordre.setText(detcordre.toString());

                RadioButton yes = (RadioButton) dialog.findViewById(R.id.yes);
                RadioButton no = (RadioButton) dialog.findViewById(R.id.no);

                if (roundup) {
                    yes.setChecked(true);
                } else {
                    no.setChecked(true);
                }

                RadioButton feetradio = (RadioButton) dialog.findViewById(R.id.feet);
                RadioButton metersradio = (RadioButton) dialog.findViewById(R.id.meters);

                if (feet) {
                    feetradio.setChecked(true);
                } else {
                    metersradio.setChecked(true);
                }

                // if button is clicked, close the custom dialog
                dialogButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        RadioButton yes = (RadioButton) dialog.findViewById(R.id.yes);
                        if (yes.isChecked()) {
                            editor.putBoolean("roundup", true);
                            roundup = true;
                        } else {
                            editor.putBoolean("roundup", false);
                            roundup = false;
                        }

                        RadioButton feetradio = (RadioButton) dialog.findViewById(R.id.feet);
                        if (feetradio.isChecked()) {
                            editor.putBoolean("feet", true);
                            feet = true;
                        } else {
                            editor.putBoolean("feet", false);
                            feet = false;
                        }

                        EditText dialogc4re = (EditText) dialog.findViewById(R.id.c4re);
                        EditText dialogcapweight = (EditText) dialog.findViewById(R.id.capweight);
                        EditText dialogboosterre = (EditText) dialog.findViewById(R.id.boosterre);
                        EditText dialogprima1000re = (EditText) dialog.findViewById(R.id.primasheet1000re);
                        EditText dialogprima2000re = (EditText) dialog.findViewById(R.id.primasheet2000re);
                        EditText dialogpetnre = (EditText) dialog.findViewById(R.id.petnre);
                        EditText dialogrdxre = (EditText) dialog.findViewById(R.id.rdxre);
                        EditText dialogh6re = (EditText) dialog.findViewById(R.id.h6re);
                        EditText dialogdetcordre = (EditText) dialog.findViewById(R.id.detcordre);
                        Spinner capmake = (Spinner) dialog.findViewById(R.id.capmake);

                        editor.putString("c4re", dialogc4re.getText().toString());
                        editor.putString("boosterre", dialogboosterre.getText().toString());
                        editor.putString("detcordre", dialogdetcordre.getText().toString());
                        editor.putString("prima1000re", dialogprima1000re.getText().toString());
                        editor.putString("prima2000re", dialogprima2000re.getText().toString());
                        editor.putString("petnre", dialogpetnre.getText().toString());
                        editor.putString("rdxre", dialogrdxre.getText().toString());
                        editor.putString("h6re", dialogh6re.getText().toString());
                        editor.putString("cappref", dialogcapweight.getText().toString());
                        int sel = capmake.getSelectedItemPosition();
                        editor.putString("capmake", String.valueOf(sel));
                        editor.commit();
                        getprefs();
                        dialog.dismiss();
                        calculatetool();
                    }
                });

                dialogButton2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                        calculatetool();
                    }
                });

                dialogButton3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /*editor.putString("c4re", c4default.toString());
                        editor.putString("boosterre", boosterdefault.toString());
                        editor.putString("detcordre", detcorddefault.toString());
                        editor.putString("prima1000re", prima1000default.toString());
                        editor.putString("prima2000re", prima2000default.toString());
                        editor.putString("petnre", petndefault.toString());
                        editor.putString("rdxre", rdxdefault.toString());
                        editor.putString("h6re", h6default.toString());
                        editor.commit();
                        getprefs();*/
                        EditText dialogc4re = (EditText) dialog.findViewById(R.id.c4re);
                        dialogc4re.setText(c4default);
                        EditText dialogcapweight = (EditText) dialog.findViewById(R.id.capweight);
                        dialogcapweight.setText(capdefault);
                        EditText dialogboosterre = (EditText) dialog.findViewById(R.id.boosterre);
                        dialogboosterre.setText(boosterdefault);
                        EditText dialogprima1000re = (EditText) dialog.findViewById(R.id.primasheet1000re);
                        dialogprima1000re.setText(prima1000default);
                        EditText dialogprima2000re = (EditText) dialog.findViewById(R.id.primasheet2000re);
                        dialogprima2000re.setText(prima2000default);
                        EditText dialogpetnre = (EditText) dialog.findViewById(R.id.petnre);
                        dialogpetnre.setText(petndefault);
                        EditText dialogrdxre = (EditText) dialog.findViewById(R.id.rdxre);
                        dialogrdxre.setText(rdxdefault);
                        EditText dialogh6re = (EditText) dialog.findViewById(R.id.h6re);
                        dialogh6re.setText(h6default);
                        EditText dialogdetcordre = (EditText) dialog.findViewById(R.id.detcordre);
                        dialogdetcordre.setText(detcorddefault);
                        Spinner capmake = (Spinner) dialog.findViewById(R.id.capmake);
                        capmake.setSelection(0);
                        capmakeval = "0";
                    }
                });

                dialog.show();

            }
        });
        settings.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                c4.setText("");
                caps.setSelection(0);
                boostnum.setSelection(0);
                detcordinput.setText("");
                detawidth.setText("");
                detalength.setText("");
                flscinput.setText("");
                calculatetool();
                return true;
            }
        });


    }

    public void calculatetool() {
        Double total = 0.0;
        Double c4val = 0.0;
        Double detval = 0.0;
        Double detaw = 0.0;
        Double detat = 0.0;
        Double detal = 0.0;
        Double flscval = 0.0;
        Double capsval = 0.0;
        Double boosterval = 0.0;

        if (c4.getText().toString().length() > 0)
            c4val = Double.parseDouble(c4.getText().toString());
        if (detcordinput.getText().toString().length() > 0)
            detval = Double.parseDouble(detcordinput.getText().toString());
        if (detawidth.getText().toString().length() > 0)
            detaw = Double.parseDouble(detawidth.getText().toString());
        if (detalength.getText().toString().length() > 0)
            detal = Double.parseDouble(detalength.getText().toString());
        if (flscinput.getText().toString().length() > 0)
            flscval = Double.parseDouble(flscinput.getText().toString());

        if (c4units.getSelectedItemPosition() == 0)
            c4val = c4val * 0.000142857 * c4re;
        if (c4units.getSelectedItemPosition() == 1)
            c4val = c4val * 0.00220462 * c4re;
        if (c4units.getSelectedItemPosition() == 2)
            c4val = c4val * c4re;
        if (c4units.getSelectedItemPosition() == 3)
            c4val = c4val * 2.20462 * c4re;
        if (c4units.getSelectedItemPosition() == 4)
            c4val = c4val * 1.25 * c4re;

        total = total + c4val;

        if (boostnum.getSelectedItemPosition() == 0)
            boosterval = 0.0;
        if (boostnum.getSelectedItemPosition() == 1)
            boosterval = 20 * 0.00220462 * boosterre * 0.25;
        if (boostnum.getSelectedItemPosition() == 2)
            boosterval = 20 * 0.00220462 * boosterre * 0.3333;
        if (boostnum.getSelectedItemPosition() == 3)
            boosterval = 20 * 0.00220462 * boosterre * 0.5;
        if (boostnum.getSelectedItemPosition() == 4)
            boosterval = 20 * 0.00220462 * boosterre * 0.75;
        if (boostnum.getSelectedItemPosition() == 5)
            boosterval = 20 * 0.00220462 * boosterre;

        Double booster = boosterval * Double.parseDouble(boostertimes.getSelectedItem().toString());

        total = total + booster;

        if(capmakeval.equals("0"))
        capsval = capweight * 0.00220462 *1.66 * Double.parseDouble(caps.getSelectedItem().toString());
        else
            capsval = capweight * 0.00220462 *1.46*  Double.parseDouble(caps.getSelectedItem().toString());

        total = total + capsval;

        if (detcordunits.getSelectedItemPosition() == 0)
            detval = detval * 0.0833333;
        if (detcordunits.getSelectedItemPosition() == 2)
            detval = detval * 0.0328084;
        if (detcordunits.getSelectedItemPosition() == 3)
            detval = detval * 3.28084;

        if (detcordtype.getSelectedItemPosition() == 0)
            detval = detval * detcordre * 25.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 1)
            detval = detval * detcordre * 40.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 2)
            detval = detval * detcordre * 41.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 3)
            detval = detval * detcordre * 42.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 4)
            detval = detval * detcordre * 42.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 5)
            detval = detval * detcordre * 43.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 6)
            detval = detval * detcordre * 44.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 7)
            detval = detval * detcordre * 45.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 8)
            detval = detval * detcordre * 46.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 9)
            detval = detval * detcordre * 47.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 10)
            detval = detval * detcordre * 48.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 11)
            detval = detval * detcordre * 49.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 12)
            detval = detval * detcordre * 50.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 13)
            detval = detval * detcordre * 51.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 14)
            detval = detval * detcordre * 52.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 15)
            detval = detval * detcordre * 53.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 16)
            detval = detval * detcordre * 54.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 17)
            detval = detval * detcordre * 55.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 18)
            detval = detval * detcordre * 56.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 19)
            detval = detval * detcordre * 57.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 20)
            detval = detval * detcordre * 58.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 21)
            detval = detval * detcordre * 59.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 22)
            detval = detval * detcordre * 60.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 23)
            detval = detval * detcordre * 100.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 24)
            detval = detval * detcordre * 100.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 25)
            detval = detval * detcordre * 200.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 26)
            detval = detval * detcordre * 200.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 27)
            detval = detval * detcordre * 400.0 * 0.000142857;
        if (detcordtype.getSelectedItemPosition() == 28)
            detval = detval * detcordre * 666.0 * 0.000142857;

        detval = detval * Double.parseDouble(dettimes.getSelectedItem().toString());


        total = total + detval;

        if (widthunit.getSelectedItemPosition() == 1)
            detaw = detaw * 12.0;
        if (widthunit.getSelectedItemPosition() == 2)
            detaw = detaw * 0.393701;
        if (widthunit.getSelectedItemPosition() == 3)
            detaw = detaw * 39.3701;

        if (lenghtunit.getSelectedItemPosition() == 1)
            detal = detal * 12.0;
        if (lenghtunit.getSelectedItemPosition() == 2)
            detal = detal * 0.393701;
        if (lenghtunit.getSelectedItemPosition() == 3)
            detal = detal * 39.3701;

        detat = detaw * detal * 0.00220462;

        if (detatype.getSelectedItemPosition() == 0)
            detat = detat * prima1000re;
        if (detatype.getSelectedItemPosition() == 1)
            detat = detat * 1.5 * prima1000re;
        if (detatype.getSelectedItemPosition() == 2)
            detat = detat * 2 * prima1000re;
        if (detatype.getSelectedItemPosition() == 3)
            detat = detat * 3 * prima1000re;
        if (detatype.getSelectedItemPosition() == 4)
            detat = detat * 4 * prima1000re;
        if (detatype.getSelectedItemPosition() == 5)
            detat = detat * 5 * prima1000re;
        if (detatype.getSelectedItemPosition() == 6)
            detat = detat * 6 * prima1000re;
        if (detatype.getSelectedItemPosition() == 7)
            detat = detat * 7 * prima1000re;
        if (detatype.getSelectedItemPosition() == 8)
            detat = detat * 8 * prima1000re;
        if (detatype.getSelectedItemPosition() == 9)
            detat = detat * 1 * prima2000re;
        if (detatype.getSelectedItemPosition() == 10)
            detat = detat * 2 * prima2000re;
        if (detatype.getSelectedItemPosition() == 11)
            detat = detat * 3 * prima2000re;
        if (detatype.getSelectedItemPosition() == 12)
            detat = detat * 4 * prima2000re;
        if (detatype.getSelectedItemPosition() == 13)
            detat = detat * 5 * prima2000re;
        if (detatype.getSelectedItemPosition() == 14)
            detat = detat * 6 * prima2000re;

        total = total + detat;

        if (flscunits.getSelectedItemPosition() == 0)
            flscval = flscval / 12.0;
        if (flscunits.getSelectedItemPosition() == 2)
            flscval = flscval * 0.0328084;
        if (flscunits.getSelectedItemPosition() == 3)
            flscval = flscval * 3.28084;

        if (flsctype.getSelectedItemPosition() == 0)
            flscval = flscval * petnre * 5400.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 1)
            flscval = flscval * petnre * 2400.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 2)
            flscval = flscval * petnre * 1200.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 3)
            flscval = flscval * petnre * 900.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 4)
            flscval = flscval * h6re * 600.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 5)
            flscval = flscval * rdxre * 600.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 6)
            flscval = flscval * h6re * 600.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 7)
            flscval = flscval * petnre * 600.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 8)
            flscval = flscval * h6re * 500.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 9)
            flscval = flscval * petnre * 450.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 10)
            flscval = flscval * h6re * 400.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 11)
            flscval = flscval * rdxre * 400.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 12)
            flscval = flscval * h6re * 400.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 13)
            flscval = flscval * h6re * 300.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 14)
            flscval = flscval * petnre * 300.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 15)
            flscval = flscval * h6re * 225.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 16)
            flscval = flscval * rdxre * 225.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 17)
            flscval = flscval * h6re * 225.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 18)
            flscval = flscval * rdxre * 200.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 19)
            flscval = flscval * rdxre * 150.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 20)
            flscval = flscval * h6re * 125.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 21)
            flscval = flscval * h6re * 125.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 22)
            flscval = flscval * rdxre * 100.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 23)
            flscval = flscval * h6re * 75.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 24)
            flscval = flscval * rdxre * 75.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 25)
            flscval = flscval * h6re * 75.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 26)
            flscval = flscval * h6re * 60.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 27)
            flscval = flscval * rdxre * 60.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 28)
            flscval = flscval * h6re * 60.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 29)
            flscval = flscval * h6re * 40.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 30)
            flscval = flscval * rdxre * 40.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 31)
            flscval = flscval * h6re * 40.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 32)
            flscval = flscval * h6re * 30.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 33)
            flscval = flscval * h6re * 30.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 34)
            flscval = flscval * rdxre * 25.0 * 0.000142857;
        if (flsctype.getSelectedItemPosition() == 35)
            flscval = flscval * h6re * 20.0 * 0.000142857;

        flscval = flscval * Double.parseDouble(flsctimes.getSelectedItem().toString());

        total = total + flscval;

        result1.setText(String.format("%.2f", total));

        if (total < 10.00)
            result1.setText(String.format("%.4f", total));

        if (caps.getSelectedItemPosition() == 0 && boostnum.getSelectedItemPosition() == 0 && c4.getText().toString().length() == 0 && detcordinput.getText().toString().length() == 0 && detalength.getText().toString().length() == 0 && detawidth.getText().toString().length() == 0 && flscinput.getText().toString().length() == 0)
            result1.setText("0");

        if (feet) {
            if (!roundup) {
                msdexternal.setText(String.format("%.2f", kfactor(total)));
                msdinternal.setText(String.format("%.2f", 2.0* kfactor(total)));
                msdinternalunits.setText(" ft");
                msdexternalunits.setText(" ft");
            }else {
                Double z = Math.ceil(kfactor(total));
                Double r = Math.ceil(2.0 * kfactor(total));
                msdexternal.setText(NumberFormat.getInstance().format(z));
                msdinternal.setText(NumberFormat.getInstance().format(r));
                msdinternalunits.setText(" ft");
                msdexternalunits.setText(" ft");
            }
        }else{
            if (!roundup) {
                msdexternal.setText(String.format("%.2f", kfactormeters(total)));
                msdinternal.setText(String.format("%.2f", 2.0* kfactormeters(total)));
                msdinternalunits.setText(" m");
                msdexternalunits.setText(" m");
            }else {
                Double z = Math.ceil(kfactormeters(total));
                Double r = Math.ceil(2.0 * kfactormeters(total));
                msdexternal.setText(NumberFormat.getInstance().format(z));
                msdinternal.setText(NumberFormat.getInstance().format(r));
                msdinternalunits.setText(" m");
                msdexternalunits.setText(" m");
            }
        }
        if (total <= 0.0) {
            result1.setText("0");
            msdexternal.setText("0");
            msdinternal.setText("0");
        }
    }

    public void getprefs() {
        ranBefore = sharedpreferences.getBoolean(ranbefore, false);
        ranBefore2 = sharedpreferences.getBoolean(ranbefore2, false);
        detcordre = Double.parseDouble(sharedpreferences.getString("detcordre", detcorddefault));
        c4re = Double.parseDouble(sharedpreferences.getString("c4re", c4default));
        capweight = Double.parseDouble(sharedpreferences.getString("cappref", capdefault));
        boosterre = Double.parseDouble(sharedpreferences.getString("boosterre", boosterdefault));
        prima1000re = Double.parseDouble(sharedpreferences.getString("prima1000re", prima1000default));
        prima2000re = Double.parseDouble(sharedpreferences.getString("prima2000re", prima2000default));
        rdxre = Double.parseDouble(sharedpreferences.getString("rdxre", rdxdefault));
        h6re = Double.parseDouble(sharedpreferences.getString("h6re", h6default));
        petnre = Double.parseDouble(sharedpreferences.getString("petnre", petndefault));
        feet = sharedpreferences.getBoolean("feet", true);
        roundup = sharedpreferences.getBoolean("roundup", true);
        capmakeval = sharedpreferences.getString("capmake", "0");
    }

    public Double kfactor(Double n) {
        Double exp = (1.00) / 3;
        Double power = Math.pow(n, exp);
        return 18.00 * power;
    }

    public Double kfactormeters(Double n) {
        Double exp = (1.00) / 3;
        Double power = Math.pow(n, exp);
        return 18.00 * power * 0.30479999;
    }
}
