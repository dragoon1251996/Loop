package com.t3h.whiyew.loophabit;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

//import android.app.TimePickerDialog;
//import android.icu.util.Calendar;
//import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
//import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;


public class MainActivity extends AppCompatActivity implements TimePickerDialog.OnTimeSetListener{
    public static final String KEY_NAME = "KEY";
    public static final String KEY_HABIT = "KEYHABIT";
    private static final int REQUEST_CODE_UPDATE = 1;
    public static final String KEY_DATA = "KEY2";
    private LinearLayout layoutday,layoutitem;
    private Boolean KT=true;
    private  NameHabit nameHabitColor;
    int a,b,index;
    private MyAdapter myAdapter;
    private ArrayList<NameHabit> arrData=new ArrayList<>();
    private ListView listView;
    private Dialog dialog,dialogColor;
    private TextView txtThu1,txtThu2,txtThu3,txtThu4,txtNgay1,txtNgay2,txtNgay3,txtNgay4;
    private Toolbar toolbar,toolbar2;
    private  NameHabit nameHabit;
    private Boolean CHECK=true;
    private ArrayList<Integer> arrIndex2;

    Data data;
    @TargetApi(Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);






        data = new Data(this, null,null,3);
     //   initData();
        listView= (ListView) findViewById(R.id.lv);
        arrData=data.getAllContacts();
        myAdapter=new MyAdapter(this,  arrData);
        listView.setAdapter(myAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
//                nameHabit= new NameHabit(data.getAllContacts().get(i).getId(),data.getAllContacts().get(i).getTenThuocTinh(),data.getAllContacts().get(i).getQuestision(),data.getAllContacts().get(i).getNgay(),data.getAllContacts().get(i).getColor(),data.getAllContacts().get(i).getRepeat(),data.getAllContacts().get(i).getGio(),data.getAllContacts().get(i).getPhut());
                Intent  intent= new Intent(MainActivity.this,InnerActivity.class);

                //nameHabit
//                Bundle bundle = new Bundle();
//                bundle.putSerializable(KEY_HABIT, nameHabit);

                //data
//                Bundle bundle2 = new Bundle();
//                bundle2.putSerializable(KEY_DATA,data);

//                  Data data1=data;
                intent.putExtra(KEY_HABIT,i);
//                intent.putExtra(KEY_DATA,data1);

//                intent.putExtras(bundle);
//                intent.putExtras(bundle2);
                startActivityForResult(intent,1);
//                startActivity(intent);
            }
        });
        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                OK=false;
                toolbar.setVisibility(View.INVISIBLE);
                toolbar2.setVisibility(View.VISIBLE);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
                return false;


            }
        });

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE_MODAL);
        // Capture ListView item click
        listView.setMultiChoiceModeListener(new AbsListView.MultiChoiceModeListener() {

            @Override
            public void onItemCheckedStateChanged(ActionMode mode, int position, long id, boolean checked) {
                // Capture total checked items
                final int checkedCount = listView.getCheckedItemCount();
                // Set the CAB title according to total checked items
                mode.setTitle(checkedCount + " Selected");
                // Calls toggleSelection method from ListViewAdapter Class
                myAdapter.toggleSelection(position);
                index=position;
//                arrIndex2=new ArrayList<Integer>();
//                arrIndex2.add(position);

                if(checkedCount>1){
                    Menu menu=mode.getMenu();
                    menu.clear();
//                    menu.setGroupVisible(R.menu.itemmenu4,true);
                   mode.getMenuInflater().inflate(R.menu.itemmenu4, menu);

                }
                else {
                    Menu menu=mode.getMenu();
                    menu.clear();
//                    menu.setGroupVisible(R.menu.itemmenu3,true);
//                    menu.setGroupVisible(R.menu.itemmenu4,false);
                    mode.getMenuInflater().inflate(R.menu.itemmenu3, menu);
                }

            }

            @Override
            public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.delete:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected = myAdapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected.size() - 1); i >= 0; i--) {
                            if (selected.valueAt(i)) {
                                NameHabit selecteditem = (NameHabit) myAdapter
                                        .getItem(selected.keyAt(i));
                                // Remove selected items following the ids
//                                myAdapter.remove(selecteditem);
                                deleteData(selecteditem);

                            }
                        }
                        // Close CAB
                        mode.finish();
                        myAdapter.removeAll();
                        return true;
                    case R.id.edit_main:
                        dialogEdit();
                        mode.finish();
                        return true;
                    case R.id.editColor_main:
                        ArrayList<Integer> arrIndex2=myAdapter.getArrayList();
                        dialogColor(arrIndex2);
                        mode.finish();
                        return true;
                    case R.id.archive:
                        return true;
                    //////////////2
                    case R.id.painColor2:
                        ArrayList<Integer> arrIndex=myAdapter.getArrayList();
                        dialogColor(arrIndex);
                        mode.finish();


                        return true;
                    case R.id.save:
                        return true;
                    case R.id.delete2:
                        // Calls getSelectedIds method from ListViewAdapter Class
                        SparseBooleanArray selected2= myAdapter
                                .getSelectedIds();
                        // Captures all selected ids with a loop
                        for (int i = (selected2.size() - 1); i >= 0; i--) {
                            if (selected2.valueAt(i)) {
                                NameHabit selecteditem = (NameHabit) myAdapter
                                        .getItem(selected2.keyAt(i));
                                // Remove selected items following the ids
//                                myAdapter.remove(selecteditem);
                                deleteData(selecteditem);

                            }
                        }
                        // Close CAB
                        mode.finish();
                        myAdapter.removeAll();
                        return true;

                    default:
                        return false;
                }
            }


            @Override
            public boolean onCreateActionMode(ActionMode mode, Menu menu) {
//                mode.getMenuInflater().inflate(R.menu.itemmenu3, menu);

                OK=false;
                toolbar.setVisibility(View.GONE);
//                toolbar2.setVisibility(View.VISIBLE);
                getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.colorAccent)));
                return true;
            }

            @Override
            public void onDestroyActionMode(ActionMode mode) {
                // TODO Auto-generated method stub
                myAdapter.removeSelection();
                myAdapter.removeAll();
                if(OK==false){
                    toolbar.setVisibility(View.VISIBLE);
//                    toolbar2.setVisibility(View.INVISIBLE);
                    OK=true;
                }


            }

            @Override
            public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
                // TODO Auto-generated method stub
                return false;
            }
        });



        toolbar= (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(Color.WHITE);

        toolbar2 =(Toolbar) findViewById(R.id.toolbar3);
        setSupportActionBar(toolbar2);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.trang), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);





        myAdapter.notifyDataSetChanged();
        txtThu1= (TextView) findViewById(R.id.txtThu1);
        txtThu2= (TextView) findViewById(R.id.txtThu2);
        txtThu3= (TextView) findViewById(R.id.txtThu3);
        txtThu4= (TextView) findViewById(R.id.txtThu4);
        txtNgay1= (TextView) findViewById(R.id.txtDay1);
        txtNgay2= (TextView) findViewById(R.id.txtDay2);
        txtNgay3= (TextView) findViewById(R.id.txtDay3);
        txtNgay4= (TextView) findViewById(R.id.txtDay4);
        layoutday= (LinearLayout) findViewById(R.id.layoutday);
        layoutitem= (LinearLayout) findViewById(R.id.layoutitem);


        Calendar calendar = Calendar.getInstance();
        int a=calendar.get(Calendar.DAY_OF_MONTH);
        int b=calendar.get(Calendar.DAY_OF_WEEK);

           txtNgay4.setText(a+"");
           txtNgay3.setText(a-1+"");
           txtNgay2.setText(a-2+"");
           txtNgay1.setText(a-3+"");


        txtThu4.setText("TH"+b);
        txtThu3.setText("TH"+(b-1));
        txtThu2.setText("TH"+(b-2));
        txtThu1.setText("TH"+(b-3));
        if(b==8||b==1){
            txtThu4.setText("CN");
        }
        if((b-1)==8||(b-1)==1){
            txtThu3.setText("CN");
        }
        if((b-2)==8||(b-2)==1){
            txtThu2.setText("CN");
        }
        if((b-3)==8||(b-3)==1){
            txtThu1.setText("CN");
        }
       }

    private void dialogEdit() {


        final Dialog dialog=new Dialog(this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
        LayoutInflater inflater = LayoutInflater.from(this);
        View view=inflater.inflate(R.layout.dialog_habit,null);
        final EditText edtName= (EditText) view.findViewById(R.id.tvName);
        ImageButton imgColor= (ImageButton) view.findViewById(R.id.imgColor);
        final EditText edtQue= (EditText) view.findViewById(R.id.tvQuestion);
        Button btnDis= (Button) view.findViewById(R.id.btnDis);
        Button btnSave= (Button) view.findViewById(R.id.btnSave);

//        int[] arrMangMau={R.color.color12,R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5,R.color.color6,R.color.color7,R.color.color8,R.color.color9,R.color.color10,R.color.color11,R.color.color12,R.color.color13};
//        edtName.setTextColor(ContextCompat.getColor(MainActivity.this,arrMangMau[b]));

        //Repeat
        final AppCompatSpinner compatSpinner= (AppCompatSpinner) view.findViewById(R.id.spinner);


        //customRepeat
        final LinearLayout linearLayout= (LinearLayout) view.findViewById(R.id.customRepeat);
        EditText edtTime= (EditText) view.findViewById(R.id.numerator);
        EditText edtDay= (EditText) view.findViewById(R.id.denominator);

        //Reminder gio vs phut
        Button btnOK= (Button) view.findViewById(R.id.btnOK);


        final LinearLayout linearLayout1= (LinearLayout) view.findViewById(R.id.layout_panel);
        final TextView edtTimePicker= (TextView) view.findViewById(R.id.edtTime);



        //Ngay
        final LinearLayout linearLayout2= (LinearLayout) view.findViewById(R.id.layout_panel2);
        linearLayout2.setVisibility(View.VISIBLE);
        final TextView txtDay= (TextView) view.findViewById(R.id.txtDay);




        edtName.setText(data.getAllContacts().get(index).getTenThuocTinh());
        edtQue.setText(data.getAllContacts().get(index).getQuestision());
            compatSpinner.setSelection(data.getAllContacts().get(index).getRepeat());
            b=data.getAllContacts().get(index).getColor();
            gio=data.getAllContacts().get(index).getGio();
            phut=data.getAllContacts().get(index).getPhut();
            repeat=data.getAllContacts().get(index).getRepeat();
            int[] arrMangMau2={R.color.color12,R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5,R.color.color6,R.color.color7,R.color.color8,R.color.color9,R.color.color10,R.color.color11,R.color.color12,R.color.color13};
            edtName.setTextColor(ContextCompat.getColor(MainActivity.this,arrMangMau2[b]));
            edtTimePicker.setText(" "+data.getAllContacts().get(index).getGio()+" : "+data.getAllContacts().get(index).getPhut()+" ");
            txtDay.setText(data.getAllContacts().get(index).getNgay());


        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NameHabit nameHabit2=new NameHabit(data.getAllContacts().get(index).getId(),edtName.getText().toString(),edtQue.getText().toString(),txtDay.getText().toString(),b,repeat,gio,phut);
//                data.addJapan(nameHabit2);
//                myAdapter=new MyAdapter(getBaseContext(),  data.getAllContacts());
//                listView.setAdapter(myAdapter);
//                myAdapter.notifyDataSetChanged();
               updateData(nameHabit2);

//                mainActivity.updateData(nameHabit2);
                dialog.dismiss();
            }
        });




        linearLayout2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final CharSequence[] items = {" Thứ bảy "," Chủ nhật "," Thứ hai "," Thứ ba "," Thứ tư "," Thứ năm "," Thứ sáu "};
                // arraylist to keep the selected items
                final ArrayList seletedItems=new ArrayList();

//                        for (int x = 0; x < seletedItems.size(); x++) {
//                            int a= (int) seletedItems.get(x);
//                            items[a].set (x, true);
//                        }
//                        for (int i = 0; i < seletedItems.size(); i++) {
//                            HashMap<String, String> asset = (HashMap<String, String>) seletedItems.get(i);
//                            asset.
//                            if (asset.get(KEY_CODE).equals(scanAsset)){
//                                asset.put(KEY_CHECKED, "Checked");
////                                Log.i("Asset code is ", scanAsset);
//                            }
//                        }
//                        myAdapter.notifyDataSetChanged();

//                        AlertDialog dialog1 = (AlertDialog) dialog;
//                        ListView v = dialog1.getListView();
//                        int i = 0;
//                        while(i < items.length) {
//                            v.setItemChecked(i, true);
//                            i++;
//                        }

                AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)

                        .setTitle("Select days")
                        .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                if (isChecked) {
                                    // If the user checked the item, add it to the selected items
                                    seletedItems.add(indexSelected);
                                } else if (seletedItems.contains(indexSelected)) {
                                    // Else, if the item is already in the array, remove it
                                    seletedItems.remove(Integer.valueOf(indexSelected));
                                }
                            }
                        }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                if(seletedItems.size()==items.length){
                                    txtDay.setText(" Any day of the week ");

                                }
                                else{
                                    String b="";
                                    for(int i=0;i<seletedItems.size();i++){
                                        int index= (int) seletedItems.get(i);
                                        b=b+items[index].toString()+",";
//                                            setTitle(seletedItems.get(i).toString()+",");
                                    }
                                    String c=b.substring(0,b.length()-1);
                                    txtDay.setText(c);
                                }

                                //  Your code when user clicked on OK
                                //  You can write the code  to save the selected item here
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {

                                //  Your code when user clicked on Cancel
                            }
                        }).create();
                dialog.show();
            }
        });
        linearLayout1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                       if(CHECK2){
                TimePickerDialog timepickerdialog = TimePickerDialog.newInstance(MainActivity.this,
                        data.getAllContacts().get(index).getGio(), data.getAllContacts().get(index).getPhut(),true);
                timepickerdialog.setThemeDark(false); //Dark Theme?
                timepickerdialog.vibrate(false); //vibrate on choosing time?
                timepickerdialog.dismissOnPause(false); //dismiss the dialog onPause() called?


                //Handling cancel event
                timepickerdialog.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
//                                   CHECK2=false;
                        gio=hourOfDay;
                        phut=minute;
                        edtTimePicker.setText(" "+hourOfDay+" : "+minute+" ");
                        if(edtTimePicker.getText().toString()!="Off"){
//                                       linearLayout2.setVisibility(View.VISIBLE);
                        }





                    }
                });
                timepickerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel(DialogInterface dialogInterface) {
                        Toast.makeText(MainActivity.this, "Cancel choosing time", Toast.LENGTH_SHORT).show();
                    }
                });
                timepickerdialog.show(getFragmentManager(), "Timepickerdialog"); //show time picker dialog
//

            }
        });


        imgColor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialogColor=new Dialog(MainActivity.this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View viewColor=inflater.inflate(R.layout.color_selection,null);



                ImageView img1= (ImageView) viewColor.findViewById(R.id.mau1);
                ImageView img2= (ImageView) viewColor.findViewById(R.id.mau2);
                ImageView img3= (ImageView) viewColor.findViewById(R.id.mau3);
                ImageView img4= (ImageView) viewColor.findViewById(R.id.mau4);
                ImageView img5= (ImageView) viewColor.findViewById(R.id.mau5);
                ImageView img6= (ImageView) viewColor.findViewById(R.id.mau6);
                ImageView img7= (ImageView) viewColor.findViewById(R.id.mau7);
                ImageView img8= (ImageView) viewColor.findViewById(R.id.mau8);
                ImageView img9= (ImageView) viewColor.findViewById(R.id.mau9);
                ImageView img10= (ImageView) viewColor.findViewById(R.id.mau10);
                ImageView img11= (ImageView) viewColor.findViewById(R.id.mau11);
                ImageView img12= (ImageView) viewColor.findViewById(R.id.mau12);
                ImageView img13= (ImageView) viewColor.findViewById(R.id.mau13);

                switch (b){
                    case 1:
                        img1.setImageResource(R.drawable.check4);
                        break;
                    case 2:
                        img2.setImageResource(R.drawable.check4);
                        break;
                    case 3:
                        img3.setImageResource(R.drawable.check4);
                        break;
                    case 4:
                        img4.setImageResource(R.drawable.check4);
                        break;
                    case 5:
                        img5.setImageResource(R.drawable.check4);
                        break;
                    case 6:
                        img6.setImageResource(R.drawable.check4);
                        break;
                    case 7:
                        img7.setImageResource(R.drawable.check4);
                        break;
                    case 8:
                        img8.setImageResource(R.drawable.check4);
                        break;
                    case 9:
                        img9.setImageResource(R.drawable.check4);
                        break;
                    case 10:
                        img10.setImageResource(R.drawable.check4);
                        break;
                    case 11:
                        img11.setImageResource(R.drawable.check4);
                        break;
                    case 12:
                        img12.setImageResource(R.drawable.check4);
                        break;
                    case 13:
                        img13.setImageResource(R.drawable.check4);
                        break;



                }

//                        arrImageColor.get(a).setImageResource(R.drawable.check4);
                img1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        b=1;

                edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color1));
//                                img1.setImageResource(R.drawable.check4);
                        dialogColor.dismiss();


                    }
                });
                img2.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        b=2;
                edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color2));
//                                img2.setImageResource(R.drawable.check4);
                        dialogColor.dismiss();

                    }
                });
                img3.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color3));

                        b=3;
                        dialogColor.dismiss();
                    }
                });
                img4.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color4));
                        b=4;
                        dialogColor.dismiss();
                    }
                });
                img5.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                                      edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color5));
                        b=5;
                        dialogColor.dismiss();
                    }
                });
                img6.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                              edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color6));
                        b=6;
                        dialogColor.dismiss();
                    }
                });
                img7.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                           edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color7));
                        b=7;
                        dialogColor.dismiss();
                    }
                });
                img8.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                          edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color8));
                        b=8;
                        dialogColor.dismiss();
                    }
                });
                img9.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                          edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color9));
                        b=9;
                        dialogColor.dismiss();
                    }
                });
                img10.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                          edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color10));
                        b=10;
                        dialogColor.dismiss();
                    }
                });
                img11.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color11));
                        b=11;
                        dialogColor.dismiss();
                    }
                });
                img12.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                          edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color12));
                        b=12;
                        dialogColor.dismiss();
                    }
                });
                img13.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                         edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color13));
                        b=13;
                        dialogColor.dismiss();
                    }
                });






                dialogColor.setCancelable(true);
                dialogColor.setContentView(viewColor);
                dialogColor.show();
            }
        });
        compatSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                repeat=i;
                String a=compatSpinner.getItemAtPosition(i).toString();
                if(a.equals("Custom …")){
                    compatSpinner.setVisibility(View.INVISIBLE);
                    linearLayout.setVisibility(View.VISIBLE);
                }
                Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnDis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        dialog.setCancelable(true);
        dialog.setContentView(view);
        dialog.show();

    }

    private void dialogColor(ArrayList<Integer> arr){

            final Dialog dialogColor=new Dialog(MainActivity.this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View viewColor=inflater.inflate(R.layout.color_selection,null);



            ImageView img1= (ImageView) viewColor.findViewById(R.id.mau1);
            ImageView img2= (ImageView) viewColor.findViewById(R.id.mau2);
            ImageView img3= (ImageView) viewColor.findViewById(R.id.mau3);
            ImageView img4= (ImageView) viewColor.findViewById(R.id.mau4);
            ImageView img5= (ImageView) viewColor.findViewById(R.id.mau5);
            ImageView img6= (ImageView) viewColor.findViewById(R.id.mau6);
            ImageView img7= (ImageView) viewColor.findViewById(R.id.mau7);
            ImageView img8= (ImageView) viewColor.findViewById(R.id.mau8);
            ImageView img9= (ImageView) viewColor.findViewById(R.id.mau9);
            ImageView img10= (ImageView) viewColor.findViewById(R.id.mau10);
            ImageView img11= (ImageView) viewColor.findViewById(R.id.mau11);
            ImageView img12= (ImageView) viewColor.findViewById(R.id.mau12);
            ImageView img13= (ImageView) viewColor.findViewById(R.id.mau13);


        if(arr.size()>1){
            b=14;
        }
            switch (b){
                case 1:
                    img1.setImageResource(R.drawable.check4);
                    break;
                case 2:
                    img2.setImageResource(R.drawable.check4);
                    break;
                case 3:
                    img3.setImageResource(R.drawable.check4);
                    break;
                case 4:
                    img4.setImageResource(R.drawable.check4);
                    break;
                case 5:
                    img5.setImageResource(R.drawable.check4);
                    break;
                case 6:
                    img6.setImageResource(R.drawable.check4);
                    break;
                case 7:
                    img7.setImageResource(R.drawable.check4);
                    break;
                case 8:
                    img8.setImageResource(R.drawable.check4);
                    break;
                case 9:
                    img9.setImageResource(R.drawable.check4);
                    break;
                case 10:
                    img10.setImageResource(R.drawable.check4);
                    break;
                case 11:
                    img11.setImageResource(R.drawable.check4);
                    break;
                case 12:
                    img12.setImageResource(R.drawable.check4);
                    break;
                case 13:
                    img13.setImageResource(R.drawable.check4);
                    break;



            }
        for(int i=0;i<arr.size();i++){
            final int index=arr.get(i);


//                        arrImageColor.get(a).setImageResource(R.drawable.check4);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    b=1;
//                    data.getAllContacts().get(index).setColor(b);
//                edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color1));
                     nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();


                }
            });
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    b=2;
                    //                 data.getAllContacts().get(index).setColor(b);
                    //          edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color2));
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();

                }
            });
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //       edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color3));

                    b=3;
                    //           data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();
                }
            });
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //       edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color4));
                    b=4;
                    //            data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);

                    dialogColor.dismiss();
                }
            });
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //                edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color5));
                    b=5;
                    //          data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();
                }
            });
            img6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //         edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color6));
                    b=6;
                    //               data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();
                }
            });
            img7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //        edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color7));
                    b=7;
                    //             data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();
                }
            });
            img8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //      edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color8));
                    b=8;
                    //             data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();
                }
            });
            img9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //     edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color9));
                    b=9;
                    //               data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();
                }
            });
            img10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color10));
                    b=10;
                    //         data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();
                }
            });
            img11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //       edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color11));
                    b=11;
                    //          data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();
                }
            });
            img12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //       edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color12));
                    b=12;
                    //          data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();
                }
            });
            img13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //      edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color13));
                    b=13;
                    //           data.getAllContacts().get(index).setColor(b);
                    nameHabitColor=new NameHabit(data.getAllContacts().get(index).getId(),data.getAllContacts().get(index).getTenThuocTinh(),data.getAllContacts().get(index).getQuestision(),data.getAllContacts().get(index).getNgay(),b,data.getAllContacts().get(index).getRepeat(),data.getAllContacts().get(index).getGio(),data.getAllContacts().get(index).getPhut());
//                                img1.setImageResource(R.drawable.check4);
                    updateData(nameHabitColor);
                    dialogColor.dismiss();
                }
            });



        }


            dialogColor.setCancelable(true);
            dialogColor.setContentView(viewColor);
            dialogColor.show();


    }


    @Override
    public void onBackPressed() {

            super.onBackPressed();

    }
  public void deleteData(NameHabit habit){
      data.delete(habit);
      data = new Data(this, null,null,3);
      arrData.clear();
//        arrData=data.getAllContacts();
      arrData.addAll( data.getAllContacts());
      myAdapter=new MyAdapter(this,  arrData);
      listView.setAdapter(myAdapter);
      myAdapter.notifyDataSetChanged();
  }
    public void updateData(NameHabit nameHabit){
        data.update(nameHabit);
        data = new Data(this, null,null,3);
        arrData.clear();
//        arrData=data.getAllContacts();
        arrData.addAll( data.getAllContacts());
        myAdapter=new MyAdapter(this,  arrData);
        listView.setAdapter(myAdapter);
        myAdapter.notifyDataSetChanged();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == 1) {
            if(resultCode == Activity.RESULT_OK){
                NameHabit result= (NameHabit) data.getSerializableExtra("result");
                updateData(result);
            }
            if (resultCode == Activity.RESULT_CANCELED) {
                //Write your code if there's no result
            }
        }
    }//
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        toolbar.inflateMenu(R.menu.itemmenu);
        toolbar.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });


        toolbar2.inflateMenu(R.menu.itemmenu3);
        toolbar2.setOnMenuItemClickListener(
                new Toolbar.OnMenuItemClickListener() {
                    @Override
                    public boolean onMenuItemClick(MenuItem item) {
                        return onOptionsItemSelected(item);
                    }
                });

        return true;
    }

    Boolean OK=true;
    int gio,phut,repeat;
    String ngay;
     EditText edtName;
public void dialog(){
    dialog=new Dialog(this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
    LayoutInflater inflater = LayoutInflater.from(this);
    View view=inflater.inflate(R.layout.dialog_habit,null);


     edtName= (EditText) view.findViewById(R.id.tvName);

    ImageButton imgColor= (ImageButton) view.findViewById(R.id.imgColor);
    final EditText edtQuestion= (EditText) view.findViewById(R.id.tvQuestion);
    Button btnDis= (Button) view.findViewById(R.id.btnDis);
    Button btnSave= (Button) view.findViewById(R.id.btnSave);



    final AppCompatSpinner compatSpinnerSolanlap= (AppCompatSpinner) view.findViewById(R.id.spinner);
    final LinearLayout linearLayoutCustomRepeat= (LinearLayout) view.findViewById(R.id.customRepeat);
    EditText edtSolanRepeat= (EditText) view.findViewById(R.id.numerator);
    EditText edtTrongNgay= (EditText) view.findViewById(R.id.denominator);
    Button btnOK= (Button) view.findViewById(R.id.btnOK);
    final TextView txtDay= (TextView) view.findViewById(R.id.txtDay);
    final TextView edtTimePicker= (TextView) view.findViewById(R.id.edtTime);
    final LinearLayout linearLayoutChonGio= (LinearLayout) view.findViewById(R.id.layout_panel);
    final LinearLayout linearLayoutChonNgay= (LinearLayout) view.findViewById(R.id.layout_panel2);

    linearLayoutChonNgay.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            final CharSequence[] items = {" Thứ bảy "," Chủ nhật "," Thứ hai "," Thứ ba "," Thứ tư "," Thứ năm "," Thứ sáu "};
            // arraylist to keep the selected items
            final ArrayList seletedItems=new ArrayList();

//                        for (int x = 0; x < seletedItems.size(); x++) {
//                            int a= (int) seletedItems.get(x);
//                            items[a].set (x, true);
//                        }
//                        for (int i = 0; i < seletedItems.size(); i++) {
//                            HashMap<String, String> asset = (HashMap<String, String>) seletedItems.get(i);
//                            asset.
//                            if (asset.get(KEY_CODE).equals(scanAsset)){
//                                asset.put(KEY_CHECKED, "Checked");
////                                Log.i("Asset code is ", scanAsset);
//                            }
//                        }
//                        myAdapter.notifyDataSetChanged();

//                        AlertDialog dialog1 = (AlertDialog) dialog;
//                        ListView v = dialog1.getListView();
//                        int i = 0;
//                        while(i < items.length) {
//                            v.setItemChecked(i, true);
//                            i++;
//                        }

            AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)

                    .setTitle("Select days")
                    .setMultiChoiceItems(items,null, new DialogInterface.OnMultiChoiceClickListener() {

                        @Override
                        public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                            if (isChecked) {
                                // If the user checked the item, add it to the selected items
                                seletedItems.add(indexSelected);
                            } else if (seletedItems.contains(indexSelected)) {
                                // Else, if the item is already in the array, remove it
                                seletedItems.remove(Integer.valueOf(indexSelected));
                            }
                        }
                    }).setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {
                            if(seletedItems.size()==items.length){
                                txtDay.setText(" Any day of the week ");
                                ngay=" Any day of the week ";
                            }
                            else{
                                String b="";
                                for(int i=0;i<seletedItems.size();i++){
                                    int index= (int) seletedItems.get(i);
                                    b=b+items[index].toString()+",";
//                                            setTitle(seletedItems.get(i).toString()+",");
                                }
                                String c=b.substring(0,b.length()-1);
                                txtDay.setText(c);
                                ngay=c;
                            }

                            //  Your code when user clicked on OK
                            //  You can write the code  to save the selected item here
                        }
                    }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int id) {

                            //  Your code when user clicked on Cancel
                        }
                    }).create();
            dialog.show();
        }
    });
    linearLayoutChonGio.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            Calendar now = Calendar.getInstance();
            TimePickerDialog timepickerdialog = TimePickerDialog.newInstance(MainActivity.this,
                    now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE),true);
            timepickerdialog.setThemeDark(false); //Dark Theme?
            timepickerdialog.vibrate(false); //vibrate on choosing time?
            timepickerdialog.dismissOnPause(false); //dismiss the dialog onPause() called?
//                        timepickerdialog.enableSeconds(true); //show seconds?

            //Handling cancel event
            timepickerdialog.setOnTimeSetListener(new TimePickerDialog.OnTimeSetListener() {
                @Override
                public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {
                    gio=hourOfDay;
                    phut=minute;

                    edtTimePicker.setText(" "+hourOfDay+" : "+minute+" ");
                    if(edtTimePicker.getText().toString()!="Off"){
                        linearLayoutChonNgay.setVisibility(View.VISIBLE);
                    }





                }
            });
            timepickerdialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    Toast.makeText(MainActivity.this, "Cancel choosing time", Toast.LENGTH_SHORT).show();
                }
            });
            timepickerdialog.show(getFragmentManager(), "Timepickerdialog"); //show time picker dialog
        }
    });


    imgColor.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialogColor=new Dialog(MainActivity.this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
            LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
            View viewColor=inflater.inflate(R.layout.color_selection,null);


            ImageView img1= (ImageView) viewColor.findViewById(R.id.mau1);
            ImageView img2= (ImageView) viewColor.findViewById(R.id.mau2);
            ImageView img3= (ImageView) viewColor.findViewById(R.id.mau3);
            ImageView img4= (ImageView) viewColor.findViewById(R.id.mau4);
            ImageView img5= (ImageView) viewColor.findViewById(R.id.mau5);
            ImageView img6= (ImageView) viewColor.findViewById(R.id.mau6);
            ImageView img7= (ImageView) viewColor.findViewById(R.id.mau7);
            ImageView img8= (ImageView) viewColor.findViewById(R.id.mau8);
            ImageView img9= (ImageView) viewColor.findViewById(R.id.mau9);
            ImageView img10= (ImageView) viewColor.findViewById(R.id.mau10);
            ImageView img11= (ImageView) viewColor.findViewById(R.id.mau11);
            ImageView img12= (ImageView) viewColor.findViewById(R.id.mau12);
            ImageView img13= (ImageView) viewColor.findViewById(R.id.mau13);
            switch (a){
                case 1:
                    img1.setImageResource(R.drawable.check4);
                    break;
                case 2:
                    img2.setImageResource(R.drawable.check4);
                    break;
                case 3:
                    img3.setImageResource(R.drawable.check4);
                    break;
                case 4:
                    img4.setImageResource(R.drawable.check4);
                    break;
                case 5:
                    img5.setImageResource(R.drawable.check4);
                    break;
                case 6:
                    img6.setImageResource(R.drawable.check4);
                    break;
                case 7:
                    img7.setImageResource(R.drawable.check4);
                    break;
                case 8:
                    img8.setImageResource(R.drawable.check4);
                    break;
                case 9:
                    img9.setImageResource(R.drawable.check4);
                    break;
                case 10:
                    img10.setImageResource(R.drawable.check4);
                    break;
                case 11:
                    img11.setImageResource(R.drawable.check4);
                    break;
                case 12:
                    img12.setImageResource(R.drawable.check4);
                    break;
                case 13:
                    img13.setImageResource(R.drawable.check4);
                    break;



            }

//                        arrImageColor.get(a).setImageResource(R.drawable.check4);
            img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    a=1;
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color1));
//                                img1.setImageResource(R.drawable.check4);
                    dialogColor.dismiss();


                }
            });
            img2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    a=2;
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color2));
//                                img2.setImageResource(R.drawable.check4);
                    dialogColor.dismiss();

                }
            });
            img3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color3));

                    a=3;
                    dialogColor.dismiss();
                }
            });
            img4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color4));
                    a=4;
                    dialogColor.dismiss();
                }
            });
            img5.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color5));
                    a=5;
                    dialogColor.dismiss();
                }
            });
            img6.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color6));
                    a=6;
                    dialogColor.dismiss();
                }
            });
            img7.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color7));
                    a=7;
                    dialogColor.dismiss();
                }
            });
            img8.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color8));
                    a=8;
                    dialogColor.dismiss();
                }
            });
            img9.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color9));
                    a=9;
                    dialogColor.dismiss();
                }
            });
            img10.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color10));
                    a=10;
                    dialogColor.dismiss();
                }
            });
            img11.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color11));
                    a=11;
                    dialogColor.dismiss();
                }
            });
            img12.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color12));
                    a=12;
                    dialogColor.dismiss();
                }
            });
            img13.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    edtName.setTextColor(ContextCompat.getColor(MainActivity.this, R.color.color13));
                    a=13;
                    dialogColor.dismiss();
                }
            });






            dialogColor.setCancelable(true);
            dialogColor.setContentView(viewColor);
            dialogColor.show();
        }
    });
    compatSpinnerSolanlap.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            String a=compatSpinnerSolanlap.getItemAtPosition(i).toString();
            repeat=i;
            if(a.equals("Custom …")){
                compatSpinnerSolanlap.setVisibility(View.INVISIBLE);
                linearLayoutCustomRepeat.setVisibility(View.VISIBLE);
            }
            Toast.makeText(MainActivity.this,a,Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    });
    btnSave.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            NameHabit nameHabit=new NameHabit(edtName.getText().toString(),edtQuestion.getText().toString(),ngay,a,repeat,gio,phut);
            data.addJapan(nameHabit);
            a= 12;
            myAdapter=new MyAdapter(getBaseContext(),  data.getAllContacts());
            listView.setAdapter(myAdapter);
            myAdapter.notifyDataSetChanged();

            dialog.dismiss();
        }
    });
    btnDis.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            dialog.dismiss();
        }
    });
    dialog.setCancelable(true);
    dialog.setContentView(view);
    dialog.show();
}

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()){
            case R.id.add:
                    dialog();
                    break;

            case R.id.show_archived:
                    break;

            case R.id.night_mode:

                    //                final RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.er);

                    //mau nen listview
                    int colorFrom = getResources().getColor(R.color.trang);
                    int colorTo = getResources().getColor(R.color.den);

                    //mau nen linerlayout
                    int colorFrom2 = getResources().getColor(R.color.trang2);
                    int colorTo2 = getResources().getColor(R.color.den2);

                    //mau text
                    int colorFrom3 = getResources().getColor(R.color.den2);
                    int colorTo3 = getResources().getColor(R.color.trang2);


                    if(KT==true){
                        //mau nen listview
                        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom, colorTo);
                        colorAnimation.setDuration(800); // milliseconds
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
//                            layoutitem.setBackgroundColor((int) animator.getAnimatedValue());
                            }

                        });
                        colorAnimation.start();




                        //mau nen linerlayout
                        ValueAnimator colorLayout = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom2, colorTo2);
                        colorLayout.setDuration(800); // milliseconds
                        colorLayout.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                layoutday.setBackgroundColor((int) animator.getAnimatedValue());
                                listView.setBackgroundColor((int) animator.getAnimatedValue());
                            }

                        });
                        colorLayout.start();


                        //mau text
                        ValueAnimator colorText = ValueAnimator.ofObject(new ArgbEvaluator(), colorFrom3, colorTo3);
                        colorText.setDuration(800); // milliseconds
                        colorText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                txtThu1.setTextColor((Integer) animator.getAnimatedValue());
                                txtThu2.setTextColor((Integer) animator.getAnimatedValue());
                                txtThu3.setTextColor((Integer) animator.getAnimatedValue());
                                txtThu4.setTextColor((Integer) animator.getAnimatedValue());
                                txtNgay1.setTextColor((Integer) animator.getAnimatedValue());
                                txtNgay2.setTextColor((Integer) animator.getAnimatedValue());
                                txtNgay3.setTextColor((Integer) animator.getAnimatedValue());
                                txtNgay4.setTextColor((Integer) animator.getAnimatedValue());
//                            layoutday.setBackgroundColor((int) animator.getAnimatedValue());
                            }

                        });
                        colorText.start();


                        KT=false;
                    }
                    else{

                        //mau nen listview
                        ValueAnimator colorAnimation = ValueAnimator.ofObject(new ArgbEvaluator(), colorTo, colorFrom);
                        colorAnimation.setDuration(800); // milliseconds
                        colorAnimation.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
//                            layoutitem.setBackgroundColor((int) animator.getAnimatedValue());
                            }

                        });
                        colorAnimation.start();





                        //mau nen linerlayout
                        ValueAnimator colorLayout = ValueAnimator.ofObject(new ArgbEvaluator(), colorTo2,colorFrom2);
                        colorLayout.setDuration(800); // milliseconds
                        colorLayout.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                layoutday.setBackgroundColor((int) animator.getAnimatedValue());
                                listView.setBackgroundColor((int) animator.getAnimatedValue());
                            }

                        });
                        colorLayout.start();


                        //mau text
                        ValueAnimator colorText = ValueAnimator.ofObject(new ArgbEvaluator(), colorTo3, colorFrom3);
                        colorText.setDuration(800); // milliseconds
                        colorText.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

                            @Override
                            public void onAnimationUpdate(ValueAnimator animator) {
                                txtThu1.setTextColor((Integer) animator.getAnimatedValue());
                                txtThu2.setTextColor((Integer) animator.getAnimatedValue());
                                txtThu3.setTextColor((Integer) animator.getAnimatedValue());
                                txtThu4.setTextColor((Integer) animator.getAnimatedValue());
                                txtNgay1.setTextColor((Integer) animator.getAnimatedValue());
                                txtNgay2.setTextColor((Integer) animator.getAnimatedValue());
                                txtNgay3.setTextColor((Integer) animator.getAnimatedValue());
                                txtNgay4.setTextColor((Integer) animator.getAnimatedValue());
//                            layoutday.setBackgroundColor((int) animator.getAnimatedValue());
                            }

                        });
                        colorText.start();






                        KT=true;
                    }


//                  Toast.makeText(getBaseContext(),"asd",Toast.LENGTH_LONG).show();
//                  RelativeLayout relativeLayout=(RelativeLayout)findViewById(R.id.er);
//                  relativeLayout.setVisibility(View.VISIBLE);
//                  item.setChecked(false);
                    // mBold = false;

                    break;

            case R.id.setting:

                Toast.makeText(MainActivity.this,"More2",Toast.LENGTH_SHORT).show();
                break;

            case R.id.about:
                    Toast.makeText(MainActivity.this,"More2",Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(this,About.class);
                    startActivity(intent);
                    item.setVisible(true);

                break;
            case R.id.edit_main:

                break;
            case R.id.editColor_main:

                break;
            case R.id.archive:

                break;
            case R.id.delete:

                break;
            default:
                toolbar2.setVisibility(View.INVISIBLE);
                toolbar.setVisibility(View.VISIBLE);
                break;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }
}
