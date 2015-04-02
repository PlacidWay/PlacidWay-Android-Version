package com.placidway.inc.adapter;

/**
 * Created by erum on 05/11/2014.
 */

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.http.AndroidHttpClient;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.placidway.inc.R;
import com.placidway.inc.modal.MedicalCenterInfo;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.HttpGet;

import java.io.InputStream;
import java.util.ArrayList;

public class MedInfoAdapter extends BaseAdapter
{
    ArrayList<MedicalCenterInfo> a_Medinfo = new ArrayList<MedicalCenterInfo>();

    Activity activity;

    ListView listview;
    LayoutInflater inflater;
    ImageView thumb_image;
    ViewHolder holder;
    Dialog dialog;


    public MedInfoAdapter(Activity activity,ArrayList<MedicalCenterInfo> medArray)
    {

        listview = (ListView) activity.findViewById(R.id.list_search);

        this.activity = activity;
        this.a_Medinfo = medArray;

        // Create global configuration and initialize ImageLoader with this config
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(activity).build();
        ImageLoader.getInstance().init(config);

        inflater = (LayoutInflater) activity
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);

    }
    public int getCount() {
        // TODO Auto-generated method stub
        return a_Medinfo.size();
    }

    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }
    public View getView(int position, View convertView, ViewGroup parent) {

        View vi=convertView;
        if(convertView==null){

            vi = inflater.inflate(R.layout.list, null);
            holder = new ViewHolder();

            holder.rl_main = (RelativeLayout) vi.findViewById(R.id.rl_main);
            holder.tv_title = (TextView)vi.findViewById(R.id.tv_title); // title
            holder.tv_description = (TextView)vi.findViewById(R.id.tv_description); // description

            holder.tv_description.setTag(position);

            holder.tv_description.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // custom dialog
                    final Dialog dialog = new Dialog(v.getContext());
                    dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                    dialog.setContentView(R.layout.dialog_alert);


                    final int pos = listview.getPositionForView(v);
                    // set the custom dialog components - text, image and button
                    TextView tv_title = (TextView) dialog.findViewById(R.id.tv_dialog_title);
                    tv_title.setText(a_Medinfo.get(pos).getTitle().toString());

                    TextView tv_description = (TextView) dialog.findViewById(R.id.tv_dialog_description);
                    tv_description.setText(a_Medinfo.get(pos).getDescription().toString());

                    TextView tv_price = (TextView) dialog.findViewById(R.id.tv_dialog_price);
                    tv_price.setText("");



                    Button dialogButton = (Button) dialog.findViewById(R.id.dialogButtonOK);
                    // if button is clicked, close the custom dialog
                    dialogButton.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            dialog.dismiss();
                        }
                    });
                    if(!dialog.isShowing())
                    {
                        dialog.show();
                    }

                }
            });

            holder.list_image =  (ImageView)vi.findViewById(R.id.list_image); // image
            holder.img_go =(ImageView)vi.findViewById(R.id.img_go); // thumb image
            holder.img_pic =(ImageView)vi.findViewById(R.id.img_pic); // thumb image

            vi.setTag(holder);
        }
        else{

            holder = (ViewHolder)vi.getTag();
        }

        // Setting all values in listview

        holder.list_image =  (ImageView)vi.findViewById(R.id.list_image); // image
        holder.img_go =(ImageView)vi.findViewById(R.id.img_go); // thumb image
        holder.img_pic =(ImageView)vi.findViewById(R.id.img_pic); // thumb image

        if (position % 3 == 2)
        {
            holder.rl_main.setBackground(activity.getResources().getDrawable(R.drawable.list_bg_3));
            holder.img_go.setBackground(activity.getResources().getDrawable(R.drawable.arrow_3));
            holder.list_image.setBackground(activity.getResources().getDrawable(R.drawable.thumb_3));

        }
        else if (position % 3 == 1)
        {
            holder.rl_main.setBackground(activity.getResources().getDrawable(R.drawable.list_bg_2));
            holder.img_go.setBackground(activity.getResources().getDrawable(R.drawable.arrow_2));
            holder.list_image.setBackground(activity.getResources().getDrawable(R.drawable.thumb_2));

        }
        else
        {
            holder.rl_main.setBackground(activity.getResources().getDrawable(R.drawable.list_bg_1));
            holder.img_go.setBackground(activity.getResources().getDrawable(R.drawable.arrow_1));
            holder.list_image.setBackground(activity.getResources().getDrawable(R.drawable.thumb_1));

        }

        holder.tv_title.setText(a_Medinfo.get(position).getTitle().toString()); // title
        holder.tv_description.setText(a_Medinfo.get(position).getDescription().toString()); // description


        ImageLoader.getInstance().displayImage(a_Medinfo.get(position).getImageName(), holder.img_pic);

        //Setting an image
       /* if (holder.list_image != null) {
            new ImageDownloaderTask(holder.list_image).execute(a_Medinfo.get(position).getImageName());
        }

        String uri = "drawable/"+ a_Medinfo.get(position).getImageName();
        int imageResource = vi.getContext().getApplicationContext().getResources().getIdentifier(uri, null, vi.getContext().getApplicationContext().getPackageName());
        Drawable image = vi.getContext().getResources().getDrawable(imageResource);
        holder.list_image.setImageDrawable(image);
*/
        return vi;
    }

    /*
	 *
	 * */
    static class ViewHolder
    {

        TextView tv_title;
        TextView tv_description;
        ImageView list_image;
        ImageView img_go;
        ImageView img_pic;
        RelativeLayout rl_main;

    }
    class ImageDownloaderTask extends AsyncTask<String, Void, Bitmap> {
        private final ImageView imageViewReference;

        public ImageDownloaderTask(ImageView imageView) {
            imageViewReference = imageView;//new WeakReference(imageView);
        }

        @Override
        // Actual download method, run in the task thread
        protected Bitmap doInBackground(String... params) {
            // params comes from the execute() call: params[0] is the url.
            return downloadBitmap(params[0]);
        }

        @Override
        // Once the image is downloaded, associates it to the imageView
        protected void onPostExecute(Bitmap bitmap) {
            if (isCancelled()) {
                bitmap = null;
            }

            if (imageViewReference != null) {
                ImageView imageView = imageViewReference;
                if (imageView != null) {

                    if (bitmap != null) {
                        imageView.setImageBitmap(bitmap);
                    } else {
                        imageView.setImageDrawable(imageView.getContext().getResources()
                                .getDrawable(R.drawable.thumb_1));
                    }
                }

            }
        }

    }
    static Bitmap downloadBitmap(String url) {
        final AndroidHttpClient client = AndroidHttpClient.newInstance("Android");
        final HttpGet getRequest = new HttpGet(url);
        try {
            HttpResponse response = client.execute(getRequest);
            final int statusCode = response.getStatusLine().getStatusCode();
            if (statusCode != HttpStatus.SC_OK) {
                Log.w("ImageDownloader", "Error " + statusCode
                        + " while retrieving bitmap from " + url);
                return null;
            }

            final HttpEntity entity = response.getEntity();
            if (entity != null) {
                InputStream inputStream = null;
                try {
                    inputStream = entity.getContent();
                    final Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                } finally {
                    if (inputStream != null) {
                        inputStream.close();
                    }
                    entity.consumeContent();
                }
            }
        } catch (Exception e) {
            // Could provide a more explicit error message for IOException or
            // IllegalStateException
            getRequest.abort();
            Log.w("ImageDownloader", "Error while retrieving bitmap from " + url);
        } finally {
            if (client != null) {
                client.close();
            }
        }
        return null;
    }

}

