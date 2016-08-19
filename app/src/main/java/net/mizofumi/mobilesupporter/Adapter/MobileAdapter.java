package net.mizofumi.mobilesupporter.Adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.nifty.cloud.mb.core.DoneCallback;
import com.nifty.cloud.mb.core.FetchFileCallback;
import com.nifty.cloud.mb.core.NCMB;
import com.nifty.cloud.mb.core.NCMBAcl;
import com.nifty.cloud.mb.core.NCMBException;
import com.nifty.cloud.mb.core.NCMBFile;

import net.mizofumi.mobilesupporter.Image.GoogleImageSearch;
import net.mizofumi.mobilesupporter.Image.GoogleImageSearchListener;
import net.mizofumi.mobilesupporter.Image.ImageDownload;
import net.mizofumi.mobilesupporter.Image.ImageDownloadListener;
import net.mizofumi.mobilesupporter.Mobile;
import net.mizofumi.mobilesupporter.R;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

/**
 * Created by mizofumi on 16/08/14.
 *
 * RecyvlerViewにセットするAdapter
 *
 */
public class MobileAdapter extends RecyclerView.Adapter<MobileAdapter.ViewHolder> {

    Context context;
    LayoutInflater inflater;
    ArrayList<Mobile> list = new ArrayList<>();

    public MobileAdapter(Context context) {
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    public void addMobile(Mobile mobile){
        list.add(mobile);
        notifyDataSetChanged();
    }
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        //return null;
        View view = inflater.inflate(R.layout.cardview_big,parent,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    /*取得したデータを表示するためにデータをセットするメソッド
     *
     * 画像については、キャッシュの存在を確認。
     * MBaasに保存しているか確認し、してなかった場合はGoogleから取得
     * あとは、MBassに送信しキャッシュの保存。
     */
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.name.setText(list.get(position).getName());
        holder.model.setText(list.get(position).getModel());
        holder.type.setText(list.get(position).getType());

        NCMBFile ncmbFile = new NCMBFile(list.get(position).getModel()+".png");
        ncmbFile.fetchInBackground(new FetchFileCallback() {
            @Override
            public void done(byte[] bytes, NCMBException e) {
                if (e == null) {
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bytes,0,bytes.length);
                    holder.imageView.setImageBitmap(bitmap);
                    Log.d("MobileAdapter","From MBaas:"+list.get(position).getModel());
                } else {
                    Log.d("MobileAdapter","From Google:"+list.get(position).getModel());
                    new GoogleImageSearch(list.get(position).getModel(), new GoogleImageSearchListener() {
                        @Override
                        public void onPreExec() {
                            holder.loadingMessage.setText("Google検索中...");
                        }

                        @Override
                        public void onPostExec(final String imageUrl) {

                            holder.loadingMessage.setText("画像取得中...");
                            new ImageDownload(imageUrl, new ImageDownloadListener() {

                                @Override
                                public void onPostExec(Bitmap bitmap) {
                                    holder.imageView.setImageBitmap(bitmap);
                                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                                    bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                                    NCMBFile ncmbFile = new NCMBFile(list.get(position).getModel() + ".png", byteArrayOutputStream.toByteArray(), new NCMBAcl());
                                    ncmbFile.saveInBackground(new DoneCallback() {
                                        @Override
                                        public void done(NCMBException e) {
                                            if (e == null) {
                                                Log.d("MobileAdapter", "uploaded");
                                            } else {
                                                e.printStackTrace();
                                            }
                                        }
                                    });
                                }

                                @Override
                                public void error(Exception e) {
                                    holder.loadingProgress.setVisibility(View.GONE);
                                    holder.loadingMessage.setText("画像取得失敗...");
                                }
                            }).download(context);

                        }

                        @Override
                        public void onBackground() {

                        }

                        @Override
                        public void error(Exception e) {
                            holder.loadingProgress.setVisibility(View.GONE);
                            holder.loadingMessage.setText("Google検索失敗...");
                        }
                    }).start();
                }
            }
        });


    }

    @Override
    public int getItemCount() {
        return list.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        //TextView text;
        TextView name;
        TextView model;
        TextView type;
        TextView loadingMessage;
        ImageView imageView;
        ProgressBar loadingProgress;

        public ViewHolder(View v) {
            super(v);
            // 2
            //text = (TextView) v.findViewById(R.id.text);
            name = (TextView)v.findViewById(R.id.name);
            model = (TextView)v.findViewById(R.id.model);
            type = (TextView)v.findViewById(R.id.type);
            loadingMessage = (TextView)v.findViewById(R.id.loadingMessage);
            loadingProgress = (ProgressBar)v.findViewById(R.id.loadingProgress);
            imageView = (ImageView)v.findViewById(R.id.imageView);
        }
    }
}
