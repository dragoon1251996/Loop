package com.t3h.whiyew.loophabit;

import android.content.Context;
import android.graphics.Color;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hoadu on 09/05/2017.
 */

public class MyAdapter extends BaseAdapter implements View.OnClickListener {
    private ArrayList<NameHabit> arrData;
    private Context context;
    private LayoutInflater inflater;
    private SparseBooleanArray mSelectedItemsIds;
    public ArrayList<Integer> selectedIds;

    public MyAdapter(Context context, ArrayList<NameHabit> arrData) {
        this.context = context;
        this.arrData = arrData;
        inflater=LayoutInflater.from(context);
        mSelectedItemsIds = new SparseBooleanArray();
        selectedIds = new ArrayList<Integer>();
    }

    @Override
    public int getCount() {
        return arrData.size();
    }

    @Override
    public Object getItem(int i) {
        return arrData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }


    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder;
        NameHabit nameHabit=arrData.get(i);
        if(view==null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.item_list,viewGroup,false);
            viewHolder.textView= (TextView) view.findViewById(R.id.txtContent);
            viewHolder.img1= (ImageView) view.findViewById(R.id.img1);
            viewHolder.img2= (ImageView) view.findViewById(R.id.img2);
            viewHolder.img3= (ImageView) view.findViewById(R.id.img3);
            viewHolder.img4= (ImageView) view.findViewById(R.id.img4);
            view.setTag(viewHolder);
        }
        else {
         viewHolder= (ViewHolder) view.getTag();

        }
        viewHolder.textView.setText(nameHabit.getTenThuocTinh().toString());
        viewHolder.img1.setOnClickListener(this);
        viewHolder.img2.setOnClickListener(this);
        viewHolder.img3.setOnClickListener(this);
        viewHolder.img4.setOnClickListener(this);

        if (selectedIds.contains(i)) {
            view.setSelected(true);
            view.setPressed(true);
            view.setBackgroundResource(R.drawable.boder1);
        }
        else
        {
            view.setSelected(false);
            view.setPressed(false);
            view.setBackgroundResource(R.drawable.boder);
        }
        return view;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.img1:
                Toast.makeText(context,"IMG1",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img2:
                Toast.makeText(context,"IMG2",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img3:
                Toast.makeText(context,"IMG3",Toast.LENGTH_SHORT).show();
                break;
            case R.id.img4:
                Toast.makeText(context,"IMG4",Toast.LENGTH_SHORT).show();
                break;

        }
    }

    public void remove(NameHabit object) {
        arrData.remove(object);
        notifyDataSetChanged();
    }

    public List<NameHabit> getWorldPopulation() {
        return arrData;
    }


    public void toggleSelection(int position) {

        selectView(position, !mSelectedItemsIds.get(position));
    }

    public void removeSelection() {
        mSelectedItemsIds = new SparseBooleanArray();
        notifyDataSetChanged();
    }
    public void removeAll(){
        selectedIds=new ArrayList<>();
        notifyDataSetChanged();
  }
  public ArrayList<Integer> getArrayList(){
        return selectedIds;
    }
    public void selectView(int position, boolean value) {
        if (value){
            mSelectedItemsIds.put(position, value);
            selectedIds.add(position);
        }

        else{
           for(int i=0;i<selectedIds.size();i++){
               if(position==selectedIds.get(i)){
                   selectedIds.remove(i);
               }
           }
            mSelectedItemsIds.delete(position);
        }

            notifyDataSetChanged();
    }

    public int getSelectedCount() {
        return mSelectedItemsIds.size();
    }

    public SparseBooleanArray getSelectedIds() {
        return mSelectedItemsIds;
    }

    class ViewHolder{
        TextView textView;
        ImageView img1,img2,img3,img4;
    }
}
