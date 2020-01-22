package com.example.recyclerviewexample.utils;

import android.util.Log;

import com.example.recyclerviewexample.error.exception.ANError;

import java.util.ArrayList;
import java.util.List;

public class UserUtils {

    public static List<User> getUserList() {

        List<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.firstname = "Amit";
        userOne.lastname = "Shekhar";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.firstname = "Manish";
        userTwo.lastname = "Kumar";
        userList.add(userTwo);

        User userThree = new User();
        userThree.firstname = "Sumit";
        userThree.lastname = "Kumar";
        userList.add(userThree);

        return userList;
    }

    public static List<ApiUser> getApiUserList() {

        List<ApiUser> apiUserList = new ArrayList<>();

        ApiUser apiUserOne = new ApiUser();
        apiUserOne.firstname = "Amit";
        apiUserOne.lastname = "Shekhar";
        apiUserList.add(apiUserOne);

        ApiUser apiUserTwo = new ApiUser();
        apiUserTwo.firstname = "Manish";
        apiUserTwo.lastname = "Kumar";
        apiUserList.add(apiUserTwo);

        ApiUser apiUserThree = new ApiUser();
        apiUserThree.firstname = "Sumit";
        apiUserThree.lastname = "Kumar";
        apiUserList.add(apiUserThree);

        return apiUserList;
    }

    public static List<User> convertApiUserListToUserList(List<ApiUser> apiUserList) {

        List<User> userList = new ArrayList<>();

        for (ApiUser apiUser : apiUserList) {
            User user = new User();
            user.firstname = apiUser.firstname;
            user.lastname = apiUser.lastname;
            userList.add(user);
        }

        return userList;
    }

    public static List<ApiUser> convertApiUserListToApiUserList(List<ApiUser> apiUserList) {
        return apiUserList;
    }


    public static List<User> getUserListWhoLovesCricket() {

        List<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.id = 1;
        userOne.firstname = "Amit";
        userOne.lastname = "Shekhar";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.id = 2;
        userTwo.firstname = "Manish";
        userTwo.lastname = "Kumar";
        userList.add(userTwo);

        return userList;
    }


    public static List<User> getUserListWhoLovesFootball() {

        List<User> userList = new ArrayList<>();

        User userOne = new User();
        userOne.id = 1;
        userOne.firstname = "Amit";
        userOne.lastname = "Shekhar";
        userList.add(userOne);

        User userTwo = new User();
        userTwo.id = 3;
        userTwo.firstname = "Sumit";
        userTwo.lastname = "Kumar";
        userList.add(userTwo);

        return userList;
    }


    public static List<User> filterUserWhoLovesBoth(List<User> cricketFans, List<User> footballFans) {
        List<User> userWhoLovesBoth = new ArrayList<User>();
        for (User cricketFan : cricketFans) {
            for (User footballFan : footballFans) {
                if (cricketFan.id == footballFan.id) {
                    userWhoLovesBoth.add(cricketFan);
                }
            }
        }
        return userWhoLovesBoth;
    }

    public static void logError(String TAG, Throwable e) {
        if (e instanceof ANError) {
            ANError anError = (ANError) e;
            if (anError.getErrorCode() != 0) {
                // received ANError from server
                // error.getErrorCode() - the ANError code from server
                // error.getErrorBody() - the ANError body from server
                // error.getErrorDetail() - just a ANError detail
                Log.d(TAG, "onError errorCode : " + anError.getErrorCode());
                Log.d(TAG, "onError errorBody : " + anError.getErrorBody());
                Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
            } else {
                // error.getErrorDetail() : connectionError, parseError, requestCancelledError
                Log.d(TAG, "onError errorDetail : " + anError.getErrorDetail());
            }
        } else {
            Log.d(TAG, "onError errorMessage : " + e.getMessage());
        }
    }

    public static class User {
        public long id;
        public String firstname;
        public String lastname;
        public boolean isFollowing;

        public User() {
        }

        public User(ApiUser apiUser) {
            this.id = apiUser.id;
            this.firstname = apiUser.firstname;
            this.lastname = apiUser.lastname;
        }

        @Override
        public String toString() {
            return "User{" +
                    "id=" + id +
                    ", firstname='" + firstname + '\'' +
                    ", lastname='" + lastname + '\'' +
                    ", isFollowing=" + isFollowing +
                    '}';
        }

        @Override
        public int hashCode() {
            return (int) id + firstname.hashCode() + lastname.hashCode();
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof User) {
                User user = (User) obj;

                return this.id == user.id
                        && this.firstname.equals(user.firstname)
                        && this.lastname.equals(user.lastname);
            }

            return false;
        }
    }

    public static class ApiUser {
        public long id;
        public String firstname;
        public String lastname;

        @Override
        public String toString() {
            return "ApiUser{" +
                    "id=" + id +
                    ", firstname='" + firstname + '\'' +
                    ", lastname='" + lastname + '\'' +
                    '}';
        }
    }

    public static final class ANConstants {
        public static final int MAX_CACHE_SIZE = 10 * 1024 * 1024;
        public static final int UPDATE = 0x01;
        public static final String CACHE_DIR_NAME = "cache_an";
        public static final String CONNECTION_ERROR = "connectionError";
        public static final String RESPONSE_FROM_SERVER_ERROR = "responseFromServerError";
        public static final String REQUEST_CANCELLED_ERROR = "requestCancelledError";
        public static final String PARSE_ERROR = "parseError";
        public static final String PREFETCH = "prefetch";
        public static final String FAST_ANDROID_NETWORKING = "FastAndroidNetworking";
        public static final String USER_AGENT = "User-Agent";
        public static final String SUCCESS = "success";
        public static final String OPTIONS = "OPTIONS";
    }

    /*public static class ParseUtil {

        private static Parser.Factory mParserFactory;

        public static void setParserFactory(Parser.Factory parserFactory) {
            mParserFactory = parserFactory;
        }

        public static Parser.Factory getParserFactory() {
            if (mParserFactory == null) {
                mParserFactory = new GsonParserFactory(new Gson());
            }
            return mParserFactory;
        }

        public static void shutDown() {
            mParserFactory = null;
        }

    }*/
}
