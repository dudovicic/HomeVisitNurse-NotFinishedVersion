package hr.ferit.dudovicic.homevisitnurse;

/**
 * Created by ACER7 on 9.9.2017.
 */
import java.util.ArrayList;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class CustomAdapter extends ArrayAdapter<Product> {
    int groupid;

    ArrayList<Product> records;

    Context context;



    public CustomAdapter(Context context, int vg, int id, ArrayList<Product>
            records) {

        super(context, vg, id, records);

        this.context = context;

        groupid = vg;

        this.records = records;



    }



    public View getView(int position, View convertView, ViewGroup parent) {



        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View itemView = inflater.inflate(groupid, parent, false);

        TextView textName = (TextView) itemView.findViewById(R.id.pro_name);

        textName.setText(records.get(position).getpName());

        TextView textPrice = (TextView) itemView.findViewById(R.id.pro_uprice);

        textPrice.setText(records.get(position).getuPrice() );



        return itemView;

    }

}
