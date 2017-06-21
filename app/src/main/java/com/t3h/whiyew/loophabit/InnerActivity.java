package com.t3h.whiyew.loophabit;

import android.app.Activity;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BubbleChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BubbleData;
import com.github.mikephil.charting.data.BubbleDataSet;
import com.github.mikephil.charting.data.BubbleEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.t3h.whiyew.LineChart.MyYAxisValueFormatter;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import devs.mulham.horizontalcalendar.HorizontalCalendar;
import devs.mulham.horizontalcalendar.HorizontalCalendarListener;
import devs.mulham.horizontalcalendar.HorizontalCalendarView;

/**
 * Created by hoadu on 24/05/2017.
 */

public class InnerActivity extends AppCompatActivity implements OnChartValueSelectedListener, TimePickerDialog.OnTimeSetListener {
    private TextView txtNameHB,txtRepeatHB,txtGioHB,txtPhutHB;
    private String[] arrRepeat={"every_day","every_week","two_times_per_week","five_times_per_week"};
    private Toolbar toolbar;
    private LineChart lineChart;
    private BubbleChart bubbleChart;
//    private LineChart mChart;
//    private LineChartView mChart2;
    int a,repeat,gio,phut;
    private Boolean CHECK=true;
    private Boolean CHECK2=true;
    private Data data;
    private MainActivity mainActivity=new MainActivity();
    private int i;
    private final String[] mLabels = {"Jan", "Fev", "Mar", "Apr", "Jun", "May", "Jul", "Aug", "Sep"};

    private final float[][] mValues = {{3.5f, 4.7f, 4.3f, 8f, 6.5f, 9.9f, 7f, 8.3f, 7.0f},
            {4.5f, 2.5f, 2.5f, 9f, 4.5f, 9.5f, 5f, 8.3f, 1.8f}};


    private final String[] mLabels3 = {"ngay1","ngay2","ngay3","ngay4","ngay5","ngay6"};

    private final float[][] mValues3 =
            {{1.8f, 2f, 2.4f, 2.2f, 3.3f, 3.45f}, {-2.8f, -3.1f, -3.55f, -3.40f, -1.40f, -2.5f},
                    {1.8f, 2.1f, 2.55f, 2.40f, 3.40f, 3.5f},
                    {-1.8f, -2f, -2.4f, -2.2f, -3.3f, -3.45f}};


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
          setContentView(R.layout.inner_main);
        data = new Data(this, null,null,3);

        init();
        lineChart= (LineChart) findViewById(R.id.lineChart);
        lineChart();
        bubbleChart= (BubbleChart) findViewById(R.id.bubbleChart);
        bubbleChart();
        horizoltalCalendar();

    }

    private void horizoltalCalendar() {
        Calendar now = Calendar.getInstance();

        /** end after 1 month from now */
        Calendar endDate = Calendar.getInstance();
        endDate.add(Calendar.DAY_OF_MONTH, 0);

/** start before 1 month from now */
        Calendar startDate = Calendar.getInstance();
        startDate.add(Calendar.MONTH, -1);



        HorizontalCalendar horizontalCalendar = new HorizontalCalendar.Builder(this, R.id.calendarView)
                .startDate(startDate.getTime())
                .endDate(endDate.getTime())
                .datesNumberOnScreen(7)   // Number of Dates cells shown on screen (Recommended 5)
                .dayNameFormat("EEE")	  // WeekDay text format
                .dayNumberFormat("dd")    // Date format
                .monthFormat("MMM") 	  // Month format
                .showDayName(true)	  // Show or Hide dayName text
                .showMonthName(true)	  // Show or Hide month text
                .textColor(Color.GRAY, Color.BLACK)    // Text color for none selected Dates, Text color for selected Date.
                .selectedDateBackground(Color.WHITE)  // Background color of the selected date cell.
                .selectorColor(Color.RED)   // Color of the selection indicator bar (default to colorAccent).
                .defaultSelectedDate(new Date(now.get(Calendar.DAY_OF_WEEK_IN_MONTH)))  // Date to be seleceted at start (default to Today)
                .build();
        horizontalCalendar.setCalendarListener(new HorizontalCalendarListener() {
            @Override
            public void onDateSelected(Date date, int position) {
                //do something
            }
            @Override
            public void onCalendarScroll(HorizontalCalendarView calendarView, int dx, int dy) {

            }
            @Override
            public boolean onDateLongClicked(Date date, int position) {
                return true;
            }
        });



    }

    private void bubbleChart() {
        ArrayList<BubbleEntry> entries = new ArrayList<>();
        entries.add(new BubbleEntry(1,50, 5));
        entries.add(new BubbleEntry(2,10, 5));
        entries.add(new BubbleEntry(3,30 ,1));
        entries.add(new BubbleEntry(4,20, 2));
        entries.add(new BubbleEntry(5,15, 4));
        //xXit : thang trong nam
        //yXit : all cac thu trong tuan
        // 1 vi tri tuong ung voi xXit( diem danh cac thang trong nam)
        // 2 vi tri tuong ung voi yXit(diem danh trong thu trong tuan)
        //3 gia tri diem danh bao nhieu lan

//        entries.add(new BubbleEntry(9f, 5));

        BubbleDataSet dataset = new BubbleDataSet(entries, "# of Calls");
//        dataset.setColor(Color.WHITE);



        final ArrayList<String> labels = new ArrayList<String>();
        labels.add("a");
        labels.add("b");
        labels.add("c");
        labels.add("d");
        labels.add("e");
        labels.add("f");



        BubbleData data = new BubbleData(labels, dataset);


        dataset.setColors(ColorTemplate.COLORFUL_COLORS);

        XAxis xAxis=bubbleChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        bubbleChart.setData(data);
        bubbleChart.animateY(2000);
        bubbleChart.setDescription("Bieu do");
        bubbleChart.setGridBackgroundColor(Color.WHITE);

        bubbleChart.setDragEnabled(true);
        bubbleChart.setScaleEnabled(true);
        bubbleChart.getAxisLeft().setEnabled(false);

        YAxis right = bubbleChart.getAxisRight();
        right.setAxisMaxValue(5f);
        right.setAxisMinValue(0f);


//        YAxis left = bubbleChart.getAxisRight();
//        left.setValueFormatter(new MyYAxisValueFormatter());


//        dataset.setDrawCubic(true);
//        dataset.setDrawFilled(true);



    }

    private void lineChart() {

        ArrayList<Entry> entries = new ArrayList<>();
        entries.add(new Entry(14, 0));
        entries.add(new Entry(58, 1));
        entries.add(new Entry(62, 2));
        entries.add(new Entry(22, 3));
        entries.add(new Entry(18, 4));
        entries.add(new Entry(59, 5));

        LineDataSet dataset = new LineDataSet(entries, "# of Calls");
//        dataset.setColor(Color.WHITE);
        dataset.setLineWidth(6f);
        dataset.setFillAlpha(15);
        dataset.setCircleSize(7f);
//        dataset.setFillColor(Color.WHITE);
        //set mau tam
        dataset.setCircleColorHole(Color.RED);
        //set mau vien
        dataset.setCircleColor(Color.WHITE);
        dataset.setColors(ColorTemplate.COLORFUL_COLORS);


        final ArrayList<String> labels = new ArrayList<String>();
        labels.add("a");
        labels.add("b");
        labels.add("c");
        labels.add("d");
        labels.add("e");
        labels.add("f");


        LineData data = new LineData(labels, dataset);



        XAxis xAxis=lineChart.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);


        YAxis left = lineChart.getAxisLeft();
        left.setValueFormatter(new MyYAxisValueFormatter());
        left.setAxisMaxValue(100f);
        left.setAxisMinValue(0f);
//        left.setPosition(YAxis.YAxisLabelPosition.OUTSIDE_CHART);


        lineChart.animateY(2000);
        lineChart.setDescription("Bieu do");
        lineChart.setGridBackgroundColor(Color.WHITE);
        lineChart.setData(data);
        lineChart.setDragEnabled(true);
        lineChart.setScaleEnabled(true);
        lineChart.getAxisRight().setEnabled(false);





//        dataset.setDrawCubic(true);
//        dataset.setDrawFilled(true);


    }


    private void init() {
        Intent intent=getIntent();
//        nameHabit= (NameHabit) intent.getSerializableExtra(MainActivity.KEY_HABIT);
         i=intent.getIntExtra(MainActivity.KEY_HABIT,1);
//        data= (Data) intent.getSerializableExtra(MainActivity.KEY_DATA);
//        nameHabit= (NameHabit) intent.getExtras().get(MainActivity.KEY_HABIT);
//        data= (Data) intent.getExtras().get(MainActivity.KEY_DATA);
//        String ten=getIntent().getStringExtra(MainActivity.KEY_NAME);
//        Toolbar toolbar= (Toolbar) findViewById(R.id.innerToolbar);
        //     setSupportActionBar(toolbar);
//        getSupportActionBar().show();


        toolbar= (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(data.getAllContacts().get(i).getTenThuocTinh());
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setSubtitleTextColor(Color.WHITE);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        final Drawable upArrow = getResources().getDrawable(R.drawable.back);
        upArrow.setColorFilter(getResources().getColor(R.color.trang), PorterDuff.Mode.SRC_ATOP);
        getSupportActionBar().setHomeAsUpIndicator(upArrow);



        txtNameHB= (TextView) findViewById(R.id.txtNameHB);
        txtRepeatHB= (TextView) findViewById(R.id.txtRepeatHB);
        txtGioHB= (TextView) findViewById(R.id.txtGioHB);
        txtPhutHB= (TextView) findViewById(R.id.txtPhutHB);


        txtNameHB.setText(data.getAllContacts().get(i).getQuestision());
        txtRepeatHB.setText(arrRepeat[repeat].toString());
        txtGioHB.setText(data.getAllContacts().get(i).getGio()+"");
        txtPhutHB.setText(data.getAllContacts().get(i).getPhut()+"");





//        mChart = (LineChart) findViewById(R.id.chart);
//        lineChart();

//        mChart2 = (LineChartView) findViewById(R.id.chart1);
//        lineCharView();

//        mChart3= (HorizontalStackBarChartView) findViewById(R.id.chart2);
//        stackBarChart();
    }

//    private void stackBarChart() {
//
//        BarSet barSet = new BarSet(mLabels3, mValues3[0]);
//        barSet.setVisible(false);
//        barSet.setColor(Color.parseColor("#90ee7e"));
//        mChart3.addData(barSet);
//
//        barSet = new BarSet(mLabels3, mValues3[1]);
//        barSet.setColor(Color.parseColor("#2b908f"));
//        mChart3.addData(barSet);
//
//
//        Paint gridPaint = new Paint();
//        gridPaint.setColor(Color.parseColor("#e7e7e7"));
//        gridPaint.setStyle(Paint.Style.STROKE);
//        gridPaint.setAntiAlias(true);
//        gridPaint.setStrokeWidth(Tools.fromDpToPx(.7f));
//        mChart3.setBarSpacing(Tools.fromDpToPx(10));
//        mChart3.setBorderSpacing(0)
//                .setStep(1)
//                .setGrid(0, 10, gridPaint)
//                .setXAxis(false)
//                .setYAxis(false)
//                .setLabelsFormat(new DecimalFormat("##'M'"))
//                .setLabelsColor(Color.BLACK)
//                .show();
//
//    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.itemmenu2,menu);
        return super.onCreateOptionsMenu(menu);
    }
    private Boolean isCheck=false;
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()){
           case R.id.edit:


               final Dialog dialog=new Dialog(this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
               LayoutInflater inflater = LayoutInflater.from(this);
               View view=inflater.inflate(R.layout.dialog_habit,null);
               final EditText edtName= (EditText) view.findViewById(R.id.tvName);
               ImageButton imgColor= (ImageButton) view.findViewById(R.id.imgColor);
               final EditText edtQue= (EditText) view.findViewById(R.id.tvQuestion);
               Button btnDis= (Button) view.findViewById(R.id.btnDis);
               Button btnSave= (Button) view.findViewById(R.id.btnSave);

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




               edtName.setText(data.getAllContacts().get(i).getTenThuocTinh());
               edtQue.setText(data.getAllContacts().get(i).getQuestision());
//               if(CHECK==true){
                   compatSpinner.setSelection(data.getAllContacts().get(i).getRepeat());
                   a=data.getAllContacts().get(i).getColor();
                   gio=data.getAllContacts().get(i).getGio();
                   phut=data.getAllContacts().get(i).getPhut();
                   repeat=data.getAllContacts().get(i).getRepeat();
                   int[] arrMangMau={R.color.color12,R.color.color1,R.color.color2,R.color.color3,R.color.color4,R.color.color5,R.color.color6,R.color.color7,R.color.color8,R.color.color9,R.color.color10,R.color.color11,R.color.color12,R.color.color13};
                   edtName.setTextColor(ContextCompat.getColor(InnerActivity.this,arrMangMau[a]));
                   edtTimePicker.setText(" "+data.getAllContacts().get(i).getGio()+" : "+data.getAllContacts().get(i).getPhut()+" ");
                   txtDay.setText(data.getAllContacts().get(i).getNgay());
//               }

               btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                CHECK=true;
                isCheck=true;
                NameHabit nameHabit2=new NameHabit(data.getAllContacts().get(i).getId(),edtName.getText().toString(),edtQue.getText().toString(),txtDay.getText().toString(),a,repeat,gio,phut);
//                data.addJapan(nameHabit2);
//                myAdapter=new MyAdapter(getBaseContext(),  data.getAllContacts());
//                listView.setAdapter(myAdapter);
//                myAdapter.notifyDataSetChanged();
                nameHabitResult=nameHabit2;
                getSupportActionBar().setTitle(nameHabit2.getTenThuocTinh());
                txtNameHB.setText(nameHabit2.getQuestision());
                txtGioHB.setText(nameHabit2.getGio()+"");
                txtPhutHB.setText(nameHabit2.getPhut()+"");
                data.update(nameHabit2);
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

                       AlertDialog dialog = new AlertDialog.Builder(InnerActivity.this)

                               .setTitle("Select days")
                               .setMultiChoiceItems(items, null, new DialogInterface.OnMultiChoiceClickListener() {

                                   @Override
                                   public void onClick(DialogInterface dialog, int indexSelected, boolean isChecked) {
                                       if (isChecked) {
                                           CHECK=false;
                                           // If the user checked the item, add it to the selected items
                                           seletedItems.add(indexSelected);
                                       } else if (seletedItems.contains(indexSelected)) {
                                           CHECK=false;
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
                           TimePickerDialog timepickerdialog = TimePickerDialog.newInstance(InnerActivity.this,
                                   data.getAllContacts().get(i).getGio(), data.getAllContacts().get(i).getPhut(),true);
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
                                   Toast.makeText(InnerActivity.this, "Cancel choosing time", Toast.LENGTH_SHORT).show();
                               }
                           });
                           timepickerdialog.show(getFragmentManager(), "Timepickerdialog"); //show time picker dialog
//

                   }
               });


               imgColor.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View view) {
                       final Dialog dialogColor=new Dialog(InnerActivity.this,android.R.style.Theme_DeviceDefault_Dialog_NoActionBar_MinWidth);
                       LayoutInflater inflater = LayoutInflater.from(InnerActivity.this);
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
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color1));
//                                img1.setImageResource(R.drawable.check4);
                               dialogColor.dismiss();


                           }
                       });
                       img2.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {

                               a=2;
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color2));
//                                img2.setImageResource(R.drawable.check4);
                               dialogColor.dismiss();

                           }
                       });
                       img3.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color3));

                               a=3;
                               dialogColor.dismiss();
                           }
                       });
                       img4.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color4));
                               a=4;
                               dialogColor.dismiss();
                           }
                       });
                       img5.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color5));
                               a=5;
                               dialogColor.dismiss();
                           }
                       });
                       img6.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color6));
                               a=6;
                               dialogColor.dismiss();
                           }
                       });
                       img7.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color7));
                               a=7;
                               dialogColor.dismiss();
                           }
                       });
                       img8.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color8));
                               a=8;
                               dialogColor.dismiss();
                           }
                       });
                       img9.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color9));
                               a=9;
                               dialogColor.dismiss();
                           }
                       });
                       img10.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color10));
                               a=10;
                               dialogColor.dismiss();
                           }
                       });
                       img11.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color11));
                               a=11;
                               dialogColor.dismiss();
                           }
                       });
                       img12.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color12));
                               a=12;
                               dialogColor.dismiss();
                           }
                       });
                       img13.setOnClickListener(new View.OnClickListener() {
                           @Override
                           public void onClick(View view) {
                               edtName.setTextColor(ContextCompat.getColor(InnerActivity.this, R.color.color13));
                               a=13;
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
                       Toast.makeText(InnerActivity.this,a,Toast.LENGTH_SHORT).show();
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



               break;
           default:
               onBackPressed();
               break;


       }

        return super.onOptionsItemSelected(item);
    }
    private NameHabit nameHabitResult;
    @Override
    public void onBackPressed() {
        if(isCheck==true){
            Intent returnIntent = new Intent();
            returnIntent.putExtra("result",nameHabitResult);
            setResult(Activity.RESULT_OK,returnIntent);
            isCheck=false;
            finish();
        }
        else{
            Intent returnIntent = new Intent();
            setResult(Activity.RESULT_CANCELED, returnIntent);
            finish();
        }

    }

//    private void lineCharView() {
//        LineSet dataset = new LineSet(mLabels, mValues[0]);
//        dataset.setColor(Color.parseColor("#758cbb"))
//                .setFill(Color.parseColor("#2d374c"))
//                .setDotsColor(Color.parseColor("#758cbb"))
//                .setThickness(4)
//                .setDashed(new float[] {10f, 10f})
//                .beginAt(5);
//        mChart2.addData(dataset);
//
//        dataset = new LineSet(mLabels, mValues[0]);
//        dataset.setColor(Color.parseColor("#b3b5bb"))
//                .setFill(Color.parseColor("#2d374c"))
//                .setDotsColor(Color.parseColor("#ffc755"))
//                .setThickness(4)
//                .endAt(6);
//        mChart2.addData(dataset);
//
//        // Chart
//        mChart2.setBorderSpacing(Tools.fromDpToPx(15))
//                .setAxisBorderValues(0, 20)
//                .setYLabels(AxisRenderer.LabelPosition.NONE)
//                .setLabelsColor(Color.parseColor("#6a84c3"))
//                .setXAxis(false)
//                .setYAxis(false);
//
//        mChart2.show();
//
////        mBaseAction = action;
////        Runnable chartAction = new Runnable() {
////            @Override
////            public void run() {
////
////                mBaseAction.run();
////                mTip.prepare(mChart.getEntriesArea(0).get(3), mValues[0][3]);
////                mChart.showTooltip(mTip, true);
////            }
////        };
//
////        Animation anim = new Animation().setEasing(new BounceInterpolator()).setEndAction(chartAction);
////
////        mChart.show(anim);
//    }

//    private void lineChart() {
//        mChart.setOnChartValueSelectedListener(this);
//
//        // no description text
//        mChart.getDescription().setEnabled(false);
//
//        // enable touch gestures
//        mChart.setTouchEnabled(true);
//
//        mChart.setDragDecelerationFrictionCoef(0.9f);
//
//        // enable scaling and dragging
//        mChart.setDragEnabled(true);
//        mChart.setScaleEnabled(true);
//        mChart.setDrawGridBackground(false);
//        mChart.setHighlightPerDragEnabled(true);
//
//        // if disabled, scaling can be done on x- and y-axis separately
//        mChart.setPinchZoom(true);
//        mChart.setBackgroundColor(Color.BLACK);
//
//        setData(20, 30);
//        mChart.animateX(2500);
//
//    }

//    private void setData(int count, float range) {
//
//        ArrayList<Entry> yVals1 = new ArrayList<Entry>();
//
//        for (int i = 0; i < count; i++) {
//            float mult = range / 2f;
//            float val = (float) (Math.random() * mult) + 10;
//            yVals1.add(new Entry(i, val));
//        }
//
//        ArrayList<Entry> yVals2 = new ArrayList<Entry>();
//
//        for (int i = 0; i < count-1; i++) {
//            float mult = range;
//            float val = (float) (Math.random() * mult) + 50;
//            yVals2.add(new Entry(i, val));
////            if(i == 10) {
////                yVals2.add(new Entry(i, val + 50));
////            }
//        }
//
//        ArrayList<Entry> yVals3 = new ArrayList<Entry>();
//
//        for (int i = 0; i < count; i++) {
//            float mult = range;
//            float val = (float) (Math.random() * mult) + 50;
//            yVals3.add(new Entry(i, val));
//        }
//
//        LineDataSet set1, set2, set3;
//
//        if (mChart.getData() != null &&
//                mChart.getData().getDataSetCount() > 0) {
//            set1 = (LineDataSet) mChart.getData().getDataSetByIndex(0);
////            set2 = (LineDataSet) mChart.getData().getDataSetByIndex(1);
////            set3 = (LineDataSet) mChart.getData().getDataSetByIndex(2);
//            set1.setValues(yVals1);
////            set2.setValues(yVals2);
////            set3.setValues(yVals3);
//            mChart.getData().notifyDataChanged();
//            mChart.notifyDataSetChanged();
//        } else {
//            // create a dataset and give it a type
//            set1 = new LineDataSet(yVals1, "DataSet 1");
//
//            set1.setAxisDependency(YAxis.AxisDependency.RIGHT);
//            set1.setColor(Color.WHITE);
//            set1.setCircleColor(Color.RED);
//            set1.setLineWidth(2f);
//            set1.setCircleRadius(4f);
//            set1.setFillAlpha(65);
//            set1.setFillColor(Color.WHITE);
//            set1.setDrawVerticalHighlightIndicator(true);
//
//            set1.setHighLightColor(Color.rgb(244, 117, 117));
//            set1.setDrawCircleHole(false);
//            //set1.setFillFormatter(new MyFillFormatter(0f));
//            //set1.setDrawHorizontalHighlightIndicator(false);
//            //set1.setVisible(false);
//            //set1.setCircleHoleColor(Color.WHITE);
//
//            // create a dataset and give it a type
////            set2 = new LineDataSet(yVals2, "DataSet 2");
////            set2.setAxisDependency(YAxis.AxisDependency.RIGHT);
////            set2.setColor(Color.RED);
////            set2.setCircleColor(Color.WHITE);
////            set2.setLineWidth(2f);
////            set2.setCircleRadius(3f);
////            set2.setFillAlpha(65);
////            set2.setFillColor(Color.RED);
////            set2.setDrawCircleHole(false);
////            set2.setHighLightColor(Color.rgb(244, 117, 117));
//            //set2.setFillFormatter(new MyFillFormatter(900f));
//
////            set3 = new LineDataSet(yVals3, "DataSet 3");
////            set3.setAxisDependency(YAxis.AxisDependency.RIGHT);
////            set3.setColor(Color.YELLOW);
////            set3.setCircleColor(Color.WHITE);
////            set3.setLineWidth(2f);
////            set3.setCircleRadius(3f);
////            set3.setFillAlpha(65);
////            set3.setFillColor(ColorTemplate.colorWithAlpha(Color.YELLOW, 200));
////            set3.setDrawCircleHole(false);
////            set3.setHighLightColor(Color.rgb(244, 117, 117));
//
//            // create a data object with the datasets
////            LineData data = new LineData(set1, set2, set3);
//            LineData data = new LineData(set1);
//            data.setValueTextColor(Color.WHITE);
//            data.setValueTextSize(9f);
//
//            // set data
//            mChart.setData(data);
//        }
//    }



    @Override
    public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {

    }

    @Override
    public void onNothingSelected() {

    }
    @Override
    public void onTimeSet(TimePickerDialog view, int hourOfDay, int minute, int second) {

    }
}