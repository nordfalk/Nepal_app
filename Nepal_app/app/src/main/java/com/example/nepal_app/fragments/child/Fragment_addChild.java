package com.example.nepal_app.fragments.child;


import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nepal_app.Factory.GetAndSet;
import com.example.nepal_app.Factory.POJO;
import com.example.nepal_app.R;
import com.example.nepal_app.fragments.ProfileFragment;
import com.google.gson.Gson;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Objects;


import static android.app.Activity.RESULT_OK;


public class Fragment_addChild extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener {
    private Button save, pick_date;
    private EditText name, gender;
    private ImageView  preview;
    private ConstraintLayout billede;
    private ArrayList<ChildObj> childArr = new ArrayList<>();
    private long currentDate;

    //Gallery select variables
    public static final int GALLERY_SELECT = 1887;

    //Upload variables
    String ImageString = "flexicu";
    Bitmap ImageData = null;
    Uri imageUri = null;
    POJO pojo;
    public Fragment_addChild() {
        // Required empty public constructor
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view2 = inflater.inflate(R.layout.fragment_add_child, container, false);
        save = view2.findViewById(R.id.save_button);
        billede = view2.findViewById(R.id.billede);
        name = view2.findViewById(R.id.name);
        pick_date = view2.findViewById(R.id.pickdate_button);
        gender = view2.findViewById(R.id.gender);
        preview = view2.findViewById(R.id.imageViewPreview);
        preview.setVisibility(View.INVISIBLE);

        pick_date.setOnClickListener(this);
        save.setOnClickListener(this);

        pojo = POJO.getInstance();

        childArr = pojo.getChildArr(getContext());

        //If you don't have the permission to open the Gallery ask for them, otherwise open the gallery
        billede.setOnClickListener((view) ->{
            Intent photoPickerIntent = new Intent(Intent.ACTION_PICK);
            photoPickerIntent.setType("image/*");
            startActivityForResult(photoPickerIntent, GALLERY_SELECT);
        });



        return view2;
    }


    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        Bitmap photo = null;
        if (resultCode == RESULT_OK) {
            try {
                imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                photo = BitmapFactory.decodeStream(imageStream);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
            ImageData = photo;
        }


    }

    @Override
    public void onClick(View v) {
        if (v == save){
            childArr.add(new ChildObj(String.valueOf(name.getText()),currentDate ,String.valueOf(gender.getText())));
            saveChild();
            FragmentManager fm = Objects.requireNonNull(getActivity()).getSupportFragmentManager();
            fm.popBackStack();
        }

        if (v == pick_date) {
            showDateDialog();
        }
    }


    /**
     *Method to create the date dialog
     */
    private void showDateDialog(){
        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),
                this,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH)
        );
        datePickerDialog.show();
        }


    @Override
    /**
     * Implements the onDateSet from the DatePickerDialog to get tha data picked date from the calendar
     *
     */
    public void onDateSet(DatePicker view, int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year,month,day);
        pick_date.setText(day + "/" + (month+1) + "/" + year);
        currentDate = c.getTimeInMillis();
    }

    /**
     * Saving the child in the cache
     */
    private void saveChild(){
    SharedPreferences sharedPreferences = this.getActivity().getSharedPreferences("Children", Context.MODE_PRIVATE);
    SharedPreferences.Editor editor = sharedPreferences.edit();
    Gson gson = new Gson();
    String json = gson.toJson(childArr);
    editor.putString("ChildArr",json);
    editor.apply();
    pojo.setChildArr(childArr);

    }
}


