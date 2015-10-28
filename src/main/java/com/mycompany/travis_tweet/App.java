/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.travis_tweet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author mech-user
 * */
import java.util.*;
import java.io.*;
import twitter4j.*;

import java.net.URL;
import java.net.HttpURLConnection;
import java.nio.charset.StandardCharsets;

public class App {
    public static void main(String[] args)throws IOException{
        for (;;) {
            System.out.println("hoge");
            String url    = "https://github.com/k-okada/2015-soft3/pulls?q=is%3Aopen+is%3Apr+sort%3Aupdated-desc";
            System.out.println("fuga");
            String result = query(url);
            System.out.println("piyo");
            int old_idx = 0;
            String write_time = "";
            File file = new File("timestamp.txt");
            BufferedReader br = new BufferedReader(new FileReader(file));
            ArrayList<String> database = new ArrayList<>();
            String str = br.readLine();
            database.add(str);
            Twitter twitter = TwitterFactory.getSingleton();
            while (str != null){
                if (!database.contains(str)){
                    database.add(str);
                }
                str = br.readLine();
            }
            br.close();

            while(true){
                int idx = result.indexOf("updated <time datetime=",old_idx);
                if (idx == -1) {
                System.out.println("htmlの終わりまで検索しました");
                    break;
                }
                old_idx = idx+1;
                String ret = result.substring(idx, idx+1000);
                String time = ret.substring(24,44);
                System.out.println("time:" + time);
                
//                if (str == null){break;}
                System.out.println("str"+str);
//                if (str.equals(time)){
//                    System.out.println("terminated");
//                
//                    break;
//                }
                if(false){}
                else{
                    int idx_url_pr = result.indexOf("/k-okada/2015-soft3/pull", idx-500);
                    String url_pr = "https://github.com" + result.substring(idx_url_pr,idx_url_pr+28);
                    System.out.println(url_pr);
                    String result_pr = query(url_pr);
                    int idx_author = result_pr.indexOf("by");
                    System.out.println(idx_author);
                    int idx_author_end = result_pr.indexOf(" ",idx_author+4);
                    System.out.println(idx_author_end);
                    String author = result_pr.substring(idx_author+3,idx_author_end);
                    System.out.println(author);
                    String result_success = result.substring(idx - 500,idx);
                    System.out.println(result_success);
                    if (result_success.contains("Failure")){
/*                        try{
                        Status status = twitter.updateStatus(author+"さんのpullreqチャレンジが失敗したみたいです。" + time);
                        }catch(TwitterException e) {}*/
                        if (!database.contains(time)){
                            database.add(time);
                        try{
                        Status status = twitter.updateStatus(author+"さんのpullreqチャレンジが失敗したみたいです。" + time);
                        }catch(TwitterException e) {}
                        }
                        System.out.println("Failure");
//                        if (latest_finished_pr_time.length()==0){
//                            latest_finished_pr_time = time;
//                        }
//                        File file_out = new File("timestamp.txt");
//                        BufferedWriter bw = new BufferedWriter(new FileWriter(file_out));
//                        bw.write(latest_time);
//                        bw.newLine();
//                        bw.close();

                    }
                    else if (result_success.contains("Success")){
                        /*try{
                        Status status = twitter.updateStatus(author+"さんのpullreqチャレンジが成功したみたいです。" + time);
                        }catch(TwitterException e) {}*/
                        System.out.println("Success");
                        if (!database.contains(time)){
                            database.add(time);
                        try{
                        Status status = twitter.updateStatus(author+"さんのpullreqチャレンジが成功したみたいです。" + time);
                        }catch(TwitterException e) {}
                        }
//                        if (latest_finished_pr_time.length()==0){
//                            latest_finished_pr_time = time;
//                        }
//                        File file_out = new File("timestamp.txt");
//                        BufferedWriter bw = new BufferedWriter(new FileWriter(file_out));
//                        bw.write(latest_time);
//                        bw.newLine();
//                        bw.close();
                    }
                    else {
//                        latest_time = time;
                    }
                }
            }
            File file_out = new File("timestamp.txt");
            BufferedWriter bw = new BufferedWriter(new FileWriter(file_out,false));
//            if (latest_finished_pr_time.length() == 0) {
            System.out.println(database.get(0));
            for (int i = 0; i < database.size(); i++) {
                bw.write(database.get(i));
                bw.newLine();
            }
//            }
//            else {
//                bw.write(latest_finished_pr_time);
//            }
//            bw.newLine();
            bw.close();

//            try{
//            Status status = twitter.updateStatus("test");
//            }catch(TwitterException e) {}
//            System.out.println("Successfully updated the status to [" + status.getText() + "].");
            try {
                Thread.sleep(1000L*60);
            } catch(InterruptedException e){}
        }
    }

    public static String query(String _url) throws IOException {
        URL url = new URL(_url);
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET"); 
        if(connection.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStreamReader isr = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader reader = new BufferedReader(isr);
            String res = "";
            String line = null;
 
            while((line = reader.readLine()) != null){
                res += line;
            }
            return res.trim();
        }
        return "";
    }
 }