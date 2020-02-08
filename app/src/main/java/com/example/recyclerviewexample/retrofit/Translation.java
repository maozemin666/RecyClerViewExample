package com.example.recyclerviewexample.retrofit;

import android.util.Log;

public class Translation {
    private static final String TAG = "maomaoTranslation";
    private int status;

    private Content content;


    private static class Content {
        private String from;
        private String to;
        private String vendor;
        private String out;
        private String ciba_use;
        private String ciba_out;
        private int err_no;

        public String getFrom() {
            return from;
        }

        public void setFrom(String from) {
            this.from = from;
        }

        public String getTo() {
            return to;
        }

        public void setTo(String to) {
            this.to = to;
        }

        public String getVendor() {
            return vendor;
        }

        public void setVendor(String vendor) {
            this.vendor = vendor;
        }

        public String getOut() {
            return out;
        }

        public void setOut(String out) {
            this.out = out;
        }

        public String getCiba_use() {
            return ciba_use;
        }

        public void setCiba_use(String ciba_use) {
            this.ciba_use = ciba_use;
        }

        public String getCiba_out() {
            return ciba_out;
        }

        public void setCiba_out(String ciba_out) {
            this.ciba_out = ciba_out;
        }

        public int getErr_no() {
            return err_no;
        }

        public void setErr_no(int err_no) {
            this.err_no = err_no;
        }
    }

    public void show() {
        Log.d(TAG, "status: "+status);
        Log.d(TAG, "content.from: "+content.from);
        Log.d(TAG, "content.to: "+content.to);
        Log.d(TAG, "content.vendor: "+content.vendor);
        Log.d(TAG, "content.out: "+content.out);
        Log.d(TAG, "content.err_no: "+content.err_no);
    }

    public String show1() {
        return content.out;
    }

    public static String getTAG() {
        return TAG;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Content getContent() {
        return content;
    }

    public void setContent(Content content) {
        this.content = content;
    }
}
