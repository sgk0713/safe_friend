package com.seoul_app_contest.safe_friend.splash;

import com.seoul_app_contest.safe_friend.UserModel;


public class SplashPresenter implements SplashContract.Presenter {
    SplashContract.View view;
    static public UserModel model;

    public SplashPresenter(SplashContract.View view) {
        this.view = view;
        this.model = new UserModel();
    }

    @Override
    public void checkRemote() {
        model.initRemote(new UserModel.RemoteCallbackListener() {
            @Override
            public void on(String title, String msg) {
                view.showDialog(title, msg);
            }

            @Override
            public void off() {
                checkAutoLogin();
            }
        });
    }

    @Override
    public void checkAutoLogin() {
        if (model.existCurrentUser()){
            model.isProtector(model.getCurrentUserEmail(), new UserModel.IsProtectorCallbackListener() {
                @Override
                public void exist() {
                    model.isCheckState("PROTECTORS", model.getUID(), new UserModel.IsCheckStateCallbackListener() {
                        @Override
                        public void onWait() {
                            view.redirectProtectorMainActivity();
                        }

                        @Override
                        public void onProgress() {
                            view.redirectMapsActivity("follower");
                        }
                    });
                }

                @Override
                public void notExist() {
                    model.isCheckState("USERS", model.getUID(), new UserModel.IsCheckStateCallbackListener() {
                        @Override
                        public void onWait() {
                            view.redirectMainActivity();
                        }

                        @Override
                        public void onProgress() {
                            view.redirectMapsActivity("user");
                        }
                    });
                }
            });
        }else {
            view.redirectLoginActivity();
        }
    }
}
