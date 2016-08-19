package net.mizofumi.mobilesupporter.Image;

import android.os.Handler;
import android.os.Looper;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by mizofumi on 16/08/17.
 *
 * Google画像検索において [model] site:kakaku.com　の結果の1つ目の画像のURLを取得するクラスです。
 * ネットワークを使用するので別スレッドで動かします。
 * GoogleImageSearchListenerを使って検索結果を受け取る仕様です。
 */
public class GoogleImageSearch extends Thread{
    String model;
    GoogleImageSearchListener listener;

    public GoogleImageSearch(String model,GoogleImageSearchListener listener){
        this.model = model;
        this.listener = listener;
        listener.onPreExec();
    }

    @Override
    public void run() {
        super.run();

        try {
            Document document = Jsoup.connect("https://www.google.co.jp/search?q="+model+"+site:kakaku.com&tbm=isch")
                    .userAgent("Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_2) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/33.0.1750.152 Safari/537.36")
                    .get();
            Elements rg_meats = document.getElementsByClass("rg_meta");
            Pattern urlPattern = Pattern.compile("(http://|https://){1}[\\w\\.\\-/:\\#\\?\\=\\&\\;\\%\\~\\+]+"
                    , Pattern.CASE_INSENSITIVE);
            ArrayList<String> urls = new ArrayList<>();
            for (Element element : rg_meats){
                final Matcher matcher = urlPattern.matcher(element.text());
                while (matcher.find()){
                    urls.add(matcher.group(0));
                    System.out.println(matcher.group(0));
                    //メインスレッドに戻してあげる
                    new Handler(Looper.getMainLooper()).post(new Runnable() {
                        @Override
                        public void run() {
                            listener.onPostExec(matcher.group(0));
                        }
                    });
                    break;
                }
                break;
            }
        } catch (final IOException e) {
            e.printStackTrace();
            new Handler(Looper.getMainLooper()).post(new Runnable() {
                @Override
                public void run() {
                    listener.error(e);
                }
            });

        }
    }
}
