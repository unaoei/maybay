package maybay.myapplication;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;

public class ListActivity extends AppCompatActivity implements View.OnClickListener
{
    EditText searchEditText;
    Button newButton;
    Button searchButton;
    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        searchEditText = (EditText) findViewById(R.id.searchEditText);
        newButton = (Button) findViewById(R.id.newButton);
        searchButton = (Button) findViewById(R.id.searchButton);
        listView = (ListView) findViewById(R.id.listView);

        DbHelper db = new DbHelper(getApplicationContext());
        List<Customer> customers = db.customerGet();
        CustomerListAdapter adapter = new CustomerListAdapter (getApplicationContext(), customers);
        listView.setAdapter(adapter);

        newButton.setOnClickListener(this);
        searchButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v)
    {
        if (v == newButton)
        {
            Intent i = new Intent(getApplicationContext(), SingupActivity.class);
            startActivityForResult(i, 100);
        }
        else if (v == searchButton)
        {
            DbHelper db = new DbHelper(getApplicationContext());
            List<Customer> customers = db.customersSearch(searchEditText.getText().toString());
            CustomerListAdapter adapter = new CustomerListAdapter(getApplicationContext(), customers);
            listView.setAdapter(adapter);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == 100)
        {
            DbHelper db = new DbHelper(getApplicationContext());
            List<Customer> customer = db.customerGet();
            CustomerListAdapter adapter = new CustomerListAdapter (getApplicationContext(), customer);
            listView.setAdapter(adapter);
        }
    }

    class CustomerViewHolder
    {
        ImageView photoImagerView;
        TextView nameTextView;
        TextView surnameTextView;
        TextView genderTextView;
        TextView emailTextView;
        TextView telephoneTextView;
    }

    class CustomerListAdapter extends BaseAdapter
    {
        List<Customer> customers;
        LayoutInflater layout;

        public CustomerListAdapter(Context context, List<Customer> customer)
        {
            layout = LayoutInflater.from(context);
            this.customers = customers;
        }

        @Override
        public int getCount() {
            return customers.size();
        }

        @Override
        public Object getItem(int position)
        {
            return customers.get(position);
        }

        @Override
        public long getItemId(int position)
        {
            return customers.get(position).Id;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent)
        {
            CustomerViewHolder vh;
            if (convertView == null)
            {
                convertView = layout.inflate(R.layout.item_list,null);

                vh = new CustomerViewHolder();
                vh.photoImagerView = (ImageView) findViewById(R.id.photoImageView);
                vh.nameTextView = (TextView) findViewById(R.id.nameTextView);
                vh.surnameTextView = (TextView) findViewById(R.id.surnameTextView);
                vh.genderTextView = (TextView) findViewById(R.id.genderTextView);
                vh.emailTextView = (TextView) findViewById(R.id.emailEditText);
                vh.telephoneTextView = (TextView) findViewById(R.id.telephoneTextView);

                convertView.setTag(vh);
            }
            else vh = (CustomerViewHolder) convertView.getTag();

            Customer customer = customers.get(position);
            vh.nameTextView.setTag("Name:" +customer.Name);
            vh.nameTextView.setTag("Surname:" +customer.Surname);
            vh.nameTextView.setTag("Gender:" +customer.Gender);
            vh.nameTextView.setTag("Email:" +customer.Email);
            vh.nameTextView.setTag("Telephone:" +customer.Telephone);

            return convertView;
        }
    }
}
